package com.errabi.openapi;

import com.errabi.openapi.services.ServerService;
import com.errabi.openapi.services.ServerVariableService;
import com.errabi.openapi.web.model.ServerModel;
import com.errabi.openapi.web.model.ServerVariableModel;
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
public class OpenApiAuiApplication implements ApplicationRunner {
	@Autowired
	private ServerService serverService;
	@Autowired
	private ServerVariableService serverVariableService;

	public static void main(String[] args) {
		SpringApplication.run(OpenApiAuiApplication.class, args);
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
