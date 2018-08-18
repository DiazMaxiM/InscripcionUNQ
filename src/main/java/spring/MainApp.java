package spring;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.config.AppConfig;
import spring.entity.User;
import spring.service.UserService;

/**
 * @author imssbora
 *
 */
public class MainApp {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		UserService userService = context.getBean(UserService.class);

		// Add Users
		userService.add(new User("Sunil", "Bora", "suni.bora@example.com"));
		userService.add(new User("David", "Miller", "david.miller@example.com"));
		userService.add(new User("Sameer", "Singh", "sameer.singh@example.com"));
		userService.add(new User("Paul", "Smith", "paul.smith@example.com"));

		// Get Users
		List<User> users = userService.listUsers();
		for (User user : users) {
			System.out.println("Id = " + user.getId());

		}

		context.close();
	}
}