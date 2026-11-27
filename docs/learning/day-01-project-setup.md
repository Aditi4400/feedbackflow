# FeedbackFlow – Daily Learning Documentation

# Day 1: Project Setup & Environment Configuration

**Date:** 25 November 2026
**Project:** FeedbackFlow
**Day:** 1
**Status:** Completed

---

## 1. Day 1 Objectives

The objective of Day 1 was to establish the foundation of a full-stack application using Java, Spring Boot, PostgreSQL, React, and TypeScript.

By the end of the day, we wanted to:

- Set up the backend development environment.
- Create a Spring Boot application.
- Configure PostgreSQL and connect it to Spring Boot.
- Set up the React + TypeScript frontend.
- Create a basic REST API.
- Establish communication between frontend and backend.
- Initialize a Git repository.

---

# PART A: Backend Setup – Java & Spring Boot

## 2. Java 22

### What is Java?

Java is a high-level, object-oriented programming language used to develop backend applications, enterprise systems, Android applications, and distributed systems.

We are using Java to develop the backend of FeedbackFlow.

### Why Java?

- Platform independent through the JVM.
- Strong object-oriented programming support.
- Good ecosystem for enterprise applications.
- Strong concurrency and multithreading support.
- Widely used in large-scale backend systems.

### What is JVM?

JVM stands for Java Virtual Machine.

It executes Java bytecode and allows Java applications to run on different operating systems.

Java compilation flow:

1. We write Java source code in `.java` files.
2. The Java compiler (`javac`) compiles it into `.class` bytecode.
3. JVM executes the bytecode.

### Important Java commands

Check installed Java version:

```bash
java -version
```

Check Java compiler:

```bash
javac -version
```

**Learning:** Java and JVM are not the same thing. Java is the programming language, whereas JVM is the runtime environment that executes Java bytecode.

---

## 3. Spring Boot

### What is Spring Framework?

Spring is a Java framework that helps us build enterprise applications.

It provides features such as:

- Dependency Injection.
- Inversion of Control.
- Database integration.
- Security.
- Web application development.
- Transaction management.

### What is Spring Boot?

Spring Boot is built on top of the Spring Framework.

It simplifies Spring application development by providing:

- Auto-configuration.
- Embedded servers.
- Starter dependencies.
- Production-ready features.
- Minimal manual configuration.

Without Spring Boot, we would need to configure many things manually.

With Spring Boot, we can start a web application with very little configuration.

### Spring Boot application entry point

Our application starts from:

`FeedbackflowApplication.java`

```java
@SpringBootApplication
public class FeedbackflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            FeedbackflowApplication.class,
            args
        );
    }
}
```

### Understanding @SpringBootApplication

This annotation combines three important annotations:

**1. @Configuration**

Marks the class as a source of Spring bean configuration.

**2. @EnableAutoConfiguration**

Tells Spring Boot to configure the application based on the dependencies and configuration available.

For example, when Spring Web is present, Spring Boot can configure an embedded web server.

**3. @ComponentScan**

Tells Spring to scan the current package and its subpackages for Spring-managed components such as:

- @Component
- @Service
- @Repository
- @Controller
- @RestController

### What is a Spring Bean?

A Spring Bean is an object managed by the Spring IoC container.

Instead of manually creating and managing every object, Spring manages their lifecycle and dependencies.

Example:

```java
@Service
public class FeedbackService {
}
```

Spring detects this class and creates a managed bean.

**Learning:** Spring Boot reduces configuration work, while the Spring Framework provides the underlying features such as dependency injection and IoC.

---

## 4. Maven and Maven Wrapper

### What is Maven?

Maven is a build automation and dependency management tool for Java projects.

It helps us:

- Download external libraries.
- Compile Java source code.
- Run tests.
- Package applications.
- Manage dependency versions.

### What is pom.xml?

`pom.xml` stands for Project Object Model.

It contains project configuration and dependency information.

For example:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

Maven reads this file and downloads the required libraries.

