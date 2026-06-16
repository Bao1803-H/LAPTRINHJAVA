package com.su26swp06.journaltracker.domain.entity.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class PaperKeywordId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long paperId;

    private Long keywordId;

    public PaperKeywordId() {
    }

    public PaperKeywordId(Long paperId, Long keywordId) {
        this.paperId = paperId;
        this.keywordId = keywordId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        PaperKeywordId that = (PaperKeywordId) object;
        return Objects.equals(paperId, that.paperId) && Objects.equals(keywordId, that.keywordId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, keywordId);
    }
}
