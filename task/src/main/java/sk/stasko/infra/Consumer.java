package sk.stasko.infra;

import sk.stasko.command.Command;
import sk.stasko.command.CommandDispatcher;
import sk.stasko.util.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class Consumer implements Runnable {
    private final BlockingQueue<Command> queue;
    private final CommandDispatcher dispatcher;
    private volatile boolean running = true;
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    public Consumer(BlockingQueue<Command> queue, CommandDispatcher dispatcher) {
        this.queue = queue;
        this.dispatcher = dispatcher;
    }

    public void stop() {
        running = false;
        logger.info("Consumer shutdown initiated.");
    }

    @Override
    public void run() {
        while (running) {
            try {
                Command command = queue.take();
                dispatcher.dispatch(command);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.severe("Error processing command: " + e.getMessage());
            }
        }
    }
}
