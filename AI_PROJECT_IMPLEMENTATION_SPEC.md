# AI Project Implementation Spec

Project: `journal-tracker`

Goal: give an AI a single, strict, complete brief so it can finish the project without skipping requirements.


## 1) Project summary

This is a Spring Boot backend for a scientific journal publication tracking system.

The system should support:
- importing and storing publication metadata
- managing users and roles
- searching papers and related metadata
- following journals and topics
- bookmarking papers
- generating notifications
- storing sync jobs and analytics/trends
- exporting dashboard/report data

Current stack:
- Java 21
- Spring Boot 4.0.x
- Spring Data JPA
- Hibernate as the JPA provider
- MySQL 8
- Docker Compose for local development

Current baseline in the repository:
- application bootstrap class exists
- a simple `/api/ping` endpoint exists
- JPA entities for the domain already exist
- repository interfaces already exist
- Docker Compose and Dockerfile already exist

## 2) What the AI must treat as the source of truth

The AI must use these in order:
1. Existing code in the repository
2. This specification file
3. The schema/domain already modeled in the project
4. The lecture topics from the chapter names above

The AI must not invent a new architecture unless explicitly requested.

## 3) Knowledge from the theory chapters and how it applies

### Chapter 01 - JPA, JPA Mapping

The AI must correctly implement:
- entity mapping with `@Entity`, `@Table`, `@Id`, `@Column`
- one-to-many, many-to-one, one-to-one, many-to-many concepts
- join tables as separate entities when there are payload columns
- composite keys using embeddable IDs
- fetch strategies and cascade rules
- lifecycle and audit timestamps
- validation annotations for entity input
- lazy loading pitfalls and relation ownership

### Chapter 02 - Hibernate Architecture - Configuration - Mapping

The AI must correctly implement:
- Hibernate as the ORM provider behind JPA
- datasource configuration
- dialect configuration
- schema generation strategy
- SQL logging
- transaction boundaries
- naming consistency between Java and database
- entity state handling and persistence context behavior

### Chapter 05 - Introduction to Spring Framework

The AI must correctly implement:
- IoC and dependency injection
- component scanning
- stereotype annotations such as `@Service`, `@Repository`, `@RestController`
- bean lifecycle and configuration
- separation between controller, service, repository, and entity layers

### Chapter 06 - Spring Web Development

The AI must correctly implement:
- REST controllers
- request mapping
- DTOs for request/response
- validation with Bean Validation
- pagination and sorting when needed
- clean HTTP status codes
- exception handling with a global handler
- JSON serialization/deserialization

## 4) Non-negotiable rules for the AI

The AI must:
- inspect existing files before changing anything
- change only the files needed for the requested feature
- keep package structure consistent
- prefer small, incremental edits
- preserve backwards compatibility unless asked otherwise
- use DTOs for API responses instead of exposing entities directly
- keep controllers thin
- keep business logic in services
- keep persistence logic in repositories
- validate inputs on the API boundary
- write or update tests for any non-trivial change
- update README or docs when behavior or run steps change
- explain what was changed and what remains after each task

The AI must not:
- rewrite the entire project unnecessarily
- remove working features while fixing a different area
- invent fields, relations, endpoints, or tables not in the spec
- hide uncertain decisions
- mark work as complete without compiling or validating
- add secrets to source control

If a requirement is ambiguous, the AI must stop and ask a concise question before coding.

## 5) Recommended implementation order

The AI should implement the project in this order:

1. Foundation and configuration
2. Data model and JPA mapping
3. Repository and service layer
4. REST APIs and validation
5. Search, filtering, pagination
6. Authentication and authorization
7. Sync integration with external sources
8. Notifications and user engagement
9. Analytics, trends, and reports
10. Testing, hardening, Docker, documentation

Do not jump to UI or advanced reporting before the data model and APIs are stable.

## 6) Detailed work breakdown

### Phase 0 - Project foundation

Tasks:
- confirm package naming and module layout
- ensure application starts successfully
- ensure MySQL connection works
- ensure Docker Compose starts MySQL and app
- define config profile strategy if needed

Deliverables:
- runnable app
- working DB connection
- `/api/ping` or equivalent health endpoint

Acceptance:
- `mvn spring-boot:run` works locally
- `docker compose up --build` works
- app can connect to MySQL

### Phase 1 - Database and JPA mapping

Tasks:
- review all entities against the database schema
- map primary keys and foreign keys
- map many-to-one relations first
- model join-table entities separately
- define composite IDs where required
- configure enums as strings
- add audit fields
- configure JSON/text columns where appropriate
- ensure table and column names match the schema

Core entities to stabilize first:
- `ApiSource`
- `AppUser`
- `Journal`
- `Author`
- `Keyword`
- `ResearchTopic`
- `ResearchPaper`
- `PaperAuthor`
- `PaperKeyword`
- `PaperTopic`

Second-wave entities:
- `SyncJob`
- `Bookmark`
- `JournalFollow`
- `TopicFollow`
- `Notification`
- `PublicationTrend`
- `DashboardReport`

Deliverables:
- entity classes
- ID classes for composite keys
- mapping validation

Acceptance:
- entities compile
- Hibernate can initialize the schema
- relation ownership is correct
- no broken foreign key mapping

### Phase 2 - Repository and service layer

Tasks:
- create repository methods for CRUD and lookup
- implement service interfaces
- add transactional boundaries
- centralize business rules in services
- add helper methods for entity creation/update

Deliverables:
- service classes
- repository query methods
- reusable domain helpers

Acceptance:
- service methods are unit-testable
- repository queries are named clearly
- no controller contains business rules

### Phase 3 - REST API layer

