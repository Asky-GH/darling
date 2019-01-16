package kz.epam.darling;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory instance;
    private Map<String, Command> commands;
    private static final String LOGIN_ACTION = "/login";
    private static final String LOGOUT_ACTION = "/logout";
    private static final String REGISTRATION_ACTION = "/registration";


    private CommandFactory() {
        commands = new HashMap<>();
        commands.put(LOGIN_ACTION, new LoginCommand());
        commands.put(LOGOUT_ACTION, new LogoutCommand());
        commands.put(REGISTRATION_ACTION, new RegistrationCommand());
    }

    public static CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    public Command getCommand(String uri) {
        Command command = commands.get(uri);
        return command != null ? command : new NoCommand();
    }
}
