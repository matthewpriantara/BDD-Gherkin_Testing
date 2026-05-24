@Login
Feature: SauceDemo Login Scenarios

  Background:
    Given User is on the login page

  @StandardUser @Positive
  Scenario: Successful login with standard user
    When User enter username "standard_user" and password "secret_sauce"
    And User click the login button
    Then User should be redirected to the inventory page

  @LockedOut @Negative
  Scenario: Login failure with locked out user
    When User enter username "locked_out_user" and password "secret_sauce"
    And User click the login button
    Then User should see error message "Sorry, this user has been locked out."

  @ProblemUser @Positive
  Scenario: Login with problem user and verify broken image
    When User enter username "problem_user" and password "secret_sauce"
    And User click the login button
    Then User should be redirected to the inventory page
    And First inventory item image source should contain "sl-404"

  @PerformanceGlitch @Positive
  Scenario: Login with performance glitch user
    When User enter username "performance_glitch_user" and password "secret_sauce"
    And User click the login button
    Then User should be redirected to the inventory page with delay

  @ErrorUser @Positive
  Scenario: Login with error user
    When User enter username "error_user" and password "secret_sauce"
    And User click the login button
    Then User should be redirected to the inventory page

  @VisualUser @Positive
  Scenario: Login with visual user and verify inventory list is displayed
    When User enter username "visual_user" and password "secret_sauce"
    And User click the login button
    Then User should be redirected to the inventory page
    And Inventory list should be displayed
