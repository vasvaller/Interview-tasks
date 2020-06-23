package ru.rlisystems.b2b.tradelensSubscribtion;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Thread monitors queue changes.
 * If there are new events sends them to db
 * @author v.vasiliev
 */
@Component
@Getter
@Setter
public class TradeLensSubscribeDAOThread extends Thread {
  @Autowired
  private TradeLensSubscribeModel model;

  @Autowired
  private TradeLensSubscribeDAO dao;

  private final Logger logger = LoggerFactory.getLogger(TradeLensSubscribeDAOThread.class);

  @Override
  public void run() {
    while (true) {
      while (model.getEventQueue().size() != 0) {
        JSONObject json = model.getEventQueue().remove();
        String rolisDbCode = json.remove("rolis-db").toString();
        if (dao.saveIntoDB(json, rolisDbCode)) {
          try {
            logger.info(String.format("Save event to DB success, eventType is %s", json.getString("eventType")));
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
      }
      try {
        currentThread().sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
