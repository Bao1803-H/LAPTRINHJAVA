package com.su26swp06.journaltracker.domain.entity;

import java.time.LocalDateTime;

import com.su26swp06.journaltracker.domain.base.AuditableEntity;
import com.su26swp06.journaltracker.domain.enums.TrendScope;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
        name = "publication_trends",
        uniqueConstraints = @UniqueConstraint(name = "uq_publication_trends_scope_year_entity", columnNames = {"trend_scope", "publication_year", "scope_entity_key"})
)
public class PublicationTrend extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trendId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "trend_scope", nullable = false, length = 32)
    private TrendScope trendScope;

    @NotNull
    @Min(1800)
    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_id")
    private Journal journal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private ResearchTopic topic;

    @Column(name = "scope_entity_key", nullable = false)
    private long scopeEntityKey;

    @Column(name = "paper_count", nullable = false)
    private Integer paperCount = 0;

    @Column(name = "citation_count_total", nullable = false)
    private Integer citationCountTotal = 0;

    @Column(name = "computed_at", nullable = false)
    private LocalDateTime computedAt;

    @PrePersist
    protected void onCreateComputedAt() {
        if (computedAt == null) {
            computedAt = LocalDateTime.now();
        }
    }

    @AssertTrue(message = "Publication trend scope does not match its entity reference")
    public boolean isScopeValid() {
        if (trendScope == null) {
            return false;
        }
        return switch (trendScope) {
            case YEAR -> journal == null && keyword == null && topic == null && scopeEntityKey == 0L;
            case JOURNAL -> journal != null && keyword == null && topic == null && journal.getJournalId() != null && journal.getJournalId().equals(scopeEntityKey);
            case KEYWORD -> keyword != null && journal == null && topic == null && keyword.getKeywordId() != null && keyword.getKeywordId().equals(scopeEntityKey);
            case TOPIC -> topic != null && journal == null && keyword == null && topic.getTopicId() != null && topic.getTopicId().equals(scopeEntityKey);
        };
    }

    public Long getTrendId() {
        return trendId;
    }

    public void setTrendId(Long trendId) {
        this.trendId = trendId;
    }

    public TrendScope getTrendScope() {
        return trendScope;
    }

    public void setTrendScope(TrendScope trendScope) {
        this.trendScope = trendScope;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    public ResearchTopic getTopic() {
        return topic;
    }

    public void setTopic(ResearchTopic topic) {
        this.topic = topic;
    }

    public long getScopeEntityKey() {
        return scopeEntityKey;
    }

    public void setScopeEntityKey(long scopeEntityKey) {
        this.scopeEntityKey = scopeEntityKey;
    }

    public Integer getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(Integer paperCount) {
        this.paperCount = paperCount;
    }

    public Integer getCitationCountTotal() {
        return citationCountTotal;
    }

    public void setCitationCountTotal(Integer citationCountTotal) {
        this.citationCountTotal = citationCountTotal;
    }

    public LocalDateTime getComputedAt() {
        return computedAt;
    }

    public void setComputedAt(LocalDateTime computedAt) {
        this.computedAt = computedAt;
    }
}
