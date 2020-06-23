package ru.rlisystems.b2b.tradelensSubscribtion;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class StatusServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    PrintWriter writer;
    try {
      writer = response.getWriter();
      writer.write("<html>");
      writer.write("<strong>TRADELENS-SUBSCRIBE ALIVE</strong><br>");
      writer.write("served " + TradeLensSubscribeController.POST_COUNTER.get() + " requests");
      writer.write("</html>");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
