package com.example.randomquoteapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class RandomQuoteApp {

	private final QuoteService quoteService;

	public static void main(String[] args) {
		SpringApplication.run(RandomQuoteApp.class, args);
	}
	public void run() throws JsonProcessingException {
		quoteService.makeAllRequests();

	}

}