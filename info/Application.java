package myservice.info;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import myservice.info.Model.User;

@SpringBootApplication
public class Application{
	public User user;
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
	
}
