package ru.examples;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.examples.model.City;
import ru.examples.repository.CityRepository;

@Component
public class TravelBot extends TelegramLongPollingBot {

  private static final String BOT_NAME = "ExampleOfTravelBot";
  private static final String BOT_TOKEN = "your token";
  private static final Logger log = Logger.getLogger(TravelBot.class.getName());
  private final CityRepository repository;

  @Autowired
  public TravelBot(CityRepository repository) {
    this.repository = repository;
  }


  public void onUpdateReceived(Update update) {
    String message = update.getMessage().getText();
    String text;
    if (message.equals("/start")) {
      text = "Туристический телеграм бот\n"
          + "Выдает пользователю справочную информацию о введенном городе.\n"
          + "Например, пользователь вводит: «Москва», чат-бот отвечает: \n"
          + "«Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))».\n";
    } else {
      City city = repository.findByName(message);
      text = city != null ? "Стоит посетить " + city.getInfo() : "Нет города в базе";
    }
    sendMsg(update.getMessage().getChatId().toString(), text);
  }

  private void sendMsg(String chatId, String text) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
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
