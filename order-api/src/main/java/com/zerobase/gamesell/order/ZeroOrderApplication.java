package com.zerobase.gamesell.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ServletComponentScan
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableJpaAuditing
@EnableFeignClients(basePackages = "com.zerobase.gamesell.order.client")
@SpringBootApplication(scanBasePackages = {
    "com.zerobase.gamesell.order",
    "com.zerobase.gamesell.user",
    "com.user.gamedomain"
})

public class ZeroOrderApplication {

  public static void main(String[] args) {
    SpringApplication.run(ZeroOrderApplication.class, args);
  }

}
