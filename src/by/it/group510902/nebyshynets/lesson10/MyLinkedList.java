package by.it.group510902.nebyshynets.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Deque<E> {
    private static class Node<E>{
        E item;
        Node<E> prev;
        Node<E> next;

        Node(Node<E> prev, E item, Node<E> next){
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private Node<E> head;
    private int size;
    private Node<E> tail;

    private void checkElementIndex(int index){
        if(!(index >= 0 && index<size)){
            throw new IndexOutOfBoundsException();
        }
    }



    private E unlink(Node<E> x) {
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }

    private E unlinkFirst(Node<E> f) {
        final E element = f.item;
        final Node<E> next = f.next;
        f.item = null;
        f.next = null; // help GC
        head = next;
        if (next == null)
            tail = null;
        else
            next.prev = null;
        size--;
        return element;
    }

    private E unlinkLast(Node<E> l) {
        final E element = l.item;
        final Node<E> prev = l.prev;
        l.item = null;
        l.prev = null; // help GC
        tail = prev;
        if (prev == null)
            head = null;
        else
            prev.next = null;
        size--;
        return element;
    }

    private Node<E> node(int index){
        if (index < (size >> 1)) {
            Node<E> x = head;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = tail;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        for(Node<E> n = head; n != null; n = n.next){
            sb.append(n.item);
            if(n.next != null){
                sb.append(", ");
            }

        }
        return sb.append("]").toString();
    }

    @Override
    public boolean add(E element){
        addLast(element);
        return true;
    }

    @Override
    public void addLast(E element){
        Node<E> t = tail;
        Node<E> newNode = new Node<>(t, element, null);
        tail = newNode;
        if(t == null){
            head = newNode;
        }
        else{
            t.next = newNode;
        }
        size++;
    }

    @Override
    public void addFirst(E element){
        Node<E> h = head;
        Node<E> newNode = new Node<>(null, element, h);
        head = newNode;
        if(h == null){
            tail = newNode;
        }
        else{
            h.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean remove(Object o){
        if(o == null){
            for(Node<E> x = head; x != null; x = x.next){
                if(x.item == null){
                    unlink(x);
                    return true;
                }
            }
        }else{
            for(Node<E> x = head; x != null; x = x.next){
                if(o.equals(x.item)){
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public E element(){
        return getFirst();
    }

    @Override
    public E getFirst(){
        Node<E> h = head;
        if(h == null){
            throw new NoSuchElementException();
        }
        return h.item;
    }

    @Override
    public E getLast(){
        Node<E> t = tail;
        if(t == null){
            throw new NoSuchElementException();
        }
        return t.item;
    }

    @Override
    public E poll(){
        return pollFirst();
    }

    @Override
    public E pollFirst(){
        Node<E> h = head;
        return (h == null) ? null : unlinkFirst(h);
    }

    @Override
    public E pollLast(){
        Node<E> t = tail;
        return (t  == null) ? null : unlinkLast(t);
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public boolean offer(E element){
        return add(element);
    }

    @Override
    public boolean offerLast(E element){
        addLast(element);
        return true;
    }

    @Override
    public boolean offerFirst(E element){
        addFirst(element);
        return true;
    }

    @Override
    public E removeFirst(){
        Node<E> h = head;
        if(h == null){
            throw new NoSuchElementException();
        }
        return unlinkFirst(h);
    }

    @Override
    public E removeLast(){
        Node<E> t = tail;
        if(t == null){
            throw new NoSuchElementException();
        }
        return unlinkLast(t);
    }

    @Override
    public E remove(){
        return removeFirst();
    }

    @Override
    public E peekFirst(){
        return (head == null) ? null : head.item;
    }

    @Override
    public E peekLast(){
        return (tail == null) ? null : tail.item;
    }

    @Override
    public E peek(){
        return peekFirst();
    }

    @Override
    public void push(E element){
        addLast(element);
    }

    @Override
    public E pop(){
        return removeFirst();
    }

    @Override
    public boolean contains(Object o) {
        for (Node<E> x = head; x != null; x = x.next) {
            if (o == null ? x.item == null : o.equals(x.item)) return true;
        }
        return false;
    }


    @Override
    public void clear(){throw new UnsupportedOperationException();}

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }
}
