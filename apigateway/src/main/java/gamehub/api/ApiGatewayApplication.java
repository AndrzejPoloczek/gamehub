package gamehub.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
