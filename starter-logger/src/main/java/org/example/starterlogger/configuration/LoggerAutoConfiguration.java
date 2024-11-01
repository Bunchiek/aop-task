package org.example.starterlogger.configuration;

import org.example.starterlogger.aop.AnnotationLoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LoggerAutoConfiguration {

    @Bean
    public AnnotationLoggingAspect getLogger() {
        return new AnnotationLoggingAspect();
    }

}
