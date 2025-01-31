package com.example.randomquoteapp.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;

@Component
@RequiredArgsConstructor
@Log4j2
public class QuoteServerProxy {

    private final RestTemplate restTemplate;

    @Value("${quoters-extend}")
    String url;


    public String getQuoteByParam(Integer id) {
        String uri = url + "/apiWithRequestParam?id=" + id;
        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
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

    public String getQuoteByHeader(String requestHeader, String headerValue) {
        String uri = url + "/apiWithHeader";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(requestHeader, headerValue);
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
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

    public void postQuote(String quote) {
        String uri = url + "/api/quote";
        HttpEntity<QuoteValue> httpEntity = new HttpEntity<>(new QuoteValue(null, quote));
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        } catch (RestClientException exception) {
            log.error("Post request exception: " + exception.getMessage());
        }
    }

    public void deleteQuote(int id) {
        String uri = url + "/api/quote/" + id;
        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);

        } catch (RestClientException exception) {
            log.error("Delete request exception: " + exception.getMessage());
        }
    }


}
