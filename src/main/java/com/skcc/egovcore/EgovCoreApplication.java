package com.skcc.egovcore;

import com.skcc.egovcore.core.netty.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.springframework.boot.Banner.Mode.OFF;

@SpringBootApplication
@EnableAsync
@Slf4j
public class EgovCoreApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(EgovCoreApplication.class);
		// 스프링부트 웰컴 메시지 관리
		application.setBannerMode(OFF);
		application.run(args);
	}


	@Bean
	public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener(NettyServer nettyServer
	) {
		return new ApplicationListener<ApplicationReadyEvent>() {
			@Override
			public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//				tcpServer.start();
				nettyServer.start();
			}
		};
	}

}
