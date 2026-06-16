package com.su26swp06.journaltracker.domain.entity;

import java.math.BigDecimal;

import com.su26swp06.journaltracker.domain.base.CreatableEntity;
import com.su26swp06.journaltracker.domain.entity.id.PaperTopicId;

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
@Table(name = "paper_topics")
public class PaperTopic extends CreatableEntity {

    @EmbeddedId
    private PaperTopicId id = new PaperTopicId();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("paperId")
    @JoinColumn(name = "paper_id", nullable = false)
    private ResearchPaper paper;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("topicId")
    @JoinColumn(name = "topic_id", nullable = false)
    private ResearchTopic topic;

    @NotNull
    @DecimalMin("0.0000")
    @DecimalMax("1.0000")
    @Digits(integer = 1, fraction = 4)
    @Column(name = "relevance_score", nullable = false, precision = 5, scale = 4)
    private BigDecimal relevanceScore = new BigDecimal("1.0000");

    public PaperTopicId getId() {
        return id;
    }

    public void setId(PaperTopicId id) {
        this.id = id;
    }

    public ResearchPaper getPaper() {
        return paper;
    }

    public void setPaper(ResearchPaper paper) {
        this.paper = paper;
        if (this.id == null) {
            this.id = new PaperTopicId();
        }
        this.id.setPaperId(paper != null ? paper.getPaperId() : null);
    }

    public ResearchTopic getTopic() {
        return topic;
    }

    public void setTopic(ResearchTopic topic) {
        this.topic = topic;
        if (this.id == null) {
            this.id = new PaperTopicId();
        }
        this.id.setTopicId(topic != null ? topic.getTopicId() : null);
    }

    public BigDecimal getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(BigDecimal relevanceScore) {
        this.relevanceScore = relevanceScore;
    }
}
