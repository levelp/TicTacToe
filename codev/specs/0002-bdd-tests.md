# Specification: BDD Test Suite Implementation

## Metadata
- **ID**: 0002-bdd-tests
- **Status**: completed
- **Created**: 2025-10-21
- **Protocol**: SPIDER-SOLO
- **Related**: [0001-code-quality-and-coverage.md](0001-code-quality-and-coverage.md)

## Clarifying Questions Asked

**Q1**: Why add BDD tests when we already have 100% JUnit coverage?
**A1**: BDD tests serve a different purpose - they provide living documentation in natural language (Russian), making the system behavior clear to non-technical stakeholders. JUnit tests verify implementation correctness, while BDD tests verify business requirements.

**Q2**: What BDD framework should we use?
**A2**: Cucumber is the industry standard for Java BDD testing. It supports Russian language (Gherkin), integrates with JUnit 4, and has excellent Maven support.

**Q3**: Should BDD tests replace JUnit tests?
**A3**: No, they complement each other. JUnit tests provide technical coverage (100% line coverage), while BDD tests provide behavioral specifications. Both are valuable.

**Q4**: What language should feature files use?
**A4**: Russian, matching the application language. This makes specifications accessible to Russian-speaking stakeholders and matches the domain language.

## Problem Statement

The TicTacToe project has excellent technical test coverage (100% via JUnit) but lacks:
1. **Living Documentation**: No human-readable specification of game behavior
2. **Stakeholder Communication**: Technical tests are hard for non-developers to understand
3. **Acceptance Criteria**: No executable acceptance tests in business language
4. **Behavioral Specifications**: Tests focus on implementation, not behavior
5. **Domain Language**: Tests use technical terms instead of domain concepts

### Current State Limitations

**Existing JUnit Tests**:
- ✅ Achieve 100% line coverage
- ✅ Verify technical correctness
- ❌ Written for developers, not stakeholders
- ❌ Don't express business requirements
- ❌ Implementation-focused, not behavior-focused
- ❌ No natural language documentation

**Example JUnit Test**:
```java
@Test
public void testMoveOutOfBoundsNegativeX() throws UserException {
    Game game = new Game();
    exception.expect(UserException.class);
    game.move(-1, 0);
}
```

**What's Missing**: This test doesn't communicate *why* negative coordinates are invalid or *what the user experience should be*.

## Desired State

### BDD Test Suite Goals

**Living Documentation**:
- ✅ Feature files in Russian (Gherkin syntax)
- ✅ Readable by product owners, testers, developers
- ✅ Serves as specification and test simultaneously
- ✅ Always up-to-date (tests fail if behavior changes)

**Example BDD Scenario**:
```gherkin
Сценарий: Ход за пределы поля
  Допустим у меня пустое поле 3x3
  Когда X пытается сходить в позицию -1, 0
  Тогда должна возникнуть ошибка "Координата x за пределами поля"
  И игра не завершена
```

**Benefits**:
- **Clarity**: Business rules expressed in natural language
- **Traceability**: Features map directly to requirements
- **Collaboration**: Product owners can write/review scenarios
- **Regression Prevention**: Behavioral specifications prevent unintended changes

## Success Criteria

- [x] Cucumber framework integrated (version 7.14.0)
- [x] 4 feature files covering all game aspects
- [x] 43 BDD scenarios in Russian (Gherkin)
- [x] Comprehensive step definitions in GameSteps.java
- [x] Cucumber test runner configured
- [x] BDD tests execute via Maven
- [x] HTML and JSON reports generated
- [x] All scenarios pass
- [x] No duplication between feature files
- [x] Clear scenario organization

## Stakeholders

- **Primary Users**: Product owners, business analysts
- **Secondary Users**: QA engineers, developers
- **Technical Team**: Project maintainers
- **Business Owners**: Project owner (levelp)
- **Future Contributors**: Developers joining the project

## Constraints

### Technical Constraints
- Java 8 compatibility required
- Must integrate with existing Maven build
- Must work with JUnit 4.13.2
- No breaking changes to existing code
- Must run in GitHub Actions CI/CD

### Business Constraints
- Feature files must be in Russian
- Must complement (not replace) JUnit tests
- Test execution time should remain under 10 seconds
- No additional runtime dependencies for application

## Assumptions

