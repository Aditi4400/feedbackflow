# Day 2 – Backend Foundation, Database Design & Spring Data JPA

## 1. Objective of Day 2

The objective of Day 2 was to build the backend foundation of FeedbackFlow using Spring Boot, Java, Spring Data JPA, and PostgreSQL.

On Day 1, we created the project structure and configured the development environment.

On Day 2, we started implementing the actual application architecture.

### What we covered

1. Backend package structure.
2. Database entity design.
3. JPA annotations.
4. Entity relationships.
5. Java enums.
6. Repository layer.
7. Spring Data JPA derived query methods.
8. Service layer and business logic.
9. DTOs (Data Transfer Objects).
10. Request validation.
11. Entity-to-DTO mapping.
12. Maven compilation and debugging.

---

# 2. Backend Package Structure

We organized our backend code into different packages under:

`com.feedbackflow`

The intended structure is:

```text
com.feedbackflow
│
├── config
│
├── controller
│
├── dto
│   ├── request
│   │   └── CreateFeedbackRequest.java
│   │
│   └── response
│       └── FeedbackResponse.java
│
├── entity
│   ├── User.java
│   ├── Workspace.java
│   ├── WorkspaceMember.java
│   └── Feedback.java
│
├── enums
│   ├── Sentiment.java
│   ├── FeedbackCategory.java
│   ├── FeedbackStatus.java
│   └── WorkspaceRole.java
│
├── exception
│
├── repository
│   ├── UserRepository.java
│   ├── WorkspaceRepository.java
│   ├── WorkspaceMemberRepository.java
│   └── FeedbackRepository.java
│
└── service
    └── FeedbackService.java
```

### Why did we create separate packages?

We follow a layered architecture.

Each layer has a separate responsibility.

| Layer | Responsibility |
|---|---|
| Controller | Receives HTTP requests and returns HTTP responses |
| Service | Contains business logic |
| Repository | Communicates with the database |
| Entity | Represents database tables |
| DTO | Transfers data between application layers and API clients |
| Config | Contains application configuration |
| Enums | Defines a fixed set of possible values |
| Exception | Handles application errors |

This separation makes the application easier to maintain, test, debug, and extend.

---

# 3. Understanding the Database Design

Before creating APIs, we need to decide what information our application will store.

For FeedbackFlow, we designed four main entities:

1. User
2. Workspace
3. WorkspaceMember
4. Feedback

## 3.1 User Entity

The User entity represents a registered user of FeedbackFlow.

Important fields:

| Field | Purpose |
|---|---|
| id | Unique user identifier |
| fullName | User's full name |
| email | Unique email address |
| passwordHash | Hashed password |
| createdAt | Account creation timestamp |
| updatedAt | Last update timestamp |

Important concepts:

- `@Entity`: Marks a Java class as a JPA entity.
- `@Table(name = "users")`: Specifies the database table name.
- `@Id`: Marks the primary key.
- `@GeneratedValue`: Automatically generates the primary key.
- `@Column(unique = true)`: Ensures email uniqueness at the database level.
- `@PrePersist`: Executes before a new entity is inserted.
- `@PreUpdate`: Executes before an existing entity is updated.

We used `users` as the table name rather than `user`, avoiding possible conflicts with database-reserved terminology.

## 3.2 Workspace Entity

A workspace represents a separate area where a team can manage feedback.

For example:

- Company A can have a workspace called Product Feedback.
- Company B can have a workspace called Mobile App Feedback.

Important fields:

| Field | Purpose |
|---|---|
| id | Unique workspace identifier |
| name | Workspace name |
| owner | User who owns the workspace |
| createdAt | Creation timestamp |
| updatedAt | Last update timestamp |

### Important relationship: Many-to-One

