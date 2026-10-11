package by.it.group510902.nebyshynets.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ListA<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ


    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public ListA(){
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    public ListA(int capacity){
        if(capacity > 0){
            this.elements = new Object[capacity];
        }else {
            throw new IllegalArgumentException("Illegal Capacity " + capacity);
        }
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        if(isEmpty()){
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < size; i++){
            sb.append(elements[i]);
            if(i<size-1){
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= elements.length) return;
        int newCapacity = Math.max(elements.length * 2, minCapacity);
        Object[] newElements = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
    @Override
    public boolean add(E e) {
        ensureCapacity(size+1);
        elements[size] = e;
        size++;
        return true;

    }

    @Override
    public E remove(int index) {
        if(index < 0 || index >= size){
            throw new ArrayIndexOutOfBoundsException("Index out of bounds" + index);
        }
        E oldValue = (E) elements[index];
        for(int i = index; i<size-1; i++){
            elements[i] = elements[i+1];
        }
        elements[size-1] = null;
        size--;
        return oldValue;
    }

    @Override
    public int size() {
        return this.size;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {
        if(index < 0 || index > size){
            throw new ArrayIndexOutOfBoundsException("Index out of bounds" + index);
        }

        ensureCapacity(size+1);
        for(int i = size; i > index; i--){
            elements[i] = elements[i-1];
        }
        elements[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i < 0) return false;
        remove(i);
        return true;
    }

    @Override
    public E set(int index, E element) {
        if(index < 0 || index >= size){
            throw new ArrayIndexOutOfBoundsException("Index out of bounds" + index);
        }

        E oldValue = (E) elements[index];
        elements[index] = element;
        return oldValue;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        for(int i = 0; i<size; i++){
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null ? elements[i] == null : o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        if(index<0 || index>=size){
            throw new ArrayIndexOutOfBoundsException("Index out of bounds" + index);
        }
        return (E) elements[index];
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = size-1; i>=0; i--){
            if(o == null) {
                if (elements[i] == null) {
                    return i;
                }

            } else{
                if(o.equals(elements[i])){
                    return i;
                }
            }

        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if(c == null){
            throw new NullPointerException();
        }
        for(Object element : c){
            if(!contains(element)){return false;}
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c == null) throw new NullPointerException();
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Object[] a = c.toArray();
        int n = a.length;
        if (n == 0) return false;

        ensureCapacity(size + n);
        for (int i = size - 1; i >= index; i--) {
            elements[i + n] = elements[i];
        }
        for (int k = 0; k < n; k++) {
            elements[index + k] = a[k];
        }
        size += n;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if(c == null){
            throw new NullPointerException();
        }

        boolean modified = false;
        int newIndex = 0;
        for(int i = 0; i < size; i++){
            if(!c.contains(elements[i])){
                elements[newIndex] = elements[i];
                newIndex++;
            }else{
                modified = true;
            }
        }

        if(modified){
            for(int i = newIndex; i<size; i++){
                elements[i] = null;
            }
            size = newIndex;
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if(c == null){
            throw new NullPointerException();
        }

        boolean modified = false;
        int newIndex = 0;

        for(int i = 0; i < size; i++){
            if(c.contains(elements[i])){
                elements[newIndex] = elements[i];
                newIndex++;
            }else{
                modified = true;
            }
        }

        if(modified){
            for(int i = newIndex; i < size; i++){
                elements[i] = null;
            }
            size = newIndex;
        }
        return modified;
    }


    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("from: " + fromIndex + ", to: " + toIndex);
        }
        ListA<E> result = new ListA<>(Math.max(1, toIndex - fromIndex));
        for (int i = fromIndex; i < toIndex; i++) {
            result.add((E) elements[i]);
        }
        return result;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return new ListItr(index);
    }

    private class ListItr implements ListIterator<E> {
        private int cursor;
        private int lastRet = -1;   // индекс последнего возвращённого элемента

        ListItr(int index) { cursor = index; }

        public boolean hasNext() { return cursor < size; }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            lastRet = cursor;
            return (E) elements[cursor++];
        }

        public boolean hasPrevious() { return cursor > 0; }

        public E previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            cursor--;
            lastRet = cursor;
            return (E) elements[cursor];
        }

        public int nextIndex() { return cursor; }
        public int previousIndex() { return cursor - 1; }

        public void remove() {
            if (lastRet < 0) throw new IllegalStateException();
            ListA.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }

        public void set(E e) {
            if (lastRet < 0) throw new IllegalStateException();
            ListA.this.set(lastRet, e);
        }

        public void add(E e) {
            ListA.this.add(cursor, e);
            cursor++;
            lastRet = -1;
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) throw new NullPointerException();
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        for (int i = 0; i < size; i++) a[i] = (T) elements[i];
        if (a.length > size) a[size] = null;
        return a;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) result[i] = elements[i];
        return result;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return listIterator(0);
    }

}
