# SU26SWP06 - Scientific Journal Publication Trend Tracking System

## Progress Checklist

### Overall Progress: ~15% Complete

---

## Phase 1: Core Authentication (Priority: CRITICAL)

### 1.1 Backend Auth
- [x] Fix `/api/auth/register` - Hoạt động ✅
- [x] Fix `/api/auth/login` - Hoạt động ✅
- [x] Fix `/api/auth/logout` - Hoạt động ✅
- [x] Fix `/api/auth/me` - Hoạt động ✅
- [x] Test full auth flow (register → login → use token) ✅

### 1.2 Security Configuration
- [x] Verify JWT token generation works ✅
- [x] Verify JWT authentication filter works ✅ (invalid token → 403 Forbidden)
- [x] Verify role-based access control ✅ (protected endpoints require auth)
- [x] Test protected endpoints with valid/invalid tokens ✅

---

## Phase 2: Core Entities & Repositories

### 2.1 Research Paper
- [ ] Create `ResearchPaper` entity
- [ ] Create `ResearchPaperRepository`
- [ ] Create `PaperService`
- [ ] Create `PaperController`

### 2.2 Journal
- [ ] Create `Journal` entity
- [ ] Create `JournalRepository`
- [ ] Create `JournalService`
- [ ] Create `JournalController`

### 2.3 Author
- [ ] Create `Author` entity
- [ ] Create `AuthorRepository`
- [ ] Create `AuthorService`
- [ ] Create `AuthorController`
- [ ] Create `PaperAuthor` join table entity

### 2.4 Keyword
- [ ] Create `Keyword` entity
- [ ] Create `KeywordRepository`
- [ ] Create `PaperKeyword` join table entity

### 2.5 Research Topic
- [ ] Create `ResearchTopic` entity
- [ ] Create `ResearchTopicRepository`
- [ ] Create `TopicService`
- [ ] Create `TopicController`
- [ ] Create `PaperTopic` join table entity

### 2.6 Publication Trend
- [ ] Create `PublicationTrend` entity
- [ ] Create `PublicationTrendRepository`
- [ ] Create `TrendService`
- [ ] Create `TrendController`

---

## Phase 3: Core APIs

### 3.1 Paper APIs
- [ ] `GET /api/papers` - List papers (paginated)
- [ ] `GET /api/papers/search` - Search by keyword/author/journal
- [ ] `GET /api/papers/{id}` - Paper details
- [ ] `GET /api/papers/trending` - Trending papers

### 3.2 Journal APIs
- [ ] `GET /api/journals` - List journals
- [ ] `GET /api/journals/{id}` - Journal details
- [ ] `GET /api/journals/{id}/papers` - Papers by journal

### 3.3 Author APIs
- [ ] `GET /api/authors` - List authors
- [ ] `GET /api/authors/{id}` - Author details
- [ ] `GET /api/authors/{id}/papers` - Papers by author

### 3.4 Keyword APIs
- [ ] `GET /api/keywords` - List keywords
- [ ] `GET /api/keywords/search` - Search papers by keyword

### 3.5 Topic APIs
- [ ] `GET /api/topics` - List topics
- [ ] `GET /api/topics/trending` - Trending topics
- [ ] `GET /api/topics/{id}` - Topic details

### 3.6 Trend APIs
- [ ] `GET /api/trends` - Get publication trends
- [ ] `GET /api/trends/by-year` - Trends by year
- [ ] `GET /api/trends/by-journal` - Trends by journal
- [ ] `GET /api/trends/by-keyword` - Trends by keyword

---

## Phase 4: User Features

### 4.1 Dashboard
- [ ] Create `Dashboard` entity
- [ ] Create `DashboardRepository`
- [ ] Create `DashboardService`
- [ ] `GET /api/dashboard/stats` - Get dashboard statistics
- [ ] `GET /api/dashboard/charts` - Get chart data

### 4.2 Bookmarks
- [ ] Create `Bookmark` entity
- [ ] Create `BookmarkRepository`
- [ ] Create `BookmarkService`
- [ ] `POST /api/bookmarks` - Add bookmark
- [ ] `GET /api/bookmarks` - List user bookmarks
- [ ] `DELETE /api/bookmarks/{id}` - Remove bookmark

### 4.3 Follows
- [ ] Create `JournalFollow` entity
- [ ] Create `TopicFollow` entity
- [ ] Create `FollowRepository`
- [ ] Create `FollowService`
- [ ] `POST /api/follows/journal/{id}` - Follow journal
- [ ] `DELETE /api/follows/journal/{id}` - Unfollow journal
- [ ] `POST /api/follows/topic/{id}` - Follow topic
- [ ] `DELETE /api/follows/topic/{id}` - Unfollow topic
- [ ] `GET /api/follows` - List user follows

