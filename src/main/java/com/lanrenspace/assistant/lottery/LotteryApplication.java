package com.lanrenspace.assistant.lottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LotteryApplication {


	public static void main(String[] args) {
		SpringApplication.run(LotteryApplication.class, args);
	}

}
