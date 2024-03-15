package com.skcc.egovcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.Banner.Mode.OFF;

@SpringBootApplication
public class EgovCoreApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(EgovCoreApplication.class);
		// 스프링부트 웰컴 메시지 관리
		application.setBannerMode(OFF);
		application.run(args);
	}

}
