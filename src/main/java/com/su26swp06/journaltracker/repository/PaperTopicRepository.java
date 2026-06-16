package com.su26swp06.journaltracker.repository;

import com.su26swp06.journaltracker.domain.entity.PaperTopic;
import com.su26swp06.journaltracker.domain.entity.id.PaperTopicId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperTopicRepository extends JpaRepository<PaperTopic, PaperTopicId> {
}
