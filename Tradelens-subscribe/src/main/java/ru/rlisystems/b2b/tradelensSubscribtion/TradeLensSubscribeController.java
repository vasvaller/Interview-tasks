package ru.rlisystems.b2b.tradelensSubscribtion;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author v.vasiliev
 */
@Getter
@Setter
@RestController
public class TradeLensSubscribeController extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private final Logger logger = LoggerFactory.getLogger(TradeLensSubscribeController.class);

  public static AtomicLong POST_COUNTER = new AtomicLong(0);

  @Autowired
  private TradeLensSubscribeModel model;

  @Override
  @PostMapping("/eventListener/*")
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    POST_COUNTER.incrementAndGet();
    try {
      String requestJson = printRequest(request);
      response.setStatus(HttpServletResponse.SC_OK);

      // добавим инфу из url, что бы знать с какой БД работать
      JSONObject jsonWithRolisDbCode = prepareJsonWithRolisDbInfo(request, requestJson);

      model.addIntoQueue(jsonWithRolisDbCode);

    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * Appends to source json Rolis database's code as "rolis-db": "%_code_%".
   * Last part of Url after last "/" provides code
   * @param request post request from user
   * @param requestJson request's body as json
   * @return json with database's code
   * @throws JSONException
   */
  public JSONObject prepareJsonWithRolisDbInfo(HttpServletRequest request, String requestJson) {
    JSONObject jsonWithRolisDbInfo = null;
    try {
      jsonWithRolisDbInfo = new JSONObject(requestJson);
      String s = request.getRequestURL().toString()
          .replaceAll(".+/eventListener", "")
          .replaceAll("/", "");
      jsonWithRolisDbInfo.put("rolis-db", s);
    } catch (JSONException e) {
      logger.error("rolis-db JSON parse error: " + e.getMessage());
    }

    return jsonWithRolisDbInfo;
  }

  /**
   * Logging received request and extract request's body
   * @param httpRequest
   */
  public String printRequest(HttpServletRequest httpRequest) {
    logger.info("URI: " + httpRequest.getRequestURI());
    logger.info("<<<<<<<<<<<<<<<<<<<<<\n\n Headers");
    Enumeration headerNames = httpRequest.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = (String) headerNames.nextElement();
      logger.info(headerName + " = " + httpRequest.getHeader(headerName));
    }

    logger.info("\nParameters");
    Enumeration params = httpRequest.getParameterNames();
    while (params.hasMoreElements()) {
      String paramName = (String) params.nextElement();
      logger.info(paramName + " = " + httpRequest.getParameter(paramName));
    }

    logger.info("\nRow data");
    String extractedRequestBody = TradeLensSubscribeUtils.extractPostRequestBody(httpRequest);
    logger.info("\n" + extractedRequestBody);
    return extractedRequestBody;
  }

}
