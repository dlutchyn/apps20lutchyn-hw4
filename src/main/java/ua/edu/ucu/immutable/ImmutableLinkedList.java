package ua.edu.ucu.collections.immutable;


import java.util.Arrays;

public class ImmutableLinkedList {

    public static class Node {
        public Node next;
        public Node prev;
        public Object value;

        public Node(Object value, Node next, Node prev) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }

    public int length;
    public Node head;
    public Node tail;

    public ImmutableLinkedList(Object[] startList) {
        if (startList.length == 0) {
            this.head = null;
            this.tail = null;
            this.length = 0;
        } else {
            for (Object o : startList) {
                Node newNode = new Node(o, null, null);
                if (this.head == null) {
                    this.head = newNode;
                } else {
                    this.tail.next = newNode;
                    newNode.prev = this.tail;
                }
                this.tail = newNode;
                this.length++;
            }
        }
    }

    private void checkIndex(int index) throws StringIndexOutOfBoundsException {
        if (index < 0 || index > this.length) {
            throw new StringIndexOutOfBoundsException("Incorrect index value");
        }
    }

    public ImmutableLinkedList add(Object e) {
        Object[] oneElemList = {e};
        return this.addAll(this.length, oneElemList);
    }

    public ImmutableLinkedList addFirst(Object e) {
        return this.add(0, e);
    }

    public ImmutableLinkedList removeFirst() {
        return this.remove(0);
    }

    public Object getFirst() {
        if (this.head == null) {
            return "Empty!";
        }
        return this.head.value;
    }

    public ImmutableLinkedList addLast(Object e) {
        return this.add(e);
    }

    public ImmutableLinkedList removeLast() {
        return this.remove(this.length - 1);
    }

    public Object getLast() {
        if (this.tail == null) {
            return "Empty!";
        }
        return this.tail.value;
    }

    public ImmutableLinkedList add(int index, Object e)
            throws StringIndexOutOfBoundsException {
        this.checkIndex(index);
        Object[] oneElemList = {e};
        return this.addAll(index, oneElemList);
    }

    public ImmutableLinkedList addAll(Object[] c) {
        return this.addAll(this.length, c);
    }

    public ImmutableLinkedList addAll(int index, Object[] c)
            throws StringIndexOutOfBoundsException{
        this.checkIndex(index);
        ImmutableLinkedList copyList = new ImmutableLinkedList(this.toArray());
        Node curNode = copyList.head;

        if (this.length == 0) {
            copyList.head = new Node(c[0], null, null);
            copyList.tail = copyList.head;
            for (int i = 1; i < c.length; i++) {
                copyList.tail.next = new Node(c[i], null, copyList.tail);
                copyList.tail =  copyList.tail.next;
            }
            copyList.length += c.length;
        } else if (index == 0) {
            ImmutableLinkedList newCopyList = new ImmutableLinkedList(c);
            copyList = newCopyList.addAll(newCopyList.length, this.toArray());
        } else if (index == copyList.length) {
            for (Object o: c) {
                copyList.tail.next = new Node(o, null, copyList.tail);
                copyList.tail = copyList.tail.next;
            }
            copyList.length += c.length;
        } else {
            while (index - 1 > 0) {
                curNode = curNode.next;
                index--;
            }
            for (Object o: c) {
                curNode.next.prev = new Node(o, curNode.next, curNode);
                curNode.next = curNode.next.prev;
                curNode = curNode.next;
            }
            copyList.length += c.length;
        }
        return copyList;
    }

    public Object get(int index) throws StringIndexOutOfBoundsException {
        this.checkIndex(index);
        Node curNode = this.head;
        while (index > 0) {
            curNode = curNode.next;
            index--;
        }
        return curNode.value;
    }

    public ImmutableLinkedList remove(int index)
            throws StringIndexOutOfBoundsException {
        this.checkIndex(index);
        ImmutableLinkedList copyList = new ImmutableLinkedList(this.toArray());

        if (this.length == 1) {
            copyList.head = null;
            copyList.tail = null;
        } else if (index == 0) {
            copyList.head = copyList.head.next;
            copyList.head.prev = null;
        } else if (index == copyList.length - 1) {
            copyList.tail = copyList.tail.prev;
            copyList.tail.next = null;
        } else {
            Node curNode = copyList.head;
            while (index > 0) {
                curNode = curNode.next;
                index--;
            }
            curNode.prev.next = curNode.next;
            curNode.next.prev = curNode.prev;
        }
        copyList.length--;
        return copyList;
    }

    public ImmutableLinkedList set(int index, Object e)
            throws StringIndexOutOfBoundsException{
        this.checkIndex(index);
        ImmutableLinkedList copyList = new ImmutableLinkedList(this.toArray());
        Node curNode = copyList.head;
        while (index > 0) {
            curNode = curNode.next;
            index--;
        }
        curNode.value = e;
        return copyList;
    }

    public int indexOf(Object e) {
        Node curNode = this.head;
        int index = 0;
        while (curNode != null) {
            if (curNode.value == e) {
                return index;
            } else {
                curNode = curNode.next;
                index++;
            }
        }
        return -1;
    }

    public int size() {
        return this.length;
    }

    public ImmutableLinkedList clear() {
        Object[] emptyArray = new Object[0];
        return new ImmutableLinkedList(emptyArray);
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public Object[] toArray() {
        Object[] copyArray = new Object[this.length];
        Node curNode = this.head;
        int index = 0;
        while (curNode != null) {
            copyArray[index] = curNode.value;
            curNode = curNode.next;
            index++;
        }
        return copyArray;
    }

    @Override
    public String toString() {
        Object[] arrayRepr = this.toArray();
        return Arrays.toString(arrayRepr);
    }
}
