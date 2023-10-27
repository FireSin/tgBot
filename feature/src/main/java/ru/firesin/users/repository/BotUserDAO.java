package ru.firesin.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.firesin.users.entity.BotUser;
import ru.firesin.users.enums.UserRole;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */

@Repository
public interface BotUserDAO extends JpaRepository<BotUser, Long> {
    BotUser findBotUserById(Long id);

    @Query("SELECT u.userRole FROM BotUser u WHERE u.id = :id")
    UserRole findUserRoleById(@Param("id") Long id);

    void deleteBotUserById(Long id);

}
