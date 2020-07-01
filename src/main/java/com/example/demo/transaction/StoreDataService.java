package com.example.demo.transaction;

/**
 * @author zyw
 * @date 2020/6/28 21:26
 */
public interface StoreDataService {
    /**
     * index insert
     * @param profile profile
     * @return id
     */
    int indexInsert(Profile profile);

    /**
     * name param insert
     * @param profile
     * @return id
     */
    int nameParamInsert(Profile profile);

    /**
     * edit transaction insert
     * @param profile profile
     * @return id
     */
    int editTransactionInsert(Profile profile);

    /**
     * annotation transaction insert
     * @param profile profile
     * @return id
     */
    int annotationTransactionInsert(Profile profile);
}
