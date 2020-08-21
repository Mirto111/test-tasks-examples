package ru.examples;

import java.util.HashMap;
import java.util.Map;
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
  private static final String BOT_TOKEN = System.getProperty("token");
  private static final Map<String, String> cities = new HashMap<>();
  private static final Logger log = Logger.getLogger(TravelBot.class.getName());

  static {
    cities.put("Москва", "Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))");
    cities.put("Санкт-Петербург", "Посетите Эрмитаж");
    cities.put("Нью-йорк", "Посетите Статую Свободы, Empire State Building");
  }

  public static void mains(String[] args) {
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
    String text = cities.get(message);
    sendMsg(update.getMessage().getChatId().toString(), text);
  }

  private synchronized void sendMsg(String chatId, String s) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
    String text = s != null ? s : "Нет города в базе";
    sendMessage.setText(text);
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
