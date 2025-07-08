import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter five numbers: ");
        TwoWayLinkedList<Double> list = new TwoWayLinkedList<>();

        // Read exactly five doubles
        for (int i = 0; i < 5; i++) {
            list.add(input.nextDouble());
        }

        // Print forward
        ListIterator<Double> iter = list.listIterator();
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
        }
        System.out.println();

        // Print backward
        while (iter.hasPrevious()) {
            System.out.print(iter.previous() + " ");
        }
        System.out.println();

        input.close();
    }
}

// Two-Way Linked List -----------------

class TwoWayLinkedList<E> {
    static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;
        Node(E e) { element = e; }
    }

    private Node<E> head, tail;
    private int size = 0;

    public boolean add(E e) {
        Node<E> newNode = new Node<>(e);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
        return true;
    }

    public ListIterator<E> listIterator() {
        return new DLLIterator(0);
    }

    public ListIterator<E> listIterator(int index) {
        return new DLLIterator(index);
    }

    private class DLLIterator implements ListIterator<E> {
        private Node<E> nextNode;
        private Node<E> lastReturned = null;
        private int nextIndex;

        DLLIterator(int index) {
            if (index < 0 || index > size) 
                throw new IndexOutOfBoundsException();
            nextNode = head;
            for (int i = 0; i < index; i++) {
                nextNode = nextNode.next;
            }
            nextIndex = index;
        }

        public boolean hasNext() { return nextIndex < size; }
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            lastReturned = nextNode;
            nextNode = nextNode.next;
            nextIndex++;
            return lastReturned.element;
        }

        public boolean hasPrevious() { return nextIndex > 0; }
        public E previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            if (nextNode == null) { // was past the end
                nextNode = tail;
            } else {
                nextNode = nextNode.previous;
            }
            lastReturned = nextNode;
            nextIndex--;
            return lastReturned.element;
        }

        public int nextIndex()     { return nextIndex; }
        public int previousIndex() { return nextIndex - 1; }

        public void remove() { throw new UnsupportedOperationException(); }
        public void set(E e) { throw new UnsupportedOperationException(); }
        public void add(E e) { throw new UnsupportedOperationException(); }
    }
}
