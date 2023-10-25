package ru.firesin.tgbot.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.firesin.tgbot.jpa.BotUser;
import ru.firesin.tgbot.jpa.enums.UserRole;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */

public interface BotUserDAO extends JpaRepository<BotUser, Long> {
    BotUser findBotUserById(Long id);

    @Query("SELECT u.userRole FROM BotUser u WHERE u.id = :id")
    UserRole findUserRoleById(@Param("id") Long id);

    void deleteBotUserById(Long id);

}
