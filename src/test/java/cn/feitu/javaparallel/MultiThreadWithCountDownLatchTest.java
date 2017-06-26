package cn.feitu.javaparallel;

import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by feitu on 17/6/14.
 */
public class MultiThreadWithCountDownLatchTest extends BaseTest{

    @Test
    public void test(){
        List<List<User>>  userList = getUserLists(THREAD_NUMBER);
        List<Thread> threads = new ArrayList<>();
        CountDownLatch countDown = new CountDownLatch(THREAD_NUMBER);

        Instant start = Instant.now();
        for(List list : userList){
            Thread t  = new Thread(new UserListWithCountDownLatchRunnable(list, userService, countDown));
            threads.add(t);
        }

        for(Thread t : threads){
            t.start();
        }
        try {
            countDown.await();
        } catch (InterruptedException e) {
            logger.error("error...", e);
        }
        Instant end = Instant.now();
        logger.info("time elapsed : {}", Duration.between(start ,end).toMillis());
    }

}
