package com.homebudget.bot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa un gasto registrado por el usuario.
 */
@Entity
@Table(name = "expenses",
        indexes = {
                @Index(name = "idx_expenses_user_id", columnList = "user_id"),
                @Index(name = "idx_expenses_category_id", columnList = "category_id"),
                @Index(name = "idx_expenses_expense_date", columnList = "expense_date"),
                @Index(name = "idx_expenses_created_at", columnList = "created_at")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_expense_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_expense_category"))
    private Category category;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", length = 3)
    @Builder.Default
    private String currency = "ARS";

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(name = "expense_date", nullable = false)
    @Builder.Default
    private LocalDate expenseDate = LocalDate.now();

    /**
     * Retorna una representación legible del gasto.
     */
    public String toDisplayString() {
        StringBuilder sb = new StringBuilder();
        sb.append(currency).append(" ").append(amount);
        if (category != null) {
            sb.append(" - ").append(category.toString());
        }
        if (description != null && !description.isEmpty()) {
            sb.append(" (").append(description).append(")");
        }
        return sb.toString();
    }
}
