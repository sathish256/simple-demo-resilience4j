package com.example.demo.controller;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DemoService;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;

@RestController
@RequestMapping("/v1/cb-demo")
public class DemoController {
	
	@Autowired
	DemoService demoService;
	
	
	@GetMapping
	public String invokeCostBasisService() {
		
		// Create a CircuitBreaker (use default configuration)
		CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendName");
		// Decorate your call to BackendService.doSomething() with a CircuitBreaker
		Supplier<String> decoratedSupplier = CircuitBreaker
		    .decorateSupplier(circuitBreaker, demoService::invokeCallWithCB);
		

		// Execute the decorated supplier and recover from any exception

		// When you don't want to decorate your lambda expression,
		// but just execute it and protect the call by a CircuitBreaker.
		return Try.ofSupplier(decoratedSupplier)
			    .recover(throwable -> "Hello from Recovery").get();
		
	}

}
