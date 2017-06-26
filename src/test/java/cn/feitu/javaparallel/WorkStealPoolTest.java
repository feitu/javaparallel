package cn.feitu.javaparallel;

import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by feitu on 17/6/16.
 */
public class WorkStealPoolTest extends  BaseTest {

    @Test
    public void test(){

        List<User> users = getSomeUsers();
        ExecutorService service = Executors.newWorkStealingPool(THREAD_NUMBER);

        Instant start = Instant.now();
        for (User user : users) {
            service.submit(new UserRunnable(user, userService));
        }
        service.shutdown();
        try {
            service.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("error... ", e);
        }
        Instant end = Instant.now();
        logger.info("time elapsed: {}", Duration.between(start, end).toMillis());
    }
}
