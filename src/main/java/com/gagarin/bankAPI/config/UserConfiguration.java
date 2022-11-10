package com.gagarin.bankAPI.config;

import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class UserConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            userRepository.saveAll(List.of(
                    new User("Aleks", BigDecimal.valueOf(1000)),
                    new User("Ben", BigDecimal.valueOf(2000)),
                    new User("Sveta", BigDecimal.valueOf(3000)),
                    new User("Kola", BigDecimal.valueOf(4000)),
                    new User("Oly", BigDecimal.valueOf(5000))
            ));
        };
    }

}
