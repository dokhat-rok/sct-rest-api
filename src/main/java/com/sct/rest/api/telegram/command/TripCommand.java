package com.sct.rest.api.telegram.command;

import com.sct.rest.api.model.dto.RentDto;
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
public class TripCommand extends AbstractBotCommand {
    private final UserService userService;
    private final TripService tripService;

    @Autowired
    public TripCommand(SendMsg msg, UserService userService, TripService tripService){
        super("/trip", "trip command", msg);
        this.userService = userService;
        this.tripService = tripService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try{
            if(userService.userExistByLogin(user.getUserName())){
                RentDto rent = tripService.beginRentBot(user.getUserName(), strings[0], strings[1]);
                msg.send(absSender, chat.getId(), "Ваша поездка началась. Приятного пути!", ParseMode.HTML, false);
                String trip = "Время начала поездки: " + rent.getBeginTimeRent() + "\n" +
                        "Стартовая парковка: " + rent.getBeginParking().getName() + "\n" +
                        "ТС: " + rent.getTransport().getIdentificationNumber();
                msg.send(absSender, chat.getId(), trip, ParseMode.HTML, false);
            }
            else{
                msg.send(absSender, chat.getId(), "Вы не зарегистрированы! Пройдите регистрацию", ParseMode.HTML, false);
            }
        }
        catch (Exception e) {
            msg.send(absSender, chat.getId(), "Произошла ошибка, попробуйте ещё раз!", ParseMode.HTML, false);
            System.out.println("Ошибка в TripCommand");
        }
    }
}