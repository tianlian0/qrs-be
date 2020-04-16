package com.bjtu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bjtu.service.HttpUtil;
import com.bjtu.service.TulingCommunicationByVoice;

@SpringBootApplication
public class ApplicationEntryPoint {
	
	public static String uuid = "600394";

	public static void main(String[] args) {
		if (args.length == 1) {
			uuid = args[0];
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		HttpUtil.trustAllHosts();
		SpringApplication.run(ApplicationEntryPoint.class, args);
	}

}
