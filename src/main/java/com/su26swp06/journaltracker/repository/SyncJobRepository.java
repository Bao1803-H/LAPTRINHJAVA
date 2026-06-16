package com.su26swp06.journaltracker.repository;

import com.su26swp06.journaltracker.domain.entity.SyncJob;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncJobRepository extends JpaRepository<SyncJob, Long> {
}
