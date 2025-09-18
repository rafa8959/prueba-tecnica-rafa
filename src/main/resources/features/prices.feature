Feature: Prices API functional tests

  Scenario: Test 1 - Get price at 2020-06-14 10:00:00
    Given the application is running
    When I request the price for product 35455 and brand 1 at "2020-06-14T10:00:00"
    Then the response status should be 200
    And the price should be 35.50

  Scenario: Test 2 - Get price at 2020-06-14 16:00:00
    Given the application is running
    When I request the price for product 35455 and brand 1 at "2020-06-14T16:00:00"
    Then the response status should be 200
    And the price should be 25.45

  Scenario: Test 3 - Get price at 2020-06-14 21:00:00
    Given the application is running
    When I request the price for product 35455 and brand 1 at "2020-06-14T21:00:00"
    Then the response status should be 200
    And the price should be 35.50

  Scenario: Test 4 - Get price at 2020-06-15 10:00:00
    Given the application is running
    When I request the price for product 35455 and brand 1 at "2020-06-15T10:00:00"
    Then the response status should be 200
    And the price should be 30.50

  Scenario: Test 5 - Get price at 2020-06-16 21:00:00
    Given the application is running
    When I request the price for product 35455 and brand 1 at "2020-06-16T21:00:00"
    Then the response status should be 200
    And the price should be 38.95