### 4.4 Notifications
- [ ] Create `Notification` entity
- [ ] Create `NotificationRepository`
- [ ] Create `NotificationService`
- [ ] `GET /api/notifications` - List user notifications
- [ ] `PUT /api/notifications/{id}/read` - Mark as read
- [ ] `PUT /api/notifications/read-all` - Mark all as read
- [ ] `DELETE /api/notifications/{id}` - Delete notification

### 4.5 Reports
- [ ] Create `Report` entity
- [ ] Create `ReportRepository`
- [ ] Create `ReportService`
- [ ] `POST /api/reports` - Generate report
- [ ] `GET /api/reports` - List user reports
- [ ] `GET /api/reports/{id}` - Get report details
- [ ] `GET /api/reports/{id}/download` - Download report

---

## Phase 5: Admin Features

### 5.1 User Management
- [ ] `GET /api/admin/users` - List all users
- [ ] `GET /api/admin/users/{id}` - User details
- [ ] `PUT /api/admin/users/{id}/role` - Update user role
- [ ] `PUT /api/admin/users/{id}/status` - Update user status
- [ ] `DELETE /api/admin/users/{id}` - Deactivate user

### 5.2 Data Source Management
- [ ] Create `ApiSource` entity
- [ ] Create `ApiSourceRepository`
- [ ] Create `ApiSourceService`
- [ ] `GET /api/admin/sources` - List data sources
- [ ] `POST /api/admin/sources` - Add data source
- [ ] `PUT /api/admin/sources/{id}` - Update data source
- [ ] `DELETE /api/admin/sources/{id}` - Remove data source

### 5.3 Sync Management
- [ ] Create `SyncJob` entity
- [ ] Create `SyncJobRepository`
- [ ] Create `SyncService`
- [ ] `GET /api/admin/sync-jobs` - List sync jobs
- [ ] `GET /api/admin/sync-jobs/{id}` - Sync job details
- [ ] `POST /api/admin/sync/trigger` - Trigger manual sync
- [ ] `GET /api/admin/sync/status` - Current sync status

---

## Phase 6: Data Synchronization

### 6.1 External API Client
- [ ] Create OpenAlex API client
- [ ] Create Crossref API client (optional)
- [ ] Implement rate limiting
- [ ] Implement retry logic
- [ ] Handle API errors gracefully

### 6.2 Sync Service
- [ ] Paper sync logic
- [ ] Author sync logic
- [ ] Journal sync logic
- [ ] Keyword extraction
- [ ] Duplicate detection (by DOI)
- [ ] Trend calculation

### 6.3 Scheduler
- [ ] Configure Spring Scheduler
- [ ] Set up cron for periodic sync
- [ ] Configurable sync interval
- [ ] Manual sync trigger

---

## Phase 7: Frontend

### 7.1 Project Setup
- [ ] Set up React/Vue project
- [ ] Configure routing
- [ ] Set up state management
- [ ] Configure API client

### 7.2 Authentication Pages
- [ ] Login page
- [ ] Register page
- [ ] Forgot password page (optional)
- [ ] Profile page

### 7.3 Paper Pages
- [ ] Paper search page
- [ ] Paper list page
- [ ] Paper detail page
- [ ] Author detail page

### 7.4 Dashboard Pages
- [ ] Main dashboard
- [ ] Trend charts
- [ ] Trending topics widget

### 7.5 User Features Pages
- [ ] Bookmarks page
- [ ] Follows page (journals & topics)
- [ ] Notifications page
- [ ] Reports page

### 7.6 Admin Pages
- [ ] Admin dashboard
- [ ] User management page
- [ ] Data source config page
- [ ] Sync status page

---

## Phase 8: Infrastructure

### 8.1 Configuration
- [ ] Environment variables setup
- [ ] Docker configuration (if not done)
- [ ] Database migrations (Flyway/Liquibase)

### 8.2 Documentation
- [ ] API documentation (Swagger/OpenAPI)
- [ ] README with setup instructions
- [ ] User guide (optional)

### 8.3 Testing
- [ ] Unit tests for services
- [ ] Integration tests for APIs
- [ ] Frontend component tests

---

## Completed Items

### Phase 1 - Auth Infrastructure (Done)
- [x] User entity
- [x] UserRepository
- [x] UserService
- [x] AuthService
- [x] AuthController
- [x] RegisterRequest DTO
- [x] LoginRequest DTO
- [x] AuthResponse DTO
- [x] UserResponse DTO
- [x] JwtService
- [x] SecurityConfig
- [x] GlobalExceptionHandler
- [x] Custom exceptions (AuthException, ResourceNotFoundException)
- [x] Enums (UserRole, UserStatus, AuthProvider)
- [x] AuditableEntity, CreatableEntity

---

## Notes

- Last updated: 2026-06-16
- Auth has HTTP 500 error - needs debugging
- Focus on Phase 1.1 first before moving forward
