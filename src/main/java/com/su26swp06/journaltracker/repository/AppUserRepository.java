package com.su26swp06.journaltracker.repository;

import com.su26swp06.journaltracker.domain.entity.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
