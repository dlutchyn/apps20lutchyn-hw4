package ua.edu.ucu.immutable;

import ua.edu.ucu.collections.immutable.ImmutableLinkedList;

public class Queue {
    private ImmutableLinkedList queue;

    public Queue() {
        this.queue = new ImmutableLinkedList(new Object[0]);
    }

    public Object peek() {
        return this.queue.getLast();
    }

    public Object dequeue() {
        Object popObject = this.peek();
        this.queue = this.queue.removeLast();
        return popObject;
    }

    public void enqueue(Object e) {
        this.queue = this.queue.addFirst(e);
    }

    public boolean isEmpty() {
        return this.queue.length <= 0;
    }
}
