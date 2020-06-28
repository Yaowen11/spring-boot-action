package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zyw
 * @date 2020/6/28 21:53
 */
@SpringBootTest
public class TransactionTest {

    private final StoreDataService storeDataService;

    @Autowired
    public TransactionTest(StoreDataService storeDataService) {
        this.storeDataService = storeDataService;
    }

    @Test
    public void testTransaction() {
        Profile profile = new Profile();
        profile.setFirstName("jim");
        profile.setLastName("green");
        profile.setPassword("123456");
        int insertCount = storeDataService.insert(profile);
        assert insertCount == 0;
    }
}
