package com.sct.rest.api.telegram.command;

import com.sct.rest.api.service.UserService;
import com.sct.rest.api.telegram.AbstractBotCommand;
import com.sct.rest.api.telegram.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class HelpCommand extends AbstractBotCommand {
    private final UserService userService;

    @Autowired
    public HelpCommand(SendMsg msg, UserService userService){
        super("/help", "help command", msg);
        this.userService = userService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try{
            if(userService.userExistByLogin(user.getUserName())){
                msg.send(absSender, chat.getId(), "Навигация", ParseMode.HTML, false);

                String help = "/info - информация о пользователе \n" +
                        "/infotrip - информация о совершенных поездках \n" +
                        "/search - поиск свободных ТС \n" +
                        "/trip имя_парковки идентификатор_ТС - начало аренды \n" +
                        "/finish имя_парковки идентификатор_ТС - конец аренды \n";
                msg.send(absSender, chat.getId(), help, ParseMode.HTML, false);
            }
            else{
                msg.send(absSender, chat.getId(), "Вы не зарегистрированы! Пройдите регистрацию", ParseMode.HTML, false);
            }
        }
        catch (Exception e){
            msg.send(absSender, chat.getId(), "Произошла ошибка, попробуйте ещё раз!", ParseMode.HTML, false);
            System.out.println("Ошибка в HelpCommand");
        }
    }
}