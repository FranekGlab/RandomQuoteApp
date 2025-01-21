package com.example.randomquoteapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuoteService {

    private final QuoteServerProxy quoteServerProxy;

    @EventListener(ApplicationStartedEvent.class)
    public void makeAllRequests() {
        String postResponse = quoteServerProxy.PostQuote();
        log.info("Quote has been posted: " + postResponse);

        String deleteResponse = quoteServerProxy.DeleteQuote("2");
        log.info("Quote has been deleted: " + deleteResponse);

        Quote paramJson = quoteServerProxy.GetQuoteByParam();
        if (paramJson != null) {
            log.info("Quote requested: " + paramJson);
        }

        List<Quote> headersJson = quoteServerProxy.GetQuoteByHeader();
        if (headersJson != null) {
            log.info("Quotes requested: " + headersJson);
        }
    }


}