### Dependencies we selected

| Dependency        | Purpose                          |
| ----------------- | -------------------------------- |
| Spring Web        | Build REST APIs                  |
| Spring Data JPA   | Database persistence             |
| Spring Security   | Authentication and authorization |
| PostgreSQL Driver | Connect Java with PostgreSQL     |
| Validation        | Validate incoming requests       |
| Lombok            | Reduce boilerplate code          |
| Spring Boot Test  | Write automated tests            |

### What is Maven Wrapper?

Our project contains:

- `mvnw`
- `mvnw.cmd`
- `.mvn/wrapper/maven-wrapper.properties`

Maven Wrapper allows developers to run Maven without requiring a globally installed Maven version.

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

### Important Maven commands

Run application:

```bash
./mvnw spring-boot:run
```

Run tests:

```bash
./mvnw test
```

Clean generated files:

```bash
./mvnw clean
```

Package application:

```bash
./mvnw package
```

**Learning:** Maven manages the build lifecycle and dependencies. Maven Wrapper makes builds more consistent across different development machines.

---

# PART B: PostgreSQL Setup

## 5. PostgreSQL

### What is PostgreSQL?

PostgreSQL is an open-source relational database management system (RDBMS).

We use it to store FeedbackFlow application data.

For example, in the future, we will store:

- Users.
- Feedback.
- Categories.
- Comments.
- Feedback status.
- Timestamps.

### What is a relational database?

A relational database stores data in tables consisting of rows and columns.

For example, a future `users` table might look like:

| id | name  | email                                          |
| -- | ----- | ---------------------------------------------- |
| 1  | Rahul | [rahul@example.com](mailto\:rahul@example.com) |
| 2  | Priya | [priya@example.com](mailto\:priya@example.com) |

Tables can be connected using relationships such as primary keys and foreign keys.

### Database creation

We created a PostgreSQL database:

```sql
CREATE DATABASE feedbackflow;
```

We also created a dedicated database user:

```sql
CREATE USER feedbackflow_user
WITH PASSWORD 'YOUR_PASSWORD';
```

And assigned database ownership:

```sql
ALTER DATABASE feedbackflow
OWNER TO feedbackflow_user;
```

### Why create a separate database user?

It is better to use a dedicated application user instead of connecting our application as the PostgreSQL superuser.

Benefits:

- Better security.
- Controlled database permissions.
- Separation between application and administrator access.
- Easier permission management.

### Important PostgreSQL commands

Connect to database:

```bash
psql -h localhost -U feedbackflow_user -d feedbackflow -W
```

Check current database:

```sql
SELECT current_database();
```

List databases:

```sql
\l
```

List tables:

```sql
\dt
```

Exit PostgreSQL:

```sql
\q
```

---

## 6. Connecting Spring Boot to PostgreSQL

We configured:

`backend/src/main/resources/application.properties`

```properties
spring.application.name=feedbackflow

spring.datasource.url=jdbc:postgresql://localhost:5432/feedbackflow
spring.datasource.username=feedbackflow_user
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```

### Understanding the database configuration

