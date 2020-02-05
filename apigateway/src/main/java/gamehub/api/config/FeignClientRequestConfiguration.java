package gamehub.api.config;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.exception.HystrixBadRequestException;

import feign.codec.ErrorDecoder;

@Configuration
public class FeignClientRequestConfiguration {

	@Bean
	public ErrorDecoder errorDecoder() {
		return (methodKey, response) -> {
			try {
                return new HystrixBadRequestException(IOUtils.toString(response.body().asReader()));
            } catch (Exception ex) {
            	ex.printStackTrace();
            	return new RuntimeException(String.format(response.toString()));
            }
		};
	}
}
