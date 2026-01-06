package com.homebudget.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal del Bot de Telegram para gestión de gastos domésticos.
 *
 * Este bot permite a los hogares registrar y gestionar sus ingresos y miembros familiares
 * a través de comandos de Telegram.
 */
@SpringBootApplication
public class BotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }
}
