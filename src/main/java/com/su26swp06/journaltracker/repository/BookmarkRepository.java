package com.su26swp06.journaltracker.repository;

import com.su26swp06.journaltracker.domain.entity.Bookmark;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
