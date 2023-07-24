package com.wenhao.calculator.common.config;

import java.net.http.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * RestTemplateConfig
 *
 * @author: Wenhao Tong
 * @date: 2023/7/24
 */
@Configuration
public class HttpClientConfig {
  @Bean
  public HttpClient restTemplate() {
    return HttpClient.newBuilder().build();
  }

  @Bean
  public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setReadTimeout(5000);
    factory.setConnectTimeout(15000);
    return factory;
  }

  @Bean
  public HttpHeaders httpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set(
            "origin",
            "https://enka.network"
    );
    headers.set(
        "user-agent",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
    return headers;
  }
}
