package com.sct.rest.api.telegram.command;

import com.sct.rest.api.model.entity.Customer;
import com.sct.rest.api.model.entity.enums.Role;
import com.sct.rest.api.service.CustomerService;
import com.sct.rest.api.telegram.AbstractBotCommand;
import com.sct.rest.api.telegram.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.math.BigDecimal;

@Component
public class StartCommand extends AbstractBotCommand {
    private final SendMsg msg;
    private final CustomerService customerService;
    @Value("${bot.name}")
    private String nameBot;
    @Value("${bot.beginBalance}")
    private BigDecimal beginBalance;

    @Autowired
    public StartCommand(SendMsg msg, CustomerService customerService){
        super("/start", "start command", msg);
        this.msg = msg;
        this.customerService = customerService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try{
            msg.send(absSender, chat.getId(), "Добро пожаловать в " + nameBot, ParseMode.HTML, false);
            if(customerService.userExistByLogin(user.getUserName())){
                msg.send(absSender, chat.getId(), String.format("Здравсвтуйте %s", user.getUserName()), ParseMode.HTML, false);
            }
            else{
                Customer newCustomer = new Customer();
                newCustomer.setLogin(user.getUserName());
                newCustomer.setPassword(user.getUserName());
                newCustomer.setBalance(beginBalance.longValue());
                newCustomer.setRole(Role.USER);
                customerService.createUser(newCustomer);

                String start = String.format(
                        "%s, вы были зарегистрированы! \n" +
                        "Для навигации введите /help", newCustomer.getLogin()
                );

                msg.send(absSender, chat.getId(), start, ParseMode.HTML, false);

            }
        }
        catch(Exception e){
            msg.send(absSender, chat.getId(), "Произошла ошибка, попробуйте ещё раз!", ParseMode.HTML, false);
            System.out.println("Ошибка в StartCommand");
        }
    }
}
