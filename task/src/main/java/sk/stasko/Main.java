package sk.stasko;

import sk.stasko.command.Command;
import sk.stasko.command.CommandType;
import sk.stasko.command.handlers.AddUserCommandHandler;
import sk.stasko.command.handlers.DeleteAllUsersCommandHandler;
import sk.stasko.command.handlers.PrintAllUsersCommandHandler;
import sk.stasko.command.CommandDispatcher;
import sk.stasko.dto.UserDto;
import sk.stasko.infra.Consumer;
import sk.stasko.infra.Producer;
import sk.stasko.mapper.UserMapper;
import sk.stasko.repository.impl.UserRepositoryImpl;
import sk.stasko.service.impl.UserServiceImpl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) throws Exception {
        BlockingQueue<Command> queue = new ArrayBlockingQueue<>(10);

        var repository = new UserRepositoryImpl();
        var service = new UserServiceImpl(repository);
        var mapper = new UserMapper();

        var dispatcher = new CommandDispatcher();
        dispatcher.register(CommandType.ADD, new AddUserCommandHandler(service, mapper));
        dispatcher.register(CommandType.PRINT_ALL, new PrintAllUsersCommandHandler(service, mapper));
        dispatcher.register(CommandType.DELETE_ALL, new DeleteAllUsersCommandHandler(service));

        var consumer = new Consumer(queue, dispatcher);
        var thread = new Thread(consumer);
        thread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consumer.stop();
            thread.interrupt();
        }));

        var producer = new Producer(queue);
        producer.send(Command.add(new UserDto(1, "a1", "Robert")));
        producer.send(Command.add(new UserDto(2, "a2", "Martin")));
        producer.send(Command.printAll());
        producer.send(Command.deleteAll());
        producer.send(Command.printAll());

        Thread.sleep(1000);
        consumer.stop();
        thread.join();
    }
}