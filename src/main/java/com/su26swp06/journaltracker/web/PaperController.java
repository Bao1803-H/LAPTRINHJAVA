package com.su26swp06.journaltracker.web;

import com.su26swp06.journaltracker.dto.paper.PaperRequest;
import com.su26swp06.journaltracker.dto.paper.PaperResponse;
import com.su26swp06.journaltracker.service.PaperService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/papers")
public class PaperController {

    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping
    public ResponseEntity<Page<PaperResponse>> getAllPapers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "paperId") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("asc") 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return ResponseEntity.ok(paperService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaperResponse> getPaperById(@PathVariable Long id) {
        return ResponseEntity.ok(paperService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PaperResponse>> searchPapers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        if (query != null && !query.isEmpty()) {
            return ResponseEntity.ok(paperService.searchAll(query, pageable));
        } else if (keyword != null && !keyword.isEmpty()) {
            return ResponseEntity.ok(paperService.searchByKeyword(keyword, pageable));
        }
        
        return ResponseEntity.ok(paperService.findAll(pageable));
    }

    @GetMapping("/trending")
    public ResponseEntity<Page<PaperResponse>> getTrendingPapers(
            @RequestParam(defaultValue = "10") int limit) {
        
        Pageable pageable = PageRequest.of(0, limit);
        return ResponseEntity.ok(paperService.findTrending(pageable));
    }

    @GetMapping("/journal/{journalId}")
    public ResponseEntity<Page<PaperResponse>> getPapersByJournal(
            @PathVariable Long journalId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(paperService.findByJournal(journalId, pageable));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<Page<PaperResponse>> getPapersByYear(
            @PathVariable Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(paperService.findByYear(year, pageable));
    }

    @PostMapping
    public ResponseEntity<PaperResponse> createPaper(@Valid @RequestBody PaperRequest request) {
        PaperResponse created = paperService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaperResponse> updatePaper(
            @PathVariable Long id,
            @Valid @RequestBody PaperRequest request) {
        return ResponseEntity.ok(paperService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaper(@PathVariable Long id) {
        paperService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
