package by.it.group510902.nebyshynets.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayDeque<E> implements Deque<E> {
    private static final int DEFAULT_CAPACITY = 8;

    private E[] elements;
    private int head;
    private int size;

    public MyArrayDeque(){
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public void grow(){
        E[] bigger = (E[]) new Object[elements.length * 2];
        for (int i = 0; i<size; i++){
            bigger[i] = elements[(head+i)% elements.length];
        }
        elements = bigger;
        head = 0;
    }
    public void checkNotNull(E element){
        if(element == null){
            throw new NullPointerException("Null elements are not allowed");
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i<size; i++){
            if(i>0){
                sb.append(", ");
            }
            sb.append(elements[(head+i)% elements.length]);
        }
        return sb.append("]").toString();
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean add(E element){
        addLast(element);
        return true;
    }

    @Override
    public void addLast(E element){
        checkNotNull(element);
        if(size == elements.length){
            grow();
        }
        elements[(head+size) % elements.length] = element;
        size++;
    }

    @Override
    public void addFirst(E element){
        checkNotNull(element);
        if(size == elements.length){
            grow();
        }
        head = (head-1+ elements.length)% elements.length;
        elements[head] = element;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E element(){
        return getFirst();
    }

    @Override
    public E getFirst(){
        if(size == 0){
            throw new NoSuchElementException();
        }
        return elements[head];
    }

    @Override
    public E getLast(){
        if(size == 0){
            throw new NoSuchElementException();
        }
        return elements[(head+size-1)% elements.length];
    }

    @Override
    public E poll(){
        return pollFirst();
    }

    @Override
    public E pollFirst(){
        if(size == 0){
            return null;
        }
        E result = elements[head];
        elements[head] = null;
        head = (head+1)% elements.length;
        size--;
        return result;
    }

    @Override
    public E pollLast(){
        if(size == 0){
            return null;
        }
        E result = elements[(head+size-1)% elements.length];
        elements[(head+size-1)% elements.length] = null;
        size--;
        return result;
    }

    @Override
    public boolean offerFirst(E element){
        addFirst(element);
        return true;
    }

    public boolean offerLast(E element){
        addLast(element);
        return true;
    }

    @Override
    public boolean offer(E element){
        return offerLast(element);
    }

    @Override
    public E removeFirst(){
        if(size == 0){
            throw new NoSuchElementException();
        }
        return pollFirst();
    }

    @Override
    public E removeLast(){
        if(size == 0){
            throw new NoSuchElementException();
        }
        return pollLast();
    }

    @Override
    public E remove(){
        return removeFirst();
    }

    @Override
    public E peekFirst(){
        if(size == 0){
            return null;
        }
        return elements[head];
    }

    @Override
    public E peekLast(){
        if(size == 0){
            return null;
        }
        return elements[(head+size-1)% elements.length];
    }

    @Override
    public E peek(){
        return peekFirst();
    }

    @Override
    public void push(E element){
        addFirst(element);
    }

    @Override
    public E pop(){
        return removeFirst();
    }

    @Override
    public boolean contains(Object o){
        if(o == null){
            return false;
        }
        for(int i = 0; i<size; i++){
            if(o.equals(elements[(head+i)% elements.length])){
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        head = 0;
        size = 0;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
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
