package com.sct.rest.api.telegram.command;

import com.sct.rest.api.model.dto.CustomerDto;
import com.sct.rest.api.service.TripService;
import com.sct.rest.api.service.CustomerService;
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
    private final CustomerService customerService;
    private final TripService tripService;

    @Autowired
    public InfoCommand(SendMsg msg, CustomerService customerService, TripService tripService){
        super("/info", "info command", msg);
        this.customerService = customerService;
        this.tripService = tripService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try{
            if(customerService.userExistByLogin(user.getUserName())){
                msg.send(absSender, chat.getId(), "Информация вашего аккаунта", ParseMode.HTML, false);
                CustomerDto customerDto = customerService.getUserByLogin(user.getUserName());
                String info = "Логин: " + customerDto.getLogin() + "\n" +
                        "Баланс: " + customerDto.getBalance() + "₽\n" +
                        "Роль: " + customerDto.getRole() + "\n" +
                        "Количество совершенных поездок: " + tripService.countRentByUserId(customerDto.getId());
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
