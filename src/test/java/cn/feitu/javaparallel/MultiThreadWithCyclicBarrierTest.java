package cn.feitu.javaparallel;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.testng.annotations.Test;

public class MultiThreadWithCyclicBarrierTest extends BaseTest{

	@Test
	public void test(){
		
		List<List<User>>  userList = getUserLists(THREAD_NUMBER);
        List<Thread> threads = new ArrayList<>();
        
        CyclicBarrier barrier = new CyclicBarrier(THREAD_NUMBER + 1);
        Instant start = Instant.now();;
        
        for(List list : userList){
        	Thread t = new Thread(new UserListWithCyclicBarrierRunnable(list, userService, barrier));
            threads.add(t);
        }

        for(Thread t : threads){
            t.start();
        }
        try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			logger.error("error...", e);
		}
        Instant end = Instant.now();
        logger.info("time elapsed : {}", Duration.between(start ,end).toMillis());
	}
}
