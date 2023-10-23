package ru.firesin.feature.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.firesin.feature.jpa.BotUser;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */

public interface BotUserDAO extends JpaRepository<BotUser, Long> {
    public BotUser findBotUserById(Long id);
}
