package by.it.group510902.nebyshynets.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> implements Set<E>{

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;

    private int size;

    public MyTreeSet() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    private int binarySearch(Object key) {
        int low = 0;
        int high = size - 1;

        Comparable<? super E> comparableKey = (Comparable<? super E>) key;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            E midVal = (E) elements[mid];

            int cmp = comparableKey.compareTo(midVal);

            if (cmp > 0) {
                low = mid + 1;
            } else if (cmp < 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -(low + 1);
    }

    @Override
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        int index = binarySearch(element);

        if (index >= 0) {
            return false;
        }

        int insertionPoint = -index - 1;

        if (size == elements.length) {
            grow();
        }

        System.arraycopy(elements, insertionPoint, elements, insertionPoint + 1, size - insertionPoint);

        elements[insertionPoint] = element;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) return false;

        int index = binarySearch(o);

        if (index < 0) {
            return false;
        }

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }

        elements[--size] = null;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) return false;
        return binarySearch(o) >= 0;
    }

    private void grow() {
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - oldCapacity < 0) newCapacity = Integer.MAX_VALUE;

        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(elements[i] == this ? "(this Collection)" : elements[i]);
        }
        sb.append(']');
        return sb.toString();
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
        for (int i = size - 1; i >= 0; i--) {
            if (!c.contains(elements[i])) {
                remove(elements[i]);
                modified = true;
            }
        }
        return modified;
    }


    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(elements, 0, result, 0, size);
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}