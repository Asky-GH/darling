package kz.epam.darling.controller.command;

import kz.epam.darling.controller.command.auth.LoginCommand;
import kz.epam.darling.controller.command.auth.LogoutCommand;
import kz.epam.darling.controller.command.auth.RegistrationCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final String LOGIN_ACTION = "/login";
    private static final String LOGOUT_ACTION = "/logout";
    private static final String REGISTRATION_ACTION = "/registration";
    private static CommandFactory instance = new CommandFactory();
    private Map<String, Command> commands = new HashMap<>();


    private CommandFactory() {
        commands.put(LOGIN_ACTION, new LoginCommand());
        commands.put(LOGOUT_ACTION, new LogoutCommand());
        commands.put(REGISTRATION_ACTION, new RegistrationCommand());
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String uri) {
        Command command = commands.get(uri);
        return command != null ? command : new NoCommand();
    }
}
