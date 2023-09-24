package com.edutrain.busbooking.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.edutrain.busbooking.payment.repository.PaymentRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = PaymentRepository.class)
@EnableEurekaClient
@ComponentScan(basePackages = "com.edutrain.busbooking.payment.controller,com.edutrain.busbooking.payment.model,com.edutrain.busbooking.payment.repository")
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
