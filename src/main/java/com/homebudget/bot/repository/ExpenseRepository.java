package com.homebudget.bot.repository;

import com.homebudget.bot.entity.Category;
import com.homebudget.bot.entity.Expense;
import com.homebudget.bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para la entidad Expense.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /**
     * Busca todos los gastos de un usuario.
     *
     * @param user Usuario propietario de los gastos
     * @return Lista de gastos del usuario
     */
    List<Expense> findByUser(User user);

    /**
     * Busca todos los gastos de un usuario ordenados por fecha descendente.
     *
     * @param user Usuario propietario de los gastos
     * @return Lista de gastos del usuario ordenados por fecha
     */
    List<Expense> findByUserOrderByExpenseDateDesc(User user);

    /**
     * Busca todos los gastos de una categoría.
     *
     * @param category Categoría de los gastos
     * @return Lista de gastos de la categoría
     */
    List<Expense> findByCategory(Category category);

    /**
     * Busca gastos de un usuario en un rango de fechas.
     *
     * @param user      Usuario propietario de los gastos
     * @param startDate Fecha de inicio
     * @param endDate   Fecha de fin
     * @return Lista de gastos en el rango de fechas
     */
    List<Expense> findByUserAndExpenseDateBetween(User user, LocalDate startDate, LocalDate endDate);

    /**
     * Busca gastos de un usuario en un rango de fechas ordenados por fecha descendente.
     *
     * @param user      Usuario propietario de los gastos
     * @param startDate Fecha de inicio
     * @param endDate   Fecha de fin
     * @return Lista de gastos en el rango de fechas ordenados
     */
    List<Expense> findByUserAndExpenseDateBetweenOrderByExpenseDateDesc(User user, LocalDate startDate, LocalDate endDate);

    /**
     * Calcula el total de gastos de un usuario.
     *
     * @param user Usuario propietario de los gastos
     * @return Suma total de los gastos
     */
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user = :user")
    BigDecimal sumAmountByUser(@Param("user") User user);

    /**
     * Calcula el total de gastos de un usuario en un rango de fechas.
     *
     * @param user      Usuario propietario de los gastos
     * @param startDate Fecha de inicio
     * @param endDate   Fecha de fin
     * @return Suma total de los gastos en el rango
     */
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user = :user AND e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByUserAndDateBetween(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Calcula el total de gastos de una categoría.
     *
     * @param category Categoría de los gastos
     * @return Suma total de los gastos de la categoría
     */
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.category = :category")
    BigDecimal sumAmountByCategory(@Param("category") Category category);

    /**
     * Calcula el total de gastos por categoría para un usuario en un rango de fechas.
     *
     * @param user      Usuario propietario de los gastos
     * @param startDate Fecha de inicio
     * @param endDate   Fecha de fin
     * @return Lista de objetos con categoría y total
     */
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e WHERE e.user = :user AND e.expenseDate BETWEEN :startDate AND :endDate GROUP BY e.category")
    List<Object[]> sumAmountByCategoryAndDateRange(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
