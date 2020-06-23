package ru.rlisystems.b2b.tradelensSubscribtion;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  @author v.vasiliev
 */
@Setter
@Getter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "config")
public class TradeLensSubscribeConfig {

  private List<DBInfo> databases = new ArrayList<>();

  /**
   * Event type list in the config
   */
  private List<String> eventTypes = new ArrayList<>();

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    eventTypes.forEach(s -> sb.append(s + "\n"));
    return sb.toString();
  }

  public List<DBInfo> getDBByCode(String code) {
    return databases.stream().filter(x -> x.getCode().equals(code)).collect(Collectors.toList());
  }

  @Setter
  @Getter
  public static class DBInfo {
    private String name;
    private String code;
    private String gateUrl;
    private String server;
    private String dbname;
    private String login;
    private String password;

    @Override
    public String toString() {
      return "DBInfo [name=" + name + ", code=" + code + ", gateUrl=" + gateUrl
          + ", server=" + server + ", dbname=" + dbname + ", login=" + login + ", password=" + password + "]";
    }
  }
}
