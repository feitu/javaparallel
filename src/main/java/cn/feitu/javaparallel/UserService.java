package cn.feitu.javaparallel;

import java.util.Objects;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by feitu on 17/6/13.
 */
public class UserService {

    private JdbcTemplate jdbcTemplate;

    private static final Random RANDOM = new Random();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public void doSth(User user){

        if(user == null){
            logger.error("user is null.");
        }
        long start = System.currentTimeMillis();
        String readSql = "SELECT value FROM user where id= ?";
        int value = jdbcTemplate.queryForObject(readSql, new Object[]{user.getId()}, Integer.class);

        String counterSql = "SELECT counter FROM user where id= ?";
        int counter = jdbcTemplate.queryForObject(counterSql, new Object[]{user.getId()}, Integer.class);

        String writeSql = "UPDATE user SET value = ? , counter = ? WHERE id= ?";
        jdbcTemplate.update(writeSql, new Object[]{value + RANDOM.nextInt(100) , ++counter, user.getId()});

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
