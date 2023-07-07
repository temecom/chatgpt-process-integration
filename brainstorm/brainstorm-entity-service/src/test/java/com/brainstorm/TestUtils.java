package com.brainstorm;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TestUtils {

    @Bean
    public RandomStringGenerator randomStringGenerator() {
        return new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .build();

    }
    
}
