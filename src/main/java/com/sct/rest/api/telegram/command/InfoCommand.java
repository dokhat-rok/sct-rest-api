package com.sct.rest.api.telegram.command;

import com.sct.rest.api.model.dto.UserDto;
import com.sct.rest.api.service.TripService;
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
public class InfoCommand extends AbstractBotCommand {
    private final UserService userService;
    private final TripService tripService;

    @Autowired
    public InfoCommand(SendMsg msg, UserService userService, TripService tripService){
        super("/info", "info command", msg);
        this.userService = userService;
        this.tripService = tripService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try{
            if(userService.userExistByLogin(user.getUserName())){
                msg.send(absSender, chat.getId(), "Информация вашего аккаунта", ParseMode.HTML, false);
                UserDto userDto = userService.getUserByLogin(user.getUserName());
                String info = "Логин: " + userDto.getLogin() + "\n" +
                        "Баланс: " + userDto.getBalance() + "₽\n" +
                        "Роль: " + userDto.getRole() + "\n" +
                        "Количество совершенных поездок: " + tripService.countRentByUserId(userDto.getId());
                msg.send(absSender, chat.getId(), info, ParseMode.HTML, false);
            }
            else{
                msg.send(absSender, chat.getId(), "Вы не зарегистрированы! Пройдите регистрацию", ParseMode.HTML, false);
            }
        }
        catch (Exception e){
            msg.send(absSender, chat.getId(), "Произошла ошибка, попробуйте ещё раз!", ParseMode.HTML, false);
            System.out.println("Ошибка в InfoCommand");
        }
    }
}
