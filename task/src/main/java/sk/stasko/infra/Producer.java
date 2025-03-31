package sk.stasko.infra;

import sk.stasko.command.Command;

import java.util.concurrent.BlockingQueue;

public class Producer {
    private final BlockingQueue<Command> queue;

    public Producer(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    public void send(Command command) throws InterruptedException {
        queue.put(command);
    }
}
