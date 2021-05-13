package com.pii.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.pii.app"})
public class PieEyeAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PieEyeAssignmentApplication.class, args);
	}

}
