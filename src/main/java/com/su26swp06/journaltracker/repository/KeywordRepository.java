package com.su26swp06.journaltracker.repository;

import com.su26swp06.journaltracker.domain.entity.Keyword;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
