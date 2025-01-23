package com.example.randomquoteapp.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class QuoteServerProxy {

    private final RestTemplate restTemplate;

    @Value("${quotes-server.service.url}")
    String url;

    @Value("${quotes-server.service.port}")
    int port;

    public String GetQuoteByParam(int id) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(url)
                .port(port)
                .path("/apiWithRequestParam")
                .queryParam("id", id);

        try {
            ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, null, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (RestClientResponseException exception) {
            log.error("Param response exception: " + exception.getMessage() + exception.getStatusCode());
        } catch (RestClientException exception) {
            log.error("Param request exception: " + exception.getMessage());
        }
        return null;
    }

    public String GetQuoteByHeader(String requestHeader, String headerValue) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(url)
                .port(port)
                .path("/apiWithHeader");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(requestHeader, headerValue);
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, httpEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (RestClientResponseException exception) {
            log.error("Headers response exception: " + exception.getMessage() + exception.getStatusCode());
        } catch (RestClientException exception) {
            log.error("Headers request exception: " + exception.getMessage());
        }
        return null;
    }

    public void PostQuote(String quote) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(url)
                .port(port)
                .path("/api/quote");
        HttpEntity<QuoteValue> httpEntity = new HttpEntity<>(new QuoteValue(null, quote));
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(builder.build().toUri(), HttpMethod.POST, httpEntity, String.class);

        } catch (RestClientException exception) {
            log.error("Post request exception: " + exception.getMessage());
        }
    }

    public void DeleteQuote(int id) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(url)
                .port(port)
                .path("/api/quote/" + id);
        try {
            ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.DELETE, null, String.class);

        } catch (RestClientException exception) {
            log.error("Delete request exception: " + exception.getMessage());
        }
    }

}
