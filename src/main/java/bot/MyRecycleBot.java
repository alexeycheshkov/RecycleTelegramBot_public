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
    private String time = "неизвестно, свяжитесь с нами удобным вам способом.";
    private String botUserName = System.getenv("BOT_NAME");
    private String botToken = System.getenv("BOT_TOKEN");

    public MyRecycleBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()){
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
            switch (itemType) {
                case "plastic":
                    execute(SendPhoto.builder().chatId(chatId).photo(new InputFile(new File("src/main/resources/photo/plastic.png"))).build());
                    execute(SendMessage.builder().chatId(chatId).text(FileUtils.readFile("src/main/resources/description/recycle/plastic.txt")).build());
                    break;
                case "fe_al":
                    execute(SendPhoto.builder().chatId(chatId).photo(new InputFile(new File("src/main/resources/photo/fe_al.png"))).build());
                    execute(SendMessage.builder().chatId(chatId).text(FileUtils.readFile("src/main/resources/description/recycle/fe_al.txt")).build());
                    break;
                case "glass":
                    execute(SendPhoto.builder().chatId(chatId).photo(new InputFile(new File("src/main/resources/photo/glass.png"))).build());
                    execute(SendMessage.builder().chatId(chatId).text(FileUtils.readFile("src/main/resources/description/recycle/glass.txt")).build());
                    break;
                case "paper":
                    execute(SendPhoto.builder().chatId(chatId).photo(new InputFile(new File("src/main/resources/photo/paper.png"))).build());
                    execute(SendMessage.builder().chatId(chatId).text(FileUtils.readFile("src/main/resources/description/recycle/paper.txt")).build());
                    break;
                default:
                    execute(SendMessage.builder().text("Error.. Please try again.").build());
                    break;
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public synchronized void responseMsg(SendMessage message) {
        Keyboard.setMainMenuButtons(message);
        String chatId = message.getChatId();
        if (message.getText().startsWith("/setTime")) {
            time = message.getText().replace("/setTime ","");
            FileUtils.writeWorkTime("src/main/resources/worktime.txt",time);
        } else {
            try {
                switch (message.getText()) {
                    case "/start":
                        message.setText("Приветствую тебя, эко-активист! Я могу рассказать о нашем движении, о сырьё которое мы собираем на переработку и о нашем пункте сбора.");
                        execute(message);
                        break;
                    case "Контакты":
                        message.setText("Остались вопросы? Звони:");
                        execute(message);
                        execute(SendContact.builder().chatId(chatId).firstName("Александра").phoneNumber("+79198443019").build());
                        execute(SendContact.builder().chatId(chatId).firstName("Виктория").phoneNumber("+79510342670").build());
                        Keyboard.setLinkButtons(message);
                        message.setText("Мы в соц сетях:");
                        execute(message);
                        break;
                    case "О нас":
                        message.setText(FileUtils.readFile("src/main/resources/description/about.txt"));
                        Keyboard.setAboutLinkButtons(message);
                        execute(message);
                        break;
                    case "Адреса":
                        execute(SendVenue.builder().chatId(chatId).title("Пункт сбора сырья").address("г. Орск, ул. Короленко 132").longitude(58.456102).latitude(51.254518).build());
                        message.setText("Ближайшее время сбора: " + FileUtils.readFile("src/main/resources/worktime.txt"));
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
}
