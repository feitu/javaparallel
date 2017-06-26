package cn.feitu.javaparallel;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by feitu on 17/6/14.
 */
public class UserListRunnable  implements Runnable{
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private List<User> users;
    private UserService userService;

    public UserListRunnable(List<User> users, UserService userService){
        this.users = users;
        this.userService = userService;
    }
    @Override
    public void run() {

        Objects.requireNonNull(users);
        Objects.requireNonNull(userService);

        for(User u : users){
            userService.doSth(u);
        }
    }
}
