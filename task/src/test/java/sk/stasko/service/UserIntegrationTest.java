package sk.stasko.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stasko.command.Command;
import sk.stasko.command.CommandDispatcher;
import sk.stasko.command.CommandType;
import sk.stasko.command.handlers.AddUserCommandHandler;
import sk.stasko.command.handlers.DeleteAllUsersCommandHandler;
import sk.stasko.command.handlers.PrintAllUsersCommandHandler;
import sk.stasko.dto.UserDto;
import sk.stasko.infra.Consumer;
import sk.stasko.infra.Producer;
import sk.stasko.mapper.UserMapper;
import sk.stasko.repository.UserRepository;
import sk.stasko.repository.impl.UserRepositoryImpl;
import sk.stasko.service.impl.UserServiceImpl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

public class UserIntegrationTest {

    private Consumer consumer;
    private Thread consumerThread;
    private Producer producer;

    @BeforeEach
    public void setUp() throws Exception {
        BlockingQueue<Command> queue = new ArrayBlockingQueue<>(10);

        UserRepository repo = new UserRepositoryImpl();
        UserService service = new UserServiceImpl(repo);
        UserMapper mapper = new UserMapper();

        CommandDispatcher dispatcher = new CommandDispatcher();
        dispatcher.register(CommandType.ADD, new AddUserCommandHandler(service, mapper));
        dispatcher.register(CommandType.PRINT_ALL, new PrintAllUsersCommandHandler(service, mapper));
        dispatcher.register(CommandType.DELETE_ALL, new DeleteAllUsersCommandHandler(service));

        consumer = new Consumer(queue, dispatcher);
        consumerThread = new Thread(consumer);
        consumerThread.start();

        producer = new Producer(queue);
    }

    @AfterEach
    public void tearDown() throws Exception {
        consumer.stop();
        consumerThread.interrupt();
        consumerThread.join();
    }

    @Test
    public void testEndToEndAddPrintDelete() {
        assertThatNoException().isThrownBy(() -> {
            producer.send(Command.add(new UserDto(200, "guid-200", "Bob")));
            producer.send(Command.printAll());
            producer.send(Command.deleteAll());
            producer.send(Command.printAll());
            Thread.sleep(500);
        });
    }
}
