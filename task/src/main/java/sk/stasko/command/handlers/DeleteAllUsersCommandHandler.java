package sk.stasko.command.handlers;

import sk.stasko.command.Command;
import sk.stasko.command.CommandHandler;
import sk.stasko.service.UserService;
import sk.stasko.util.LoggerFactory;

import java.util.logging.Logger;

public class DeleteAllUsersCommandHandler implements CommandHandler {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(DeleteAllUsersCommandHandler.class);

    public DeleteAllUsersCommandHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(Command command) {
        try {
            userService.deleteAllUsers();
        } catch (Exception e) {
            logger.severe("Failed to delete users: " + e.getMessage());
        }
    }
}
