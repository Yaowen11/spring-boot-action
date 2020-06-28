package com.example.demo;

/**
 * @author zyw
 * @date 2020/6/28 21:26
 */
public interface StoreDataService {
    /**
     * transaction insert
     * @param profile profile
     * @return id
     */
    int insert(Profile profile);
}
