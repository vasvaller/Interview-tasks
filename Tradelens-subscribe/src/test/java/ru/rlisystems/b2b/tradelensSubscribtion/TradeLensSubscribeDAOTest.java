package ru.rlisystems.b2b.tradelensSubscribtion;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rlisystems.dac.DacDatasourceInterface;
import ru.rlisystems.dac.SQLRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TradeLensSubscribeDAOTest {

  @Autowired
  private DatabaseDataSourceFactory dsFactory;

  /**
   * Is save-to-db-method available for user
   */
  @Test
  public void saveIntoDB_TestUserHasRightsForMethod() {
    List<String> dbList = new ArrayList<>();
    dbList.add("primsyb");
    dbList.add("vsc");
    dbList.add("plp");

    AtomicBoolean sqlRequestSuccessfull = new AtomicBoolean(true);
    dbList.forEach(db -> {
      DacDatasourceInterface dacDatasource = dsFactory.getDacDatasource(db);
      SQLRequest req = new SQLRequest(dacDatasource);
      req.setCommand("app_MISC_tl_getansew");
      try {
        req.doCommand().handleResult();
      } catch (SQLException e) {
        sqlRequestSuccessfull.set(false);
        e.printStackTrace();
      }
    });
    Assert.assertEquals(new AtomicBoolean(true).get(), sqlRequestSuccessfull.get());
  }
}