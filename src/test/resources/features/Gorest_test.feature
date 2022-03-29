Feature: Gorest test
#  https://gorest.co.in/

  Scenario: 1 - Getting all users

    Given user has "getAllUsersRQ" request

    When user sends "GET" "getAllUsersRQ" request

    Then "getAllUsersRS" code is "200"
    And number of all users equal "20" in "getAllUsersRS"


  Scenario: 2 - Getting user by name

    Given user has "getUserByIdRQ" request with id "3977"

    When user sends "GET" "getUserByIdRQ" request

    Then "getUserByIdRS" code is "200"
    And user name contains expected "Anal Kaniyar"

  Scenario: 3 - Creating user

    Given user has "createUserRQ" request with following parameters
      | email              | name | status | gender |
      | email111@gmail.com | test | active | male   |

    When user sends "POST" "createUserRQ" request

    And "createUserRS" code is "201"
    And user has "getUserByIdRQ" request with id from "createUserRS" response
    And user sends "GET" "getUserByIdRQ" request
    Then "getUserByIdRS" code is "200"
    And user name contains expected 'test'

  Scenario: 4 - Deleting user
    Given user has "createUserRQ" request with following parameters
      | email              | name | status | gender |
      | email111@gmail.com | test | active | male   |

    When user sends "POST" "createUserRQ" request
    And user has "deleteUserRQ" request with id from "createUserRS" response
    And user sends "DELETE" "deleteUserRQ" request

    Then "deleteUserRS" code is "204"