We used:

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "owner_id")
private User owner;
```

Meaning:

- One user can own multiple workspaces.
- Each workspace has one owner.

The `owner_id` column in the workspace table stores the ID of the owner.

## 3.3 WorkspaceMember Entity

This entity represents the membership of users in workspaces.

Why do we need it?

Suppose a workspace has five users:

- One OWNER
- One ADMIN
- Three MEMBER users

We need to store not only which users belong to the workspace, but also what role each user has.

Important fields:

| Field | Purpose |
|---|---|
| id | Unique membership identifier |
| workspace | Workspace reference |
| user | User reference |
| role | Member's role |
| joinedAt | When the user joined |

### Why did we use WorkspaceMember instead of Many-to-Many directly?

A simple Many-to-Many relationship only represents membership.

But our application also needs additional information about the relationship, especially the user's role.

Therefore, we created an explicit join entity called `WorkspaceMember`.

The relationship becomes:

```text
User
  |
  | One-to-Many
  |
WorkspaceMember
  |
  | Many-to-One
  |
Workspace
```

This is a common database design pattern called an association entity or join entity.

We also used a unique constraint on:

`workspace_id + user_id`

This prevents the same user from being added to the same workspace more than once.

## 3.4 Feedback Entity

This is the main entity of FeedbackFlow.

It stores feedback submitted by users.

Important fields:

| Field | Purpose |
|---|---|
| id | Unique feedback ID |
| title | Feedback title |
| content | Full feedback description |
| sentiment | Positive, negative, or neutral |
| category | Bug, feature request, etc. |
| status | Current feedback status |
| priority | Feedback priority |
| workspace | Workspace to which feedback belongs |
| createdAt | Creation timestamp |
| updatedAt | Last update timestamp |

### Important relationship

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "workspace_id")
private Workspace workspace;
```

Meaning:

- One workspace can contain multiple feedback records.
- Each feedback record belongs to one workspace.

### Why is this relationship important?

FeedbackFlow is a multi-workspace application.

Suppose:

```text
Workspace A
  ├── Feedback 1
  ├── Feedback 2
  └── Feedback 3

Workspace B
  ├── Feedback 4
  └── Feedback 5
```

When a user opens Workspace A, we should only retrieve feedback belonging to Workspace A.

This is the foundation of workspace-level data isolation.

---

# 4. Important JPA Concepts and Annotations

JPA stands for Java Persistence API. It provides a standard way to map Java objects to relational database tables.

Hibernate is the JPA implementation used by Spring Boot in our project.

## 4.1 @Entity

```java
@Entity
public class Feedback {
}
```

Tells JPA that this class should be mapped to a database table.

## 4.2 @Table

```java
@Table(name = "feedback")
```

Specifies the database table name.

## 4.3 @Id

```java
@Id
private Long id;
```

Defines the primary key.

A primary key uniquely identifies each row in a table.

## 4.4 @GeneratedValue

