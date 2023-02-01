package com.sct.rest.api.telegram.command;

import com.sct.rest.api.model.dto.ParkingDto;
import com.sct.rest.api.model.entity.enums.TransportType;
import com.sct.rest.api.service.ParkingService;
import com.sct.rest.api.service.CustomerService;
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
public class SearchCommand extends AbstractBotCommand {
    private final CustomerService customerService;
    private final ParkingService parkingService;

    @Autowired
    public SearchCommand(SendMsg msg, CustomerService customerService, ParkingService parkingService){
        super("/search", "search command", msg);
        this.customerService = customerService;
        this.parkingService = parkingService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try {
            if (customerService.userExistByLogin(user.getUserName())) {
                msg.send(absSender, chat.getId(), "Парковки", ParseMode.HTML, false);
                List<ParkingDto> parkingDtoList = parkingService.getAllParking();
                for(var parking : parkingDtoList){
                    StringBuilder search = new StringBuilder();
                    search.append(parking.getName()).append("\n");
                    for (var transport : parking.getTransports()) {
                        String type;
                        String speedAndPercentage;
                        if(transport.getType() == TransportType.BICYCLE){
                            type = "Велосипед";
                            speedAndPercentage = "\n";
                        }
                        else{
                            type = "Электрический самокат";
                            speedAndPercentage = transport.getChargePercentage() + "% " + "Скорость: " + transport.getMaxSpeed() + " км/ч\n";
                        }
                        search.append(type).append(" (").append(transport.getIdentificationNumber()).append(") ").append(speedAndPercentage);
                    }
                    msg.send(absSender, chat.getId(), search.toString(), ParseMode.HTML, false);
                }

            }
            else {
                msg.send(absSender, chat.getId(), "Вы не зарегистрированы! Пройдите регистрацию", ParseMode.HTML, false);
            }
        }
        catch (Exception e) {
            msg.send(absSender, chat.getId(), "Произошла ошибка, попробуйте ещё раз!", ParseMode.HTML, false);
            System.out.println("Ошибка в SearchCommand");
        }
    }
}