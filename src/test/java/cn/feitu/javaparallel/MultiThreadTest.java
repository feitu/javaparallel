package cn.feitu.javaparallel;

import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadTest extends BaseTest{

	@Test
	public void test() throws InterruptedException {

        List<List<User>> userList = getUserLists(THREAD_NUMBER);
        List<Thread> threads = new ArrayList<>();

        Instant start = Instant.now();
        for(List list : userList){
            Thread t = new Thread(new UserListRunnable(list, userService));
            threads.add(t);
        }

        for(Thread t : threads){
            t.start();
        }
        for(Thread t : threads){
            t.join();
        }

        Instant end = Instant.now();
        logger.info("time elapsed : {}", Duration.between(start ,end).toMillis());;
    }

}
