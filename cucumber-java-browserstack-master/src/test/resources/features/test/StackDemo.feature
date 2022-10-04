Feature: Check Exam Categories

  Scenario: Verify all Exams
    Given I login on the website 'https://a13-tpc.compucram.com/'
    When I take exams
    Then Take Vocabulary Test
