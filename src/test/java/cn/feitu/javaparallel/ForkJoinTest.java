package cn.feitu.javaparallel;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

public class ForkJoinTest extends BaseTest{

	@Test
	public void test(){
		List<User> users = getSomeUsers();

		Instant start = Instant.now();
		UserRecursiveAction action = new UserRecursiveAction(users, userService);
		ForkJoinPool pool = new ForkJoinPool();
		pool.execute(action);
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.error("error...", e);
		}

		Instant end = Instant.now();
		logger.info("time elapsed: {}", Duration.between(start, end).toMillis());
	}
}
