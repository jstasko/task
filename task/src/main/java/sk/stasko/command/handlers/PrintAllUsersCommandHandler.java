package sk.stasko.command.handlers;

import sk.stasko.command.Command;
import sk.stasko.command.CommandHandler;
import sk.stasko.mapper.UserMapper;
import sk.stasko.service.UserService;
import sk.stasko.util.LoggerFactory;

import java.util.logging.Logger;

public class PrintAllUsersCommandHandler implements CommandHandler {
    private final UserService service;
    private final UserMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(PrintAllUsersCommandHandler.class);

    public PrintAllUsersCommandHandler(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public void handle(Command command) {
        try {
            service.getAllUsers().stream()
                    .map(mapper::toDto)
                    .forEach(user -> System.out.println(user.id() + " " + user.guid() + " " + user.name()));
        } catch (Exception e) {
            logger.severe("Failed to print users: " + e.getMessage());
        }
    }
}
