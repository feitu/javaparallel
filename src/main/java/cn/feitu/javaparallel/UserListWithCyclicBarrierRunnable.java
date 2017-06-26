package cn.feitu.javaparallel;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by feitu on 17/6/14.
 */
public class UserListWithCyclicBarrierRunnable extends UserListRunnable {

	private CyclicBarrier barrier;
    public UserListWithCyclicBarrierRunnable(List<User> users, UserService userService, CyclicBarrier barrier) {
        super(users, userService);
        this.barrier = barrier;
    }

    @Override
    public void run() {
        super.run();
        try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			logger.error("error...", e);
		}
    }
}
