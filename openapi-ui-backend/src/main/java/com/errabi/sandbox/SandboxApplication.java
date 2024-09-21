package com.errabi.sandbox;

import com.errabi.sandbox.services.ServerService;
import com.errabi.sandbox.services.ServerVariableService;
import com.errabi.sandbox.web.model.ServerModel;
import com.errabi.sandbox.web.model.ServerVariableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Set;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
public class SandboxApplication implements ApplicationRunner {
	@Autowired
	private ServerService serverService;
	@Autowired
	private ServerVariableService serverVariableService;

	public static void main(String[] args) {
		SpringApplication.run(SandboxApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		ServerVariableModel serverVariableModel = ServerVariableModel.builder()
				.name("name")
				.description("description")
				.defaultValue("defaultValue")
				.value("value")
				.build();
		ServerModel serverModel = ServerModel.builder()
				.url("url")
				.description("description")
				.variables(Set.of(serverVariableModel))
				.build();
		serverService.save(serverModel);

		serverVariableService.findAll().forEach(System.out::println);
	}
}
