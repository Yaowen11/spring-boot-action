package com.example.demo.rest;

import com.example.demo.tools.Hash;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author z
 */
@Service
@Log
public class AccessServiceImpl implements AccessService {

    private final RestTemplate restTemplate;

    public AccessServiceImpl() {
        restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(
                List.of(new StringHttpMessageConverter(StandardCharsets.UTF_8), new FormHttpMessageConverter()));
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> userPass) {
        final String url = "http://172.16.15.200/UsersController/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("userid", userPass.get("username"));
        parameter.add("password", userPass.get("password"));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameter, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        List<String> cookies = responseEntity.getHeaders().get(HttpHeaders.SET_COOKIE);
        log.info("cookies: " + cookies);
        log.info("body: " + responseEntity.getBody());
        log.info("status code: " + responseEntity.getStatusCode());
        log.info("status code value: " + responseEntity.getStatusCodeValue());
        return responseEntity;
    }

    @Override
    public void access(Map<String, String> userPass, HttpServletResponse httpServletResponse) {
        final String accessUrl = "http://172.16.15.200/UsersController/linkToReport?ckey=6ddfc9eceaa119d2607c5a04aa113eaf&staffOaid=00292407";
        final String key = "http://www.scjwt.com/";
        StringBuilder stringBuilder = new StringBuilder("http://172.16.15.200/UsersController/linkToReport?")
                .append("ckey=")
                .append(Hash.hash(Hash.hash(userPass.get("username") + key, "MD5"), "MD5"))
                .append("&staffOaid=")
                .append(userPass.get("username"));
        log.info("url: " + stringBuilder.toString());
        log.info("access: " + accessUrl);
        log.info("url == access: " + accessUrl.equals(stringBuilder.toString()));
        try {
            httpServletResponse.sendRedirect(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] singleCookieKeyValue(String cookie) {
        final String split = ";";
        final String keyValueSplit = "=";
        String key = cookie.substring(0, cookie.indexOf(keyValueSplit));
        String value = cookie.substring(cookie.indexOf(keyValueSplit) + 1);
        if (cookie.contains(split)) {
            value = cookie.substring(
                    cookie.indexOf(keyValueSplit) + 1,
                    cookie.length() - (cookie.length() - cookie.indexOf(split)));
        }
        return new String[]{key, value};
    }


}
