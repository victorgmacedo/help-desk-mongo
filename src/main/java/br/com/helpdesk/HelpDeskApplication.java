package br.com.helpdesk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.helpdesk.api.entity.User;
import br.com.helpdesk.api.enums.ProfileEnum;
import br.com.helpdesk.api.exception.NotFoundException;
import br.com.helpdesk.api.services.UserService;

@SpringBootApplication
public class HelpDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpDeskApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserService userService, PasswordEncoder passwordEncoder){
		return args -> {
			initUser(userService, passwordEncoder);
		};
	}

	private void initUser(UserService userService, PasswordEncoder passwordEncoder){
		User admin = new User();
		admin.setEmail("admin@helpdesk.com");
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setProfile(ProfileEnum.ROLE_ADMIN);

		try{
			userService.findByEmail("admin@helpdesk.com");
		}catch (NotFoundException ex){
			userService.createOrUpdate(admin);
		}
	}
}
