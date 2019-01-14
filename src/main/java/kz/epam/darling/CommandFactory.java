package kz.epam.darling;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory instance = null;
    private Map<String, Command> commands = new HashMap<>();
    private static final String LOGIN_ACTION = "/login";
    private static final String LOGOUT_ACTION = "/logout";


    private CommandFactory() {
        commands.put(LOGIN_ACTION, new LoginCommand());
        commands.put(LOGOUT_ACTION, new LogoutCommand());
    }

    public static CommandFactory getInstance() {
        return instance != null ? instance : new CommandFactory();
    }

    public Command getCommand(String uri) {
        Command command = commands.get(uri);
        return command != null ? command : new NoCommand();
    }
}
