package com.su26swp06.journaltracker.domain.entity;

import com.su26swp06.journaltracker.domain.base.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
        name = "authors",
        uniqueConstraints = @UniqueConstraint(name = "uq_authors_source_external", columnNames = {"source_id", "external_author_id"})
)
public class Author extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private ApiSource source;

    @NotBlank
    @Size(max = 255)
    @Column(name = "external_author_id", nullable = false, length = 255)
    private String externalAuthorId;

    @NotBlank
    @Size(max = 255)
    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Size(max = 255)
    @Column(length = 255)
    private String affiliation;

    @Size(max = 32)
    @Column(length = 32)
    private String orcid;

    @Size(max = 1024)
    @Column(name = "profile_url", length = 1024)
    private String profileUrl;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public ApiSource getSource() {
        return source;
    }

    public void setSource(ApiSource source) {
        this.source = source;
    }

    public String getExternalAuthorId() {
        return externalAuthorId;
    }

    public void setExternalAuthorId(String externalAuthorId) {
        this.externalAuthorId = externalAuthorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
