package com.team1.etuser.user.repository;

import com.team1.etuser.user.domain.User;
import com.team1.etuser.user.domain.UserTradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTradeHistoryRepository extends JpaRepository<UserTradeHistory, Long> {
}
