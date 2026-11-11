package by.it.group510902.nebyshynets.lesson12;

import java.util.SortedMap;
import java.util.Set;
import java.util.Collection;

public class MyRbMap implements SortedMap<Integer, String>{

    public static final boolean RED = true;
    public static final boolean BLACK = false;

    private static class Node{
        Integer key;
        String value;
        Node parent;
        Node left;
        Node right;

        boolean color = RED;

        Node(Integer key, String value, Node parent){
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }

    private Node root;
    private int size;

    private static boolean colorOf(Node node){
        return node == null ? BLACK : node.color;
    }

    private static Node parentOf(Node node){
        return node == null ? null : node.parent;
    }

    private static Node leftOf(Node node){
        return node == null ? null : node.left;
    }

    private static Node rightOf(Node node){
        return node == null ? null : node.right;
    }

    private static void setColor(Node node, boolean color){
        if(node != null){
            node.color = color;
        }
    }

    private void rotateLeft(Node x){
        Node y = x.right;
        x.right = y.left;
        if(y.left != null){
            y.left.parent = x;
        }
        y.parent = x.parent;
        if(x.parent == null){
            root = y;
        }else if(x == x.parent.left){
            x.parent.left = y;
        } else if(x == x.parent.right){
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x){
        Node y  = x.left;
        x.left = y.right;
        if(y.right != null){
            y.right.parent = x;
        }
        y.parent = x.parent;
        if(x.parent == null){
            root = y;
        }else if(x == x.parent.left){
            x.parent.left = y;
        }else if(x == x.parent.right){
            x.parent.right = y;
        }
        y.right = x;
        x.parent = y;
    }

    @Override
    public String put(Integer key, String value){
        if(root == null){
            root = new Node(key, value, null);
            root.color = BLACK;
            size = 1;
            return null;
        }

        Node current = root;
        Node parent = null;
        int cmp = 0;
        while(current != null){
            parent = current;
            cmp = key.compareTo(current.key);
            if(cmp < 0){
                current = current.left;
            }else if(cmp > 0){
                current = current.right;
            }else{
                String old = current.value;
                current.value = value;
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
        fixAfterInsert(node);
        return null;
    }

    private void fixAfterInsert(Node x){
        while(x != root && colorOf(parentOf(x)) == RED){
            Node p = parentOf(x);
            Node g = parentOf(p);
            if(p == leftOf(g)){
                Node uncle = rightOf(g);
                if(colorOf(uncle) == RED){
                    setColor(p, BLACK);
                    setColor(uncle, BLACK);
                    setColor(g, RED);
                }else{
                    if(x == rightOf(p)){
                        x = p;
                        rotateLeft(x);
                        p = parentOf(x);
                    }
                    setColor(p, BLACK);
                    setColor(g, RED);
                    rotateRight(g);
                }
            }else{
                Node uncle = leftOf(g);
                if(colorOf(uncle) == RED) {
                    setColor(p, BLACK);
                    setColor(uncle, BLACK);
                    setColor(g, RED);
                }else{
                    if(x == leftOf(p)){
                        x = p;
                        rotateRight(x);
                        p = parentOf(x);
                    }
                    setColor(p, BLACK);
                    setColor(g, RED);
                    rotateLeft(g);
                }
            }
        }
        root.color = BLACK;
    }

    @Override
    public String remove(Object key){
        Node node = findNode((Integer) key);
        if(node == null){
            return null;
        }
        String old = node.value;
        deleteNode(node);
        size--;
        return old;
    }

    private void deleteNode(Node n){
        if(n.left != null && n.right != null){
            Node s = minNode(n.right);
            n.key = s.key;
            n.value = s.value;
            n = s;
        }

        Node replacement = (n.left != null) ? n.left : n.right;

        if(replacement != null){
            replacement.parent = n.parent;
            if(n.parent == null){
                root = replacement;
            }else if(n == n.parent.left){
                n.parent.left = replacement;
            }else{
                n.parent.right = replacement;
            }
            n.left = n.right = n.parent = null;
            if(n.color == BLACK){
                fixAfterDelete(replacement);
            }
        }else if(n.parent == null){
            root = null;
        }else{
            if(n.color == BLACK){
                fixAfterDelete(n);
            }
            if (n.parent != null) {
                if (n == n.parent.left) {
                    n.parent.left = null;
                    } else if (n == n.parent.right) {
                    n.parent.right = null;
                    }
                n.parent = null;
                }
        }
    }

    private void fixAfterDelete(Node x){
        while(x != root && colorOf(x)==BLACK){
            if(x == leftOf(parentOf(x))){
                Node sib = rightOf(parentOf(x));
                if(colorOf(sib) == RED){
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    sib = rightOf(x);
                }
                if(colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                }else{
                    if(colorOf(rightOf(sib)) == BLACK){
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    x = root;
                }
            } else { // зеркально
                Node sib = leftOf(parentOf(x));
                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                    }
                if (colorOf(rightOf(sib)) == BLACK && colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                    } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                        }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                    }
                }
            }
        setColor(x, BLACK);

    }

    private Node minNode(Node node){
        while(node.left != null){
            node = node.left;
        }
        return node;
    }

    private Node maxNode(Node node){
        while(node.right != null){
            node = node.right;
        }
        return node;
    }

    private Node findNode(Integer key){
        Node current = root;
        while(current != null){
            int cmp = key.compareTo(current.key);
            if(cmp < 0){
                current = current.left;
            }else if(cmp > 0){
                current = current.right;
            }else{
                return current;
            }
        }
        return null;
    }

    @Override
    public String get(Object key) {
        Node node = findNode((Integer) key);
        return node == null ? null : node.value;
    }

@   Override
    public boolean containsKey(Object key) {
        return findNode((Integer) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValue(root, value);
    }

    private boolean containsValue(Node node, Object value) {
        if (node == null) {
            return false;
        }
        if (value == null ? node.value == null : value.equals(node.value)) {
            return true;
        }
        return containsValue(node.left, value) || containsValue(node.right, value);
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

    @Override
    public Integer firstKey() {
        return root == null ? null : minNode(root).key;
        }

    @Override
    public Integer lastKey() {
        return root == null ? null : maxNode(root).key;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MyRbMap result = new MyRbMap();
        headMap(root, toKey, result);
        return result;
    }

    private void headMap(Node node, Integer toKey, MyRbMap result) {
        if (node == null) {
            return;
        }
        headMap(node.left, toKey, result);
        if (node.key.compareTo(toKey) < 0) {
            result.put(node.key, node.value);
            headMap(node.right, toKey, result);
        }
    }


    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MyRbMap result = new MyRbMap();
        tailMap(root, fromKey, result);
        return result;
    }

    private void tailMap(Node node, Integer fromKey, MyRbMap result) {
        if (node == null) {
            return;
        }
        if (node.key.compareTo(fromKey) >= 0) {
            tailMap(node.left, fromKey, result);
            result.put(node.key, node.value);
        }
        tailMap(node.right, fromKey, result);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        inOrder(root, sb);
        if (size > 0) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    private void inOrder(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        inOrder(node.left, sb);
        sb.append(node.key).append('=').append(node.value).append(", ");
        inOrder(node.right, sb);
    }


    @Override public java.util.Comparator<? super Integer> comparator() { throw new UnsupportedOperationException(); }
    @Override public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) { throw new UnsupportedOperationException(); }
    @Override public Set<Integer> keySet() { throw new UnsupportedOperationException(); }
    @Override public Collection<String> values() { throw new UnsupportedOperationException(); }
    @Override public Set<Entry<Integer, String>> entrySet() { throw new UnsupportedOperationException(); }
    @Override public void putAll(java.util.Map<? extends Integer, ? extends String> m) { throw new UnsupportedOperationException(); }
}
