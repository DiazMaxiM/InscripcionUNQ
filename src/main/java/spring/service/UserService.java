package spring.service;

import java.util.List;

import spring.entity.User;

public interface UserService {
	void add(User user);

	List<User> listUsers();
}
