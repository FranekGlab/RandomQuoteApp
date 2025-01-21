package com.example.randomquoteapp;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class QuoteServerProxy {

    private final RestTemplate restTemplate;

    public Quote GetQuoteByParam() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/apiWithRequestParam")
                .queryParam("id", "4");

        try {
            ResponseEntity<Quote> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, null, Quote.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (RestClientResponseException exception) {
            log.error("ParamRequest exception: " + exception.getMessage() + exception.getStatusCode());
        }
        return null;
    }

    public List<Quote> GetQuoteByHeader() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/apiWithHeader");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("requestId", "quoters-extended");
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<List<Quote>> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
            });
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (RestClientResponseException exception) {
            log.error("HeadersRequest exception " + exception.getMessage() + exception.getStatusCode());
        }
        return null;
    }

    public String PostQuote() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/quote");
        PostQuoteRequest requestbody = new PostQuoteRequest("posting test quote");
        HttpEntity<PostQuoteRequest> httpEntity = new HttpEntity<>(requestbody);
        try {
            ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.POST, httpEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (RestClientResponseException exception) {
            log.error("PostRequest exception: " + exception.getMessage() + exception.getStatusCode());
        }
        return null;
    }

    public String DeleteQuote(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/quote/" + id);
        try {
            ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.DELETE, null, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (RestClientResponseException exception) {
            log.error("DeleteRequest exception: " + exception.getMessage() + exception.getStatusCode());
        }
        return null;
    }

}
