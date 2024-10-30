package com.nithack.insuranceServiceApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableFeignClients
@PropertySource(value = "file:.env", ignoreResourceNotFound = true)
public class InsuranceServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceServiceApiApplication.class, args);
	}

}
