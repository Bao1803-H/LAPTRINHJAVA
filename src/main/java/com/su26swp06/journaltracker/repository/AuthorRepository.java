package com.su26swp06.journaltracker.repository;

import com.su26swp06.journaltracker.domain.entity.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
