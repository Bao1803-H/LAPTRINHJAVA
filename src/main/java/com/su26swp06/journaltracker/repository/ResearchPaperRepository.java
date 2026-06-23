package com.su26swp06.journaltracker.repository;

import com.su26swp06.journaltracker.domain.entity.ResearchPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchPaperRepository extends JpaRepository<ResearchPaper, Long> {

    @Query("SELECT p FROM ResearchPaper p WHERE " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.abstractText) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ResearchPaper> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Page<ResearchPaper> findByJournalJournalId(Long journalId, Pageable pageable);

    Page<ResearchPaper> findByPublicationYear(Integer year, Pageable pageable);

    Page<ResearchPaper> findByDoi(String doi, Pageable pageable);

    @Query("SELECT p FROM ResearchPaper p ORDER BY p.citationCount DESC")
    Page<ResearchPaper> findTrending(Pageable pageable);

    @Query("SELECT p FROM ResearchPaper p WHERE " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.abstractText) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.doi) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<ResearchPaper> searchAll(@Param("query") String query, Pageable pageable);
}
