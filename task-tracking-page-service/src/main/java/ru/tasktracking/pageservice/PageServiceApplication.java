package ru.tasktracking.pageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PageServiceApplication.class, args);
	}
}
