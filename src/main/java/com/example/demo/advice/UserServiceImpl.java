package com.example.demo.advice;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

/**
 * @author zyw
 * @date 2020/7/6 22:33
 */
@Service
@Log
public class UserServiceImpl implements UserService {
    @Override
    public void sayHi() throws NullPointerException {
        if (System.currentTimeMillis() % 2 == 0) {
            throw new NullPointerException();
        }
        log.info("hi!");
    }
}
