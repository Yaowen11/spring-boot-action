package com.example.demo.rest;

import lombok.extern.java.Log;
import org.apache.tika.parser.txt.CharsetDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zyw
 * @date 2020/7/1 20:31
 */
@Controller
@Log
public class SiteAccessController {

    private final AccessService accessService;

    @Autowired
    public SiteAccessController(AccessService accessService) {
        this.accessService = accessService;
    }

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

    @GetMapping("/access/attendance")
    public void redirect(HttpServletResponse response) {
        Map<String, String> userPass = new HashMap<>();
        userPass.put("username", "00292407");
        userPass.put("password", "4a8a21097cc2073a37d2c7433dbbf5f9");
        accessService.access(userPass, response);
    }
}
