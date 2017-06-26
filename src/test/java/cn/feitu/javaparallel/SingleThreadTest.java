package cn.feitu.javaparallel;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.testng.annotations.Test;

/**
 * Created by feitu on 17/6/13.
 */
public class SingleThreadTest extends BaseTest{

    @Test
    public void test(){

		Instant start = Instant.now();
    	List<User> users = getSomeUsers();
    	for(User user : users){
    		userService.doSth(user);
    	}
		Instant end = Instant.now();
		logger.info("time elapsed : {}", Duration.between(start ,end).toMillis());
    }
}
