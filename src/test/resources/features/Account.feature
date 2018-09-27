Feature: Account

  Scenario: The creation of a account occurred with success
    Given the name Aragorn
    When create account
    Then should return status 200
    And should return the account id created

  Scenario: The creation of a account occurred with error because not was informed a name
    Given a name not informed
    When create account
    Then should return status 400
    And should return a message of error