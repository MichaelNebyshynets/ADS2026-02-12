package by.it.group510902.nebyshynets.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MyPriorityQueue<E> implements Queue<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] heap;
    private int size;

    public MyPriorityQueue(){
        this.heap = (E[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    private void grow(){
        E[] newHeap = (E[]) new Object[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }

    private void swap(int i, int j){
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void siftUp(int index){
        while(index > 0){
            int parentIndex = (index - 1) / 2;
            Comparable<? super E> parent = (Comparable<? super E>) heap[parentIndex];
            if(parent.compareTo(heap[index]) <= 0){
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void siftDown(int index){
        while(true){
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;
            if(leftChild < size){
                Comparable<? super E> left = (Comparable<? super E>) heap[leftChild];
                if(left.compareTo(heap[smallest]) < 0){
                    smallest = leftChild;
                }
            }

            if(rightChild < size){
                Comparable<? super E> right = (Comparable<? super E>) heap[rightChild];
                if(right.compareTo(heap[smallest]) < 0){
                    smallest = rightChild;
                }
            }

            if(smallest == index){
                break;
            }
            swap(index, smallest);
            index = smallest;
        }
    }

    private void checkNotEmpty(){
        if(size == 0){
            throw new NoSuchElementException();
        }
    }

    @Override
    public String toString(){
        if(size == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < size; i++){
            sb.append(heap[i]);
            if(i < size - 1){
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void clear(){
        for(int i = 0; i<size; i++){
            heap[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean add(E element){
        return offer(element);
    }

    public boolean offer(E element){
        if(element == null){
            throw new NullPointerException();
        }
        if(size == heap.length) {
            grow();
        }
        heap[size] = element;
        siftUp(size);
        size++;
        return true;
    }

    @Override
    public boolean contains(Object o){
        for(int i = 0; i<size; i++){
            if(o == null ? heap[i] == null : o.equals(heap[i])){
                return true;
            }
        }
        return false;
    }

    @Override
    public E remove(){
        if(size == 0){
            throw new NoSuchElementException();
        }
        return poll();
    }

    @Override
    public E poll(){
        if(size == 0){
            return null;
        }
        E result = heap[0];
        heap[0] = heap[size-1];
        heap[size-1] = null;
        size--;
        if(size > 0){
            siftDown(0);
        }
        return result;
    }

    @Override
    public E peek(){
        return (size == 0) ? null : heap[0];
    }

    @Override
    public E element(){
        checkNotEmpty();
        return heap[0];
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public boolean containsAll(Collection<?> c){
        for(Object o : c){
            if (!contains(o)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c){
        boolean modified = false;
        for(E element : c){
            if (offer(element)){
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c){
        boolean modified = false;
        E[] temp = (E[]) new Object[size];
        int newSize = 0;

        for(int i = 0; i < size; i++){
            if(!c.contains(heap[i])){
                temp[newSize++] = heap[i];
            }else{
                modified = true;
            }
        }

        if(modified){
            heap = temp;
            size = newSize;
            for(int i = size/2; i>=0; i--){
                siftDown(i);
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        E[] temp = (E[]) new Object[size];
        int newSize = 0;

        for (int i = 0; i < size; i++) {
            if (c.contains(heap[i])) {
                temp[newSize++] = heap[i];
            } else {
                modified = true;
            }
        }

        if (modified) {
            heap = temp;
            size = newSize;
            for (int i = size / 2; i >= 0; i--) {
                siftDown(i);
            }
        }
        return modified;
    }


    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

}
