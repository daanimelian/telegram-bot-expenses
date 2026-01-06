package com.homebudget.bot.repository;

import com.homebudget.bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad User.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su ID de Telegram.
     *
     * @param telegramId ID de Telegram del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> findByTelegramId(Long telegramId);

    /**
     * Verifica si existe un usuario con el ID de Telegram dado.
     *
     * @param telegramId ID de Telegram del usuario
     * @return true si existe, false en caso contrario
     */
    boolean existsByTelegramId(Long telegramId);

    /**
     * Busca un usuario activo por su ID de Telegram.
     *
     * @param telegramId ID de Telegram del usuario
     * @return Optional con el usuario si existe y está activo
     */
    Optional<User> findByTelegramIdAndIsActiveTrue(Long telegramId);
}
