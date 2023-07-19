package org.example.config;

import org.example.dao.entity.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Book book(){return new Book();}
}
