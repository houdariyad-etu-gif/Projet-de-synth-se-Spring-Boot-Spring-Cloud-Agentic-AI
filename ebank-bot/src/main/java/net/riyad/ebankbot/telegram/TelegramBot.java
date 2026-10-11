package net.riyad.ebankbot.telegram;

import jakarta.annotation.PostConstruct;
import net.riyad.ebankbot.agents.EbankAIAgent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final EbankAIAgent aiAgent;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.username:EbankBot}")
    private String username;

    public TelegramBot(EbankAIAgent aiAgent) {
        this.aiAgent = aiAgent;
    }

    @PostConstruct
    public void registerTelegramBot() throws TelegramApiException {
        new TelegramBotsApi(DefaultBotSession.class).registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String chatId = update.getMessage().getChatId().toString();
        String query = update.getMessage().getText();
        String conversationId = "telegram-" + chatId;

        try {
            String answer = aiAgent.chat(query, conversationId);
            execute(new SendMessage(chatId, answer));
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Unable to send Telegram response", e);
        } catch (RuntimeException e) {
            try {
                execute(new SendMessage(chatId, "Le service IA est temporairement indisponible."));
            } catch (TelegramApiException sendException) {
                e.addSuppressed(sendException);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
