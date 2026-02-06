package com.kejin.expression.util;

import com.kejin.expression.util.NodeStack;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class NodeStackTest {

    @Test
    public void testNodeStackFunctionality() {
        // 测试NodeStack的基本功能
        NodeStack<String> stack = new NodeStack<>();
        assertTrue(stack.isEmpty());

        stack.add("first");
        stack.add("second");
        stack.add("third");

        assertFalse(stack.isEmpty());

        // 测试迭代器功能
        List<String> items = new ArrayList<>();
        for (String item : stack) {
            items.add(item);
        }
        assertEquals(3, items.size());
        assertEquals("first", items.get(0));
        assertEquals("second", items.get(1));
        assertEquals("third", items.get(2));

        // 测试isEmpty
        assertFalse(stack.isEmpty());

        // 测试LinkNode的基本功能
        NodeStack.LinkNode<String> node = new NodeStack.LinkNode<>("test");
        assertEquals("test", node.getValue());
        assertNull(node.getNext());
        assertNull(node.getPrev());
    }
}
