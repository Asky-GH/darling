package kz.epam.darling.controller.command;

import kz.epam.darling.controller.command.auth.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final String LOGIN_ACTION = "/login";
    private static final String LOGOUT_ACTION = "/logout";
    private static final String REGISTRATION_ACTION = "/registration";
    private static final String PROFILE_ACTION = "/profile";
    private static final String MAIN_ACTION = "/main";
    private static final String MATCH_ACTION = "/match";
    private static final String CHAT_ACTION = "/chat";
    private static CommandFactory instance = new CommandFactory();
    private Map<String, Command> commands = new HashMap<>();


    private CommandFactory() {
        commands.put(LOGIN_ACTION, new LoginCommand());
        commands.put(LOGOUT_ACTION, new LogoutCommand());
        commands.put(REGISTRATION_ACTION, new RegistrationCommand());
        commands.put(PROFILE_ACTION, new ProfileCommand());
        commands.put(MAIN_ACTION, new MainCommand());
        commands.put(MATCH_ACTION, new MatchCommand());
        commands.put(CHAT_ACTION, new ChatCommand());
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String uri) {
        Command command = commands.get(uri);
        return command != null ? command : new NoCommand();
    }
}
