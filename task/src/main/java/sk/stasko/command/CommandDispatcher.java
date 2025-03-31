package sk.stasko.command;

import java.util.EnumMap;
import java.util.Map;

public class CommandDispatcher {
    private final Map<CommandType, CommandHandler> handlers = new EnumMap<>(CommandType.class);

    public void register(CommandType type, CommandHandler handler) {
        handlers.put(type, handler);
    }

    public void dispatch(Command command) {
        var handler = handlers.get(command.type());
        if (handler != null) handler.handle(command);
    }
}
