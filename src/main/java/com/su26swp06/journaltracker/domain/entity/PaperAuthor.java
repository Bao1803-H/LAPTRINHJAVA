package com.su26swp06.journaltracker.domain.entity;

import com.su26swp06.journaltracker.domain.base.CreatableEntity;
import com.su26swp06.journaltracker.domain.entity.id.PaperAuthorId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "paper_authors")
public class PaperAuthor extends CreatableEntity {

    @EmbeddedId
    private PaperAuthorId id = new PaperAuthorId();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("paperId")
    @JoinColumn(name = "paper_id", nullable = false)
    private ResearchPaper paper;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("authorId")
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @NotNull
    @Min(1)
    @Column(name = "author_order", nullable = false)
    private Integer authorOrder = 1;

    @Column(name = "is_corresponding", nullable = false)
    private boolean corresponding;

    public PaperAuthorId getId() {
        return id;
    }

    public void setId(PaperAuthorId id) {
        this.id = id;
    }

    public ResearchPaper getPaper() {
        return paper;
    }

    public void setPaper(ResearchPaper paper) {
        this.paper = paper;
        if (this.id == null) {
            this.id = new PaperAuthorId();
        }
        this.id.setPaperId(paper != null ? paper.getPaperId() : null);
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
        if (this.id == null) {
            this.id = new PaperAuthorId();
        }
        this.id.setAuthorId(author != null ? author.getAuthorId() : null);
    }

    public Integer getAuthorOrder() {
        return authorOrder;
    }

    public void setAuthorOrder(Integer authorOrder) {
        this.authorOrder = authorOrder;
    }

    public boolean isCorresponding() {
        return corresponding;
    }

    public void setCorresponding(boolean corresponding) {
        this.corresponding = corresponding;
    }
}
