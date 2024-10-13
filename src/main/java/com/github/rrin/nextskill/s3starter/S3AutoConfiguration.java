package com.github.rrin.nextskill.s3starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(S3Properties.class)
public class S3AutoConfiguration {
    @Bean
    @ConditionalOnProperty(name = "ns.aws.s3.enabled", havingValue = "true")
    public S3Service s3Service(S3Properties s3Properties) {
        return new S3Service(s3Properties);
    }
}
