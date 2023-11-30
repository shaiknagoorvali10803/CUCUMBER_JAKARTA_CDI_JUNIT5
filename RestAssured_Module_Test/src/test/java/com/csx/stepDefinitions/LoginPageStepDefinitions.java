package com.csx.stepDefinitions;


import com.csx.page.actions.HRMLoginPageActions;
import com.csx.test.util.WebDriverProvider;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.inject.Inject;

public class LoginPageStepDefinitions {
  
  @Inject
  private WebDriverProvider driverProvider;

  @Inject
  HRMLoginPageActions login;
	
  @Given("I have browser opened and url is navigated")
  public void i_have_browser_opened_and_url_is_navigated() {
	 driverProvider.getInstance().get("https://opensource-demo.orangehrmlive.com/");
  }

  @When("user validate the application logo")
  public void user_validate_the_application_logo() {
	  login.isHRMLogoDisplayed();
      
  }

  @Then("user should be able login with valid credentials")
  public void user_should_be_able_login_with_valid_credentials() throws InterruptedException {
	  login.verifyLoginAsValidUser();
	  Thread.sleep(2000);
	  login.logout();
	  Thread.sleep(3000);
     
  }
  
  
  @Then("user should be able login with invalid credentials")
  public void user_should_be_able_login_with_invalid_credentials() throws InterruptedException {
	  login.verifyLoginAsInvalidUser();
	  login.isLoginFailedErrorDisplayed();
	  Thread.sleep(12000);
     
  }
}