**1. spring.datasource.url**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/feedbackflow
```

Breakdown:

- `jdbc`: Java Database Connectivity.
- `postgresql`: Database type.
- `localhost`: Database is running on our local machine.
- `5432`: Default PostgreSQL port.
- `feedbackflow`: Database name.

**2. spring.datasource.username**

```properties
spring.datasource.username=feedbackflow_user
```

The database user used by our application.

**3. spring.datasource.password**

```properties
spring.datasource.password=${DB_PASSWORD}
```

Spring Boot reads the password from an environment variable called `DB_PASSWORD`.

We set it in the terminal:

```bash
export DB_PASSWORD='YOUR_ACTUAL_PASSWORD'
```

### What is an environment variable?

An environment variable is a key-value configuration available to a running process.

It is commonly used for:

- Database passwords.
- API keys.
- Secret tokens.
- Environment-specific configurations.

Why not directly write passwords in application.properties?

Because application.properties is usually committed to Git.

If we commit actual passwords, anyone with repository access may be able to see them.

**Learning:** Sensitive configuration should be kept outside source-controlled configuration files.

### 7. Understanding JPA and Hibernate

We configured:

```properties
spring.jpa.hibernate.ddl-auto=update
```

What does this mean?

JPA stands for Java Persistence API. It provides a standard programming model for mapping Java objects to relational database tables.

Hibernate is a popular implementation of JPA.

For example, a Java class can represent a database table:

```java
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
```

Hibernate can use this entity mapping to create or update the corresponding database schema.

Other common `ddl-auto` values:

- `none`: No automatic schema management.
- `validate`: Validate that the database schema matches the entities.
- `update`: Update schema based on entity changes.
- `create`: Recreate the schema when starting.
- `create-drop`: Create the schema at startup and drop it at shutdown.

For local development, `update` is convenient.

For production, we should use database migration tools such as Flyway or Liquibase instead of relying on automatic schema updates.

---

# PART C: Spring Boot REST API

## 8. What is a REST API?

REST stands for Representational State Transfer.

REST is an architectural style used to design APIs that allow different applications to communicate over HTTP.

Our React frontend will communicate with our Spring Boot backend using REST APIs.

Common HTTP methods:

| Method | Purpose               |
| ------ | --------------------- |
| GET    | Retrieve data         |
| POST   | Create data           |
| PUT    | Update/replace data   |
| PATCH  | Partially update data |
| DELETE | Delete data           |

Example future FeedbackFlow APIs:

```text
GET    /api/feedback
GET    /api/feedback/{id}
POST   /api/feedback
PUT    /api/feedback/{id}
DELETE /api/feedback/{id}
```

## 9. Creating our first health endpoint

We created this package structure:

```text
com.feedbackflow/
│
├── FeedbackflowApplication.java
│
├── controller/
│   └── HealthController.java
│
├── service/
├── repository/
├── entity/
├── dto/
├── config/
└── exception/
```

### Why separate packages?

We use layered architecture to keep responsibilities separated.

- controller: Handles incoming HTTP requests.
- service: Contains business logic.
- repository: Handles database access.
- entity: Represents database entities.
- dto: Defines request and response objects.
- config: Contains application configurations.
- exception: Handles application exceptions.

This makes the application easier to maintain, test, and extend.

### HealthController.java

```java
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "message", "FeedbackFlow backend is running!"
        );
    }
}
```

### Understanding annotations

**@RestController**

Marks the class as a REST controller.

It combines `@Controller` and `@ResponseBody`.

The returned Java object is serialized into JSON.

**@RequestMapping("/api")**

Defines the common URL prefix for the controller.

**@GetMapping("/health")**

Maps HTTP GET requests to the health method.

Therefore, the complete endpoint is:

```http
GET /api/health
```

**Map.of()**

Creates an immutable Java Map containing key-value pairs.

Spring converts this Map into JSON.

### Testing the API

We tested using:

```bash
curl http://localhost:8080/api/health
```

Expected response:

```json
{
  "status": "UP",
  "message": "FeedbackFlow backend is running!"
}
```

**Learning:** We have successfully created our first REST endpoint and returned JSON from Spring Boot.

Note: This is an application liveness check. It does not independently verify PostgreSQL health.

---

## 10. Spring Security Configuration

We included Spring Security in our backend dependencies.

### What is Spring Security?

Spring Security is a framework that provides:

- Authentication.
- Authorization.
- Password encoding.
- Security filters.
- Protection against common web attacks.

### Authentication vs Authorization

Authentication answers:

"Who are you?"

Authorization answers:

"What are you allowed to access?"

Example:

- Authentication: A user logs in with their email and password.
- Authorization: Only an administrator can delete another user's account.

### Why did we configure SecurityFilterChain?

Spring Security secures endpoints by default.

We wanted our health endpoint to be accessible without logging in.

We created:

`config/SecurityConfig.java`

```java
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/health")
                .permitAll()
                .anyRequest()
                .authenticated()
            );

        return http.build();
    }
}
```

Important concepts:

- `@Configuration`: Defines application configuration.
- `@Bean`: Registers the returned object in Spring's IoC container.
- `SecurityFilterChain`: Defines how HTTP requests are secured.
- `permitAll()`: Allows access without authentication.
- `authenticated()`: Requires an authenticated user.
- `csrf.disable()`: Disables CSRF protection for this development API setup.

**Learning:** We can define different security rules for different API endpoints.

Important: CSRF should not be disabled blindly in production. Its configuration depends on the authentication mechanism and whether the application uses browser cookies or stateless bearer tokens.

---

# PART D: React + TypeScript Frontend

## 11. What is React?

React is a JavaScript library for building user interfaces.

It allows us to create reusable UI components.

Examples of future FeedbackFlow components:

- Navbar.
- Sidebar.
- FeedbackCard.
- FeedbackForm.
- DashboardStats.
- UserProfile.

### What is TypeScript?

TypeScript is a programming language built on JavaScript that adds static typing.

Example:

JavaScript:

```javascript
function add(a, b) {
    return a + b;
}
```

TypeScript:

```typescript
function add(a: number, b: number): number {
    return a + b;
}
```

Benefits:

- Type safety.
- Better editor support.
- Easier refactoring.
- Catching many errors before runtime.
- Better maintainability for larger applications.

### What is Vite?

Vite is a frontend build tool and development server.

We used it to create and run our React + TypeScript application.

Important commands:

```bash
npm install
```

Installs project dependencies.

```bash
npm run dev
```

Starts the local development server.

```bash
npm run build
```

Creates an optimized production build.

Our frontend runs on:

```text
http://localhost:5173
```

---

## 12. Frontend Libraries

We installed the following libraries:

### Axios

Axios is an HTTP client used to make requests to backend APIs.

Example:

```typescript
const response = await axios.get('/api/health');
```

It supports GET, POST, PUT, PATCH, and DELETE requests.

### TanStack Query

TanStack Query helps manage server state.

It provides:

- Data fetching.
- Caching.
- Loading states.
- Error handling.
- Automatic refetching.
- Mutation management.

We will use it when we start building feedback APIs.

### React Router DOM

Used for client-side navigation.

Future routes:

```text
/login
/register
/dashboard
/feedback
/profile
```

### Lucide React

Provides reusable icons for the frontend.

Example:

```tsx
import { Heart } from 'lucide-react';

