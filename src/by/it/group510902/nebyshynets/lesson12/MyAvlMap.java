package by.it.group510902.nebyshynets.lesson12;

import java.util.Map;

public class MyAvlMap implements Map<Integer, String> {

    private static class Node{
        Integer key;
        String value;
        Node left;
        Node right;
        int height;
        Node(Integer key, String value){
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;
    private int size = 0;

    private int height(Node node){
        return node == null ? 0 : node.height;
    }

    private void updateHeight(Node node){
        node.height = Math.max(height(node.left), height(node.right))+1;
    }

    private int balanceFactor(Node node){
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y){
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node x){
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);
        return y;
    }

    private Node balance(Node node){
        if(node == null) return null;

        updateHeight(node);
        int bf = balanceFactor(node);

        if(bf > 1){
            if(balanceFactor(node.left) < 0){
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if(bf < -1){
            if(balanceFactor(node.right) > 0){
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    @Override
    public String put(Integer key, String value){
        if(key == null){
            throw new NullPointerException();
        }

        String oldValue = get(key);
        root = putRecursive(root, key, value);
        return oldValue;
    }

    public Node putRecursive(Node node, Integer key, String value){
        if(node == null){
            size++;
            return new Node(key, value);
        }

        if(key < node.key){
            node.left = putRecursive(node.left, key, value);
        } else if(key > node.key){
            node.right = putRecursive(node.right, key, value);
        } else{
            node.value = value;
            return node;
        }

        return balance(node);
    }

    @Override
    public String get(Object key){
        if(!(key instanceof Integer)) return null;
        Integer intKey = (Integer) key;

        Node current = root;
        while(current != null){
            if(intKey < current.key){
                current = current.left;
            } else if(intKey > current.key){
                current = current.right;
            }else{
                return current.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key){
        if(!(key instanceof Integer)) return false;
        Integer intKey = (Integer) key;

        Node current = root;
        while(current != null){
            if(intKey < current.key){
                current = current.left;
            } else if(intKey > current.key){
                current = current.right;
            }else{
                return true;
            }
        }
        return false;
    }

    @Override
    public String remove(Object key){
        if(!(key instanceof Integer)) return null;
        Integer intKey = (Integer) key;

        if(!containsKey(intKey)) return null;

        String removedValue = get(intKey);
        root = removeRecursive(root, intKey);
        size--;
        return removedValue;
    }

    private Node removeRecursive(Node node, Integer key){
        if(node == null) return null;
        if(key < node.key){
            node.left = removeRecursive(node.left, key);
        } else if(key > node.key){
            node.right = removeRecursive(node.right, key);
        } else {
            if(node.left == null) return node.right;
            else if(node.right == null) return node.left;

            Node minNode = findMin(node.right);

            node.key = minNode.key;
            node.value = minNode.value;

            node.right = removeRecursive(node.right, minNode.key);
        }

        return balance(node);
    }

    private Node findMin(Node node){
        while(node.left != null){
            node = node.left;
        }
        return node;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return size==0;
    }

    @Override
    public void clear(){
        root = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        buildStringInOrder(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private void buildStringInOrder(Node node, StringBuilder sb) {
        if (node == null) return;
        buildStringInOrder(node.left, sb);
        if (sb.length() > 1) {
            sb.append(", ");
        }
        sb.append(node.key).append("=").append(node.value);
        buildStringInOrder(node.right, sb);
    }
    @Override
    public boolean containsValue(Object value) { throw new UnsupportedOperationException(); }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) { throw new UnsupportedOperationException(); }

    @Override
    public java.util.Set<Integer> keySet() { throw new UnsupportedOperationException(); }

    @Override
    public java.util.Collection<String> values() { throw new UnsupportedOperationException(); }

    @Override
    public java.util.Set<Entry<Integer, String>> entrySet() { throw new UnsupportedOperationException(); }

}
