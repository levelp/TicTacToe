# TicTacToe Project Instructions for AI Agents

> **Note**: This file follows the [AGENTS.md standard](https://agents.md/) for cross-tool compatibility with Cursor, GitHub Copilot, and other AI coding assistants. An identical [CLAUDE.md](CLAUDE.md) file is also maintained for Claude Code-specific support. Both files contain the same content and should be kept synchronized.

## Project Context

This is a TicTacToe game implementation in Java with both Swing GUI and console interfaces. The project uses Maven for build management and follows a model-view-controller architecture.

### Technology Stack
- **Language**: Java 8
- **Build Tool**: Maven
- **Testing**: JUnit 4.13.2
- **Coverage**: JaCoCo (100% coverage requirement for model package)
- **CI/CD**: GitHub Actions

### Project Structure
```
TicTacToe/
├── src/main/java/
│   ├── model/              # Game logic and state management
│   ├── view/               # User interface implementations
│   │   ├── swing/         # Swing GUI interface
│   │   └── console/       # Console interface
│   └── controller/         # Application entry points
├── src/test/java/model/    # Comprehensive test suite
├── .github/workflows/      # CI/CD workflows
└── codev/                  # Codev methodology files
```

## Codev Methodology

This project uses the Codev context-driven development methodology.

### Active Protocol
- **Protocol**: SPIDER-SOLO (single-agent variant)
- **Location**: codev/protocols/spider-solo/protocol.md
- **Reason**: Zen MCP server is not available, using self-review approach

### Alternative Protocols
- **TICK**: Fast autonomous implementation - `codev/protocols/tick/protocol.md`
  - Use for small features and bug fixes
- **SPIDER**: Multi-agent consultation - `codev/protocols/spider/protocol.md`
  - Requires Zen MCP server (currently not available)

### Directory Structure
- **Specifications**: codev/specs/ - What to build
- **Plans**: codev/plans/ - How to build it
- **Reviews**: codev/reviews/ - Lessons learned
- **Resources**: codev/resources/ - Reference materials

## Protocol Selection Guide

### Use SPIDER-SOLO for:
- New game features (e.g., different board sizes, AI opponent)
- Architecture changes
- Complex refactoring
- API design and implementation
- Performance optimization

### Use TICK for:
- Small features (< 300 lines)
- Bug fixes with known solutions
- Simple enhancements
- Utility additions
- Code quality improvements

### Skip formal protocols for:
- README typos
- Minor documentation updates
- Small configuration changes
- Dependency version updates

## Core Workflow

When implementing new features:

1. **Specify**: Create specification in `codev/specs/####-feature-name.md`
2. **Plan**: Create implementation plan in `codev/plans/####-feature-name.md`
3. **Implement-Defend-Evaluate** (IDE Loop):
   - **Implement**: Write the code
   - **Defend**: Write comprehensive tests
   - **Evaluate**: Verify all requirements are met
4. **Review**: Document lessons learned in `codev/reviews/####-feature-name.md`

### File Naming Convention
All Codev documents use the format: `####-descriptive-name.md`
- Example: `0001-ai-opponent.md` appears in specs/, plans/, and reviews/

### Git Integration
- Each major stage gets one pull request
- Phases can have multiple commits within the PR
- User approval required before creating PRs
- Branch naming: `claude/feature-name-<session-id>`

## Testing Requirements

### Coverage Standards
- **Model package**: 100% line coverage (enforced by JaCoCo)
- **View/Controller packages**: Excluded from coverage requirements
- All new code must include comprehensive tests
- Tests must cover edge cases and error conditions

### Running Tests
```bash
mvn clean test                    # Run tests
mvn jacoco:report                 # Generate coverage report
mvn jacoco:check                  # Verify coverage requirements
```

## Development Guidelines

### Code Style
- Follow existing code conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public APIs
- Keep methods focused and single-purpose

### Commit Messages
- Write clear, descriptive commit messages
- Include "why" not just "what"
- Reference issue/PR numbers when applicable
- Follow project commit message format

### Pull Request Process
1. Create feature branch from main/master
2. Implement changes with tests
3. Ensure all tests pass
4. Push to remote branch
5. User creates and reviews PR
6. Merge after approval

## Available Agents

Custom workflow agents are available in `.claude/agents/`:

- **spider-protocol-updater**: Analyzes SPIDER implementations and recommends improvements
- **architecture-documenter**: Maintains architecture documentation
- **codev-updater**: Updates Codev installation to latest version

## Getting Started with Codev

To start a new feature:
1. Choose appropriate protocol (SPIDER-SOLO or TICK)
2. Create specification in `codev/specs/`
3. Follow the protocol phases
4. Document lessons learned in `codev/reviews/`

For detailed protocol instructions, see: `codev/protocols/spider-solo/protocol.md`

## Important Notes

- **Context drives development**: Specifications come before code
- **Three documents per feature**: spec, plan, review (same filename)
- **Self-review approach**: SPIDER-SOLO uses careful self-examination
- **Human approval required**: Present for validation at key checkpoints
- **Continuous improvement**: Each review informs future development