<Heart size={24} />
```

### Tailwind CSS

Tailwind CSS is a utility-first CSS framework.

It allows us to style components using utility classes instead of writing all CSS manually.

Tailwind setup should follow its current Vite integration instructions. This is a separate setup item to verify if it hasn't been completed yet.

---

# PART E: Frontend and Backend Integration

## 13. How does React communicate with Spring Boot?

Our architecture:

```text
React + TypeScript
localhost:5173
       |
       | HTTP Request
       |
       v
Vite Development Proxy
       |
       | Forwards /api requests
       |
       v
Spring Boot
localhost:8080
       |
       |
       v
REST Controller
       |
       |
       v
JSON Response
```

### Step 1: Configure Vite proxy

We configured:

```typescript
server: {
    proxy: {
        '/api': {
            target: 'http://localhost:8080',
            changeOrigin: true,
        },
    },
},
```

What does this do?

When React calls:

```text
/api/health
```

Vite forwards the request to:

```text
http://localhost:8080/api/health
```

### Why use a proxy?

Our frontend and backend run on different ports.

Without appropriate cross-origin configuration, the browser can block requests due to CORS restrictions.

A Vite proxy lets our frontend send same-origin development requests, which Vite forwards to the backend.

Note: This is a development configuration, not a production deployment architecture.

### Step 2: Create an API service

We created:

`src/services/healthService.ts`

```typescript
import axios from 'axios';

