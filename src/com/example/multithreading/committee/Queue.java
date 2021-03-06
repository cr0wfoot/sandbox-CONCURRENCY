package com.example.multithreading.committee;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Queue {

    private final static int LOW_LIMIT = 25;

    private Semaphore prod;
    private List<Document> docs = new LinkedList<>();

    public Queue(Semaphore prod) {
        this.prod = prod;
    }

    public synchronized void push(Document doc) {
        docs.add(doc);
        if (docs.size() > 0) {
            notifyAll();
        }
    }

    public synchronized Document pop() {
        int size;
        if ((size = docs.size()) <= LOW_LIMIT && docs.size() != 0 && prod.availablePermits() == 0) {
            System.out.println("!!! now size is " + size + " so release pusher");
            prod.release(2);
        }
        if (docs.size() == 0) {
            return null;
        }
        return docs.remove(0);
    }
}
