package com.homebudget.bot.repository;

import com.homebudget.bot.entity.Category;
import com.homebudget.bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Category.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Busca todas las categorías de un usuario.
     *
     * @param user Usuario propietario de las categorías
     * @return Lista de categorías del usuario
     */
    List<Category> findByUser(User user);

    /**
     * Busca todas las categorías activas de un usuario.
     *
     * @param user Usuario propietario de las categorías
     * @return Lista de categorías activas del usuario
     */
    List<Category> findByUserAndIsActiveTrue(User user);

    /**
     * Busca una categoría por nombre y usuario.
     *
     * @param name Nombre de la categoría
     * @param user Usuario propietario de la categoría
     * @return Optional con la categoría si existe
     */
    Optional<Category> findByNameAndUser(String name, User user);

    /**
     * Busca una categoría activa por nombre y usuario.
     *
     * @param name Nombre de la categoría
     * @param user Usuario propietario de la categoría
     * @return Optional con la categoría si existe y está activa
     */
    Optional<Category> findByNameAndUserAndIsActiveTrue(String name, User user);

    /**
     * Verifica si existe una categoría con el nombre dado para un usuario.
     *
     * @param name Nombre de la categoría
     * @param user Usuario propietario de la categoría
     * @return true si existe, false en caso contrario
     */
    boolean existsByNameAndUser(String name, User user);
}
