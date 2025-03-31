package sk.stasko.command.handlers;

import sk.stasko.command.Command;
import sk.stasko.command.CommandHandler;
import sk.stasko.mapper.UserMapper;
import sk.stasko.service.UserService;
import sk.stasko.util.LoggerFactory;

import java.util.logging.Logger;

public class AddUserCommandHandler implements CommandHandler {
    private final UserService service;
    private final UserMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(AddUserCommandHandler.class);

    public AddUserCommandHandler(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public void handle(Command command) {
        try {
            service.addUser(mapper.toEntity(command.payload()));
        } catch (Exception e) {
            logger.severe("Failed to add user: " + e.getMessage());
        }
    }
}
