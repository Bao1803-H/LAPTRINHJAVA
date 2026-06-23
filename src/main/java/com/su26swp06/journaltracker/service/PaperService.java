package com.su26swp06.journaltracker.service;

import com.su26swp06.journaltracker.domain.entity.ApiSource;
import com.su26swp06.journaltracker.domain.entity.Journal;
import com.su26swp06.journaltracker.domain.entity.ResearchPaper;
import com.su26swp06.journaltracker.dto.paper.PaperRequest;
import com.su26swp06.journaltracker.dto.paper.PaperResponse;
import com.su26swp06.journaltracker.exception.ResourceNotFoundException;
import com.su26swp06.journaltracker.repository.ApiSourceRepository;
import com.su26swp06.journaltracker.repository.JournalRepository;
import com.su26swp06.journaltracker.repository.ResearchPaperRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PaperService {

    private final ResearchPaperRepository paperRepository;
    private final ApiSourceRepository apiSourceRepository;
    private final JournalRepository journalRepository;

    public PaperService(ResearchPaperRepository paperRepository,
                        ApiSourceRepository apiSourceRepository,
                        JournalRepository journalRepository) {
        this.paperRepository = paperRepository;
        this.apiSourceRepository = apiSourceRepository;
        this.journalRepository = journalRepository;
    }

    public Page<PaperResponse> findAll(Pageable pageable) {
        return paperRepository.findAll(pageable)
                .map(PaperResponse::fromEntity);
    }

    public PaperResponse findById(Long id) {
        ResearchPaper paper = paperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paper not found with id: " + id));
        return PaperResponse.fromEntity(paper);
    }

    public Page<PaperResponse> searchByKeyword(String keyword, Pageable pageable) {
        return paperRepository.searchByKeyword(keyword, pageable)
                .map(PaperResponse::fromEntity);
    }

    public Page<PaperResponse> searchAll(String query, Pageable pageable) {
        return paperRepository.searchAll(query, pageable)
                .map(PaperResponse::fromEntity);
    }

    public Page<PaperResponse> findByJournal(Long journalId, Pageable pageable) {
        return paperRepository.findByJournalJournalId(journalId, pageable)
                .map(PaperResponse::fromEntity);
    }

    public Page<PaperResponse> findByYear(Integer year, Pageable pageable) {
        return paperRepository.findByPublicationYear(year, pageable)
                .map(PaperResponse::fromEntity);
    }

    public Page<PaperResponse> findTrending(Pageable pageable) {
        return paperRepository.findTrending(pageable)
                .map(PaperResponse::fromEntity);
    }

    @Transactional
    public PaperResponse create(PaperRequest request) {
        ResearchPaper paper = new ResearchPaper();
        mapRequestToEntity(request, paper);
        
        ResearchPaper saved = paperRepository.save(paper);
        return PaperResponse.fromEntity(saved);
    }

    @Transactional
    public PaperResponse update(Long id, PaperRequest request) {
        ResearchPaper paper = paperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paper not found with id: " + id));
        
        mapRequestToEntity(request, paper);
        
        ResearchPaper updated = paperRepository.save(paper);
        return PaperResponse.fromEntity(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!paperRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paper not found with id: " + id);
        }
        paperRepository.deleteById(id);
    }

    private void mapRequestToEntity(PaperRequest request, ResearchPaper paper) {
        paper.setExternalPaperId(request.getExternalPaperId());
        paper.setDoi(request.getDoi());
        paper.setTitle(request.getTitle());
        paper.setAbstractText(request.getAbstractText());
        paper.setPublicationVenue(request.getPublicationVenue());
        paper.setPublicationYear(request.getPublicationYear());
        
        if (request.getPublicationDate() != null && !request.getPublicationDate().isEmpty()) {
            paper.setPublicationDate(LocalDate.parse(request.getPublicationDate()));
        }
        
        paper.setVolume(request.getVolume());
        paper.setIssue(request.getIssue());
        paper.setPages(request.getPages());
        paper.setUrl(request.getUrl());
        paper.setPdfUrl(request.getPdfUrl());
        paper.setOpenAccessUrl(request.getOpenAccessUrl());
        paper.setLanguageCode(request.getLanguageCode());
        
        if (request.getCitationCount() != null) {
            paper.setCitationCount(request.getCitationCount());
        }

        if (request.getJournalId() != null) {
            Journal journal = journalRepository.findById(request.getJournalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Journal not found with id: " + request.getJournalId()));
            paper.setJournal(journal);
        }

        if (request.getSourceId() != null) {
            ApiSource source = apiSourceRepository.findById(request.getSourceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Source not found with id: " + request.getSourceId()));
            paper.setSource(source);
        } else if (paper.getSource() == null) {
            ApiSource defaultSource = apiSourceRepository.findById(1L)
                    .orElseThrow(() -> new ResourceNotFoundException("Default source not found"));
            paper.setSource(defaultSource);
        }
    }
}
