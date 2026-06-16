package com.su26swp06.journaltracker.domain.entity;

import java.time.LocalDate;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "research_papers",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_research_papers_source_external", columnNames = {"source_id", "external_paper_id"}),
                @UniqueConstraint(name = "uq_research_papers_doi", columnNames = "doi")
        }
)
public class ResearchPaper extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paperId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private ApiSource source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_id")
    private Journal journal;

    @NotBlank
    @Size(max = 255)
    @Column(name = "external_paper_id", nullable = false, length = 255)
    private String externalPaperId;

    @Size(max = 255)
    @Column(length = 255)
    private String doi;

    @NotBlank
    @Size(max = 1024)
    @Column(nullable = false, length = 1024)
    private String title;

    @Column(name = "abstract", columnDefinition = "text")
    private String abstractText;

    @Size(max = 255)
    @Column(name = "publication_venue", length = 255)
    private String publicationVenue;

    @NotNull
    @Min(1800)
    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Size(max = 50)
    @Column(length = 50)
    private String volume;

    @Size(max = 50)
    @Column(length = 50)
    private String issue;

    @Size(max = 50)
    @Column(length = 50)
    private String pages;

    @Size(max = 1024)
    @Column(length = 1024)
    private String url;

    @Size(max = 1024)
    @Column(name = "pdf_url", length = 1024)
    private String pdfUrl;

    @Size(max = 1024)
    @Column(name = "open_access_url", length = 1024)
    private String openAccessUrl;

    @Size(max = 20)
    @Column(name = "language_code", length = 20)
    private String languageCode;

    @Column(name = "citation_count", nullable = false)
    private Integer citationCount = 0;

    @Column(name = "raw_payload", nullable = false, columnDefinition = "json")
    private String rawPayload = "{}";

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public ApiSource getSource() {
        return source;
    }

    public void setSource(ApiSource source) {
        this.source = source;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public String getExternalPaperId() {
        return externalPaperId;
    }

    public void setExternalPaperId(String externalPaperId) {
        this.externalPaperId = externalPaperId;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getPublicationVenue() {
        return publicationVenue;
    }

    public void setPublicationVenue(String publicationVenue) {
        this.publicationVenue = publicationVenue;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getOpenAccessUrl() {
        return openAccessUrl;
    }

    public void setOpenAccessUrl(String openAccessUrl) {
        this.openAccessUrl = openAccessUrl;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Integer getCitationCount() {
        return citationCount;
    }

    public void setCitationCount(Integer citationCount) {
        this.citationCount = citationCount;
    }

    public String getRawPayload() {
        return rawPayload;
    }

    public void setRawPayload(String rawPayload) {
        this.rawPayload = rawPayload;
    }
}
