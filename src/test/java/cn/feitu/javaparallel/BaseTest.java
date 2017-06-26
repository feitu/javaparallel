package cn.feitu.javaparallel;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeTest;

/**
 * Created by feitu on 17/6/13.
 */
@ContextConfiguration("/spring/ctx.xml")
public class BaseTest extends AbstractTestNGSpringContextTests {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected UserService userService;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    protected static final int THREAD_NUMBER = 8;
    private static final long NUMBER = 10000;
    private volatile static List<User> USERS = null;

    @BeforeTest
    public void beforeTest() throws Exception {
        springTestContextPrepareTestInstance();
        Path sqlScript = Paths.get(ClassLoader.getSystemResource("scheme.sql").toURI());
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new PathResource(sqlScript));
    }

    protected synchronized List<User> getSomeUsers() {
        if (USERS == null) {
            initUsers();
        }
        return USERS;
    }

    protected List<List<User>> getUserLists(int threadNumber) {
        List<User> users = getSomeUsers();
        List<List<User>> userList = new ArrayList<>();

        for (int i = 0; i < threadNumber; i++) {
            userList.add(new ArrayList<User>());
        }

        for (int i = 0; i < users.size(); i++) {
            userList.get(i % threadNumber).add(users.get(i));
        }
        return userList;
    }

    volatile int counter = 0;
    private synchronized void initUsers() {
        SecureRandom random = new SecureRandom();
        USERS = Collections.synchronizedList(new ArrayList<>());

        Instant start = Instant.now();
        LongStream.range(1, NUMBER+1).parallel().forEach(
                id -> {
                    User user = new User();
                    user.setId(id);
                    jdbcTemplate.update("INSERT INTO user VALUES (?,?,?)", new Object[]{id, random.nextInt(100), 0});
                    USERS.add(user);

                    counter++;
                }
        );

        Instant end = Instant.now();
        logger.info("init users finished, time elapsed : {}", Duration.between(start, end).toMillis());
    }

}
