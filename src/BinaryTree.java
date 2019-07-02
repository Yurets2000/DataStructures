import java.util.NoSuchElementException;

/**
 * Реалізація бінарного дерева
 * @param <K> параметр типу ключа, за яким знаходяться відповідні значення в бінарному дереві.
 * Параметр ключа повинен реалізувати інтерфейс Comparable, для реалізації основних методів
 * @param <V> параметр типу значення, яке знаходиться за відповідним ключем
 */

public class BinaryTree<K extends Comparable<K>, V> {

    private int size = 0;
    private Node<K, V> root;

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> leftChild, rightChild, parent;

        Node(Node<K, V> left, Node<K, V> parent, Node<K, V> right, K key, V value) {
            this.key = key;
            this.value = value;
            this.leftChild = left;
            this.parent = parent;
            this.rightChild = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    /**
     * Перевіряє чи бінарне дерево містить елементи
     * @return логічне {@code true}, якщо дане дерево пусте, {@code false} - інакше
     */

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Повертає кількість елементів в бінарному дереві
     * @return кількість елементів в бінарному дереві
     */

    public int size(){
        return size;
    }

    /**
     * Додає пару ключ-значення в бінарне дерево
     * @param key ключ, який додається
     * @param value значення, яке додається
     */

    public void add(K key, V value){
        Node<K, V> toInsert = new Node<>(null, null, null, key, value);
        if(root == null) {
            root = toInsert;
        }else{
            Node<K, V> current = root;
            boolean added = false;
            while (!added){
                if(key.compareTo(current.key) <= 0){
                    if(current.leftChild != null){
                        current = current.leftChild;
                    }else{
                        current.leftChild = toInsert;
                        toInsert.parent = current;
                        added = true;
                    }
                }else{
                    if(current.rightChild != null){
                        current = current.rightChild;
                    }else{
                        current.rightChild = toInsert;
                        toInsert.parent = current;
                        added = true;
                    }
                }
            }
            size++;
        }
    }

    /**
     * Знаходить значення в бінарному дереві за ключем,
     * якому дане значення поставлене у відповідність
     * @param key ключ, за яким відбувається пошук
     * @return значення, якщо відповідна пара ключ-значення існує в бінарному дереві
     * @throws NoSuchElementException якщо дерево пусте,
     * чи відповідної пари ключ-значення немає в бінарному дереві
     */

    public V get(K key){
        return findNode(key).value;
    }

    private Node<K, V> findNode(K key){
        if(root == null){
            throw new NoSuchElementException("Неможливо знайти елемент так як дерево пусте");
        }

        Node<K, V> current = root;

        while(current != null){
            if(key.compareTo(current.key) < 0){
                current = current.leftChild;
            }else if(key.compareTo(current.key) > 0){
                current = current.rightChild;
            }else{
                return current;
            }
        }

        throw new NoSuchElementException("Не вдалося знайти заданий елемент");
    }

    private Node<K, V> findMinBeginsWith(Node<K, V> node){
        Node<K, V> current = node;
        while(current.leftChild != null){
            current = current.leftChild;
        }

        return current;
    }

    private Node<K, V> findMaxBeginsWith(Node<K, V> node){
        Node<K, V> current = node;
        while(current.rightChild != null){
            current = current.rightChild;
        }

        return current;
    }

    /**
     * Знаходить значення, яке відповідає максимальному за величиною ключу
     * @return значення, яке відповідає максимальному за величиною ключу
     * @throws NullPointerException якщо дерево пусте
     */

    public V max(){
        return findMaxBeginsWith(root).value;
    }

    /**
     * Знаходить значення, яке відповідає мінімальному за величиною ключу
     * @return значення, яке відповідає мінімальному за величиною ключу
     * @throws NullPointerException якщо дерево пусте
     */

    public V min(){
        return findMinBeginsWith(root).value;
    }

    /**
     * Видаляє пару ключ-значення
     * @param key ключ, за яким відбувається видалення пари
     * @throws IllegalStateException якщо дерево пусте
     */

    public void remove(K key){
        if(root == null){
            throw new IllegalStateException("Неможливо видалити елемент так як дерево пусте");
        }else{
            Node<K, V> found = findNode(key);
            if(found.leftChild != null) {
                Node<K, V> toReplace = findMaxBeginsWith(found.leftChild);
                if(!toReplace.equals(found.leftChild)) {
                    toReplace.parent.rightChild = null;
                    toReplace.leftChild = found.leftChild;
                }
                toReplace.parent = found.parent;
                if(found.equals(root)){
                    root = toReplace;
                }
            }else if(found.rightChild != null){
                Node<K, V> toReplace = findMinBeginsWith(found.rightChild);
                if(!toReplace.equals(found.rightChild)) {
                    toReplace.parent.leftChild = null;
                    toReplace.rightChild = found.rightChild;
                }
                toReplace.parent = found.parent;
                if(found.equals(root)){
                    root = toReplace;
                }
            }else{
                if(found.parent != null){
                    if(found.key.compareTo(found.parent.key) <= 0){
                        found.parent.leftChild = null;
                    }else{
                        found.parent.rightChild = null;
                    }
                }else{
                    root = null;
                }
            }
        }
        size--;
    }

    private void preOrder(Node<K, V> node){
        if(node != null){
            System.out.println(node);
            preOrder(node.leftChild);
            preOrder(node.rightChild);

        }
    }

    private void inOrder(Node<K, V> node){
        if(node != null){
            inOrder(node.leftChild);
            System.out.println(node);
            inOrder(node.rightChild);
        }
    }

    private void postOrder(Node<K, V> node){
        if(node != null){
            postOrder(node.leftChild);
            postOrder(node.rightChild);
            System.out.println(node);
        }
    }

    /**
     * Виконує прямий обхід дерева, виводячи його елементи
     */

    public void preOrder(){
        preOrder(root);
    }

    /**
     * Виконує симетричний обхід дерева, виводячи його елементи
     */

    public void inOrder(){
        inOrder(root);
    }

    /**
     * Виконує обернений обхід дерева, виводячи його елементи
     */

    public void postOrder(){
        postOrder(root);
    }
}
