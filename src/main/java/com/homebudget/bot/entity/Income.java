package com.homebudget.bot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 * Entidad que representa los ingresos mensuales de un hogar.
 * Permite registrar ingresos en pesos argentinos (ARS) y dólares (USD).
 */
@Entity
@Table(name = "incomes",
        indexes = {
                @Index(name = "idx_incomes_user_id", columnList = "user_id"),
                @Index(name = "idx_incomes_month_year", columnList = "month_year"),
                @Index(name = "idx_incomes_user_month", columnList = "user_id, month_year")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_income_user_month", columnNames = {"user_id", "month_year"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_income_user"))
    private User user;

    @NotNull
    @Column(name = "month_year", nullable = false, columnDefinition = "VARCHAR(7)")
    @Convert(converter = YearMonthConverter.class)
    private YearMonth monthYear;

    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "amount_ars", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal amountARS = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "amount_usd", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal amountUSD = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "exchange_rate", precision = 10, scale = 2)
    private BigDecimal exchangeRate;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    /**
     * Calcula el ingreso total equivalente en ARS.
     * Si hay ingresos en USD, los convierte usando el tipo de cambio.
     */
    public BigDecimal getTotalInARS() {
        BigDecimal total = amountARS != null ? amountARS : BigDecimal.ZERO;

        if (amountUSD != null && exchangeRate != null &&
            amountUSD.compareTo(BigDecimal.ZERO) > 0 &&
            exchangeRate.compareTo(BigDecimal.ZERO) > 0) {
            total = total.add(amountUSD.multiply(exchangeRate));
        }

        return total;
    }

    /**
     * Retorna una representación legible del ingreso.
     */
    public String toDisplayString() {
        StringBuilder sb = new StringBuilder();
        sb.append(monthYear.toString()).append(": ");

        if (amountARS != null && amountARS.compareTo(BigDecimal.ZERO) > 0) {
            sb.append("ARS ").append(amountARS);
        }

        if (amountUSD != null && amountUSD.compareTo(BigDecimal.ZERO) > 0) {
            if (amountARS != null && amountARS.compareTo(BigDecimal.ZERO) > 0) {
                sb.append(" + ");
            }
            sb.append("USD ").append(amountUSD);

            if (exchangeRate != null) {
                sb.append(" (@").append(exchangeRate).append(")");
            }
        }

        return sb.toString();
    }

    /**
     * Converter para YearMonth a String (formato YYYY-MM)
     */
    @Converter
    public static class YearMonthConverter implements AttributeConverter<YearMonth, String> {

        @Override
        public String convertToDatabaseColumn(YearMonth attribute) {
            return attribute != null ? attribute.toString() : null;
        }

        @Override
        public YearMonth convertToEntityAttribute(String dbData) {
            return dbData != null ? YearMonth.parse(dbData) : null;
        }
    }
}
