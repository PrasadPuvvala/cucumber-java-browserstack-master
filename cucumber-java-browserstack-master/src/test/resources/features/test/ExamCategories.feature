Feature: Check Exam Categories

  @smoke
  Scenario: Verify all Exams
    Given I login on the website 'https://a13-tpc.compucram.com/'
    When I take exams
    Then Take Vocabulary Test
  @sanity
  Scenario: Verify all Exams
    Given I login on the website 'https://a13-tpc.compucram.com/'
    When I take exams
    Then Take Vocabulary Test
  @sanity
  Scenario: Verify all Exams1
  Given
  When
  Then