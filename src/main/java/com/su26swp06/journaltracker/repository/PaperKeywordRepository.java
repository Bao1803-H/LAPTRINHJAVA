package com.su26swp06.journaltracker.repository;

import com.su26swp06.journaltracker.domain.entity.PaperKeyword;
import com.su26swp06.journaltracker.domain.entity.id.PaperKeywordId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperKeywordRepository extends JpaRepository<PaperKeyword, PaperKeywordId> {
}
