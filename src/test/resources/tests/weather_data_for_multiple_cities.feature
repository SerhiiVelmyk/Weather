Feature: Multiple location weather data

  @smoke
  Scenario: Verify data for search end point
    Given set up GET data for search in circle end point
      | 55.5 | 37.5 | 10 |
    When execute get request
    Then response status code is 200
