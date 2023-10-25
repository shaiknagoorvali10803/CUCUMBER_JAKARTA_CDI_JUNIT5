@Chrome @login
Feature: Home Page Validation
	Scenario: Validating the login functionality
	  Given I have browser opened and url is navigated
	  When user validate the application logo
		Then user should be able login with valid credentials
		
	Scenario: Validating the login functionality
	  Given I have browser opened and url is navigated
	  When user validate the application logo
		Then user should be able login with invalid credentials
			