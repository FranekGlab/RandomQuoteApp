package com.example.randomquoteapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class RandomQuoteApp {

	private final QuoteService quoteService;

	public static void main(String[] args) {
		SpringApplication.run(RandomQuoteApp.class, args);
	}

	@EventListener(ApplicationStartedEvent.class)
	public void makeAllRequests() {
		log.info(quoteService.getQuoteByRequestParam(3));
		log.info(quoteService.getQuotesByRequestHeaders("requestId", "quotes-extended"));
		quoteService.postQuote("Btw i use arch");
		quoteService.deleteQuote(13);
	}

}