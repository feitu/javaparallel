package cn.feitu.javaparallel;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.testng.annotations.Test;

public class ParallelStreamTest extends BaseTest {

	@Test
	public void test(){
		List<User> users = getSomeUsers();
		Instant start = Instant.now();
		users.parallelStream().forEach(
				u -> userService.doSth(u)
				);
		Instant end = Instant.now();
		logger.info("time elapsed : {}", Duration.between(start ,end).toMillis());
	}
}
