package com.homebudget.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Aplicación principal del Bot de Telegram para gestión de gastos domésticos.
 *
 * Este bot permite a los usuarios registrar y gestionar sus gastos personales
 * a través de comandos de Telegram.
 */
@SpringBootApplication
@EnableJpaAuditing
public class BotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }
}
