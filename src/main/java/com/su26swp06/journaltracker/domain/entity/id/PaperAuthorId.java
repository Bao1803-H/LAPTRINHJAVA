package com.su26swp06.journaltracker.domain.entity.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class PaperAuthorId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long paperId;

    private Long authorId;

    public PaperAuthorId() {
    }

    public PaperAuthorId(Long paperId, Long authorId) {
        this.paperId = paperId;
        this.authorId = authorId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        PaperAuthorId that = (PaperAuthorId) object;
        return Objects.equals(paperId, that.paperId) && Objects.equals(authorId, that.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, authorId);
    }
}
