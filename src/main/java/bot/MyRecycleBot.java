package bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.FileUtils;

import java.io.File;

public class MyRecycleBot extends TelegramLongPollingBot {
    private static final String START = "/start";
    private static final String SET_TIME = "/setTime";
    private static final String PHOTO_SOURCE_PATH = "src/main/resources/photo/";
    private static final String TEXT_SOURCE_PATH = "src/main/resources/description/";
    private static final String BOT_USER_NAME = System.getenv("BOT_NAME");
    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String WORK_TIME_FILE_DIR = "src/main/resources/worktime.txt";

    public MyRecycleBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotUsername() {
        return BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            recycleItemResponse(update.getCallbackQuery().getMessage().getChatId().toString(), update.getCallbackQuery().getData());
        }
        if (update.hasMessage() | update.hasCallbackQuery()) {
            if (update.getMessage() != null && update.getMessage().hasText()) {
                messageHandling(update.getMessage().getChatId().toString(), update.getMessage().getText());
            }
        }
    }

    public synchronized void messageHandling(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        responseMsg(sendMessage);
    }

    public synchronized void recycleItemResponse(String chatId, String itemType) {
        try {
            execute(SendPhoto.builder().chatId(chatId).photo(getPhotoByItem(itemType)).build());
            execute(SendMessage.builder().chatId(chatId).text(getTextByItem(itemType)).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public synchronized void responseMsg(SendMessage message) {
        Keyboard.setMainMenuButtons(message);
        String chatId = message.getChatId();
        if (message.getText().startsWith(SET_TIME)) {
            String time = message.getText().replace(SET_TIME, "").trim();
            FileUtils.writeWorkTime(WORK_TIME_FILE_DIR, time);
        } else {
            try {
                switch (message.getText()) {
                    case START:
                        message.setText("Приветствую тебя, эко-активист!\nЯ могу рассказать о нашем движении, о сырье которое " +
                                "мы собираем на переработку и о нашем пункте сбора.");
                        execute(message);
                        break;
                    case "Контакты":
                        message.setText("Остались вопросы? Звони:");
                        execute(message);
                        execute(SendContact.builder().chatId(chatId).firstName("Александра").phoneNumber("+79198443019").build());
                        execute(SendContact.builder().chatId(chatId).firstName("Надежда").phoneNumber("+79225580545").build());
                        Keyboard.setLinkButtons(message);
                        message.setText("Мы в соц сетях:");
                        execute(message);
                        break;
                    case "О нас":
                        message.setText(getTextByItem("about"));
                        Keyboard.setAboutLinkButtons(message);
                        execute(message);
                        break;
                    case "Адреса":
                        execute(SendVenue.builder().chatId(chatId).title("Пункт сбора сырья").address("г. Орск, ул. Короленко 132")
                                .longitude(58.456102).latitude(51.254518).build());
//                        String meetTime = FileUtils.readFile(WORK_TIME_FILE_DIR);
//                        if (meetTime.isEmpty()) {
//                            meetTime = "неизвестно, свяжитесь с нами удобным для вас способом.";
//                        }
//                        message.setText("Ближайшее время сбора: " + meetTime);
                        execute(message);
                        break;
                    case "Что принимаем":
                        Keyboard.setRecycleItemButtons(message);
                        message.setText("Выберете тип сырья для просмотра подробной информации");
                        execute(message);
                        break;
                    default:
                        message.setText("Введите корректную команду или выберите требуемый пункт меню");
                        execute(message);
                        break;
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private InputFile getPhotoByItem(String item) {
        return new InputFile(new File(PHOTO_SOURCE_PATH + item + ".png"));
    }

    private String getTextByItem(String item) {
        return FileUtils.readFile(TEXT_SOURCE_PATH + item + ".txt");
    }
}
