package main;

import bot.MyRecycleBot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        String PORT = System.getenv("PORT");
        MyRecycleBot recycleBot = new MyRecycleBot(new DefaultBotOptions());
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(recycleBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        try (ServerSocket serverSocket = new ServerSocket(Integer.valueOf(PORT))) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
