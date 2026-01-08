package com.systemdesign.tinyurl.service.impl;

import ch.qos.logback.core.util.MD5Util;
import com.systemdesign.tinyurl.models.UrlMapping;
import com.systemdesign.tinyurl.repository.TinyUrlRepository;
import com.systemdesign.tinyurl.service.TinyUrlService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.view.RedirectView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TinyUrlServiceImpl implements TinyUrlService {

    private final TinyUrlRepository tinyUrlRepository;

    @Value("${tiny-base-url}")
    private String baseUrl;

    @SneakyThrows
    @Override
    public Map<String, String> generateTinyUrl(Map<String, String> longUrlMap) {
        if (!longUrlMap.containsKey("longUrl"))
            throw new RuntimeException("Please provide URL");

        String longUrl = longUrlMap.get("longUrl");
        String shortUrl = encryptString(longUrl);

        UrlMapping urlMapping = UrlMapping.
                builder()
                .longUrl(longUrl)
                .shortUrl(shortUrl)
                .build();

        tinyUrlRepository.save(urlMapping);
        return Map.of("shortUrl", baseUrl + shortUrl);
    }

    @Override
    public RedirectView redirectToOrigUrl(String shortUrl) {
        Optional<UrlMapping> optionalUrlMapping = tinyUrlRepository.getByShortUrl(shortUrl);
        if (optionalUrlMapping.isEmpty()) {
            throw new RuntimeException("URL not found!!");
        }
        String longUrl = optionalUrlMapping.get().getLongUrl();
        return new RedirectView(longUrl);
    }

    private String encryptString(String longUrl) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return DigestUtils.md5DigestAsHex(md.digest(longUrl.getBytes()));
    }
}
