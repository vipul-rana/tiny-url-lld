package com.systemdesign.tinyurl.controller;

import com.systemdesign.tinyurl.service.TinyUrlService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TinyUrlController {

    private final TinyUrlService tinyUrlService;

    @PostMapping(value = "/shorten", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> generateUrl(@RequestBody Map<String, String> longUrlBody) {
        return tinyUrlService.generateTinyUrl(longUrlBody);
    }

    @GetMapping(value = "/{shortUrl}")
    public RedirectView redirectUrl(@PathVariable String shortUrl) {
        return tinyUrlService.redirectToOrigUrl(shortUrl);
    }
}
