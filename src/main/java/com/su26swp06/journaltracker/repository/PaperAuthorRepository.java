package com.su26swp06.journaltracker.repository;

import com.su26swp06.journaltracker.domain.entity.PaperAuthor;
import com.su26swp06.journaltracker.domain.entity.id.PaperAuthorId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperAuthorRepository extends JpaRepository<PaperAuthor, PaperAuthorId> {
}
