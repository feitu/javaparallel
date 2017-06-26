package cn.feitu.javaparallel;

import java.util.Objects;

/**
 * Created by feitu on 17/6/14.
 */
public class UserRunnable implements  Runnable{

    private User user;
    private UserService userService;

    public UserRunnable(User user, UserService userService){
        this.user = user;
        this.userService = userService;
    }
    @Override
    public void run() {
        Objects.requireNonNull(user);
        Objects.requireNonNull(userService);
        userService.doSth(user);
    }
}
