package com.homebudget.bot.repository;

import com.homebudget.bot.entity.Income;
import com.homebudget.bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Income.
 */
@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    /**
     * Busca todos los ingresos de un hogar.
     *
     * @param user Hogar propietario de los ingresos
     * @return Lista de ingresos del hogar
     */
    List<Income> findByUser(User user);

    /**
     * Busca todos los ingresos de un hogar ordenados por mes/año descendente.
     *
     * @param user Hogar propietario de los ingresos
     * @return Lista de ingresos del hogar ordenados
     */
    List<Income> findByUserOrderByMonthYearDesc(User user);

    /**
     * Busca un ingreso por hogar y mes/año.
     *
     * @param user      Hogar propietario del ingreso
     * @param monthYear Mes y año del ingreso
     * @return Optional con el ingreso si existe
     */
    Optional<Income> findByUserAndMonthYear(User user, YearMonth monthYear);

    /**
     * Verifica si existe un ingreso para un hogar en un mes/año específico.
     *
     * @param user      Hogar propietario del ingreso
     * @param monthYear Mes y año del ingreso
     * @return true si existe, false en caso contrario
     */
    boolean existsByUserAndMonthYear(User user, YearMonth monthYear);

    /**
     * Busca ingresos de un hogar en un rango de meses.
     *
     * @param user      Hogar propietario de los ingresos
     * @param startMonth Mes de inicio (inclusive)
     * @param endMonth   Mes de fin (inclusive)
     * @return Lista de ingresos en el rango
     */
    List<Income> findByUserAndMonthYearBetween(User user, YearMonth startMonth, YearMonth endMonth);

    /**
     * Busca ingresos de un hogar en un rango de meses ordenados por mes descendente.
     *
     * @param user       Hogar propietario de los ingresos
     * @param startMonth Mes de inicio (inclusive)
     * @param endMonth   Mes de fin (inclusive)
     * @return Lista de ingresos en el rango ordenados
     */
    List<Income> findByUserAndMonthYearBetweenOrderByMonthYearDesc(User user, YearMonth startMonth, YearMonth endMonth);

    /**
     * Calcula el total de ingresos en ARS para un hogar.
     *
     * @param user Hogar propietario de los ingresos
     * @return Suma total de ingresos en ARS
     */
    @Query("SELECT COALESCE(SUM(i.amountARS), 0) FROM Income i WHERE i.user = :user")
    BigDecimal sumAmountARSByUser(@Param("user") User user);

    /**
     * Calcula el total de ingresos en USD para un hogar.
     *
     * @param user Hogar propietario de los ingresos
     * @return Suma total de ingresos en USD
     */
    @Query("SELECT COALESCE(SUM(i.amountUSD), 0) FROM Income i WHERE i.user = :user")
    BigDecimal sumAmountUSDByUser(@Param("user") User user);

    /**
     * Calcula el total de ingresos en ARS para un hogar en un rango de meses.
     *
     * @param user       Hogar propietario de los ingresos
     * @param startMonth Mes de inicio
     * @param endMonth   Mes de fin
     * @return Suma total de ingresos en ARS en el rango
     */
    @Query("SELECT COALESCE(SUM(i.amountARS), 0) FROM Income i WHERE i.user = :user AND i.monthYear BETWEEN :startMonth AND :endMonth")
    BigDecimal sumAmountARSByUserAndMonthYearBetween(@Param("user") User user,
                                                       @Param("startMonth") YearMonth startMonth,
                                                       @Param("endMonth") YearMonth endMonth);

    /**
     * Busca los últimos N ingresos de un hogar.
     *
     * @param user  Hogar propietario de los ingresos
     * @param limit Cantidad de ingresos a retornar
     * @return Lista de los últimos ingresos
     */
    @Query("SELECT i FROM Income i WHERE i.user = :user ORDER BY i.monthYear DESC LIMIT :limit")
    List<Income> findTopNByUserOrderByMonthYearDesc(@Param("user") User user, @Param("limit") int limit);
}
