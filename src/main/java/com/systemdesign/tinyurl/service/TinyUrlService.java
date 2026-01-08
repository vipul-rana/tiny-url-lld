package com.systemdesign.tinyurl.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

public interface TinyUrlService {

    Map<String, String> generateTinyUrl(Map<String, String> longUrl);

    RedirectView redirectToOrigUrl(String shortUrl);
}
