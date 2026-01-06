package com.homebudget.bot.repository;

import com.homebudget.bot.entity.FamilyMember;
import com.homebudget.bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad FamilyMember.
 */
@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    /**
     * Busca todos los miembros familiares de un hogar.
     *
     * @param user Hogar propietario de los miembros
     * @return Lista de miembros familiares del hogar
     */
    List<FamilyMember> findByUser(User user);

    /**
     * Busca todos los miembros familiares activos de un hogar.
     *
     * @param user Hogar propietario de los miembros
     * @return Lista de miembros familiares activos del hogar
     */
    List<FamilyMember> findByUserAndIsActiveTrue(User user);

    /**
     * Busca un miembro familiar por nombre y hogar.
     *
     * @param name Nombre del miembro familiar
     * @param user Hogar propietario del miembro
     * @return Optional con el miembro familiar si existe
     */
    Optional<FamilyMember> findByNameAndUser(String name, User user);

    /**
     * Busca un miembro familiar activo por nombre y hogar.
     *
     * @param name Nombre del miembro familiar
     * @param user Hogar propietario del miembro
     * @return Optional con el miembro familiar si existe y está activo
     */
    Optional<FamilyMember> findByNameAndUserAndIsActiveTrue(String name, User user);

    /**
     * Verifica si existe un miembro familiar con el nombre dado en un hogar.
     *
     * @param name Nombre del miembro familiar
     * @param user Hogar propietario del miembro
     * @return true si existe, false en caso contrario
     */
    boolean existsByNameAndUser(String name, User user);

    /**
     * Cuenta los miembros familiares activos de un hogar.
     *
     * @param user Hogar propietario de los miembros
     * @return Cantidad de miembros activos
     */
    long countByUserAndIsActiveTrue(User user);
}
