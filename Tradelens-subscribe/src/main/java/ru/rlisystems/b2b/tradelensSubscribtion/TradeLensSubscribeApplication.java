package ru.rlisystems.b2b.tradelensSubscribtion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
    // Отключаем default Spring "Whitelabel Error Page".
    // При запуске под Apache Tomcat будет использоваться страница с ошибкой, генерируемая Tomcat-ом
    // и она лучше (нет проблем с кодировкой русского текста)
    ErrorMvcAutoConfiguration.class
})
public class TradeLensSubscribeApplication {

  private static TradeLensSubscribeDAOThread daoThread;

  public static void main(String[] args) {
    SpringApplication.run(TradeLensSubscribeApplication.class, args);
    daoThread.start();
  }

  @Autowired
  public void setDaoThread(TradeLensSubscribeDAOThread daoThread) {
    TradeLensSubscribeApplication.daoThread = daoThread;
  }

  @Bean
  public ServletRegistrationBean<StatusServlet> statusServletRegistrationBean() {
    return new ServletRegistrationBean<>(new StatusServlet(),"/tradelens-subscribe/status");
  }
}
