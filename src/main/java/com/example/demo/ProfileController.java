package com.example.demo;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zyw
 * @date 2020/6/28 21:26
 */
@RestController
@Log
public class ProfileController {

    private final StoreDataService storeDataService;

    @Autowired
    public ProfileController(StoreDataService storeDataService) {
        this.storeDataService = storeDataService;
    }

    @PostMapping("/store")
    public Profile store(@RequestBody Profile profile) {
        int indexInsertId = storeDataService.indexInsert(profile);
        log.info("index insert id: " + indexInsertId);
        return profile;
    }
}
