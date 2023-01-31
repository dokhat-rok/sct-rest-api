package com.sct.rest.api.telegram.command;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.entity.RentStatus;
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

import java.util.List;

@Component
public class TripInfoCommand extends AbstractBotCommand {
    private final UserService userService;
    private final TripService tripService;

    @Autowired
    public TripInfoCommand(SendMsg msg, UserService userService, TripService tripService){
        super("/infotrip", "tripInfo command", msg);
        this.userService = userService;
        this.tripService = tripService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try{
            if(userService.userExistByLogin(user.getUserName())){
                msg.send(absSender, chat.getId(), "Ваши поездки", ParseMode.HTML, false);
                List<RentDto> rentDtoList = tripService.allRentByUser(user.getUserName());
                for(var rent : rentDtoList){
                    String status;
                    String amount;
                    String endTime;
                    String endParkingName;

                    if(rent.getStatus() == RentStatus.OPEN){
                        status = "открыта";
                    }
                    else{
                        status = "закрыта";
                    }

                    if(rent.getAmount() == null){
                        amount = "-";
                    }
                    else {
                        amount = rent.getAmount().toString() + "₽";
                    }

                    if(rent.getEndTimeRent() == null){
                        endTime = "-";
                    }
                    else{
                        endTime = rent.getEndTimeRent().toString();
                    }

                    if(rent.getEndParking() == null){
                        endParkingName = "-";
                    }
                    else{
                        endParkingName = rent.getEndParking().getName();
                    }

                    String tripInfo = "Начало поездки: " + rent.getBeginTimeRent() + "\n" +
                            "Конец поездки: " + endTime + "\n" +
                            "Начальная парковка: " + rent.getBeginParking().getName() + "\n" +
                            "Конечная парковка: " + endParkingName + "\n" +
                            "ТС: " + rent.getTransport().getIdentificationNumber() + "\n" +
                            "Статус: " + status + "\n" +
                            "Сумма: " + amount;
                    msg.send(absSender, chat.getId(), tripInfo, ParseMode.HTML, false);
                }
            }
            else{
                msg.send(absSender, chat.getId(), "Вы не зарегистрированы! Пройдите регистрацию", ParseMode.HTML, false);
            }
        }
        catch (Exception e){
            msg.send(absSender, chat.getId(), "Произошла ошибка, попробуйте ещё раз!", ParseMode.HTML, false);
            System.out.println("Ошибка в TripInfoCommand");
        }
    }
}