```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

Tells the database to generate the ID automatically when a record is inserted.

## 4.5 @Column

```java
@Column(nullable = false, length = 200)
private String title;
```

Allows us to define database column constraints.

Examples:

- `nullable = false`: Value cannot be NULL.
- `unique = true`: Value must be unique.
- `length = 200`: Sets the maximum column length.

## 4.6 @Enumerated

```java
@Enumerated(EnumType.STRING)
private FeedbackStatus status;
```

Stores an enum in the database as text.

For example:

`NEW`, `REVIEWED`, `PLANNED`, `RESOLVED`

We chose `EnumType.STRING` rather than `EnumType.ORDINAL`.

Why?

ORDINAL stores enum positions such as 0, 1, 2, 3.

If we change the order of enum values later, existing database records could represent the wrong status.

STRING stores the actual enum name, making it safer and more readable.

## 4.7 @ManyToOne

Represents a relationship where multiple records reference one record.

Example: Multiple feedback records can belong to one workspace.

## 4.8 FetchType.LAZY

```java
@ManyToOne(fetch = FetchType.LAZY)
```

LAZY loading means Hibernate does not necessarily load the associated entity immediately.

For example, when retrieving feedback, we may only need its title and content. We don't always need to load the entire workspace object.

This can help avoid unnecessary database queries and data loading.

## 4.9 Lifecycle callbacks

We used:

```java
@PrePersist
@PreUpdate
```

These annotations allow us to automatically set timestamps.

- `@PrePersist`: Runs before inserting a new record.
- `@PreUpdate`: Runs before updating an existing record.

This avoids manually setting timestamps in every service method.

---

# 5. Enums in FeedbackFlow

Enums define a fixed set of allowed values.

We created four enums.

## 5.1 Sentiment

```java
public enum Sentiment {
    POSITIVE,
    NEGATIVE,
    NEUTRAL
}
```

Represents the sentiment of submitted feedback.

Later, AI can automatically identify sentiment.

## 5.2 FeedbackCategory

```java
public enum FeedbackCategory {
    BUG,
    FEATURE_REQUEST,
    PERFORMANCE,
    UX,
    OTHER
}
```

Represents the type of feedback.

For example:

- App crashes → BUG
- Add dark mode → FEATURE_REQUEST
- Slow loading → PERFORMANCE
- Confusing navigation → UX

## 5.3 FeedbackStatus

```java
public enum FeedbackStatus {
    NEW,
    REVIEWED,
    PLANNED,
    RESOLVED
}
```

Represents the current lifecycle stage of feedback.

Example workflow:

```text
NEW → REVIEWED → PLANNED → RESOLVED
```

## 5.4 WorkspaceRole

```java
public enum WorkspaceRole {
    OWNER,
    ADMIN,
    MEMBER
}
```

Defines the user's permission level within a workspace.

These roles will later help implement authorization and access control.

---

# 6. Repository Layer – Spring Data JPA

After creating entities, we created repositories to interact with the database.

Repositories are responsible for database operations.

We created:

- UserRepository
- WorkspaceRepository
- WorkspaceMemberRepository
- FeedbackRepository

Example:

```java
public interface FeedbackRepository
        extends JpaRepository<Feedback, Long> {

    List<Feedback> findByWorkspaceId(Long workspaceId);

    List<Feedback> findByWorkspaceIdAndStatus(
            Long workspaceId,
            FeedbackStatus status
    );
}
```

## 6.1 What is JpaRepository?

`JpaRepository` is provided by Spring Data JPA.

It gives us several built-in database operations without writing their implementations.

Examples:

| Method | Purpose |
|---|---|
| save() | Insert or update a record |
| findById() | Find a record using its ID |
| findAll() | Retrieve all records |
| deleteById() | Delete a record by ID |
| existsById() | Check whether an ID exists |
| count() | Count records |

Example:

```java
feedbackRepository.save(feedback);
```

We do not need to write an INSERT SQL query ourselves for this operation.

## 6.2 Derived Query Methods

One important concept we learned is that Spring Data JPA can generate queries from method names.

For example:

```java
findByWorkspaceIdAndStatus(
    Long workspaceId,
    FeedbackStatus status
);
```

We did not manually implement this method.

Spring Data JPA understands the method name and generates the query automatically.

It interprets:

| Method part | Meaning |
|---|---|
| findBy | Retrieve records |
| WorkspaceId | Filter by workspace ID |
| And | Combine conditions using AND |
| Status | Filter by status |

Conceptually, the generated SQL is:

```sql
SELECT *
FROM feedback
WHERE workspace_id = ?
AND status = ?;
```

### An important point about WorkspaceId

Our Feedback entity contains:

```java
private Workspace workspace;
```

It does not directly contain a `workspaceId` field.

Spring Data JPA understands nested entity properties.

`WorkspaceId` means:

- Navigate through the `workspace` property.
- Access the `id` property of Workspace.

This is why the method works.

## 6.3 Other derived query examples

```java
findByEmail(String email);

existsByEmail(String email);

findByOwnerId(Long ownerId);

findByWorkspaceId(Long workspaceId);

findByStatus(FeedbackStatus status);

