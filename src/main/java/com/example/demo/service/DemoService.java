package com.example.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class DemoService {

	
	int i=3;
	
	
	public String invokeCallWithCB() {
		i++;
		System.out.println(i);
		
		if(i%2 ==0 )
			throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
		
	
		return "Call->Service";
	}

}
