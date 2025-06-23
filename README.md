# FWC-ScoreBoard

A simple Java application that tracks football match scores in real time. Designed with test-driven development and clean architecture principles.

The project includes Maven Wrapper for build consistency, and static code analysis tools such as PMD, SpotBugs, and Checkstyle to check code quality and maintainability.

## Thinking process
Before starting development, I outlined edge cases and approached each of them using Test-Driven Development (TDD). The final implementation could be slightly more concise and polished - for example, by using Parameterized Tests. Additionally, mocking the repository with Mockito would have been a viable option. However, I intentionally kept the code as simple and straightforward as possible.

I also tried to simulate a structure similar to the one I’ve used in previous projects - with clear separation into layers (service, repository, model), with in-memory repository interface to mimic a more realistic architecture.

## How to run
#### Download
```bash
git clone https://github.com/SpellZZZ/FWC-ScoreBoard.git
cd FWC-ScoreBoard
```

#### Tests

```bash
./mvnw clean test
```

#### Build

```bash
./mvnw clean package
```

#### Check code analysis

You can run static code analysis and generate reports using:


```bash
./mvnw clean verify site
```
After execution, a local HTML report will be generated at: ```target/site/index.html```

Program build - verify phase pass: 

![obraz](https://github.com/user-attachments/assets/ed1088fc-38b4-4cc0-bd7a-ca345fdba069)


Jacoco raport in  ```target/site/jacoco/index.html```

Result of jacoco raport:

![obraz](https://github.com/user-attachments/assets/9d704abe-741f-4fff-8825-ec7ec6be953a)



---
## Class description

### Exception

- **`ScoreException`**  - Custom runtime exception thrown when an invalid score is provided (e.g. negative numbers).

- **`WCMatchException`**  - Custom runtime exception used for invalid match operations, such as duplicate team names or conflicting matches.

---

### Model
- **`Match`**  - Domain model class representing a football match. Includes team names, scores, and the match start time.

---

### Repository

- **`MatchRepository`**  - Interface that defines the contract for in-memory match storage.

- **`MatchRepositoryImpl`**  - Implementation of the repository using a simple list.

---

### Service

- **`ScoreBoard`**  - Service interface for managing football matches (start, update, finish, summarize).

- **`ScoreBoardImpl`**  - Business logic implementation of `ScoreBoard`, with validation and sorting behavior.

---
