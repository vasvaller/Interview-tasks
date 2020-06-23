package ru.rlisystems.b2b.tradelensSubscribtion;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;

public class TradeLensSubscribeControllerTest {
  private TradeLensSubscribeController controller = Mockito.mock(TradeLensSubscribeController.class);

  /**
   * Checks that json from request processed correctly WITH code in url
   */
  @Test
  public void prepareJsonWithRolisDbCode_TestWithCodeInUrl() {
    try {
      String url = "localhost:8080/tradelens-subscribe/eventListener/vsc";
      String json = "{\"name\": \"MySubscription\",\"eventType\": \"actualBargeArrival\",\"terminal\": \"USSAVPLP\"}";

      HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
      Mockito.when(req.getRequestURL()).thenReturn(new StringBuffer(url));

      Mockito.when(controller.prepareJsonWithRolisDbInfo(Mockito.any(), Mockito.any())).thenCallRealMethod();
      JSONObject actual = controller.prepareJsonWithRolisDbInfo(req, json.trim());
      JSONObject expected = new JSONObject("{\"rolis-db\": \"vsc\",\"name\": \"MySubscription\",\"eventType\": \"actualBargeArrival\",\"terminal\": \"USSAVPLP\"}");
      assertEquals(expected.toString(), actual.toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks that json from request processed correctly WITHOUT code in url
   */
  @Test
  public void prepareJsonWithRolisDbCode_TestWithoutCodeInUrl() {
    try {
      String url = "localhost:8080/tradelens-subscribe/eventListener";
      String json = "{\n" +
          "  \"name\": \"MySubscription\",\n" +
          "  \"terminal\": \"USSAVPLP\",\n" +
          "  \"eventType\": \"actualBargeArrival\"\n" +
          "}\n";

      HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
      Mockito.when(req.getRequestURL()).thenReturn(new StringBuffer(url));

      Mockito.when(controller.prepareJsonWithRolisDbInfo(Mockito.any(), Mockito.any())).thenCallRealMethod();
      JSONObject actual = controller.prepareJsonWithRolisDbInfo(req, json);
      JSONObject expected = new JSONObject("{\n" +
                                               "  \"rolis-db\": \"\",\n" +
                                               "  \"terminal\": \"USSAVPLP\",\n" +
                                               "  \"name\": \"MySubscription\",\n" +
                                               "  \"eventType\": \"actualBargeArrival\"\n" +
                                               "}\n");
      assertEquals(expected.toString(), actual.toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
}