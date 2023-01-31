package com.sct.rest.api.telegram.command;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.service.TripService;
import com.sct.rest.api.service.UserService;
import com.sct.rest.api.telegram.AbstractBotCommand;
import com.sct.rest.api.telegram.SendMsg;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class FinishCommand extends AbstractBotCommand {
    private final UserService userService;
    private final TripService tripService;

    public FinishCommand(SendMsg msg, UserService userService, TripService tripService){
        super("/finish", "finish command", msg);
        this.userService = userService;
        this.tripService = tripService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try{
            if(userService.userExistByLogin(user.getUserName())){
                RentDto rent = tripService.endRentBot(user.getUserName(), strings[0], strings[1]);
                msg.send(absSender, chat.getId(), "Ваша поездка окончена! Спасибо, что выбрали нашего бота", ParseMode.HTML, false);
                String finish = "Время окончания поездки: " + rent.getEndTimeRent() + "\n" +
                        "Конечная парковка: " + rent.getEndParking().getName() + "\n" +
                        "ТС: " + rent.getTransport().getIdentificationNumber() + "\n" +
                        "Сумма поездки: " + rent.getAmount() + "₽";
                msg.send(absSender, chat.getId(), finish, ParseMode.HTML, false);
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