findByTitleContaining(String keyword);
```

Spring Data JPA parses these method names and generates the corresponding queries.

## 6.4 What if a query is complicated?

We can use `@Query`.

Example:

```java
@Query("""
    SELECT f FROM Feedback f
    WHERE f.workspace.id = :workspaceId
    AND f.status = :status
    ORDER BY f.createdAt DESC
""")
List<Feedback> findRecentFeedback(
        @Param("workspaceId") Long workspaceId,
        @Param("status") FeedbackStatus status
);
```

This example uses JPQL.

JPQL works with Java entity names and properties rather than directly using database table and column names.

We can also use native SQL:

```java
@Query(
    value = """
        SELECT * FROM feedback
        WHERE workspace_id = :workspaceId
    """,
    nativeQuery = true
)
List<Feedback> findFeedbackUsingSql(
        @Param("workspaceId") Long workspaceId
);
```

### When should we use which approach?

- Simple filters: Derived query methods.
- Complex entity-based queries: JPQL with `@Query`.
- Database-specific SQL requirements: Native SQL.

---

# 7. Service Layer – Business Logic

We created `FeedbackService.java`.

The service layer contains the business logic of the application.

It sits between the controller and repository.

The expected flow is:

```text
Frontend
   |
   | HTTP Request
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
PostgreSQL
```

### Why do we need a service layer?

Suppose a user creates feedback.

The operation may involve:

1. Checking whether the workspace exists.
2. Creating a Feedback object.
3. Assigning the workspace to the feedback.
4. Saving the feedback.
5. Converting the saved entity into a response DTO.
6. Returning the response.

This is business logic.

We should not put all these responsibilities inside the controller.

## 7.1 @Service

```java
@Service
public class FeedbackService {
}
```

Tells Spring that this class contains service/business logic and should be managed as a Spring bean.

## 7.2 Constructor Injection

We used constructor-based dependency injection.

Example:

```java
private final FeedbackRepository feedbackRepository;
private final WorkspaceRepository workspaceRepository;

