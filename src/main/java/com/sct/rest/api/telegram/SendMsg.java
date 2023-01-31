package com.sct.rest.api.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class SendMsg {
    public void send(AbsSender sender, Long chatId, String text, String parseMode, boolean enableMarkDown){
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId.toString());
        msg.enableMarkdown(enableMarkDown);
        msg.setText(text);
        try{
            sender.execute(msg);
        }
        catch (TelegramApiException e){
            System.out.println("Ошибка отправки сообщения");
        }
    }
}