Tasks:
- create request/response DTOs
- create controllers for each module
- add validation annotations to requests
- return proper HTTP status codes
- add pagination and sorting where needed
- implement global exception handling

Recommended endpoint groups:
- `/api/auth`
- `/api/users`
- `/api/sources`
- `/api/journals`
- `/api/authors`
- `/api/keywords`
- `/api/topics`
- `/api/papers`
- `/api/bookmarks`
- `/api/follows`
- `/api/notifications`
- `/api/trends`
- `/api/reports`
- `/api/sync-jobs`

Acceptance:
- APIs return JSON DTOs
- validation errors are readable
- error responses are consistent

### Phase 4 - Search and paper discovery

Tasks:
- search papers by title, abstract, venue, DOI
- filter by year, journal, author, keyword, topic
- sort by citation count, year, newest, relevance
- implement pagination
- add paper detail endpoint

Deliverables:
- search endpoints
- query specification or criteria logic
- paper detail DTO

Acceptance:
- search is fast and predictable
- filters can be combined
- results are paginated

### Phase 5 - Authentication and authorization

Tasks:
- implement login/registration strategy
- choose JWT or session approach
- support roles such as admin, lecturer/student, researcher
- protect endpoints by role
- support local and Google auth if required by scope

Deliverables:
- auth flow
- security config
- role-based access control

Acceptance:
- unauthorized access is blocked
- user identity is attached to protected actions

### Phase 6 - Sync pipeline

Tasks:
- create external API client(s)
- implement sync job execution
- store sync job status and counters
- import journals, authors, papers, keywords, topics
- make sync idempotent
- log errors and partial success

Deliverables:
- sync service
- scheduler or manual trigger
- import mapping logic

Acceptance:
- repeated sync does not duplicate data
- sync job history is stored
- failures are traceable

### Phase 7 - Engagement features

Tasks:
- bookmark paper
- follow journal
- follow topic
- create notifications for new papers or trends
- mark notifications read/unread

Deliverables:
- engagement endpoints
- notification service

Acceptance:
- unique constraints are respected
- user actions create expected side effects

### Phase 8 - Analytics and reports

Tasks:
- compute publication trends
- store summary metrics
- build dashboard/report endpoints
- support export of report snapshots
- keep analytics calculations reproducible

Deliverables:
- trend calculation logic
- dashboard/report DTOs
- export endpoints

Acceptance:
- trend data matches filter scope
- report output is deterministic

### Phase 9 - Testing and hardening

Tasks:
- add unit tests for services
- add integration tests for repositories/controllers
- test validation and error handling
- test critical DB mappings
- verify Docker startup
- clean up logs and warnings

Deliverables:
- test suite
- validation coverage
- runbook

Acceptance:
- project builds cleanly
- tests pass
- startup works locally and in Docker

## 7) Suggested module ownership if work is split

If the project is divided among multiple people or multiple AI runs, split it like this:

### Module A - Platform and configuration
- Spring Boot config
- Docker
- database connection
- logging
- base exceptions

### Module B - Core data model
- entities
- mappings
- audit fields
- enums

### Module C - Search and content APIs
- papers
- journals
- authors
- keywords
- topics
- search/filter endpoints

### Module D - User and engagement
- users
- auth
- bookmarks
- follows
- notifications

### Module E - Sync and analytics
- external sync
- jobs
- trends
- dashboard reports

## 8) Required completion checklist for every AI task

Before coding:
- confirm scope
- read affected files
- list the files to be edited

During coding:
- make minimal changes
- keep naming consistent
- update related code paths

After coding:
- compile or run tests
- verify affected behavior
- summarize changes
- list remaining risks

## 9) Prompt template to give the AI

Use this prompt when asking the AI to implement a feature:

```text
You are working on a Spring Boot 4.0.6 + Spring Data JPA + MySQL 8 project named journal-tracker.

Your job:
1. Read the current code before editing.
2. Implement only the requested feature.
3. Follow the existing package structure and entity mappings.
4. Use DTOs for API input/output.
5. Keep controllers thin and put business logic in services.
6. Preserve existing behavior unless the request says otherwise.
7. If any requirement is ambiguous, stop and ask a concise question.
8. After editing, run validation/compile/tests for the changed area.
9. Report exactly what files changed, what was added, and what still remains.

Rules:
- Do not rewrite unrelated code.
- Do not invent missing requirements.
- Do not skip tests or validation.
- Do not expose entities directly from controllers.
- Do not mark the task done until the code compiles.

Output format:
- Summary
- Files changed
- Validation performed
- Risks / follow-up items
```

## 10) Prompt template for one specific feature

When asking the AI to work on a single feature, use:

```text
Feature: <name>
Goal: <what should exist at the end>
Scope: <files/modules allowed to change>
Inputs: <existing files, schema, constraints>
Expected output: <endpoints, services, tests, docs>
Acceptance criteria:
- <criterion 1>
- <criterion 2>
- <criterion 3>

Hard rules:
- inspect current code first
- do not modify unrelated modules
- do not guess column names or endpoint behavior
- add tests if the change is non-trivial
- compile and verify before reporting done
```

## 11) Definition of done

The project is only “done” when:
- the app starts without errors
- the database connects correctly
- the entities map correctly
- the required REST APIs work
- auth/roles are correct if required
- sync features work if required
- reports/trends work if required
- tests pass
- Docker runs correctly
- README/run instructions are correct

## 12) Practical warning for AI runs

If an AI run touches code:
- it should not stop after creating files only
- it must verify the code compiles
- it must keep the database model consistent
- it must explain any unresolved ambiguity

If the AI is unsure, it should ask before editing rather than “best guessing”.