public FeedbackService(
        FeedbackRepository feedbackRepository,
        WorkspaceRepository workspaceRepository
) {
    this.feedbackRepository = feedbackRepository;
    this.workspaceRepository = workspaceRepository;
}
```

Spring automatically provides the required repository objects when creating the service.

This is called Dependency Injection.

Why is it useful?

- Reduces tight coupling.
- Makes testing easier.
- Avoids manually creating repository objects.
- Makes dependencies explicit.

## 7.3 @Transactional

```java
@Transactional
public FeedbackResponse createFeedback(...) {
}
```

A transaction groups database operations into a single logical unit.

If an operation fails, the transaction can be rolled back rather than leaving partially completed database changes.

For example, if a business operation updates multiple related records, transactions help maintain consistency.

---

# 8. DTO – Data Transfer Object

DTO stands for Data Transfer Object.

It is used to transfer data between different parts of an application, particularly between the backend and frontend.

We created two DTOs:

1. CreateFeedbackRequest
2. FeedbackResponse

## 8.1 Why not directly use Entity objects in APIs?

Our Feedback entity represents the database structure.

But the frontend should not necessarily receive every database field.

For example, a database entity may contain internal relationships or fields that should not be exposed.

DTOs let us control:

- Which fields the frontend can send.
- Which fields the backend returns.
- How data is represented in API requests and responses.

## 8.2 CreateFeedbackRequest

This DTO represents the data the frontend sends when creating feedback.

Our implementation:

```java
package com.feedbackflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateFeedbackRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
```

It contains only the fields required to create feedback.

The backend will assign fields such as ID, workspace, timestamps, and initial status.

## 8.3 FeedbackResponse

This DTO represents the data returned to the frontend.

We used a Java record:

```java
public record FeedbackResponse(
        Long id,
        String title,
        String content,
        Sentiment sentiment,
        FeedbackCategory category,
        FeedbackStatus status,
        Integer priority,
        Long workspaceId,
        LocalDateTime createdAt
) {}
```

A Java record is a concise way of defining an immutable data carrier.

Java automatically provides the constructor, accessors, equals(), hashCode(), and toString() methods.

The response DTO gives us control over exactly what information the API returns.

---

# 9. Request Validation

We used Jakarta Bean Validation annotations.

These help validate incoming request data.

## 9.1 @NotBlank

```java
@NotBlank(message = "Title is required")
private String title;
```

Prevents:

- null values
- empty strings
- strings containing only whitespace

## 9.2 @Size

```java
@Size(max = 200)
private String title;
```

Ensures that the title does not exceed 200 characters.

## 9.3 @Email

We can use this annotation for validating email addresses in future registration DTOs.

## 9.4 @Valid

When we add a controller endpoint, we will use `@Valid` to trigger validation.

Example:

```java
@PostMapping
public ResponseEntity<FeedbackResponse> createFeedback(
        @Valid @RequestBody CreateFeedbackRequest request
) {
    // Call service
}
```

Important:

- `@RequestBody` converts incoming JSON into a Java object.
- `@Valid` triggers the validation rules defined on that object.
- If validation fails, Spring raises a validation exception.

We also need the `spring-boot-starter-validation` dependency for these annotations and validation integration.

---

# 10. Entity-to-DTO Mapping

We discussed why we should not directly return database entities from our APIs.

Instead, we convert entities into DTOs.

For example:

```java
private FeedbackResponse mapToResponse(Feedback feedback) {
    return new FeedbackResponse(
            feedback.getId(),
            feedback.getTitle(),
            feedback.getContent(),
            feedback.getSentiment(),
            feedback.getCategory(),
            feedback.getStatus(),
            feedback.getPriority(),
            feedback.getWorkspace().getId(),
            feedback.getCreatedAt()
    );
}
```

This process is called Entity-to-DTO Mapping.

Why is it important?

- Prevents exposing unnecessary entity fields.
- Avoids exposing internal database relationships.
- Gives frontend a predictable response structure.
- Separates API contracts from database design.

---

# 11. Database Schema Generation

We configured Hibernate to automatically create or update database tables during development.

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### What does ddl-auto=update mean?

Hibernate checks the entity definitions and updates the database schema accordingly.

For example:

If we create a new entity called Feedback, Hibernate can create the corresponding database table.

### Other ddl-auto options

| Value | Meaning |
|---|---|
| none | Does not automatically manage schema |
| validate | Checks whether database schema matches entities |
| update | Attempts to update schema |
| create | Creates schema from scratch |
| create-drop | Creates schema and drops it when the application shuts down |

For our learning project, `update` is convenient.

For production-like applications, database migrations using Flyway or Liquibase are generally preferable.

---

# 12. Important Errors and Debugging During Day 2

We also learned how to debug Java compilation errors.

## Issue 1: Java could not find classes

Initially, Maven reported errors such as:

```text
cannot access FeedbackCategory
bad source file
```

Similar errors appeared for Workspace, FeedbackRepository, and FeedbackResponse.

We investigated the relevant source files and found that the files needed to be saved with their actual class definitions.

Lesson:

A Java file must contain the expected class or interface, and its package declaration must match its directory structure.

For example:

```text
com/feedbackflow/enums/FeedbackCategory.java
```

should declare:

```java
package com.feedbackflow.enums;
```

## Issue 2: Cannot find symbol – CreateFeedbackRequest

Maven later reported:

```text
cannot find symbol
symbol: class CreateFeedbackRequest
location: class FeedbackService
```

The request DTO existed in:

```text
com.feedbackflow.dto.request
```

But FeedbackService needed to import it.

We identified the required import:

```java
import com.feedbackflow.dto.request.CreateFeedbackRequest;
```

Lesson:

Java classes in different packages generally need to be imported before they can be referenced by simple class name.

## Issue 3: Lombok and getter methods

During the earlier compilation errors, missing getter methods also appeared.

For example:

```text
cannot find symbol: method getTitle()
```

This can happen when Lombok-generated methods are not available to the compiler, or when the underlying source class has other compilation problems.

We should verify Lombok dependency and annotation processing if these errors remain.

---

# 13. Important Terms Learned on Day 2

| Term | Explanation |
|---|---|
| JPA | Java standard for mapping objects to relational databases |
| Hibernate | ORM framework implementing JPA |
| ORM | Object Relational Mapping; connects Java objects with database tables |
| Entity | Java class mapped to a database table |
| Primary Key | Unique identifier for a database record |
| Foreign Key | Column referencing another table's primary key |
| One-to-Many | One record is related to multiple records |
| Many-to-One | Multiple records reference one record |
| Many-to-Many | Multiple records can relate to multiple records |
| Repository | Layer responsible for database operations |
| JpaRepository | Spring Data interface providing common CRUD operations |
| Derived Query | Query automatically generated from a repository method name |
| JPQL | Query language based on Java entities |
| Native SQL | Direct SQL query executed against the database |
| Service Layer | Contains application business logic |
| Dependency Injection | Spring provides required objects to a class |
| Transaction | Group of database operations treated as one logical unit |
| DTO | Object used to transfer data between application boundaries |
| Validation | Checking whether incoming data satisfies defined rules |
| Enum | Fixed set of named constants |
| Lazy Loading | Loads related entities only when needed |
| Lifecycle Callback | Method executed at a particular entity persistence event |

---

# 14. How Everything Connects in FeedbackFlow

Consider the example of a user submitting new feedback.

### Step 1: Frontend sends a request

```json
{
  "title": "App crashes during login",
  "content": "The application crashes when I try to log in."
}
```

### Step 2: Controller receives the request

The controller will receive the JSON and convert it into a `CreateFeedbackRequest` object.

### Step 3: Validation takes place

Spring validates the title and content using `@NotBlank` and `@Size`.

### Step 4: Service processes the request

FeedbackService checks whether the workspace exists and prepares the Feedback entity.

### Step 5: Repository saves the entity

FeedbackRepository calls:

```java
feedbackRepository.save(feedback);
```

### Step 6: Hibernate communicates with PostgreSQL

Hibernate generates the appropriate INSERT SQL statement.

### Step 7: Entity is converted into a response DTO

The service converts the saved Feedback entity into FeedbackResponse.

### Step 8: Backend returns the response

The frontend receives a structured response containing the feedback ID, title, content, status, and other permitted fields.

The complete flow:

```text
React Frontend
      |
      | JSON Request
      v
