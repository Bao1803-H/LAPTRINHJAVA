package com.su26swp06.journaltracker.domain.entity;

import java.time.LocalDateTime;

import com.su26swp06.journaltracker.domain.base.CreatableEntity;
import com.su26swp06.journaltracker.domain.enums.JobStatus;
import com.su26swp06.journaltracker.domain.enums.JobType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sync_jobs")
public class SyncJob extends CreatableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private ApiSource source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "triggered_by_user_id")
    private AppUser triggeredByUser;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false, length = 32)
    private JobType jobType = JobType.SCHEDULED;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private JobStatus status = JobStatus.PENDING;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @NotNull
    @Min(0)
    @Column(name = "fetched_count", nullable = false)
    private Integer fetchedCount = 0;

    @NotNull
    @Min(0)
    @Column(name = "inserted_count", nullable = false)
    private Integer insertedCount = 0;

    @NotNull
    @Min(0)
    @Column(name = "updated_count", nullable = false)
    private Integer updatedCount = 0;

    @NotNull
    @Min(0)
    @Column(name = "failed_count", nullable = false)
    private Integer failedCount = 0;

    @Column(name = "error_message", columnDefinition = "text")
    private String errorMessage;

    @Column(name = "payload_json", nullable = false, columnDefinition = "json")
    private String payloadJson = "{}";

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public ApiSource getSource() {
        return source;
    }

    public void setSource(ApiSource source) {
        this.source = source;
    }

    public AppUser getTriggeredByUser() {
        return triggeredByUser;
    }

    public void setTriggeredByUser(AppUser triggeredByUser) {
        this.triggeredByUser = triggeredByUser;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Integer getFetchedCount() {
        return fetchedCount;
    }

    public void setFetchedCount(Integer fetchedCount) {
        this.fetchedCount = fetchedCount;
    }

    public Integer getInsertedCount() {
        return insertedCount;
    }

    public void setInsertedCount(Integer insertedCount) {
        this.insertedCount = insertedCount;
    }

    public Integer getUpdatedCount() {
        return updatedCount;
    }

    public void setUpdatedCount(Integer updatedCount) {
        this.updatedCount = updatedCount;
    }

    public Integer getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(Integer failedCount) {
        this.failedCount = failedCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getPayloadJson() {
        return payloadJson;
    }

    public void setPayloadJson(String payloadJson) {
        this.payloadJson = payloadJson;
    }
}
