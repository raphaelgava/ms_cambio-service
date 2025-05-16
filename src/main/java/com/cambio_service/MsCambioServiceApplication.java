package com.cambio_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient // Registra a aplicação no servidor de descoberta e permite que a aplicação encontre outros serviços através do nome, facilitando a comunicação entre microserviços.
public class MsCambioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCambioServiceApplication.class, args);
	}

}