- Cucumber 7.14.0 is compatible with Java 8
- Russian language support in Gherkin is stable
- Stakeholders can read and validate feature files
- BDD tests will be maintained alongside code changes
- Maven is the build tool (no Gradle support needed)

## Solution Approach

### Approach: Cucumber with Russian Gherkin (SELECTED)

**Description**: Implement Cucumber BDD framework with feature files written in Russian, covering all game behaviors with 43 comprehensive scenarios.

**Feature File Organization**:
1. **game-play.feature**: Core gameplay mechanics (6 scenarios)
2. **win-conditions.feature**: Win/draw detection (11 scenarios)
3. **error-handling.feature**: Error cases and validation (9 scenarios)
4. **model-classes.feature**: Model class behaviors (17 scenarios)

**Implementation Components**:
1. Add Cucumber dependencies to pom.xml
2. Create feature files in `src/test/resources/features/`
3. Implement step definitions in `src/test/java/bdd/steps/GameSteps.java`
4. Create test runner `src/test/java/bdd/RunCucumberTest.java`
5. Configure report generation (HTML, JSON)

**Pros**:
- Industry-standard BDD framework
- Excellent Russian language support
- Integrates seamlessly with JUnit 4
- Rich reporting capabilities
- Large community and documentation
- Maven plugin well-maintained

**Cons**:
- Adds test execution time (~2-3 seconds)
- Additional dependencies (cucumber-java, cucumber-junit)
- Learning curve for team members unfamiliar with Gherkin

**Estimated Complexity**: Medium
**Risk Level**: Low

## Feature Breakdown

### Feature 1: game-play.feature (6 scenarios)

**Purpose**: Specify core gameplay mechanics

