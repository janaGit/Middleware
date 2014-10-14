package de.klemp.middleware.controller;

import org.junit.Assert;
import org.testng.annotations.Test;

public class ControllerTest {

  @Test
  public void controllerToList() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void createDBConnection() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void init() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void searchMethods() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void start() {
      String ok=Controller.start();
    Assert.assertEquals("Could not start", ok,"ok");
  }
}
