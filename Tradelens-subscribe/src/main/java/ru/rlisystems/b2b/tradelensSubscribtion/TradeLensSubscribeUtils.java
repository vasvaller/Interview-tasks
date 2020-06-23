package ru.rlisystems.b2b.tradelensSubscribtion;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author v.vasiliev
 */
public class TradeLensSubscribeUtils {

  /**
   * @param request
   * @return valid JSON-string with event like this:
   * {
   * "eventSubmissionTime": 1569989627463,
   * "correlationId": "gems;shipmentRef=586209720;gemsId=5708763190;Shipment_ETD;integrationId=138359102",
   * "eventName": "Estimated vessel departure",
   * "originatorId": "MAEU",
   * "eventOccurrenceTime": 1570154400000,
   * "eventOccurrenceTime8601": "2019-10-04T10:00:00.0000000+08:00",
   * "originatorName": "Maersk Line",
   * "vehicleId": "9502958",
   * "voyageId": "938W",
   * "transportationPhase": "Export",
   * "eventTransactionId": "a661622b-c25a-4338-8986-09177a5bc07b",
   * "carrierBookingNumber": "586209720",
   * "terminal": "CNXMN-XICT",
   * "transportEquipmentId": "92748d35-b693-49b8-8d19-660bdf06d2aa",
   * "equipmentNumber": "MSKU5059429",
   * "subscriptionId": "9b87a192-1895-4dae-934e-40356883008d",
   * "fullStatus": "Full",
   * "eventType": "estimatedVesselDeparture",
   * "associatedConsignmentIds": ["a00faff0-560a-44c2-8256-897f2aedfa55"],
   * "associatedCarrierBookingNumbers": ["586209720"],
   * "location": {
   * "type": "UN/Locode",
   * "value": "CNXMN"
   * },
   * "messages": ["common.event.status.success"]
   * }
   */
  static String extractPostRequestBody(HttpServletRequest request) {
    String extractedBody;
    if ("POST".equalsIgnoreCase(request.getMethod())) {
      Scanner s = null;
      try {
        s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
      } catch (IOException e) {
        e.printStackTrace();
      }
      StringBuilder json = new StringBuilder(s.hasNext() ? s.next() : "");
      String retVal = json.toString();
      if (retVal.startsWith("[") && retVal.endsWith("]")) {
        extractedBody = json.toString().substring(1, json.lastIndexOf("]"));
      } else extractedBody = json.toString();
    } else extractedBody = "";
    return extractedBody;
  }
}
