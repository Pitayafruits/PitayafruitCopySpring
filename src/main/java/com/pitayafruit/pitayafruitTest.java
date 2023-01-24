package com.pitayafruit;

import com.spring.AppConfig;
import com.spring.pitayafruitApplicationContext;

public class pitayafruitTest {

    public static void main(String[] args) {
        pitayafruitApplicationContext applicationContext = new pitayafruitApplicationContext(AppConfig.class);
        Object userService = applicationContext.getBean("UserService");
    }
}
