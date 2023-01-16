package com.zzq.flowableui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"org.flowable.ui.application","com.zzq.flowableui"})
@SpringBootApplication
public class FlowableUiAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowableUiAppApplication.class, args);
	}

}
