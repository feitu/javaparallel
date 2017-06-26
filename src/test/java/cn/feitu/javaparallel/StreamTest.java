package cn.feitu.javaparallel;

import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Created by feitu on 17/6/16.
 */
public class StreamTest extends BaseTest{

    @Test
    public void test(){
        List<User> users = getSomeUsers();

        Instant start = Instant.now();
        users.stream().forEach(
                u -> userService.doSth(u)
        );
        Instant end = Instant.now();
        logger.info("time elapsed : {}", Duration.between(start ,end).toMillis());
    }
}
