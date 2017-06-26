package cn.feitu.javaparallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.RecursiveAction;

public class UserRecursiveAction extends RecursiveAction{

    private final Logger logger = LoggerFactory.getLogger(UserRecursiveAction.class);
	private static final int SIZE = 10;
	private List<User> users;
	private UserService userService;
	public UserRecursiveAction(List<User> users, UserService userService ) {
		Objects.requireNonNull(users);
		Objects.requireNonNull(userService);
		this.users = users;
		this.userService = userService;
	}
	@Override
	protected void compute() {
		if(users.size() > SIZE){
		    int half = users.size() / 2;
			invokeAll(
					new UserRecursiveAction(users.subList(0, half), userService),
					new UserRecursiveAction(users.subList(half, users.size()), userService));
			return;
		}
		
		for(User u : users){
            userService.doSth(u);
		}
	}

}
