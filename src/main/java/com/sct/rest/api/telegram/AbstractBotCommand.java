package com.sct.rest.api.telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

public abstract class AbstractBotCommand extends BotCommand {
    protected final SendMsg msg;

    public AbstractBotCommand(String commandIdentifier, String description, SendMsg msg){
        super(commandIdentifier, description);
        this.msg = msg;
    }
}