FeedbackController
      |
      | CreateFeedbackRequest
      v
Validation
      |
      v
FeedbackService
      |
      | Business Logic
      v
FeedbackRepository
      |
      v
Hibernate / JPA
      |
      | SQL
      v
PostgreSQL
      |
      | Saved Feedback
      v
FeedbackService
      |
      | Entity-to-DTO Mapping
      v
FeedbackResponse
      |
      v
React Frontend
```

---

# 15. Day 2 Deliverables

The intended Day 2 implementation covers:

- [x] Defined the backend package structure.
- [x] Designed User entity.
- [x] Designed Workspace entity.
- [x] Designed WorkspaceMember entity.
- [x] Designed Feedback entity.
- [x] Defined Sentiment enum.
- [x] Defined FeedbackCategory enum.
- [x] Defined FeedbackStatus enum.
- [x] Defined WorkspaceRole enum.
- [x] Created repository interfaces.
- [x] Created FeedbackService.
- [x] Created CreateFeedbackRequest DTO.
- [x] Created FeedbackResponse DTO.
- [x] Added request validation annotations.
- [x] Learned Spring Data JPA derived query methods.
- [x] Learned how entities, repositories, services, and DTOs work together.

**Build verification:** The earlier missing-source-file errors were resolved. The latest reported compilation error was the missing `CreateFeedbackRequest` import. A final successful `./mvnw clean compile` result still needs to be confirmed.

---

# 16. What We Will Do Next

The next step is to expose the backend functionality through REST APIs.

Planned work:

1. Create FeedbackController.
2. Implement POST API to create feedback.
3. Implement GET API to retrieve feedback by workspace.
4. Implement GET API to retrieve feedback by status.
5. Add request validation using `@Valid`.
6. Test APIs using Postman or another API client.
7. Verify records in PostgreSQL.
8. Connect the React frontend to the backend.

After this, we will have the first working feedback-management flow.

---

# 17. Final Learning Summary

The most important learning from Day 2 is understanding the responsibilities of each backend layer.

- Entity defines how application data is represented in the database.
- Repository handles database access.
- Service contains business logic.
- DTO controls what data enters and leaves the application.
- Validation protects the application from invalid requests.
- JPA/Hibernate handles the conversion between Java objects and database records.
- Spring Data JPA can automatically generate common queries from repository method names.

These concepts form the foundation of most Spring Boot backend applications.

Day 2 was not just about creating Java classes. It was about understanding how a real backend application is structured and how its components communicate with one another.