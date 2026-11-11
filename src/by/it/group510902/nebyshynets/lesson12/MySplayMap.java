package by.it.group510902.nebyshynets.lesson12;

import java.util.*;

public class MySplayMap implements NavigableMap<Integer, String> {
    private static class Node{
        Integer key;
        String value;
        Node left;
        Node right;
        Node parent;

        Node(Integer key, String value, Node parent){
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }

    private Node root;
    private int size;

    private void rotate(Node x){
        Node p = x.parent;
        Node g = p.parent;
        if(x == p.left){
            p.left = x.right;
            if(x.right != null){
                x.right.parent = p;
            }
            x.right = p;
        }else{
            p.right = x.left;
            if(x.left != null){
                x.left.parent = p;
            }
            x.left = p;
        }
        p.parent = x;
        x.parent = g;
        if(g == null){
            root = x;
        }else if(g.left == p){
            g.left =x;
        }else{
            g.right = x;
        }
    }

    private void splay(Node x){
        while(x.parent != null){
            Node p = x.parent;
            Node g = p.parent;
            if(g==null){
                rotate(x);
            }else if((x==p.left) == (p==g.left)){
                rotate(p);
                rotate(x);
            }else{
                rotate(x);
                rotate(x);
            }
        }
    }

    private Node findNode(Integer key){
        Node current = root;
        Node last = null;
        while(current != null){
            last = current;
            int cmp = key.compareTo(current.key);
            if(cmp<0){
                current = current.left;
            }else if(cmp > 0){
                current = current.right;
            }else{
                splay(current);
                return current;
            }
        }
        if (last != null){
            splay(last);
        }
        return null;
    }

    @Override
    public String put(Integer key, String value){
        if(root == null){
            root = new Node(key, value, null);
            size = 1;
            return null;
        }
        Node current = root;
        Node parent = null;
        int cmp = 0;
        while(current != null){
            parent = current;
            cmp = key.compareTo(current.key);
            if(cmp<0){
                current = current.left;
            }else if(cmp > 0){
                current = current.right;
            }else{
                String old = current.value;
                current.value = value;
                splay(current);
                return old;
            }
        }
        Node node = new Node(key, value, parent);
        if(cmp < 0){
            parent.left = node;
        }else{
            parent.right = node;
        }
        size++;
        splay(node);
        return null;
    }

    @Override
    public String get(Object key){
        Node node = findNode((Integer) key);
        return node == null ? null : node.value;
    }

    @Override
    public boolean containsKey(Object key){
        return findNode((Integer) key) != null;
    }

    @Override
    public String remove(Object key){
        Node node = findNode((Integer) key);
        if(node == null){
            return null;
        }
        String old = node.value;
        Node left = node.left;
        Node right = node.right;
        if(left != null){
            left.parent = null;
        }
        if(right != null){
            right.parent = null;
        }
        if(left == null){
            root = right;
        }else{
            root = left;
            Node max = left;
            while(max.right != null){
                max = max.right;
            }
            splay(max);
            root.right = right;
            if(right != null){
                right.parent = root;
            }
        }
        size--;
        return old;
    }

    private Node minNode(Node node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node maxNode(Node node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
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
        root = null;
        size = 0;
    }

    private Node successor(Node node) {
        if (node.right != null) {
            return minNode(node.right);
        }
        Node p = node.parent;
        while (p != null && node == p.right) {
            node = p;
            p = p.parent;
        }
        return p;
    }

    @Override
    public boolean containsValue(Object value){
        for (Node n = minNode(root); n != null; n = successor(n)) {
            if (value == null ? n.value == null : value.equals(n.value)) {
                return true;
            }
        }
        return false;
    }



    @Override
    public Integer firstKey() {
        Node min = minNode(root);
        if (min == null) {
            return null;
        }
        splay(min);
        return min.key;
    }

    @Override
    public Integer lastKey() {
        Node max = maxNode(root);
        if (max == null) {
            return null;
        }
        splay(max);
        return max.key;
    }

    @Override
    public Integer lowerKey(Integer key) { // наибольший ключ < key
        Node current = root;
        Node last = null;
        Integer result = null;
        while (current != null) {
            last = current;
            if (current.key.compareTo(key) < 0) {
                result = current.key; // подходит, но, может, есть больше
                current = current.right;
            } else {
                current = current.left;
            }
        }
        if (last != null) {
            splay(last);
        }
        return result;
    }

    @Override
    public Integer floorKey(Integer key) {
        Node current = root;
        Node last = null;
        Integer result = null;
        while (current != null) {
            last = current;
            if (current.key.compareTo(key) <= 0) {
                result = current.key;
                current = current.right;
            } else {
                current = current.left;
                }
        }
        if (last != null) {
            splay(last);
         }
        return result;
    }

    @Override
    public Integer ceilingKey(Integer key) {
        Node current = root;
        Node last = null;
        Integer result = null;
        while (current != null) {
            last = current;
            if (current.key.compareTo(key) >= 0) {
                result = current.key;
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (last != null) {
            splay(last);
        }
        return result;
    }

    @Override
    public Integer higherKey(Integer key) {
        Node current = root;
        Node last = null;
        Integer result = null;
        while (current != null) {
            last = current;
            if (current.key.compareTo(key) > 0) {
                result = current.key;
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (last != null) {
            splay(last);
        }
        return result;
    }


    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MySplayMap result = new MySplayMap();
        for (Node n = minNode(root); n != null && n.key.compareTo(toKey) < 0; n = successor(n)) {
            result.put(n.key, n.value);
        }
        return result;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MySplayMap result = new MySplayMap();
        for (Node n = minNode(root); n != null; n = successor(n)) {
            if (n.key.compareTo(fromKey) >= 0) {
                result.put(n.key, n.value);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Node n = minNode(root); n != null; n = successor(n)) {
            sb.append(n.key).append('=').append(n.value);
            if (successor(n) != null) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }


    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        for (Map.Entry<? extends Integer, ? extends String> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }


    @Override public java.util.Comparator<? super Integer> comparator() { throw new UnsupportedOperationException(); }
    @Override public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) { throw new UnsupportedOperationException(); }
    @Override public Entry<Integer, String> lowerEntry(Integer key) { return null; }
    @Override public Entry<Integer, String> floorEntry(Integer key) { return null; }
    @Override public Entry<Integer, String> ceilingEntry(Integer key) { return null; }
    @Override public Entry<Integer, String> higherEntry(Integer key) { return null; }
    @Override public Entry<Integer, String> firstEntry() { return null; }
    @Override public Entry<Integer, String> lastEntry() { return null; }
    @Override public Entry<Integer, String> pollFirstEntry() { return null; }
    @Override public Entry<Integer, String> pollLastEntry() { return null; }
    @Override public NavigableMap<Integer, String> descendingMap() { return null; }
    @Override public NavigableSet<Integer> navigableKeySet() { return null; }
    @Override public NavigableSet<Integer> descendingKeySet() { return null; }
    @Override public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) { return null;}
    @Override public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) { return null;}
    @Override public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) { return null;}
    @Override public Set<Integer> keySet() { return null; }
    @Override public Collection<String> values() { return null; }
    @Override public Set<Entry<Integer, String>> entrySet() { return null; }
}
