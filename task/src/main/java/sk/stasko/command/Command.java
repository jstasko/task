package sk.stasko.command;

import sk.stasko.dto.UserDto;

public record Command(CommandType type, UserDto payload) {
    public static Command add(UserDto user) { return new Command(CommandType.ADD, user); }
    public static Command printAll() { return new Command(CommandType.PRINT_ALL, null); }
    public static Command deleteAll() { return new Command(CommandType.DELETE_ALL, null); }
}
