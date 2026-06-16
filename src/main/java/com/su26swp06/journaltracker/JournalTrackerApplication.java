package com.su26swp06.journaltracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.su26swp06.journaltracker.config.SecurityConfig;

@SpringBootApplication(excludeName = "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration")
@Import(SecurityConfig.class)
public class JournalTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalTrackerApplication.class, args);
    }
}
