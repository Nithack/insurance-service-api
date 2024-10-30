package com.nithack.insuranceServiceApi;

import org.springframework.boot.SpringApplication;

public class TestInsuranceServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(InsuranceServiceApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
