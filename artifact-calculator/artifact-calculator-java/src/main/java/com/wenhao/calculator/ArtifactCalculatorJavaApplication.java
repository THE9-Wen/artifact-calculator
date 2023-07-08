package com.wenhao.calculator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wenhao
 */
@SpringBootApplication
@MapperScan("com.wenhao.calculator.artifact.mapper")
public class ArtifactCalculatorJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtifactCalculatorJavaApplication.class, args);
    }

}
