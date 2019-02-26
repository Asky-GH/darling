package kz.epam.darling.controller.command;

import kz.epam.darling.controller.command.admin.*;
import kz.epam.darling.controller.command.auth.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory instance = new CommandFactory();
    private Map<String, Command> commands = new HashMap<>();


    private CommandFactory() {
        commands.put("/main", new MainCommand());
        commands.put("/match", new MatchCommand());
        commands.put("/image", new ImageCommand());

        commands.put("/login", new LoginCommand());
        commands.put("/registration", new RegistrationCommand());

        commands.put("/logout", new LogoutCommand());

        commands.put("/profile", new ProfileCommand());
        commands.put("/messages", new MessagesCommand());

        commands.put("/chat", new ChatCommand());

        commands.put("/admin", new AdminCommand());
        commands.put("/admin/users", new UsersCommand());
        commands.put("/admin/languages", new LanguagesCommand());
        commands.put("/admin/genders", new GendersCommand());
        commands.put("/admin/countries", new CountriesCommand());
        commands.put("/admin/cities", new CitiesCommand());

        commands.put("/refresh", new RefreshCommand());
        commands.put("/location", new LocationCommand());
        commands.put("/language", new LanguageCommand());
        commands.put("/country", new CountryCommand());
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String uri) {
        Command command = commands.get(uri);
        return command != null ? command : new NoCommand();
    }
}
