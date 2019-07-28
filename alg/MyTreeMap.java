package alg;

import java.util.NoSuchElementException;

/*
Ассоциативный массив в java реализованный с помощью ассоциативного поиска.
Копия, с некоторыми упрощениями класса TreeMap из библиотеки Сollections
 */
public class MyTreeMap<Key extends Comparable, Value> {

    private class Node {
        Key key;
        Value value;
        Node left;
        Node right;
        int size; // количество узлов в дереве, корнем которого является данный узел
        int height;
        public Node (Key key, Value value, int size){
            this.key = key;
            this.value = value;
            this.size = size;
            this.height = 0;
        }
    }
    private Node root = null;

    public boolean isEmpty(){return  root == null;}

    private int size(Node node){
        if (node==null){return 0;}
        return node.size;
    }
    private int height(Node node){
        if (node==null){return 0;}
        return node.height;
    }
    public int size(){
        return size(root);
    }
    public int height(){
        return height(root);
    }

    public Value get(Key key) {
        return get(key, root);
    }

    private Value get (Key key, Node node) {
        if (key == null) {throw new IllegalArgumentException("Key can not bee null");}
        if (node == null){ return  null;}
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {return node.value;}
        if (cmp < 0) {
            return get(key, node.left);
        }
        else {
            return get(key, node.right);
        }
    }

   public void put(Key key, Value value) { //a[key] = value
        root = put(key, value, root);
   }

   private Node put (Key key, Value value, Node node){
       if (key == null) {throw new IllegalArgumentException("Key can not bee null");}
       if (node == null){ return new Node(key, value,1);}
       int cmp = key.compareTo(node.key);
       if (cmp == 0) {node.value =value;}
       else if (cmp < 0) {
           node.left = put(key, value, node.left);
       }
       else { //cmp > 0
           node.right = put(key, value, node.right);
       }
       node.size = (size(node.left) + size(node.right) + 1);
       return  node;
   }
   private Node max (Node node){ // находит наибольший ключ в нашем дереве
        if (node.right == null){return node;}
        else {return max(node.right);}

   }
   public Value max() {return max(root).value;}

    private Node min (Node node){ // находит наименьший ключ в нашем дереве
        if (node.left==null){return node;}
        else {return max(node.left);}

    }
    public Value min() {return min(root).value;}

    private Node removeMin(Node node){
        if (node.left == null) {
            return node.right;
        }
        else {
            node.left = removeMin(node.left);
        }
        node.size = size(node.left) + size(node.right)+1;
        return node;

    }
    public void removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Map is empty.");
        }
        root = removeMin(root);
    }

    private Node removeMax(Node node){
        if (node.right == null) {
            return node.left;
        }
        else {
            node.right = removeMin(node.right); /// ???? почему остался метод removeMin
        }
        node.size = size(node.left) + size(node.right)+1;
        return node;

    }
    public void removeMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Map is empty.");
        }
        root = removeMax(root);
    }
    private Node  remove (Key key, Node node) {
        if (key == null) {throw new IllegalArgumentException("Key can not bee null");}
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);

        if (cmp == 0){ //remove
            if (node.left==null){
                return node.right;
            }
            if (node.right == null){
                return node.left;
            }
            // далее метод хибарда
            Node tmp = node;
            node = max(node.left);
            node.left = removeMax(tmp.left);
            node.right = tmp.right;
            tmp =null;
        }
        else if (cmp < 0) {
            node.left = remove(key, node.left);
        }
        else {// cmp>0
            node.right = remove(key, node.right);
        }
        node.size = size(node.left) + size(node.right)+1;
        return node;
    }
    public boolean isBalanced(){
        return false;
    }

    public  boolean MyIsBalanced(){
        if
        (
                (size(root.left) == (size(root.right) + 1))
                        ||
                        ((size(root.left)+1) == size(root.right))
                        ||
                        (size(root.left) == size(root.right))

        )
        {
            return true;
        }
        return false;
    }



}
