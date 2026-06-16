package com.su26swp06.journaltracker.repository;

import com.su26swp06.journaltracker.domain.entity.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
