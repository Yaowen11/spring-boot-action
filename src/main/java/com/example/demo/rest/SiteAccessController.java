package com.example.demo.rest;

import lombok.extern.java.Log;
import org.apache.tika.parser.txt.CharsetDetector;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author zyw
 * @date 2020/7/1 20:31
 */
@Controller
@Log
public class SiteAccessController {

    @GetMapping("/access/{site}")
    @ResponseBody
    public String access(@PathVariable String site) throws IOException {
        String webSite = "https://www." + site + ".com";
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        RestTemplate rest = new RestTemplateBuilder().additionalMessageConverters(stringHttpMessageConverter).build();
        String response = rest.getForObject(webSite, String.class);
        CharsetDetector detector = new CharsetDetector();
        detector.setText(response.getBytes());
        log.info("response: " + response);
        log.info("charset: " + detector.detect().getName());
        return response;
    }
}
