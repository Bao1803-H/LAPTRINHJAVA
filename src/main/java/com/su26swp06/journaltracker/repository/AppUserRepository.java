package com.su26swp06.journaltracker.repository;

import com.su26swp06.journaltracker.domain.entity.AppUser;
import com.su26swp06.journaltracker.domain.enums.UserStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<AppUser> findByStatusNot(UserStatus status, Pageable pageable);
}
