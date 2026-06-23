package com.su26swp06.journaltracker.dto.paper;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PaperRequest {

    @NotBlank(message = "External paper ID is required")
    @Size(max = 255)
    private String externalPaperId;

    @Size(max = 255)
    private String doi;

    @NotBlank(message = "Title is required")
    @Size(max = 1024)
    private String title;

    private String abstractText;

    @Size(max = 255)
    private String publicationVenue;

    @NotNull(message = "Publication year is required")
    @Min(1800)
    private Integer publicationYear;

    private String publicationDate;

    @Size(max = 50)
    private String volume;

    @Size(max = 50)
    private String issue;

    @Size(max = 50)
    private String pages;

    @Size(max = 1024)
    private String url;

    @Size(max = 1024)
    private String pdfUrl;

    @Size(max = 1024)
    private String openAccessUrl;

    @Size(max = 20)
    private String languageCode;

    @Min(0)
    private Integer citationCount;

    @NotNull(message = "Source ID is required")
    private Long sourceId;

    private Long journalId;

    // Getters and Setters
    public String getExternalPaperId() { return externalPaperId; }
    public void setExternalPaperId(String externalPaperId) { this.externalPaperId = externalPaperId; }

    public String getDoi() { return doi; }
    public void setDoi(String doi) { this.doi = doi; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAbstractText() { return abstractText; }
    public void setAbstractText(String abstractText) { this.abstractText = abstractText; }

    public String getPublicationVenue() { return publicationVenue; }
    public void setPublicationVenue(String publicationVenue) { this.publicationVenue = publicationVenue; }

    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }

    public String getPublicationDate() { return publicationDate; }
    public void setPublicationDate(String publicationDate) { this.publicationDate = publicationDate; }

    public String getVolume() { return volume; }
    public void setVolume(String volume) { this.volume = volume; }

    public String getIssue() { return issue; }
    public void setIssue(String issue) { this.issue = issue; }

    public String getPages() { return pages; }
    public void setPages(String pages) { this.pages = pages; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getPdfUrl() { return pdfUrl; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }

    public String getOpenAccessUrl() { return openAccessUrl; }
    public void setOpenAccessUrl(String openAccessUrl) { this.openAccessUrl = openAccessUrl; }

    public String getLanguageCode() { return languageCode; }
    public void setLanguageCode(String languageCode) { this.languageCode = languageCode; }

    public Integer getCitationCount() { return citationCount; }
    public void setCitationCount(Integer citationCount) { this.citationCount = citationCount; }

    public Long getSourceId() { return sourceId; }
    public void setSourceId(Long sourceId) { this.sourceId = sourceId; }

    public Long getJournalId() { return journalId; }
    public void setJournalId(Long journalId) { this.journalId = journalId; }
}
