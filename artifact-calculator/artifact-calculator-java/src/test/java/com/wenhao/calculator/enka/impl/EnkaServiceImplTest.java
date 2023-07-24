package com.wenhao.calculator.enka.impl;

import com.wenhao.calculator.enka.EnkaService;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnkaServiceImplTest {

  @Autowired
  private EnkaService enkaService;

    @Test
    void test_getArtifactsByUid() throws NoSuchAlgorithmException {
      enkaService.getArtifactsByUid("212378990");
    }
}
