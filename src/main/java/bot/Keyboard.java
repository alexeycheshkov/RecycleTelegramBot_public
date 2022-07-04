package bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

    public static synchronized void setMainMenuButtons(SendMessage message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(new KeyboardButton("Что принимаем"));
        firstRow.add(new KeyboardButton("О нас"));

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add(new KeyboardButton("Контакты"));
        secondRow.add(new KeyboardButton("Адреса"));

        keyboard.add(firstRow);
        keyboard.add(secondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public static synchronized void setRecycleItemButtons(SendMessage message) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> buttonsSecondRow = new ArrayList<>();
        List<InlineKeyboardButton> buttonsThirdRow = new ArrayList<>();

        InlineKeyboardButton plastic = new InlineKeyboardButton("plastic");
        plastic.setText("Пластик");
        plastic.setCallbackData("plastic");
        buttonsFirstRow.add(plastic);

        InlineKeyboardButton metal = new InlineKeyboardButton("metal");
        metal.setText("Металл");
        metal.setCallbackData("fe_al");
        buttonsSecondRow.add(metal);

        InlineKeyboardButton glass = new InlineKeyboardButton("glass");
        glass.setText("Стекло");
        glass.setCallbackData("glass");
        buttonsSecondRow.add(glass);

        InlineKeyboardButton paper = new InlineKeyboardButton("paper");
        paper.setText("Макулатура");
        paper.setCallbackData("paper");
        buttonsThirdRow.add(paper);

        InlineKeyboardButton tetra = new InlineKeyboardButton("tetra");
        tetra.setText("Tetra-Pak");
        tetra.setCallbackData("tetra");
        buttonsThirdRow.add(tetra);

        keyboard.add(buttonsFirstRow);
        keyboard.add(buttonsSecondRow);
        keyboard.add(buttonsThirdRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(inlineKeyboardMarkup);
    }

    public static synchronized void setLinkButtons(SendMessage message) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttonsFirstRow = new ArrayList<>();

        InlineKeyboardButton vkLink = new InlineKeyboardButton("vk");
        vkLink.setText("VK");
        vkLink.setUrl("https://vk.com/chistyisled_orsk");
        InlineKeyboardButton instLink = new InlineKeyboardButton("inst");
        instLink.setText("Instagram");
        instLink.setUrl("https://www.instagram.com/chistyisled_orsk/");

        buttonsFirstRow.add(vkLink);
        buttonsFirstRow.add(instLink);
        keyboard.add(buttonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);
    }

    public static synchronized void setAboutLinkButtons(SendMessage message) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> buttonsSecondRow = new ArrayList<>();
        List<InlineKeyboardButton> buttonsThirdRow = new ArrayList<>();
        List<InlineKeyboardButton> buttonsForthRow = new ArrayList<>();
        List<InlineKeyboardButton> buttonsFifthRow = new ArrayList<>();
        List<InlineKeyboardButton> buttonsSixRow = new ArrayList<>();

        InlineKeyboardButton oh = new InlineKeyboardButton("oh");
        oh.setText("От хорового пения до сортировки мусора – один шаг");
        oh.setUrl("https://hron.ru/news/read/66682");
        InlineKeyboardButton go = new InlineKeyboardButton("go");
        go.setText("Мусор, собранный в гараже, отправят на переработку");
        go.setUrl("http://gazetaorsk.ru/upravdom/musor-sobrannyj-v-garazhe-otpravyat-na-pererabotku");
        InlineKeyboardButton oru = new InlineKeyboardButton("oru");
        oru.setText("11 орских семей уже начали разделять мусор по категориям");
        oru.setUrl("https://pda.orsk.ru/news/106648-11-orskih-semey-uje-nachali-razdelyat-musor-po-kategoriyam");
        InlineKeyboardButton oru2 = new InlineKeyboardButton("oru2");
        oru2.setText("Орчане организовали сбор вторсырья");
        oru2.setUrl("https://orsk.ru/news/113522");
        InlineKeyboardButton oh2 = new InlineKeyboardButton("oh2");
        oh2.setText("Как местный тетрапак до мегаполисов добирается");
        oh2.setUrl("https://hron.ru/news/read/68815");
        InlineKeyboardButton oh3 = new InlineKeyboardButton("oh3");
        oh3.setText("Мусор в дело, или О том, как добровольцы Чистого следа учат раздельному сбору");
        oh3.setUrl("https://hron.ru/news/read/69699");

        buttonsFirstRow.add(oh);
        buttonsSecondRow.add(oru);
        buttonsThirdRow.add(go);
        keyboard.add(buttonsFirstRow);
        keyboard.add(buttonsSecondRow);
        keyboard.add(buttonsThirdRow);
        buttonsForthRow.add(oru2);
        buttonsFifthRow.add(oh2);
        buttonsSixRow.add(oh3);
        keyboard.add(buttonsForthRow);
        keyboard.add(buttonsFifthRow);
        keyboard.add(buttonsSixRow);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);
    }
}
