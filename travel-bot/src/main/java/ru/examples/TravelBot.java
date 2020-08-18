package ru.examples;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class TravelBot extends TelegramLongPollingBot {

  private static final String BOT_NAME = "ExampleOfTravelBot";
  private static final String BOT_TOKEN = "your token";

  Logger log = Logger.getLogger("TravelBot");

  public static void main(String[] args) {
    ApiContextInitializer.init();
    TelegramBotsApi telegramBotApi = new TelegramBotsApi();
    TravelBot travelBot = new TravelBot();

    try {
      telegramBotApi.registerBot(travelBot);
    } catch (TelegramApiRequestException e) {
      e.printStackTrace();
    }
  }

  public void onUpdateReceived(Update update) {
    String message = update.getMessage().getText();
    sendMsg(update.getMessage().getChatId().toString(), message);
  }

  public synchronized void sendMsg(String chatId, String s) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(s);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      log.log(Level.SEVERE, "Exception: ", e.toString());
    }
  }

  public String getBotUsername() {
    return BOT_NAME;
  }

  public String getBotToken() {
    return BOT_TOKEN;
  }

}
