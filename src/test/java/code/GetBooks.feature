Feature:
  Scenario: Given baseURI when we make GET CALL to books endpoint verify response
    Given baseURI
    When Get call to "/books"
    Then Verify response
    And Verify status code