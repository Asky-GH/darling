package kz.epam.darling.controller.command;

import kz.epam.darling.controller.command.auth.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final String GET_LOGIN_ACTION = "get-login";
    private static final String POST_LOGIN_ACTION = "post-login";
    private static final String LOGOUT_ACTION = "logout";
    private static final String GET_REGISTRATION_ACTION = "get-registration";
    private static final String POST_REGISTRATION_ACTION = "post-registration";
    private static CommandFactory instance = new CommandFactory();
    private Map<String, Command> commands = new HashMap<>();


    private CommandFactory() {
        commands.put(GET_LOGIN_ACTION, new GetLoginCommand());
        commands.put(POST_LOGIN_ACTION, new PostLoginCommand());
        commands.put(LOGOUT_ACTION, new LogoutCommand());
        commands.put(GET_REGISTRATION_ACTION, new GetRegistrationCommand());
        commands.put(POST_REGISTRATION_ACTION, new PostRegistrationCommand());
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String uri) {
        Command command = commands.get(uri);
        return command != null ? command : new NoCommand();
    }
}
