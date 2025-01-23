package com.example.randomquoteapp.service;

import com.example.randomquoteapp.proxy.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
@Log4j2
public class QuoteMapper {

    private final ObjectMapper objectMapper;

    List<Quote> mapJsonToQuotesList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("QuoteMapper can't map json");
            return new ArrayList<>(Collections.emptyList());
        }
    }



    Quote mapJsonToQuote(String json) {

        try {
            return objectMapper.readValue(json, Quote.class);
        } catch (JsonProcessingException e) {
            log.error("QuoteMapper can't map json");
            return new Quote(null, null);
        }

    }

}
