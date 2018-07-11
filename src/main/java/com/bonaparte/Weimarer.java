package com.bonaparte;

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
public class Weimarer {
}
