package com.su26swp06.journaltracker.domain.entity;

import com.su26swp06.journaltracker.domain.base.AuditableEntity;
import com.su26swp06.journaltracker.domain.enums.JournalType;

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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "journals",
        uniqueConstraints = @UniqueConstraint(name = "uq_journals_source_external", columnNames = {"source_id", "external_journal_id"})
)
public class Journal extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journalId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private ApiSource source;

    @NotBlank
    @Size(max = 255)
    @Column(name = "external_journal_id", nullable = false, length = 255)
    private String externalJournalId;

    @NotBlank
    @Size(max = 512)
    @Column(nullable = false, length = 512)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "journal_type", nullable = false, length = 32)
    private JournalType journalType = JournalType.JOURNAL;

    @Size(max = 32)
    @Column(length = 32)
    private String issn;

    @Size(max = 255)
    @Column(length = 255)
    private String publisher;

    @Size(max = 100)
    @Column(length = 100)
    private String country;

    @Size(max = 1024)
    @Column(length = 1024)
    private String url;

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public ApiSource getSource() {
        return source;
    }

    public void setSource(ApiSource source) {
        this.source = source;
    }

    public String getExternalJournalId() {
        return externalJournalId;
    }

    public void setExternalJournalId(String externalJournalId) {
        this.externalJournalId = externalJournalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JournalType getJournalType() {
        return journalType;
    }

    public void setJournalType(JournalType journalType) {
        this.journalType = journalType;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
