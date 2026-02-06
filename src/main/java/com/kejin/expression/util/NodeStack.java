package com.kejin.expression.util;

import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;

public class NodeStack<T> implements Iterable<T>, Iterator<T> {
    @Override
    public Iterator<T> iterator() {
        this.cursor = this.head;
        return this;
    }

    @Override
    public boolean hasNext() {
        return this.cursor != null;
    }

    @Override
    public T next() {
        T value = this.cursor.value;
        this.cursor = this.cursor.next;
        return value;
    }

    public boolean isEmpty() {
        return head == null;
    }

    @Getter
    @Setter
    public static class LinkNode<T> {
        public LinkNode<T> next;
        public LinkNode<T> prev;
        public final T value;

        public LinkNode(T value) {
            this.value = value;
        }
    }

    /**
     * 头指针
     */
    private LinkNode<T> head;
    /**
     * 游标
     */
    private LinkNode<T> cursor;
    /**
     * 尾指针
     */
    private LinkNode<T> tail;

    public void add(T value) {
        LinkNode<T> linkNode = new LinkNode<>(value);
        if (head == null) {
            head = linkNode;
        } else if (head == tail) {
            head.next = linkNode;
            linkNode.prev = head;
        } else {
            tail.next = linkNode;
            linkNode.prev = tail;
        }
        tail = linkNode;
    }

    public void cursorToHead() {
        cursor = head;
    }

    public void cursorToTail() {
        cursor = tail;
    }

    // 弹出栈底元素
    public LinkNode<T> peek() {
        if (cursor == null) {
            cursor = head;
            return cursor;
        } else if (cursor == tail) {
            return null;
        }
        cursor = cursor.next;
        // peek出当前节点
        return cursor;
    }

    public LinkNode<T> pop() {
        if (cursor == null) {
            cursor = tail;
            return cursor;
        }
        cursor = cursor.prev;
        // pop出当前节点
        return cursor;
    }

    public void replace(LinkNode<T> start, LinkNode<T> end, T value) {
        LinkNode<T> node = new LinkNode<>(value);
        //替换开始节点

        if (start == head) {
            head = node;
        }
        if (start == tail || end == tail) {
            tail = node;
        }
        node.prev = start.prev;
        node.next = end.next;
        if (start.prev != null) {
            start.prev.next = node;
        }
        if (end.next != null) {
            end.next.prev = node;
        }
        start.prev = null;
        start.next = null;
        end.prev = null;
        end.next = null;
    }

    public void remove(LinkNode<T> node) {
        if (node == head) {
            head = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
    }

    public static void main(String[] args) {
        NodeStack<Integer> stack = new NodeStack<>();
        stack.add(1);
        stack.add(2);
        stack.add(3);
        stack.add(4);
        stack.add(5);

        for (Integer value : stack) {
            System.out.println(value);
        }
        stack.peek();
        LinkNode<Integer> start = stack.peek();

        stack.peek();
        stack.peek();
        LinkNode<Integer> end = stack.peek();
        stack.replace(start, end, 6);
        for (Integer value : stack) {
            System.out.println(value);
        }
    }

}
