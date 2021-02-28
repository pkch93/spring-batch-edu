package edu.pkch.batch.domain.user;

import edu.pkch.batch.domain.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCreatedAtBeforeAndUserStatusEquals(LocalDateTime createdAt, UserStatus userStatus);
}