interface HealthResponse {
    status: string;
    message: string;
}

export const checkBackendHealth = async (): Promise<HealthResponse> => {
    const response = await axios.get<HealthResponse>('/api/health');
    return response.data;
};
```

### Important TypeScript concepts

**Interface**

```typescript
interface HealthResponse {
    status: string;
    message: string;
}

```

An interface defines the expected structure of an object.

**Promise**

```typescript
Promise<HealthResponse>
```

Indicates that the function returns a Promise that resolves to a HealthResponse object.

**async/await**

Used for writing asynchronous code in a readable way.

**axios.get\<T>()**

The generic type `T` tells TypeScript the expected response data structure.

Important: This is a compile-time type declaration. It does not validate the actual JSON response at runtime.

### Why create a separate services folder?

Instead of writing API calls directly inside every React component, we keep API communication in a dedicated layer.

Benefits:

- Reusability.
- Separation of concerns.
- Easier testing.
- Centralized API handling.
- Cleaner components.

---

## 14. React Hooks Used

We updated App.tsx using React's useState and useEffect hooks.

### useState

Used to manage component state.

```typescript
const [status, setStatus] = useState('Checking...');
```

Here:

- `status` is the current state value.
- `setStatus` updates the state.
- `Checking...` is the initial value.

When the state changes, React re-renders the component.

### useEffect

Used to perform side effects in a component.

```typescript
useEffect(() => {
    checkBackendHealth()
        .then((data) => {
            setStatus(data.status);
        })
        .catch(() => {
            setStatus('DOWN');
        });
}, []);
```

The empty dependency array means this effect runs after the component mounts, rather than after every render.

### Promise handling

We used:

- `.then()` to handle successful API responses.
- `.catch()` to handle API errors.

### Why use these hooks?

We wanted the frontend to automatically check whether the backend is running when the application loads.

If the backend responds successfully, we display UP.

If the request fails, we display DOWN.

---

# PART F: Git and Version Control

## 15. What is Git?

Git is a distributed version control system.

It tracks changes in our code and helps developers collaborate.

### Git vs GitHub

Git:

- Version control system.
- Runs locally.
- Tracks code changes.
- Creates commits and branches.

GitHub:

- Cloud-based platform.
- Hosts Git repositories.
- Supports collaboration.
- Provides Pull Requests and code reviews.

### We initialized Git

```bash
git init
```

This created a local Git repository in our FeedbackFlow project root.

### Understanding Git status

```bash
git status
```

Shows:

- Current branch.
- Modified files.
- Untracked files.
- Staged changes.

### Git add

```bash
git add .
```

Stages changes for the next commit.

### Git commit

```bash
git commit -m "chore: initialize FeedbackFlow project"
```

Creates a commit containing the staged changes.

### Git push

```bash
git push -u origin main
```

Uploads local commits to a remote GitHub repository after configuring the remote.

Note: Initializing Git does not automatically upload anything to GitHub.

### What is .gitignore?

We have a `.gitignore` file to prevent unnecessary or sensitive files from being tracked.

Examples:

```gitignore
node_modules/
target/
.env
.idea/
.DS_Store
```

Why ignore these?

- `node_modules/`: Dependencies can be installed using package.json and package-lock.json.
- `target/`: Generated Maven build output.
- `.env`: May contain secrets.
- `.idea/`: IDE-specific configuration.
- `.DS_Store`: macOS-generated metadata.

**Learning:** Git should track source code and required configuration, not build output, installed dependencies, or secrets.

---

# PART G: Debugging and Troubleshooting

## 16. Problems encountered on Day 1

### Problem 1: Maven command not found

We initially encountered:

```text
zsh: command not found: mvn
```

We learned that Maven was not available through the global PATH.

Instead of depending on globally installed Maven, we used:

```bash
./mvnw spring-boot:run
```

This taught us about Maven Wrapper.

### Problem 2: LF and CRLF warning

We encountered:

```text
LF will be replaced by CRLF
```

This is related to line endings.

- LF: Common on Linux and macOS.
- CRLF: Common on Windows.

This was a warning, not a Git failure.

### Problem 3: Environment variable configuration

We learned that:

```properties
spring.datasource.password=${DB_PASSWORD}
```

does not directly contain a password.

Spring Boot resolves the placeholder using available configuration sources, including environment variables.

We set:

```bash
export DB_PASSWORD='YOUR_PASSWORD'
```

### Problem 4: Port 8080 already in use

We encountered:

```text
Web server failed to start.
Port 8080 was already in use.
```

We learned that only one process can bind to a particular IP address and port combination at a time.

To identify the process:

```bash
lsof -i :8080
```

We can stop the process using its PID:

```bash
kill <PID>
```

Or configure Spring Boot to use another port:

```properties
server.port=8081
```

### Important learning

Debugging is an important part of software engineering.

We should identify the root cause of an error instead of randomly changing configurations.

---

# PART H: Application Execution

## 17. How to run FeedbackFlow

We need two terminal sessions.

### Terminal 1: Backend

```bash
cd feedbackflow/backend

