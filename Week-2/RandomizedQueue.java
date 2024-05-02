import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> { // 16
    private int tail = 0; // 4
    private int size = 0; // 4
    private Item[] q; // 24 + 8N
    // 48 + 8N

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == q.length) {
            resize(size * 2);
        }
        q[tail] = item;
        tail = (tail + 1) % q.length;
        size += 1;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniformInt(size);
        Item result = q[index];
        q[index] = null;
        adjust(index);
        size -= 1;
        tail = size;
        if (size > 0 && size == q.length / 4) {
            resize(q.length / 2);
        }
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniformInt(size);
        return q[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int initialSize = size;
        private int cnt = 0;
        private Item[] seq;

        public ArrayIterator() {
            seq = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                seq[i] = q[i];
            }
            StdRandom.shuffle(seq);
        }

        public boolean hasNext() {
            if (initialSize != size) {
                throw new UnsupportedOperationException();
            }
            return cnt != size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item result = seq[cnt];
            cnt += 1;
            return result;
        }
    }

    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = q[i];
        }
        q = newArray;
        tail = size;
    }

    private void adjust(int index) {
        for (int i = index; i < size - 1; i++) {
            q[i] = q[i + 1];
        }
        q[size - 1] = null;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.size();
        queue.enqueue(62);
        queue.enqueue(667);
        queue.enqueue(910);
        queue.isEmpty();
        queue.size();
        queue.enqueue(122);
        queue.enqueue(122);
        queue.enqueue(122);
        queue.enqueue(122);
        queue.enqueue(122);
        queue.enqueue(122);
        queue.enqueue(122);
        queue.enqueue(122);
        queue.enqueue(122);
        queue.enqueue(122);
        queue.enqueue(122);
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(1);

        StdOut.println(queue.dequeue());
        queue.enqueue(2);
        StdOut.println(queue.sample());


    }

}
