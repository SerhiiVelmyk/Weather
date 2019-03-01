Feature: One location weather data

  Scenario Outline: Verify data by city name, id and zip code
    Given parameter name <parameter name> and parameter value <parameter value> for end point
    When call Weather end point
    Then assert that body parameter <body parameter> equal to value <body value>
    Examples:
      | parameter name | parameter value | body parameter | body value |
      | id             | 703448          | name           | Kiev       |
      | q              | Frankfurt, DE   | name           | Frankfurt  |
      | zip            | 90001           | name           | Lynwood    |

  @smoke
  Scenario: Verify data by geographic coordinates
    Given parameter name lon, lat and parameter value 103.85, 1.29 for end point
    When call Weather end point
    Then assert that body parameter name equal to value Singapore

  Scenario Outline: Verify errors when data is incorrect
    Given parameter name <parameter name> and parameter value <parameter value> for end point
    When call Weather end point
    Then assert error message and status code
    Examples:
      | parameter name | parameter value |
      | zip            | test            |
      | id             | 100000          |
      | q              | WWWWWWW         |

  @ui
  Scenario Outline: Verify correct weather by place, city and zip and compare with UI
    Given I open main page
    And I set <value> to the search
    When I click to the first searched result
    Then Searched data by <parameter> is correct comparing with UI
    Examples:
      | parameter | value              |
      | zip       | 50311              |
      | city      | London             |
      | place     | Brooklyn, New York |
