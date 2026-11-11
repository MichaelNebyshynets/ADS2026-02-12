package by.it.group510902.nebyshynets.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private Node<E>[] table;
    private int size;
    Node<E> head;
    Node<E> tail;
    private int threshold;

    private static class Node<E>{
        E element;
        Node<E> next;
        int hash;

        Node<E> before;
        Node<E> after;

        Node(E element, int hash, Node<E> next){
            this.element = element;
            this.hash = hash;
            this.next = next;
        }
    }

    public MyLinkedHashSet(){
        this.table = new Node[DEFAULT_CAPACITY];
        this.threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    }

    private int indexFor(int hash, int length){
        return Math.abs(hash%length);
    }

    private int hash(Object key){
        return (key == null) ? 0 : key.hashCode();
    }

    private void resize() {
        int oldCapacity = table.length;
        int newCapacity = oldCapacity * 2;

        if (oldCapacity >= 1 << 30) {
            return;
        }

        Node<E>[] newTable = new Node[newCapacity];
        threshold = (int) (newCapacity * LOAD_FACTOR);

        for (int i = 0; i < oldCapacity; i++) {
            Node<E> current = table[i];
            while (current != null) {
                Node<E> next = current.next;

                int newIndex = indexFor(current.hash, newCapacity);

                current.next = newTable[newIndex];
                newTable[newIndex] = current;

                current = next;
            }
        }
        table = newTable;
    }


    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public void clear(){
        for(int i = 0; i < table.length; i++){
            table[i] = null;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(E element){
        int hash = hash(element);
        int index = indexFor(hash, table.length);

        Node<E> current = table[index];
        while (current != null) {
            if (current.hash == hash && (element == current.element || (element != null && element.equals(current.element)))) {
                return false;
            }
            current = current.next;
        }

        Node<E> newNode = new Node<>(element, hash, table[index]);
        table[index] = newNode;
        size++;

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.after = newNode;
            newNode.before = tail;
            tail = newNode;
        }

        if(size >= threshold){
            resize();
        }

        return true;
    }

    @Override
    public boolean remove(Object o){
        int hash = hash(o);
        int index = indexFor(hash, table.length);

        Node<E> current = table[index];
        Node<E> prev = null;
        while(current!=null){
            if (current.hash == hash && (o == current.element || (o != null && o.equals(current.element)))) {
                if(prev == null){
                    table[index] = current.next;
                }else{
                    prev.next = current.next;
                }

                if (current.before == null) {
                    head = current.after;
                } else {
                    current.before.after = current.after;
                }

                if (current.after == null) {
                    tail = current.before;
                } else {
                    current.after.before = current.before;
                }

                current.before = null;
                current.after = null;
                current.next = null;
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean contains(Object o){
        int hash = hash(o);
        int index = indexFor(hash, table.length);

        Node<E> current = table[index];
        while(current != null){
            if(current.hash == hash && (o == current.element || (o != null && o.equals(current.element)))){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        Node<E> current = head;
        while (current != null) {
            sb.append(current.element == this ? "(this Collection)" : current.element);
            if (current.after != null) {
                sb.append(", ");
            }
            current = current.after;
        }

        sb.append(']');
        return sb.toString();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E o : c) {
            if (add(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node<E> current = head;

        while (current != null) {
            Node<E> nextNode = current.after;
            if (!c.contains(current.element)) {
                remove(current.element);
                modified = true;
            }
            current = nextNode;
        }
        return modified;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
