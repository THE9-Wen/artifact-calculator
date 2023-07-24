package com.wenhao.calculator.enka.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wenhao.calculator.artifact.model.Artifact;
import com.wenhao.calculator.common.exceptions.ArtifactException;
import com.wenhao.calculator.enka.EnkaService;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * EnkaServiceImpl
 *
 * @author: Wenhao Tong
 * @date: 2023/7/24
 */
@Service
public class EnkaServiceImpl implements EnkaService {

  private final HttpClient client;

  public EnkaServiceImpl(HttpClient client) {
    this.client = client;
  }

  @Override
  public List<Artifact> getArtifactsByUid(String uid) {
    HttpRequest request =
            HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://enka.network/api/uid/" + uid))
                    .headers(
                            "user-agent",
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                    .timeout(Duration.ofMillis(5000))
                    .build();
    HttpResponse<String> response = null;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new ArtifactException(e.getMessage());
    }
    JSONObject result = JSON.parseObject(response.body());
    return null;
  }
}
