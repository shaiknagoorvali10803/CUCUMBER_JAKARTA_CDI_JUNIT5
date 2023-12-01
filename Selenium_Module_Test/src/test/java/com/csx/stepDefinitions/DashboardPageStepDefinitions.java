package com.csx.stepDefinitions;



import com.csx.page.actions.HRMDashboardPageActions;
import com.csx.page.actions.HRMLoginPageActions;
import com.csx.test.util.WebDriverProvider;
import io.cucumber.java.en.Then;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;


public class DashboardPageStepDefinitions {
  
  @Inject
  private WebDriverProvider driverProvider;
  @Inject
  HRMLoginPageActions login;
  @Inject
  HRMDashboardPageActions dashboard;
	  
  @Then("i will veryfy the Dashboard content Apple leave")
  public void user_should_be_able_verify_applyleave() throws InterruptedException {
	  login.verifyLoginAsValidUser();
	  String verifyUserAccess = dashboard.verifyUserAccess();
	  Assertions.assertEquals(verifyUserAccess, "Dashboard");
	  Thread.sleep(2000);
	  dashboard.verifyassignleave_link();
	  Thread.sleep(2000);
	  login.logout();
	       
  }
  
  @Then("i will veryfy the Dashboard content leave link")
  public void user_should_be_able_verify_leavelink() throws InterruptedException {
	  login.verifyLoginAsValidUser();
	  String verifyUserAccess = dashboard.verifyUserAccess();
      Assertions.assertEquals(verifyUserAccess, "Dashboard");
	  Thread.sleep(2000);
	  dashboard.verifyleavelist_link();
	  Thread.sleep(12000);
     
  }
}
