package com.bonaparte;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by yangmingquan on 2018/7/11.
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class Weimarer implements CommandLineRunner{
    public static void main(String[] args){
        SpringApplication.run(Weimarer.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

    }
}