export DB_PASSWORD='YOUR_PASSWORD'

./mvnw spring-boot:run
```

Backend URL:

```text
http://localhost:8080
```

### Terminal 2: Frontend

```bash
cd feedbackflow/frontend

npm run dev
```

Frontend URL:

```text
http://localhost:5173
```

### Verify backend

```bash
curl http://localhost:8080/api/health
```

Expected response:

```json
{
    "status": "UP",
    "message": "FeedbackFlow backend is running!"
}
```

### Verify frontend

Open:

```text
http://localhost:5173
```

Expected result:

- FeedbackFlow heading.
- Backend Connection Status.
- Status: UP.
- Backend success message.

### Run automated checks

Backend:

```bash
./mvnw test
```

Frontend:

```bash
npm run build
```

These verify that the backend tests pass and the frontend can produce a production build.

---

# 18. Important Concepts to Revise

At the end of Day 1, these are the concepts I should be able to explain without looking at the documentation:

1. What is Java and JVM?
2. What is Spring Framework?
3. What is Spring Boot?
4. What is dependency injection?
5. What is a Spring Bean?
6. What is Maven?
7. What is Maven Wrapper?
8. What is a REST API?
9. What is the difference between GET and POST?
10. What is PostgreSQL?
11. What is JDBC?
12. What is JPA?
13. What is Hibernate?
14. What is an environment variable?
15. What is Spring Security?
16. What is authentication vs authorization?
17. What is React?
18. What is TypeScript?
19. What is Vite?
20. What are useState and useEffect?
21. What is Axios?
22. What is a Promise?
23. What is a Vite proxy?
24. What is Git?
25. What is the difference between Git and GitHub?
26. What are git add, commit, pull, and push?

---

# 19. Day 1 Final Outcome

At the end of Day 1, we have established the initial full-stack foundation of FeedbackFlow.

Our application currently has:

- A Java 22 + Spring Boot backend.
- A PostgreSQL database.
- A React + TypeScript frontend.
- A basic REST health-check endpoint.
- Frontend-to-backend HTTP communication.
- Initial Spring Security configuration.
- A local Git repository.

**Architecture established:**

React + TypeScript → Spring Boot REST API → PostgreSQL

The next objective is to start building actual application functionality instead of just testing connectivity.

---

# 20. Day 1 Future Improvements

These are not part of today's completed scope, but should be considered later:

- Add Flyway database migrations.
- Configure centralized exception handling.
- Create a reusable Axios instance.
- Add request and response interceptors.
- Configure environment-specific properties.
- Add structured logging.
- Add integration tests.
- Configure Docker for backend and database.
- Add CI/CD using GitHub Actions.
- Implement proper JWT-based authentication.
- Add health and database readiness monitoring using Spring Boot Actuator.
