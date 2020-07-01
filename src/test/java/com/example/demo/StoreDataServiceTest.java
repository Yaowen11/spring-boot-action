package com.example.demo;

import com.example.demo.transaction.Profile;
import com.example.demo.transaction.StoreDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author zyw
 * @date 2020/6/28 21:53
 */
@SpringBootTest
public class StoreDataServiceTest {

    private final StoreDataService storeDataService;

    private final JdbcTemplate jdbcTemplate;

    private final Profile profile = new Profile();

    @Autowired
    public StoreDataServiceTest(StoreDataService storeDataService, JdbcTemplate jdbcTemplate) {
        this.storeDataService = storeDataService;
        this.jdbcTemplate = jdbcTemplate;
        profile.setFirstName("Shirley");
        profile.setLastName("yang");
        profile.setPassword("123456");
    }

    private int currentTableMaxId() {
        try {
            final String sql = "select max(id) from profile";
            return jdbcTemplate.queryForObject(sql, int.class);
        } catch (NullPointerException ignoreException) {
            return 0;
        }
    }

    @Test
    public void testIndexInsertTest() {
        int currentMaxId = currentTableMaxId();
        System.out.println("index current max id: " + currentMaxId);
        int indexInsertId = storeDataService.indexInsert(profile);
        System.out.println("index insert id: " + indexInsertId);
        assert indexInsertId == currentMaxId + 1;
    }

    @Test
    public void testNameParamInsert() {
        int currentMaxId = currentTableMaxId();
        System.out.println("name current max id: " + currentMaxId);
        int nameParamInsertId = storeDataService.nameParamInsert(profile);
        System.out.println("name param insert id: " + nameParamInsertId);
        assert nameParamInsertId == currentMaxId + 1;
    }

    @Test
    public void testEditTransactionInsert() {
        int currentMaxId = currentTableMaxId();
        System.out.println("edit transaction current max id: " + currentMaxId);
        int transactionInsertId = storeDataService.editTransactionInsert(profile);
        System.out.println("edit transaction id: " + transactionInsertId);
        assert transactionInsertId == currentMaxId + 1;
    }

    @Test
    public void annotationTransactionInsert() {
        int currentMaxId = currentTableMaxId();
        System.out.println("annotation transaction current max id: " + currentMaxId);
        int transactionId = storeDataService.annotationTransactionInsert(profile);
        System.out.println("annotation id: " + transactionId);
        assert transactionId == currentMaxId + 1;
    }
}
