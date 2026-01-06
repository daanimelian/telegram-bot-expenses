package com.homebudget.bot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un hogar registrado en el bot.
 * Cada hogar tiene un chat de Telegram asociado y puede tener múltiples miembros familiares.
 */
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_telegram_chat_id", columnList = "telegram_chat_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "telegram_chat_id", nullable = false, unique = true, length = 255)
    private String telegramChatId;

    @Column(name = "household_name", length = 255)
    private String householdName;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "encryption_salt", length = 255)
    private String encryptionSalt;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FamilyMember> familyMembers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Income> incomes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    /**
     * Retorna el nombre del hogar o un valor por defecto.
     */
    public String getDisplayName() {
        return householdName != null ? householdName : "Hogar " + telegramChatId;
    }
}
