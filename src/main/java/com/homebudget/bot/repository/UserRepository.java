package com.homebudget.bot.repository;

import com.homebudget.bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad User (Hogar).
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un hogar por su Chat ID de Telegram.
     *
     * @param telegramChatId Chat ID de Telegram del hogar
     * @return Optional con el hogar si existe
     */
    Optional<User> findByTelegramChatId(String telegramChatId);

    /**
     * Verifica si existe un hogar con el Chat ID de Telegram dado.
     *
     * @param telegramChatId Chat ID de Telegram del hogar
     * @return true si existe, false en caso contrario
     */
    boolean existsByTelegramChatId(String telegramChatId);

    /**
     * Busca un hogar activo por su Chat ID de Telegram.
     *
     * @param telegramChatId Chat ID de Telegram del hogar
     * @return Optional con el hogar si existe y está activo
     */
    Optional<User> findByTelegramChatIdAndIsActiveTrue(String telegramChatId);

    /**
     * Busca todos los hogares activos.
     *
     * @return Lista de hogares activos
     */
    List<User> findByIsActiveTrue();
}
