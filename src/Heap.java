import java.util.NoSuchElementException;

/**
 * Реалізація двійкової кучі
 * @param <K> параметр типу ключа, за яким знаходяться відповідні значення в двійковій кучі.
 * Параметр типу ключа повинен реалізувати інтерфейс Comparable, для реалізації основних методів
 * @param <V> параметр типу значення, яке знаходиться за відповідним ключем
 */

public class Heap<K extends Comparable<K>, V> {

    private static class Node<K, V> {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private GenericContainer<Node<K, V>> heapArray;
    private int maxSize;
    private int currentSize;

    /**
     * Створює двійкову кучу, що являє собою масив пар ключ-значення
     * @param maxSize максимальний розмір масиву
     */

    public Heap(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        heapArray = new GenericContainer<>(maxSize);
    }

    /**
     * Перевіряє чи двійкова куча містить елементи
     * @return логічне {@code true}, якщо дана двійкова куча пуста,
     * {@code false} - інакше
     */

    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Повертає розмір двійкової кучі
     * @return розмір двійкової кучі
     */

    public int size(){
        return currentSize;
    }

    /**
     * Додає пару ключ-значення в двійкову кучу
     * @param key ключ, який додається
     * @param value значення, яке додається
     * @return логічне {@code true}, якщо пара ключ-значення була додана,
     * {@code false} - інкаше
     */

    public boolean add(K key, V value) {
        if (currentSize == maxSize) {
            return false;
        }
        Node<K, V> newNode = new Node<>(key, value);
        heapArray.set(currentSize, newNode);
        trickleUp(currentSize++);

        return true;
    }

    private void trickleUp(int index) {
        int parent = (index - 1) / 2;
        Node<K, V> bottom = heapArray.get(index);
        while (index > 0 && heapArray.get(parent).key.compareTo(bottom.key) < 0) {
            heapArray.set(index, heapArray.get(parent));
            index = parent;
            parent = (parent - 1) / 2;
        }
        heapArray.set(index, bottom);
    }

    /**
     * Видаляє пару ключ-значення з двійкової кучі і повертає відповідне значення
     * @return значення, яке відповідає найбільшому ключу
     * @throws NoSuchElementException якщо двійкова куча є пустою
     */

    public V remove() {
        V removed = poll();
        if(removed == null){
            throw new NoSuchElementException("Неможливо видалити елемент, так як двійкова куча є пустою");
        }

        return removed;
    }

    /**
     * Видаляє пару ключ-значення з двійкової кучі і повертає відповідне значення
     * @return значення, яке відповідає найбільшому ключу, якщо двійкова куча не пустою;
     * {@code null} якщо двійкова куча пуста
     */

    public V poll() {
        if (isEmpty()) {
            return null;
        }
        Node<K, V> root = heapArray.get(0);
        heapArray.set(0, heapArray.get(--currentSize));
        trickleDown(0);

        return root.value;
    }

    private void trickleDown(int index) {
        int largerChild;
        Node<K, V> top = heapArray.get(index);

        while (index < currentSize / 2) {
            int leftChild = 2 * index + 1;
            int rightChild = leftChild + 1;

            if (rightChild < currentSize &&
                    heapArray.get(leftChild).key.compareTo(heapArray.get(rightChild).key) < 0) {
                largerChild = rightChild;
            } else {
                largerChild = leftChild;
            }

            if (top.key.compareTo(heapArray.get(largerChild).key) >= 0) break;

            heapArray.set(index, heapArray.get(leftChild));
            index = largerChild;
        }
        heapArray.set(index, top);
    }

    /**
     * Змінює значення ключа для пари ключ-значення,
     * що знаходиться на заданій позиції в масиві, який являє собою двійкова куча
     * @param index позиція в масиві, на якій знаходиться ключ
     * @param newValue нове значення ключа
     * @return логічне {@code true} якщо зміна відбулась успішно,
     * {@code false} - інкаше
     */

    public boolean change(int index, K newValue) {
        if (index < 0 || index >= currentSize) return false;
        K oldValue = heapArray.get(index).key;
        heapArray.get(index).key = newValue;
        if (oldValue.compareTo(newValue) < 0) {
            trickleUp(index);
        } else {
            trickleDown(index);
        }
        return true;
    }

    @Override
    public String toString(){
        String result = "";
        for(int i = 0; i < heapArray.size(); i++){
            result += "Node #" + i + ": Key = " + heapArray.get(i).key + "; Value = " + heapArray.get(i).value + "\n";
        }

        return result;
    }
}
