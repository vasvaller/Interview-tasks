package ru.rlisystems.b2b.tradelensSubscribtion;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Model provide processing of received request.
 * @author v.vasiliev
 */
@Getter
@Setter
@Component
public class TradeLensSubscribeModel {

  @Autowired
  private TradeLensSubscribeConfig eventTypesConfig;

  @Autowired
  TradeLensSubscribeController controller;

  private final Logger logger = LoggerFactory.getLogger(TradeLensSubscribeModel.class);

  /**
   * Multithreading-safe queue
   * We put (JSONObject) event into queue if config contains eventType from JSONObject
   * Other thread get first event in queue and process it. Then remove it from queue.
   */
  private Queue<JSONObject> eventQueue = new ConcurrentLinkedQueue<>();

  /**
   * Add into the queue with checking
   * Is eventType contained in config
   * @param json
   * @throws JSONException
   */
  public void addIntoQueue(JSONObject json) {
    List<String> eventTypes = eventTypesConfig.getEventTypes();
    try {
      String eventType = json.getString("eventType");
      logger.info(String.format("Got Event: %s", eventType));
      if (eventTypes.contains(eventType)) {
        eventQueue.add(json);
        logger.info(String.format("Event in queue, eventName is: %s", json.getString("eventName")));
      } else {
        logger.info(String.format("No such eventType in config, eventType is: %s", eventType));
      }
    } catch (JSONException e) {
      logger.warn("Incorrect JSON body received: no \"eventType\" field");
    }
  }
}
