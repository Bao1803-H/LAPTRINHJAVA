package com.su26swp06.journaltracker.domain.entity;

import java.time.LocalDateTime;

import com.su26swp06.journaltracker.domain.base.AuditableEntity;
import com.su26swp06.journaltracker.domain.enums.ApiSourceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "api_sources")
public class ApiSource extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sourceId;

    @NotBlank
    @Size(max = 150)
    @Column(name = "source_name", nullable = false, length = 150)
    private String sourceName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 32)
    private ApiSourceType sourceType;

    @NotBlank
    @Size(max = 500)
    @Column(name = "base_url", nullable = false, length = 500)
    private String baseUrl;

    @Size(max = 255)
    @Column(name = "api_key_hint", length = 255)
    private String apiKeyHint;

    @Column(nullable = false)
    private boolean enabled = true;

    @NotNull
    @Min(1)
    @Column(name = "sync_interval_minutes", nullable = false)
    private Integer syncIntervalMinutes = 1440;

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public ApiSourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(ApiSourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiKeyHint() {
        return apiKeyHint;
    }

    public void setApiKeyHint(String apiKeyHint) {
        this.apiKeyHint = apiKeyHint;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getSyncIntervalMinutes() {
        return syncIntervalMinutes;
    }

    public void setSyncIntervalMinutes(Integer syncIntervalMinutes) {
        this.syncIntervalMinutes = syncIntervalMinutes;
    }

    public LocalDateTime getLastSyncedAt() {
        return lastSyncedAt;
    }

    public void setLastSyncedAt(LocalDateTime lastSyncedAt) {
        this.lastSyncedAt = lastSyncedAt;
    }
}
