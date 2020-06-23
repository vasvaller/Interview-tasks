package ru.rlisystems.b2b.tradelensSubscribtion;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rlisystems.dac.DacDatasourceInterface;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DatabaseDataSourceFactoryTest {
  @Autowired
  private DatabaseDataSourceFactory dsFactory;

  /**
   * No code in the uri
   */
  @Test(expected = IllegalArgumentException.class)
  public void applicationConfig_TestCodeNull() {
    dsFactory.getDacDatasource(null);
  }

  /**
   * Empty code in the uri
   */
  @Test(expected = IllegalArgumentException.class)
  public void applicationConfig_TestCodeEmpty() {
    dsFactory.getDacDatasource("");
  }

  /**
   * Incorrect code in the uri
   */
  @Test(expected = IllegalArgumentException.class)
  public void applicationConfig_TestCodeIncorrect() {
    dsFactory.getDacDatasource("kjfghbkxdjhfgbskdjfg");
  }


  /**
   * Correct code in the uri
   */
  @Test()
  public void applicationConfig_TestCodeCorrect() {
    DacDatasourceInterface vsc = dsFactory.getDacDatasource("vsc");
    Assert.assertNotNull(vsc);
  }

  /**
   * Checking for user in dependence of db code in the uri
   */
  @Test
  public void databaseDataSourceFactory_TestDbCodeGettingAndUserPicking() {
    Map<String, String> map = new HashMap<>();
    map.put("primsyb", "TL_subscription");
    map.put("vsc", "TL_subscription");
    map.put("plp", "TL_subscription");

    map.forEach((db, expectedUser) -> {
      DacDatasourceInterface dacDatasource = dsFactory.getDacDatasource(db);
      String actualUser = dacDatasource.getRealUser();
      Assert.assertEquals(actualUser, expectedUser);
    });
  }
}