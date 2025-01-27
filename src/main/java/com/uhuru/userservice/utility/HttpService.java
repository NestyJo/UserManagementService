package com.uhuru.userservice.utility;

import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HttpService {
    private final RestTemplate restTemplate;
    public final Gson jsonProcessor;
    private final LoggerService logger;

    public HttpService(RestTemplate restTemplate, Gson jsonProcessor, LoggerService logger) {
        this.restTemplate = restTemplate;
        this.jsonProcessor = jsonProcessor;
        this.logger = logger;
    }

    public <T> T postJson(String url, String jsonString, Class<T> clazz){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        try {
            return restTemplate.exchange(url, HttpMethod.POST, request, clazz).getBody();
        }catch (Exception e){
            logger.log(e.getMessage(), "error");
            return null;
        }
    }

    public <T> T postXml(String url, String xml, Map<String, String> customHeader, Class<T> clazz){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        for(Map.Entry<String, String> entry : customHeader.entrySet()){
            headers.add(entry.getKey(), entry.getValue());
        }
        HttpEntity<String> httpRequest = new HttpEntity<>(xml, headers);
        try {
            return restTemplate.exchange(url, HttpMethod.POST, httpRequest, clazz).getBody();
        }catch (Exception e){
            logger.log(e.getMessage(), "error");
            return null;
        }
    }

    public <T> T getUrl(String url, Class<T> clazz){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);//.getBody();
            return jsonProcessor.fromJson(response.getBody(), clazz);
        }catch (Exception e){
            logger.log(e.getMessage(), "error");
            return null;
        }
    }

    public <T> T getDataUrl(String url, Class<T> clazz){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder().url(url).method("GET", null).build();
        try {
            Response response = client.newCall(request).execute();
            return jsonProcessor.fromJson(response.toString(), clazz);
        }catch (Exception e){
            return null;
        }
    }

    public String getTokenProcessor(RequestBody requestBody, Headers headers, String url) {
        String response;
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .headers(headers)
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            Call httpRequestCall = client.newCall(request);
            Response httpCallResponse;
            httpCallResponse = httpRequestCall.execute();
            assert httpCallResponse.body() != null;
            response = httpCallResponse.body().string();
            logger.log(response);
        } catch (Exception e) {
            response = "EXCEPTION - " + e.getMessage();
            logger.log(response);
        }
        return response;
    }
}
