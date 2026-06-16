package com.su26swp06.journaltracker.domain.entity.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class PaperTopicId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long paperId;

    private Long topicId;

    public PaperTopicId() {
    }

    public PaperTopicId(Long paperId, Long topicId) {
        this.paperId = paperId;
        this.topicId = topicId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        PaperTopicId that = (PaperTopicId) object;
        return Objects.equals(paperId, that.paperId) && Objects.equals(topicId, that.topicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, topicId);
    }
}
