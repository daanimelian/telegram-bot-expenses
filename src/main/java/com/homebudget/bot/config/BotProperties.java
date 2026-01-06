package com.homebudget.bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Configuración de propiedades del bot de Telegram.
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "telegram.bot")
public class BotProperties {

    /**
     * Username del bot de Telegram (sin @)
     */
    @NotBlank(message = "El username del bot es requerido")
    private String username;

    /**
     * Token de autenticación del bot obtenido de BotFather
     */
    @NotBlank(message = "El token del bot es requerido")
    private String token;
}
