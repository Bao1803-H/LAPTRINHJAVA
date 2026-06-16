package com.su26swp06.journaltracker.domain.entity;

import java.math.BigDecimal;

import com.su26swp06.journaltracker.domain.base.CreatableEntity;
import com.su26swp06.journaltracker.domain.entity.id.PaperKeywordId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "paper_keywords")
public class PaperKeyword extends CreatableEntity {

    @EmbeddedId
    private PaperKeywordId id = new PaperKeywordId();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("paperId")
    @JoinColumn(name = "paper_id", nullable = false)
    private ResearchPaper paper;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("keywordId")
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    @NotNull
    @DecimalMin("0.0000")
    @DecimalMax("1.0000")
    @Digits(integer = 1, fraction = 4)
    @Column(name = "relevance_score", nullable = false, precision = 5, scale = 4)
    private BigDecimal relevanceScore = new BigDecimal("1.0000");

    public PaperKeywordId getId() {
        return id;
    }

    public void setId(PaperKeywordId id) {
        this.id = id;
    }

    public ResearchPaper getPaper() {
        return paper;
    }

    public void setPaper(ResearchPaper paper) {
        this.paper = paper;
        if (this.id == null) {
            this.id = new PaperKeywordId();
        }
        this.id.setPaperId(paper != null ? paper.getPaperId() : null);
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
        if (this.id == null) {
            this.id = new PaperKeywordId();
        }
        this.id.setKeywordId(keyword != null ? keyword.getKeywordId() : null);
    }

    public BigDecimal getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(BigDecimal relevanceScore) {
        this.relevanceScore = relevanceScore;
    }
}
