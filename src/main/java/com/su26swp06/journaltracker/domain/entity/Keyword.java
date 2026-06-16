package com.su26swp06.journaltracker.domain.entity;

import com.su26swp06.journaltracker.domain.base.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "keywords",
        uniqueConstraints = @UniqueConstraint(name = "uq_keywords_normalized_term", columnNames = "normalized_term")
)
public class Keyword extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String term;

    @NotBlank
    @Size(max = 255)
    @Column(name = "normalized_term", nullable = false, length = 255)
    private String normalizedTerm;

    public Long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getNormalizedTerm() {
        return normalizedTerm;
    }

    public void setNormalizedTerm(String normalizedTerm) {
        this.normalizedTerm = normalizedTerm;
    }
}
