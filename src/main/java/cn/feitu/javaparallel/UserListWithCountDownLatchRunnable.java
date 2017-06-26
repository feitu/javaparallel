package cn.feitu.javaparallel;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by feitu on 17/6/14.
 */
public class UserListWithCountDownLatchRunnable extends UserListRunnable {

    private CountDownLatch countDown;

    public UserListWithCountDownLatchRunnable(List<User> users, UserService userService, CountDownLatch countDown) {
        super(users, userService);
        this.countDown = countDown;
    }

    @Override
    public void run() {
        super.run();
        countDown.countDown();
    }
}
