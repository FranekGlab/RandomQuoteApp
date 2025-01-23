package com.example.randomquoteapp.service;

import com.example.randomquoteapp.proxy.Quote;
import com.example.randomquoteapp.proxy.QuoteServerProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuoteService {

    private final QuoteServerProxy quoteServerProxy;

    private final QuoteMapper quoteMapper;

    public Quote getQuoteByRequestParam(int id) {
        String getQuoteByRequestParamResponse = quoteServerProxy.GetQuoteByParam(id);
        return quoteMapper.mapJsonToQuote(getQuoteByRequestParamResponse);
    }

    public List<Quote> getQuotesByRequestHeaders(String requestHeader, String headerValue) {
        String getQuoteByRequestHeadersResponse = quoteServerProxy.GetQuoteByHeader(requestHeader, headerValue);
        return quoteMapper.mapJsonToQuotesList(getQuoteByRequestHeadersResponse);
    }

    public void postQuote(String quote) {
        quoteServerProxy.PostQuote(quote);
        log.info("Quote has been successfully posted: " + quote);
    }

    public void deleteQuote(int id) {
        quoteServerProxy.DeleteQuote(id);
        log.info("Quote has been successfully deleted: " + id);
    }



}
