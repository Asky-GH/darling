package kz.epam.darling.command;

import kz.epam.darling.constant.Route;
import kz.epam.darling.command.admin.*;
import kz.epam.darling.command.auth.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory instance = new CommandFactory();
    private Map<String, Command> commands = new HashMap<>();

    private CommandFactory() {
        commands.put(Route.MAIN, new MainCommand());
        commands.put(Route.MATCH, new MatchCommand());
        commands.put(Route.IMAGE, new ImageCommand());
        commands.put(Route.LOGIN, new LoginCommand());
        commands.put(Route.REGISTRATION, new RegistrationCommand());
        commands.put(Route.LOGOUT, new LogoutCommand());
        commands.put(Route.PROFILE, new ProfileCommand());
        commands.put(Route.MESSAGES, new MessagesCommand());
        commands.put(Route.CHAT, new ChatCommand());
        commands.put(Route.ADMIN, new AdminCommand());
        commands.put(Route.USERS, new UsersCommand());
        commands.put(Route.LANGUAGES, new LanguagesCommand());
        commands.put(Route.GENDERS, new GendersCommand());
        commands.put(Route.COUNTRIES, new CountriesCommand());
        commands.put(Route.CITIES, new CitiesCommand());
        commands.put(Route.REFRESH, new RefreshCommand());
        commands.put(Route.LOCATION, new LocationCommand());
        commands.put(Route.LANGUAGE, new LanguageCommand());
        commands.put(Route.COUNTRY, new CountryCommand());
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String uri) {
        Command command = commands.get(uri);
        return command != null ? command : new NoCommand();
    }
}
