package com.example.demo.transaction;

/**
 * @author zyw
 * @date 2020/7/5 12:40
 */
public interface ProfileRepository {
    Profile getOne(int id);

    void save(Profile profile);
}
