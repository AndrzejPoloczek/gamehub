package gamehub.gamebind.config;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.exception.HystrixBadRequestException;

import feign.codec.ErrorDecoder;

import java.util.Objects;

@Configuration
public class FeignClientRequestConfiguration {

    private static final Logger LOG = LogManager.getLogger(FeignClientRequestConfiguration.class);

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            try {
                if (Objects.nonNull(response.body())) {
                    return new HystrixBadRequestException(IOUtils.toString(response.body().asReader()));
                } else {
                    return new HystrixBadRequestException("Unable to get response body");
                }
            } catch (Exception ex) {
                LOG.error("Unexpected exception found", ex);
                return new RuntimeException(String.format(response.toString()));
            }
        };
    }
}