package com.homebudget.bot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Configuración principal del bot de Telegram.
 * Maneja los mensajes entrantes y las actualizaciones del bot.
 */
@Slf4j
@Component
public class TelegramBotConfig extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Loguear información básica del mensaje recibido
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getFrom().getUserName();
            String firstName = update.getMessage().getFrom().getFirstName();
            String messageText = update.getMessage().getText();

            log.info("Mensaje recibido de {} (@{}) [chatId: {}]: {}",
                    firstName, userName, chatId, messageText);

            // TODO: Implementar lógica de manejo de comandos
            // Por ahora solo logueamos los mensajes recibidos
        } else if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String callbackData = update.getCallbackQuery().getData();

            log.info("Callback recibido [chatId: {}]: {}", chatId, callbackData);

            // TODO: Implementar lógica de manejo de callbacks
        } else {
            log.debug("Update recibido sin mensaje de texto: {}", update.getUpdateId());
        }
    }
}
