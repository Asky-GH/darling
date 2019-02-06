package kz.epam.darling.controller.command;

import kz.epam.darling.controller.command.auth.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory instance = new CommandFactory();
    private Map<String, Command> commands = new HashMap<>();


    private CommandFactory() {
        commands.put("/login", new LoginCommand());
        commands.put("/logout", new LogoutCommand());
        commands.put("/registration", new RegistrationCommand());
        commands.put("/profile", new ProfileCommand());
        commands.put("/main", new MainCommand());
        commands.put("/match", new MatchCommand());
        commands.put("/chat", new ChatCommand());
        commands.put("/image", new ImageCommand());
        commands.put("/refresh", new RefreshCommand());
        commands.put("/location", new LocationCommand());
        commands.put("/messages", new MessagesCommand());
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String uri) {
        Command command = commands.get(uri);
        return command != null ? command : new NoCommand();
    }
}
