Let's build FeedbackFlow from scratch, and this time we'll follow a fixed 14-day execution plan.

I want us to treat this like building a real SaaS product, not just completing a tutorial. We'll write the code ourselves, understand the architecture, test everything, and deploy a working application.

The target is to have a project that you can confidently demonstrate in interviews and explain at the code and system-design level.

# FeedbackFlow — 14-day development roadmap

# FeedbackFlow

14-day target

AI-powered customer feedback management and insights platform

[Feedia - feedback into actions](https://images.openai.com/static-rsc-4/r-yMclSEx53Z3RGcqgmy5Ij029U-XjZq7KDpX47o2SK00wJvNKroy0zfCn96l42lG4QLQsKYWqJtWTNicEbPvUkXbrno9TVwpf01FtklSIuqykNX46tYZt5zhtManr7hSe8OPxW3iM9Jup6LdDchOdY0xHJNsSJl9HzbsyVlvmc?purpose=inline)

[User feedback web app dashboard by Leandro Ubilla on Dribbble](https://images.openai.com/static-rsc-4/k6lGEojLXq8BrfXZHaqwfn5qSm81FZWHZnuskcIdLzR7KvGYD-xydXF7NzzHmbtoHzv26duUvLxpdFO9LWtcQY4QH6QWpBCnU0poX11RUwHSgfEP7jFUGxIWi0uvOaTUEHpwOTl0dwImKiBsH504eKLz-dZ2v8HEkQ87fQIEjwc?purpose=inline)

[Insights Dashboard - UI/UX by Design Buffs on Dribbble](https://images.openai.com/static-rsc-4/3zD3oaXs0R0bX_MhlOEEPXrgt0alx0z7M-TwMqbLBHTTuRl3W3ZH_u2EQAOTFPgx0Ubm6t4FAI_Vvg3BFwCxAhzWOtkWUHummE61e7ycEki14GwjzqQw_YbcrFRx-BpuusRDnTAa_98ay6Zd_3v5kw34rWHc3IepinBUZ0HdP0Y?purpose=inline)

16

Duration

14 days

Daily commitment

7–9 focused hours

Backend

Java + Spring Boot

Frontend

React + TypeScript

Database

PostgreSQL + pgvector

AI / Async

LLM + RAG + background jobs

## 1. What exactly are we building?

A user logs in, creates a workspace, and manages customer feedback.

For example, imagine a company has received these customer messages:

- "The application crashes whenever I try to make a payment."
- "Please add dark mode."
- "The new dashboard is very slow."
- "I love the new checkout experience."

FeedbackFlow will:

1. Store the feedback.
2. Automatically classify it.
3. Detect its sentiment.
4. Assign a category and priority.
5. Show analytics on the dashboard.
6. Allow users to ask AI questions about their feedback.
7. Retrieve relevant feedback and generate answers with evidence.

### Final application screens

[Feedia - feedback into actions](https://images.openai.com/static-rsc-4/r-yMclSEx53Z3RGcqgmy5Ij029U-XjZq7KDpX47o2SK00wJvNKroy0zfCn96l42lG4QLQsKYWqJtWTNicEbPvUkXbrno9TVwpf01FtklSIuqykNX46tYZt5zhtManr7hSe8OPxW3iM9Jup6LdDchOdY0xHJNsSJl9HzbsyVlvmc?purpose=inline)

1\. Dashboard

Feedback statistics, sentiment distribution, trends and categories.

[User Feedback Board: The user feedback tool for product teams | Sleekplan](https://images.openai.com/static-rsc-4/JK6XviX5y9dq47n_2hes96cQ_HzQs6srpNRAxLDDDZgy8BBDT25Giw04LDGKHJswagIklBXUT7ezr1rVg2cULRstvLeynwgB0_AsPXanCk4jHim8WOYRA7r-7-mUVAPGPUKdf85GT-ujYSoV5BHe0b2U7oZuXrlY7TqtyjWpkNc?purpose=inline)

2\. Feedback management

List, search, filter, create and update feedback.

[Run Batch Evaluations via CSV Upload](https://images.openai.com/static-rsc-4/sNIH7nV5vMLxjL-HPjRklH_wyLhvaHX18eCXm6IoW05TbZnZolmoHnS-4kJaHMrL7Y4HrcsNb6lweTGqWgC5zsgm_FD4UokfFgWNONv34b7JDFD7wa4bAQQCA9U0GJ23YUmvFvM2YI8NcOb3oHkL_AX7vWHcFH7LWSEKUT5PlTc?purpose=inline)

3\. Import feedback

Upload CSV and track asynchronous processing.

[Daysi — The AI Revenue Agent for Scaling Businesses](https://images.openai.com/static-rsc-4/SzrmQIOxoDoFSteUkSVTcY-GXWlP09NXK8dkRqK503BudR5hXvCRz9HxWkS_RgrLaTy_3Mpde0qNwB_G-5zMxtQh_u11ax3gs-6ifL0-icbitx3Zy80ufn0IVKob6lkZPcrmFCFsajAoh654sEQGYCuDotR25iSUMhE226sj3dY?purpose=inline)

4\. AI insights

Ask questions and receive evidence-backed answers.

## 2. Technology stack

We'll use a practical stack that teaches you concepts relevant to real software engineering jobs.

| Layer            | Technology                             | Why we're using it               |
| ---------------- | -------------------------------------- | -------------------------------- |
| Frontend         | React, TypeScript                      | UI and component architecture    |
| Styling          | Tailwind CSS                           | Fast, consistent UI              |
| API integration  | TanStack Query, Axios                  | Server state and API calls       |
| Backend          | Java 22, Spring Boot                   | REST APIs and business logic     |
| Security         | Spring Security, JWT                   | Authentication and authorization |
| ORM              | Spring Data JPA, Hibernate             | Database operations              |
| Database         | PostgreSQL                             | Relational data                  |
| Vector search    | pgvector                               | Embeddings and RAG               |
| Async processing | Spring `@Async` + database job records | Background processing            |
| AI               | LLM API + embedding API                | Classification and AI insights   |
| Testing          | JUnit, Mockito, Vitest/RTL             | Backend and frontend tests       |
| Deployment       | Docker, Docker Compose                 | Reproducible environments        |
| CI/CD            | GitHub Actions                         | Automated build and tests        |

One deliberate decision: We'll use PostgreSQL-backed job records and Spring's asynchronous executor for the first version instead of adding Redis and a separate message broker immediately. This gives us real async processing, job status, retries and idempotency without spending days on infrastructure.

We can add Redis and a dedicated queue after the MVP.

# 3. Architecture we'll build

React + TypeScript frontend

Dashboard · Feedback · Import · AI chat

HTTP / REST APIs

Spring Boot application

Controllers · Services · Repositories · Security

PostgreSQL

Users, workspaces, feedback, jobs, embeddings

Async executor

CSV processing, AI analysis, retries

AI service

LLM · Embeddings · Classification · RAG · AI answers

We'll start with a modular monolith. This means one Spring Boot application, but properly separated modules and responsibilities.

### Backend package structure

```
com.feedbackflow
│
├── auth
│   ├── controller
│   ├── service
│   ├── dto
│   └── security
│
├── workspace
│   ├── controller
│   ├── service
│   ├── repository
│   └── entity
│
├── feedback
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── dto
│
├── dashboard
│   ├── controller
│   └── service
│
├── importjob
│   ├── controller
│   ├── service
│   └── entity
│
├── ai
│   ├── service
│   ├── client
│   ├── prompt
│   └── rag
│
└── common
    ├── exception
    ├── config
    └── response
```

### Database design

We'll initially create these tables:

| Table                 | Responsibility                           |
| --------------------- | ---------------------------------------- |
| `users`               | User accounts                            |
| `workspaces`          | Company/project workspaces               |
| `workspace_members`   | User-workspace membership                |
| `feedback`            | Customer feedback and AI classifications |
| `import_jobs`         | CSV import job status and progress       |
| `feedback_embeddings` | Vector representations of feedback       |
| `chat_sessions`       | AI chat conversations                    |
| `chat_messages`       | User questions and AI answers            |

Relationships:

- One user can belong to multiple workspaces.
- One workspace has many feedback records.
- One workspace has many import jobs.
- One feedback record can have an embedding.
- One workspace has multiple chat sessions.
- One chat session has multiple messages.

We'll use workspace-level authorization so a user cannot access another workspace's feedback simply by changing an ID in an API request.

# 4. The detailed 14-day plan

## Week 1 — Full-stack foundation

Goal: Working application without AI

By the end of Week 1, users should be able to register, log in, create feedback, view feedback and see dashboard statistics.

### Day 1 — Requirements, architecture and project setup

Foundation day

Estimated time: 7–8 hours

Part A: Understand the product (1 hour)

Before coding, understand the user journey:

1. User opens FeedbackFlow.
2. User registers or logs in.
3. User enters a workspace.
4. User creates feedback.
5. User views the feedback dashboard.
6. User can search, filter and update feedback.

Write down the functional requirements and API requirements.

Part B: Set up the backend (2 hours)

Create a Spring Boot project with:

- Spring Web
- Spring Data JPA
- Spring Security
- PostgreSQL Driver
- Validation
- Lombok (optional)
- Spring Boot Test

Set up:

- Java 22
- Maven
- application configuration
- package structure
- Git repository

Part C: Set up PostgreSQL (1 hour)

Create a database called:

```
CREATE DATABASE feedbackflow;
```

Configure the database connection in Spring Boot.

Part D: Set up the frontend (2 hours)

Create React + TypeScript using Vite.

Install:

```
npm install
npm install axios
npm install @tanstack/react-query
npm install react-router-dom
npm install lucide-react
```

Set up Tailwind CSS using its current Vite integration instructions.

Create these folders:

```
src/
├── components/
├── pages/
├── layouts/
├── hooks/
├── services/
├── types/
├── context/
└── utils/
```

Part E: Initial integration (1–2 hours)

Create a Spring Boot health endpoint:

```
GET /api/health
```

Connect React to it and display the backend status.

Day 1 deliverable

- Spring Boot application runs.
- React application runs.
- PostgreSQL connection works.
- Frontend can call backend.
- Git repository initialized.

### Day 2 — Database design and backend fundamentals

Estimated time: 8 hours

Today we'll learn how to structure a backend properly.

Part A: Design entities (2 hours)

Create these initial entities:

1. User
2. Workspace
3. WorkspaceMember
4. Feedback

Example Feedback entity:

```
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private Sentiment sentiment;

    @Enumerated(EnumType.STRING)
    private FeedbackCategory category;

    @Enumerated(EnumType.STRING)
    private FeedbackStatus status;

    private Integer priority;

    private LocalDateTime createdAt;
}
```

We'll add workspace relationships, audit fields and constraints as part of the implementation.

Enums:

```
public enum Sentiment {
    POSITIVE,
    NEGATIVE,
    NEUTRAL
}
```

```
public enum FeedbackCategory {
    BUG,
    FEATURE_REQUEST,
    PERFORMANCE,
    UX,
    OTHER
}
```

```
public enum FeedbackStatus {
    NEW,
    REVIEWED,
    PLANNED,
    RESOLVED
}
```

Part B: Learn and implement JPA relationships (2 hours)

Understand:

- `@OneToMany`
- `@ManyToOne`
- `@OneToOne`
- `@ManyToMany`
- Lazy vs eager loading
- Foreign keys
- Database constraints

Implement the workspace-feedback relationship.

Part C: Repository and service layers (2 hours)

Implement:

- `UserRepository`
- `WorkspaceRepository`
- `FeedbackRepository`
- `FeedbackService`

Understand why controllers should not directly contain database queries.

Part D: DTOs and validation (2 hours)

Create request and response DTOs.

Learn:

- Why we don't expose entities directly.
- `@NotBlank`
- `@Size`
- `@Email`
- `@Valid`
- Mapping between entities and DTOs.

Day 2 deliverable

- Database tables created.
- Entity relationships working.
- Repository and service layers created.
- DTO-based API design established.

### Day 3 — Authentication and authorization

Estimated time: 8–9 hours

This is important because real SaaS products need secure access control.

Part A: Registration (2 hours)

Implement:

```
POST /api/auth/register
```

Request:

```
{
  "name": "John",
  "email": "john@example.com",
  "password": "password123"
}
```

Requirements:

- Validate email.
- Check duplicate email.
- Hash password using BCrypt.
- Save user.
- Create a default workspace.
- Add user as workspace owner.

Part B: Login using JWT (3 hours)

Implement:

```
POST /api/auth/login
```

Learn:

- What JWT is.
- Header, payload and signature.
- Access tokens.
- Token validation.
- Spring Security filters.
- Password hashing vs encryption.

Part C: Secure endpoints (2 hours)

Implement:

- Authentication filter.
- Security configuration.
- Public and protected routes.
- User identity extraction.

Part D: Test using Postman (1–2 hours)

Test:

- Registration.
- Duplicate registration.
- Successful login.
- Invalid password.
- Missing token.
- Invalid token.

Day 3 deliverable

- Register works.
- Login works.
- JWT authentication works.
- Passwords are hashed.
- Protected APIs reject unauthorized requests.

### Day 4 — Feedback CRUD APIs

Estimated time: 8 hours

Today we build the main business functionality.

Implement:

| Method | Endpoint             | Purpose              |
| ------ | -------------------- | -------------------- |
| POST   | `/api/feedback`      | Create feedback      |
| GET    | `/api/feedback`      | Get feedback list    |
| GET    | `/api/feedback/{id}` | Get feedback details |
| PUT    | `/api/feedback/{id}` | Update feedback      |
| DELETE | `/api/feedback/{id}` | Delete feedback      |

Part A: Create feedback (2 hours)

The user can submit:

- Title
- Description
- Customer name (optional)
- Source (manual, CSV)
- Category (initially OTHER or user-selected)
- Status

For now, AI-related fields will be populated later.

Part B: Read APIs (2 hours)

Implement:

- Pagination.
- Sorting.
- Filtering by sentiment.
- Filtering by category.
- Filtering by status.
- Search by title or content.

Example:

```
GET /api/feedback?page=0&size=10&status=NEW
```

Part C: Update and delete (1 hour)

Implement status updates and feedback deletion.

Part D: Exception handling (2 hours)

Learn and implement:

- `@ControllerAdvice`
- Custom exceptions.
- `ResponseEntity`
- Standard API error responses.
- Not found errors.
- Validation errors.

Part E: Security checks (1 hour)

Verify workspace ownership or membership on every operation.

Day 4 deliverable

- Complete feedback CRUD.
- Pagination and filtering.
- Search.
- Exception handling.
- Workspace-level data isolation.

### Day 5 — React frontend foundation

Estimated time: 8 hours

Now we start building the user interface.

Part A: Routing and layouts (2 hours)

Create:

```
pages/
├── Login.tsx
├── Register.tsx
├── Dashboard.tsx
├── Feedback.tsx
├── Import.tsx
└── AIInsights.tsx
```

Create:

- Sidebar.
- Header.
- Main content area.
- Protected routes.

Part B: Authentication integration (2 hours)

Implement:

- Registration page.
- Login page.
- Login API integration.
- Logout.
- Protected navigation.

For the MVP, use a short-lived access token and an appropriately protected browser storage strategy. A production-grade refresh-token and HttpOnly-cookie flow can be an extension.

Part C: API service layer (2 hours)

Create an Axios instance.

Implement:

- Base URL configuration.
- Authorization headers.
- Common error handling.
- API services.

Part D: React Query (2 hours)

Learn:

- `useQuery`
- `useMutation`
- Query keys.
- Query invalidation.
- Loading and error states.

Use React Query for feedback fetching and mutations.

Day 5 deliverable

- Login UI.
- Registration UI.
- Sidebar and layout.
- Protected routes.
- Backend integration.

### Day 6 — Feedback management UI

Estimated time: 8 hours

Build the main feedback screen.

Part A: Feedback list (2 hours)

Display:

- Title.
- Customer.
- Sentiment.
- Category.
- Priority.
- Status.
- Created date.

Part B: Create feedback modal (2 hours)

Build a form with:

- Title.
- Description.
- Customer.
- Source.

Connect it to the create API.

Part C: Search and filters (2 hours)

Implement:

- Search.
- Category filter.
- Sentiment filter.
- Status filter.
- Pagination.

Part D: Edit and delete (2 hours)

Implement status updates and deletion with confirmation.

Day 6 deliverable

- Feedback listing.
- Create feedback.
- Edit feedback.
- Delete feedback.
- Search and filtering.

### Day 7 — Dashboard and Week 1 integration

Estimated time: 8 hours

By now we should have a functional application. Today we'll make it feel like a real product.

Part A: Dashboard APIs (3 hours)

Implement:

```
GET /api/dashboard/summary
GET /api/dashboard/sentiment
GET /api/dashboard/categories
GET /api/dashboard/trends
```

Dashboard metrics:

- Total feedback.
- Positive feedback.
- Negative feedback.
- Neutral feedback.
- Open feedback.
- Resolved feedback.

Part B: Dashboard frontend (3 hours)

Create:

- Summary cards.
- Sentiment pie chart.
- Category distribution chart.
- Feedback trend chart.
- Recent feedback list.

Part C: Integration testing (2 hours)

Test the entire flow:

Register → Login → Create feedback → View dashboard → Update feedback.

Week 1 milestone

You should now have a complete full-stack feedback management application without AI.

# Week 2 — Async processing, AI, RAG and deployment

Goal: Complete the AI-powered product

### Day 8 — CSV upload and asynchronous job processing

Estimated time: 8–9 hours

This is where we introduce an important backend engineering concept: asynchronous processing.

Part A: CSV upload API (2 hours)

Implement:

```
POST /api/imports
```

Accept a CSV file containing:

```
title,content,customer_name
Payment issue,Payment fails on checkout,John
Dark mode,Please add dark mode,Alice
Slow dashboard,Dashboard takes too long to load,David
```

Validate:

- File type.
- File size.
- Required columns.
- Empty rows.
- Invalid data.

Part B: Create import job (2 hours)

Create an `ImportJob` entity:

```
ImportJob
---------
id
workspace_id
file_name
status
total_records
processed_records
failed_records
created_at
completed_at
error_message
```

Job statuses:

```
public enum ImportStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED
}
```

When a user uploads a file:

1. Validate the file.
2. Save the job record.
3. Return the job ID immediately.
4. Process the file in the background.

Part C: Async execution (3 hours)

Learn and implement:

- `@EnableAsync`
- `@Async`
- `ThreadPoolTaskExecutor`
- Thread pool size.
- Exception handling in async methods.
- Transaction boundaries.

Do not process the entire CSV inside the HTTP request.

Part D: Job status API (1–2 hours)

Implement:

```
GET /api/imports/{jobId}
```

Return:

```
{
  "jobId": 101,
  "status": "PROCESSING",
  "totalRecords": 100,
  "processedRecords": 65,
  "failedRecords": 2
}
```

Day 8 deliverable

- CSV upload.
- Import job creation.
- Background processing.
- Job progress API.

### Day 9 — Async reliability and frontend import flow

Estimated time: 8 hours

A real application needs to handle failures.

Part A: Idempotency (2 hours)

Understand:

What happens if a job gets executed twice?

Implement an idempotent processing strategy, such as a unique constraint on the import job and row number for imported records.

Part B: Retry handling (2 hours)

Implement:

- Retry count.
- Maximum attempts.
- Failed job handling.
- Error messages.
- Retry only eligible failures.

Distinguish between transient failures and invalid input. Retrying an invalid CSV row repeatedly won't fix it.

Part C: Frontend CSV upload (2 hours)

Create:

- Upload component.
- File validation.
- Upload progress.
- Job status.
- Processed/failed record count.

Part D: Polling (2 hours)

Use React Query polling to check job status every few seconds while the job is active. Stop polling after completion or terminal failure.

Day 9 deliverable

- Reliable background import.
- Retry support.
- Idempotent processing.
- Import progress UI.
- Error visibility.

### Day 10 — AI integration and prompt engineering

Estimated time: 8–9 hours

This is a major part of the project.

We'll integrate an LLM API. Keep the provider behind an interface so we can change models later.

Part A: Understand LLM integration (1 hour)

Learn:

- What an LLM is.
- Tokens and context windows.
- Temperature.
- System prompts vs user prompts.
- Structured output.
- API timeouts and rate limits.

Part B: AI service integration (2 hours)

Create an AI service:

```
AIService
├── classifyFeedback()
├── analyzeSentiment()
├── generateSummary()
└── answerQuestion()
```

Part C: Prompt engineering (2 hours)

Create a classification prompt.

Example:

```
You are a customer feedback analysis assistant.

Analyze the feedback provided.

Return:
1. Sentiment: POSITIVE, NEGATIVE or NEUTRAL
2. Category: BUG, FEATURE_REQUEST, PERFORMANCE, UX or OTHER
3. Priority: 1 to 5
4. Short summary

Rules:
- Do not invent information.
- Return valid JSON.
- Use only the provided feedback.
- If the category is unclear, select OTHER.
```

Use structured JSON output and validate the response before saving it.

Part D: AI classification (2 hours)

When feedback is created or imported, trigger AI analysis asynchronously.

Save:

- Sentiment.
- Category.
- Priority.
- Summary.
- Analysis status.

Part E: AI failure handling (1–2 hours)

Handle:

- Invalid AI responses.
- API timeout.
- Rate limits.
- Empty response.
- Model errors.

Day 10 deliverable

- LLM API integrated.
- Prompt templates created.
- AI sentiment classification.
- AI categorization.
- AI priority and summary.
- AI analysis status stored.

### Day 11 — Embeddings and vector search

Estimated time: 8–9 hours

Today we'll learn how to make the application retrieve semantically relevant feedback.

Part A: Understand embeddings (2 hours)

Learn:

- What embeddings are.
- How text becomes a vector.
- Cosine similarity.
- Semantic search vs keyword search.
- Vector databases.

Example:

A customer asks:

"Are users facing payment problems?"

The database should retrieve feedback such as:

- "Checkout transaction is failing."
- "My card payment doesn't complete."
- "Payment page shows an error."

These may not contain the exact words in the question, but they are semantically related.

Part B: Configure pgvector (2 hours)

Enable the PostgreSQL `vector` extension.

Create a table or column to store embeddings. We'll use a fixed embedding model and its corresponding vector dimension.

Part C: Generate embeddings (2 hours)

For each feedback record:

1. Prepare the text.
2. Call the embedding API.
3. Store the vector.
4. Link it to the feedback record.

Part D: Implement semantic search (2 hours)

Create:

```
POST /api/ai/search
```

The API should:

1. Generate an embedding for the search query.
2. Search similar feedback records.
3. Return relevant results ranked by similarity.

Day 11 deliverable

- pgvector configured.
- Embedding generation.
- Embedding storage.
- Semantic search API.

### Day 12 — RAG-powered AI assistant

Estimated time: 8–9 hours

This is the most important AI feature of FeedbackFlow.

We'll build RAG: Retrieval-Augmented Generation.

How RAG works

User question

"What are the most common customer complaints?"

Generate query embedding

Convert the question into a vector.

Retrieve relevant feedback

Search similar records from the workspace.

Build context and prompt

Send the question and retrieved feedback to the LLM.

Evidence-backed answer

Answer with references to actual feedback records.

Part A: Implement retrieval (2 hours)

Retrieve the top relevant feedback records from the user's workspace.

Part B: Context construction (2 hours)

Build a prompt containing:

- User's question.
- Retrieved feedback.
- Feedback IDs.
- Relevant metadata.

Important: workspace filtering must happen in the retrieval query itself, not just in the frontend.

Part C: LLM answer generation (2 hours)

Instruct the LLM to:

- Answer only from supplied evidence.
- Mention when evidence is insufficient.
- Avoid inventing statistics.
- Include references to feedback IDs.

Part D: Chat APIs (2 hours)

Implement:

```
POST /api/ai/chat
GET /api/ai/chat/sessions
GET /api/ai/chat/sessions/{sessionId}
```

Save the conversation and its messages.

Day 12 deliverable

- RAG pipeline.
- Relevant feedback retrieval.
- AI-generated answers.
- Evidence references.
- Chat history persistence.

### Day 13 — AI chat UI, testing and quality

Estimated time: 8 hours

Part A: AI chat UI (2 hours)

Create a chat interface with:

- Message input.
- User messages.
- AI responses.
- Loading state.
- Error state.
- Feedback references that link back to records.

Part B: Backend testing (3 hours)

Learn and implement:

- JUnit 5.
- Mockito.
- `@SpringBootTest`.
- `@WebMvcTest`.
- Mocking external AI API calls.

Test:

- Feedback CRUD.
- Authentication.
- Workspace isolation.
- Import job processing.
- AI response validation.
- RAG retrieval filtering.

Part C: Frontend testing (1 hour)

Test:

- Login.
- Feedback creation.
- Feedback list.
- Import status.
- AI chat response rendering.

Part D: Manual testing and bug fixes (2 hours)

Test the complete workflow with multiple users and workspaces.

Day 13 deliverable

- AI chat interface.
- Backend tests.
- Frontend tests.
- Error handling.
- Multi-workspace isolation checks.

### Day 14 — Docker, deployment and final polish

Estimated time: 8–9 hours

Today we make FeedbackFlow accessible as a real deployed product.

Part A: Dockerize the application (2 hours)

Create Dockerfiles for:

- Frontend.
- Backend.

Create Docker Compose for:

- Frontend.
- Backend.
- PostgreSQL.

Include pgvector in the PostgreSQL image.

Part B: Environment configuration (1 hour)

Move secrets and environment-specific values into environment variables.

For example:

```
DATABASE_URL=
DATABASE_USERNAME=
DATABASE_PASSWORD=
JWT_SECRET=
LLM_API_KEY=
EMBEDDING_API_KEY=
```

Never commit real secrets to Git.

Part C: Production readiness (2 hours)

Check:

- Database migrations.
- CORS.
- Logging.
- API errors.
- Authentication.
- Environment configuration.
- AI API timeouts.
- File upload limits.

Part D: Deployment (2 hours)

Deploy the application using a hosting setup that supports the backend, persistent PostgreSQL storage and required vector extension.

Part E: Documentation and demo (1–2 hours)

Prepare:

- README.
- Architecture diagram.
- Database schema diagram.
- API documentation.
- Screenshots.
- Demo video.
- Known limitations.

Week 2 milestone

FeedbackFlow is now a complete, deployed, AI-powered full-stack application.

# 5. API checklist — What we'll have at the end

This is the expected MVP API surface.

| Module    | API                          | Method |
| --------- | ---------------------------- | ------ |
| Auth      | `/api/auth/register`         | POST   |
| Auth      | `/api/auth/login`            | POST   |
| Workspace | `/api/workspaces/current`    | GET    |
| Feedback  | `/api/feedback`              | GET    |
| Feedback  | `/api/feedback`              | POST   |
| Feedback  | `/api/feedback/{id}`         | GET    |
| Feedback  | `/api/feedback/{id}`         | PUT    |
| Feedback  | `/api/feedback/{id}`         | DELETE |
| Dashboard | `/api/dashboard/summary`     | GET    |
| Dashboard | `/api/dashboard/sentiment`   | GET    |
| Dashboard | `/api/dashboard/categories`  | GET    |
| Import    | `/api/imports`               | POST   |
| Import    | `/api/imports/{jobId}`       | GET    |
| AI        | `/api/ai/search`             | POST   |
| AI        | `/api/ai/chat`               | POST   |
| AI        | `/api/ai/chat/sessions`      | GET    |
| AI        | `/api/ai/chat/sessions/{id}` | GET    |

# 6. What we will deliberately extend AFTER the first 2 weeks

This is important. We are not trying to build every possible feature in the first 14 days.

We'll finish the core product first, then make it more sophisticated.

## Phase 2 — Advanced backend engineering

After MVP

- Redis Streams or RabbitMQ/Kafka for durable messaging.
- Dedicated worker services.
- Dead-letter queues.
- Exponential backoff and retry policies.
- Distributed locking.
- Better idempotency guarantees.
- Scheduled jobs.
- Caching with Redis.
- Rate limiting and API throttling.
- Database indexing and query optimization.
- Read replicas and connection pooling.
- Optimistic locking and concurrency handling.
  ## Phase 3 — Advanced AI engineering
  After MVP
- Hybrid search combining keyword and vector search.
- Reranking retrieved documents.
- Query rewriting.
- Conversational memory.
- Better chunking strategies.
- AI-generated product roadmap suggestions.
- Automatic duplicate feedback detection.
- Topic clustering and trend detection.
- AI evaluation datasets and automated evaluation.
- Hallucination and citation accuracy evaluation.
- Model comparison and cost optimization.
- Prompt versioning and experimentation.
  ## Phase 4 — Advanced frontend and product features
- Real-time dashboard updates using WebSockets or SSE.
- Advanced analytics and date-range filters.
- Custom dashboard widgets.
- Team invitations and role-based access control.
- Notifications.
- Dark mode.
- Export reports to CSV/PDF.
- Mobile-responsive refinements.
- Customer feedback submission portal.
- Public feedback voting board.
  ## Phase 5 — Production and scale
- Kubernetes.
- Cloud object storage for uploads.
- CI/CD with deployment environments.
- OpenTelemetry distributed tracing.
- Prometheus and Grafana monitoring.
- Centralized log management.
- Audit logs.
- Data retention and deletion policies.
- Multi-tenant isolation improvements.
- Load testing and performance benchmarking.
- Backup and disaster recovery.

## 7. What you'll be able to discuss in interviews

The goal isn't just to have a GitHub repository. It's to understand the decisions behind it.

| Interview area | What you'll be able to explain                             |
| -------------- | ---------------------------------------------------------- |
| Java           | OOP, collections, streams, concurrency, exception handling |
| Spring Boot    | Dependency injection, REST, JPA, transactions, security    |
| Database       | Normalization, relationships, indexing, pagination         |
| Frontend       | React hooks, state management, API integration             |
| Async          | Thread pools, background processing, retries, idempotency  |
| AI             | Prompt engineering, embeddings, RAG, structured outputs    |
| System design  | Modular monolith, API boundaries, scaling and caching      |
| Reliability    | Validation, error handling, testing and observability      |

# 8. How we'll work together, day by day

I don't want you to receive 14 days of code and blindly copy it. We'll work incrementally.

For each day, I'll provide:

1. Concepts to learn: the theory you need before implementation.
2. Implementation steps: exactly which files to create and modify.
3. Code explanations: why we're writing the code this way.
4. Hands-on tasks: things you should implement yourself.
5. Testing: how to verify that the implementation works.
6. Interview questions: relevant questions from that day's concepts.
7. End-of-day checklist: clear criteria before moving to the next day.

We'll also maintain a Git commit at the end of each day's work.

## Our project completion tracker

14-day progress

# 0/14

Day 1 — Architecture and setup

Day 2 — Database and backend fundamentals

Day 3 — Authentication and JWT

Day 4 — Feedback CRUD APIs

Day 5 — React frontend foundation

Day 6 — Feedback management UI

Day 7 — Dashboard and integration

Day 8 — CSV and async processing

Day 9 — Reliability and job tracking

Day 10 — LLM and prompt engineering

Day 11 — Embeddings and vector search

Day 12 — RAG and AI chat

Day 13 — Testing and quality

Day 14 — Docker and deployment

# 9. Let's start: Day 1

Before writing the first line of code, let's establish our development environment.

I suggest we use:

- Java 22 (you've already worked with this version).
- Spring Boot 4.x if compatible with the libraries we select.
- Maven.
- React + TypeScript + Vite.
- PostgreSQL with pgvector support.
- IntelliJ IDEA for Java.
- VS Code for React.
- Postman for API testing.
- GitHub for version control.

Our first task will be setting up the backend and frontend projects, connecting PostgreSQL, and making our first API call from React to Spring Boot.

We'll proceed one day at a time. I won't move us to Day 2 until Day 1 is working and you understand what we've built.