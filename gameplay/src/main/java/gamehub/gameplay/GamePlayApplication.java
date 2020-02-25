package gamehub.gameplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties
public class GamePlayApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GamePlayApplication.class, args);
	}
}