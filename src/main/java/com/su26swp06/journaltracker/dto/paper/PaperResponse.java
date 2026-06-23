package com.su26swp06.journaltracker.dto.paper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.su26swp06.journaltracker.domain.entity.ResearchPaper;

public class PaperResponse {
    
    private Long paperId;
    private String externalPaperId;
    private String doi;
    private String title;
    private String abstractText;
    private String publicationVenue;
    private Integer publicationYear;
    private LocalDate publicationDate;
    private String volume;
    private String issue;
    private String pages;
    private String url;
    private String pdfUrl;
    private String openAccessUrl;
    private String languageCode;
    private Integer citationCount;
    private Long sourceId;
    private String sourceName;
    private Long journalId;
    private String journalTitle;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PaperResponse() {}

    public static PaperResponse fromEntity(ResearchPaper paper) {
        PaperResponse response = new PaperResponse();
        response.setPaperId(paper.getPaperId());
        response.setExternalPaperId(paper.getExternalPaperId());
        response.setDoi(paper.getDoi());
        response.setTitle(paper.getTitle());
        response.setAbstractText(paper.getAbstractText());
        response.setPublicationVenue(paper.getPublicationVenue());
        response.setPublicationYear(paper.getPublicationYear());
        response.setPublicationDate(paper.getPublicationDate());
        response.setVolume(paper.getVolume());
        response.setIssue(paper.getIssue());
        response.setPages(paper.getPages());
        response.setUrl(paper.getUrl());
        response.setPdfUrl(paper.getPdfUrl());
        response.setOpenAccessUrl(paper.getOpenAccessUrl());
        response.setLanguageCode(paper.getLanguageCode());
        response.setCitationCount(paper.getCitationCount());
        response.setCreatedAt(paper.getCreatedAt());
        response.setUpdatedAt(paper.getUpdatedAt());
        
        if (paper.getSource() != null) {
            response.setSourceId(paper.getSource().getSourceId());
            response.setSourceName(paper.getSource().getSourceName());
        }
        
        if (paper.getJournal() != null) {
            response.setJournalId(paper.getJournal().getJournalId());
            response.setJournalTitle(paper.getJournal().getName());
        }
        
        return response;
    }

    // Getters and Setters
    public Long getPaperId() { return paperId; }
    public void setPaperId(Long paperId) { this.paperId = paperId; }
    
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
    
    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }
    
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
    
    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }
    
    public Long getJournalId() { return journalId; }
    public void setJournalId(Long journalId) { this.journalId = journalId; }
    
    public String getJournalTitle() { return journalTitle; }
    public void setJournalTitle(String journalTitle) { this.journalTitle = journalTitle; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
