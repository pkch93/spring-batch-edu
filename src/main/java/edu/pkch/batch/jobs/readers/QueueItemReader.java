package edu.pkch.batch.jobs.readers;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueItemReader<User> implements ItemReader<User> {

    private Queue<User> queue;

    public QueueItemReader(List<User> users) {
        this.queue = new LinkedList<>(users);
    }

    @Override
    public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return this.queue.poll();
    }

}