**Scenarios**:
1. Creating new game with default board size (3x3)
2. Creating new game with custom board size (5x5)
3. Simple move sequence (X moves, then O's turn)
4. Player turn alternation (X→O→X→O)
5. Game not finished when board has empty cells
6. Game toString() representation

**Key Behaviors**:
- Board initialization
- Move execution
- Turn management
- State representation

### Feature 2: win-conditions.feature (11 scenarios)

**Purpose**: Specify all win/draw conditions

**Scenarios**:
1. X wins horizontally (row 0)
2. X wins horizontally (row 1)
3. X wins horizontally (row 2)
4. X wins vertically (column 0)
5. X wins vertically (column 1)
6. X wins vertically (column 2)
7. X wins on main diagonal
8. X wins on anti-diagonal
9. O wins (any win condition)
10. Draw when board full and no winner
11. Scenario Outline: All win lines (parameterized)

**Key Behaviors**:
- Horizontal line detection (all rows)
- Vertical line detection (all columns)
- Diagonal line detection (both diagonals)
- Draw detection
- Both players can win

### Feature 3: error-handling.feature (9 scenarios)

**Purpose**: Specify error conditions and validation

**Scenarios**:
1. Move with x < 0 (out of bounds)
2. Move with x >= size (out of bounds)
3. Move with y < 0 (out of bounds)
4. Move with y >= size (out of bounds)
5. Move to occupied cell (already X)
6. Move to occupied cell (already O)
7. Move after game over (X won)
8. Move after game over (O won)
9. Move after draw

**Key Behaviors**:
- Coordinate validation
- Cell occupancy checking
- Game state validation
- Error message correctness

### Feature 4: model-classes.feature (17 scenarios)

**Purpose**: Specify model class behaviors

**Scenarios**:

**Cell (3 scenarios)**:
1. Cell enum values (X, O, EMPTY)
2. Cell toString() representations
3. Cell valueOf() method

**CellState (5 scenarios)**:
4. CellState creation and getter
5. CellState setter changes state
6. CellState listener notification
7. CellState listener not called for same value
8. CellState toString() delegation

**Move (4 scenarios)**:
9. Move creation with coordinates
10. Move toString() format
11. Move with zero coordinates
12. Move with negative coordinates

**UserException (3 scenarios)**:
13. UserException carries message
14. UserException is throwable
15. UserException extends Exception

**Listeners (2 scenarios)**:
16. GameUpdateListener receives notifications
17. Multiple listeners all notified

**Key Behaviors**:
- Enum correctness
- Observer pattern
- Value object behavior
- Exception handling

## Test Coverage Analysis

### Relationship to JUnit Tests

| Coverage Type | JUnit Tests | BDD Tests | Purpose |
|--------------|------------|-----------|---------|
| Line Coverage | 100% | Not measured | Technical correctness |
| Behavior Coverage | Partial | 100% | Business requirements |
| Edge Cases | ✅ All covered | ✅ Key scenarios | Robustness |
| Integration | ✅ Via mocks | ✅ End-to-end | Component interaction |
| Documentation | ❌ Code only | ✅ Living docs | Knowledge sharing |

### Complementary Nature

**JUnit Tests** (38 tests):
- Verify every line of code executes correctly
- Test internal implementation details
- Fast execution (< 1 second)
- Technical focus

**BDD Tests** (43 scenarios):
- Verify business requirements are met
- Express expected user-facing behavior
- Medium execution (2-3 seconds)
- Business focus

**Together**: Comprehensive quality assurance at both technical and business levels.

## Technical Design

### Directory Structure

```
TicTacToe/
├── src/test/resources/features/    # Feature files (Gherkin)
│   ├── game-play.feature           # 6 scenarios
│   ├── win-conditions.feature      # 11 scenarios
│   ├── error-handling.feature      # 9 scenarios
│   └── model-classes.feature       # 17 scenarios
├── src/test/java/bdd/
│   ├── RunCucumberTest.java        # Test runner
│   └── steps/
│       └── GameSteps.java          # Step definitions (~340 lines)
└── target/cucumber-reports/        # Generated reports
    ├── cucumber.html               # HTML report
    └── cucumber.json               # JSON report
```

### Maven Dependencies

```xml
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.14.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>7.14.0</version>
    <scope>test</scope>
</dependency>
```

### Step Definition Architecture

**GameSteps.java** contains:
- **State Management**: Game instance, exception tracking, listener tracking
- **Given Steps (@Допустим)**: Setup game state
- **When Steps (@Когда)**: Execute actions
- **Then Steps (@Тогда)**: Verify outcomes
- **Helper Methods**: State verification, board setup

**Key Design Decisions**:
1. **Instance Variables**: Maintain state between steps in a scenario
2. **Exception Handling**: Catch and store exceptions for verification
3. **Listener Implementation**: Anonymous classes to verify observer pattern
4. **Russian Annotations**: @Допустим, @Когда, @Тогда for natural language

## Scenario Examples

### Example 1: Win Detection
```gherkin
Сценарий: Победа X по горизонтали в первой строке
  Допустим у меня пустое поле 3x3
  Когда X ходит в позицию 0, 0
  И O ходит в позицию 1, 0
  И X ходит в позицию 0, 1
  И O ходит в позицию 1, 1
  И X ходит в позицию 0, 2
  Тогда победитель X
  И игра завершена
```

### Example 2: Error Handling
```gherkin
Сценарий: Попытка хода на занятую клетку
  Допустим у меня пустое поле 3x3
  И X ходит в позицию 0, 0
  Когда O пытается сходить в позицию 0, 0
  Тогда должна возникнуть ошибка "занята"
  И ход ноликов
```

### Example 3: Model Behavior
```gherkin
Сценарий: CellState уведомляет слушателей при изменении
  Допустим у меня новый CellState со значением EMPTY
  И я добавил слушателя к CellState
  Когда я устанавливаю значение CellState в X
  Тогда слушатель был уведомлён о значении X
```

## Performance Requirements

- **BDD Test Execution**: < 5 seconds for all 43 scenarios
- **Report Generation**: < 1 second for HTML/JSON reports
- **Memory Usage**: < 50 MB additional heap
- **CI/CD Impact**: < 10% increase in total build time

## Dependencies

### External Libraries
- Cucumber Java 7.14.0
- Cucumber JUnit 7.14.0
- JUnit 4.13.2 (already present)

### Internal Dependencies
- All model classes (Game, Cell, CellState, Move, UserException)
- Listener interfaces (GameUpdateListener, CellChangedListener)

### Build Tools
- Maven 3.6+
- Java 8

## Risks and Mitigation

| Risk | Probability | Impact | Mitigation |
|------|------------|--------|------------|
| Cucumber version incompatibility | Low | Medium | Pin to tested version 7.14.0 |
| Russian encoding issues | Low | Low | Ensure UTF-8 encoding in pom.xml |
| Test maintenance burden | Medium | Medium | Keep scenarios focused, avoid duplication |
| Increased build time | Medium | Low | Run BDD tests in parallel, optimize step definitions |
| Step definition complexity | Low | Medium | Keep step methods simple, reuse helper methods |

## Open Questions

### Resolved
- [x] ~~Which BDD framework?~~ → Cucumber (industry standard)
- [x] ~~What Cucumber version?~~ → 7.14.0 (latest stable for Java 8)
- [x] ~~Feature file language?~~ → Russian (matches domain)
- [x] ~~How many scenarios?~~ → 43 (comprehensive coverage)

### Future Considerations
- [ ] Should we add scenario outlines for all win conditions? (Currently partially done)
- [ ] Do we need custom formatters for reports?
- [ ] Should BDD tests run before or after JUnit tests?

## Implementation Results

### Files Created

1. **pom.xml** (modified):
   - Added Cucumber dependencies

2. **Feature Files** (4 files, 43 scenarios):
   - game-play.feature (6 scenarios)
   - win-conditions.feature (11 scenarios)
   - error-handling.feature (9 scenarios)
   - model-classes.feature (17 scenarios)

3. **Step Definitions** (1 file, ~340 lines):
   - GameSteps.java (all step implementations)

4. **Test Runner** (1 file):
   - RunCucumberTest.java (Cucumber configuration)

### Metrics

```
BDD Test Suite:
├─ Feature Files: 4
├─ Total Scenarios: 43
├─ Total Steps: ~150 (Given/When/Then)
├─ Step Definitions: ~40 methods
├─ Code Lines: ~340 (GameSteps.java)
└─ Test Execution: ~3 seconds

Coverage:
├─ Game Behaviors: 100%
├─ Win Conditions: 100%
├─ Error Conditions: 100%
├─ Model Classes: 100%
└─ Edge Cases: Key scenarios covered
```

### Test Execution Output

```bash
mvn test

# Output includes:
# - JUnit tests: 38 tests passed
# - Cucumber scenarios: 43 scenarios passed
# - Total time: ~5-6 seconds
# - Reports: target/cucumber-reports/cucumber.html
```

## Success Validation

### Functional Validation
- [x] All 43 scenarios pass
- [x] No undefined steps
- [x] No pending scenarios
- [x] Reports generated successfully

### Quality Validation
- [x] Feature files readable by non-developers
- [x] Scenarios follow Given-When-Then pattern
- [x] Step definitions are reusable
- [x] No code duplication in steps

### Integration Validation
- [x] BDD tests run via `mvn test`
- [x] BDD tests run in GitHub Actions
- [x] Reports accessible after build
- [x] No conflicts with JUnit tests

## References

- **Cucumber Documentation**: https://cucumber.io/docs/cucumber/
- **Gherkin Reference**: https://cucumber.io/docs/gherkin/reference/
- **Cucumber-JVM**: https://github.com/cucumber/cucumber-jvm
- **BDD Best Practices**: https://cucumber.io/docs/bdd/
- **Russian Gherkin**: https://cucumber.io/docs/gherkin/languages/

## Future Enhancements

### Potential Improvements
1. **Scenario Outlines**: Expand use of parameterized scenarios
2. **Tags**: Add @smoke, @regression tags for selective execution
3. **Custom Formatters**: Create Russian-language test reports
4. **Step Definition Refactoring**: Extract common patterns to helper methods
5. **Data Tables**: Use Gherkin data tables for complex scenarios
6. **Hooks**: Add @Before/@After hooks for common setup/teardown

### Advanced Features
1. **Parallel Execution**: Run scenarios in parallel for faster feedback
2. **Cucumber Reports Plugin**: Integrate advanced reporting plugin
3. **IDE Integration**: Configure IntelliJ IDEA Cucumber plugin
4. **Living Documentation**: Auto-generate stakeholder-friendly docs from features
5. **Example Mapping**: Document example mapping sessions in feature files

## Approval

- [x] ✅ Specification reviewed and approved
- [x] ✅ Implementation completed
- [x] ✅ All scenarios passing
- [x] ✅ Reports validated

---

**Status**: ✅ **COMPLETED**
**Date Completed**: 2025-10-21
**Scenarios**: 43 scenarios across 4 feature files
**Execution**: All scenarios pass, reports generated
**Integration**: Fully integrated with Maven and GitHub Actions
