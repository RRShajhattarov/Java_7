package util;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class CustomList<T> {

    private Node<Task> head;
    private Node<Task> tail;

    private int size = 0;


    public Node<Task> linkLast(Task element) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, element, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;

        return newNode;
    }

    public List<Task> getTasks(CustomList<Node<Task>> customList) {
        List<Task> tasks = new ArrayList<>();
        Node<Task> element = customList.head;
        tasks.add(element.data);
        while (element.next != null) {
            element = element.next;
            tasks.add(element.data);
        }
        return tasks;
    }

    public void removeNode(Node<Task> node) {
        if(node == null) {
            return;
        }
        Node<Task> currentNext = node.next;
        Node<Task> currentPrev = node.prev;
        if (currentNext == null && currentPrev == null) {
            head = null;
            tail = null;
            return;
        }
        node.next = null;
        node.prev = null;
        if (currentPrev == null) {
            currentNext.prev = null;
            head = currentNext;
        } else if (currentNext == null) {
            currentPrev.next = null;
            tail = currentPrev;
        } else {
            currentNext.prev = currentPrev;
            currentPrev.next = currentNext;
        }
        node.data = null;
        size--;
    }

    @Override
    public String toString() {
        return "CustomList{" +
                "head=" + head +
                ", tail=" + tail +
                ", size=" + size +
                '}';
    }

    public int getSize() {
        return size;
    }
}
