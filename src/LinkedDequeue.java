import java.util.NoSuchElementException;

/**
 * Реалізація двобічної черги
 * @param <T> параметр типу елементів черги
 */

public class LinkedDequeue<T> {

    private Node<T> tail, head;
    private int size;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * Повертає кількість елементів в черзі
     * @return кількість елементів в черзі
     */

    public int size(){
        return size;
    }

    /**
     * Перевіряє чи черга містить елементи
     * @return логічне {@code true}, якщо даний черга пуста, {@code false} - інакше
     */

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Вставляє заданий елемент у голову черги
     * @param item елемент, який вставляється
     */

    public void offerFirst(T item){
        if(isEmpty()){
            head = new Node<>(null, item, null);
            tail = head;
            tail.next = head;
            head.prev = tail;
        }else{
            head = new Node<>(head, item, null);
            head.prev.next = head;
        }

        size++;
    }

    /**
     * Вставляє заданий елемент у хвіст черги
     * @param item елемент, який вставляється
     */

    public void offerLast(T item){
        if(isEmpty()){
            tail = new Node<>(null, item, null);
            head = tail;
            tail.next = head;
            head.prev = tail;
        }else{
            tail = new Node<>(null, item, head);
            tail.next.prev = tail;
        }

        size++;
    }

    /**
     * Видаляє елемент із голови черги
     * @return елемент, що був видалений
     * @throws NoSuchElementException якщо черга пуста
     */

    public T removeFirst(){
        T removed = pollFirst();
        if(removed == null){
            throw new NoSuchElementException("Неможливо видалити елемент, так як черга пуста");
        }

        return removed;
    }

    /**
     * Видаляє елемент із хвоста черги
     * @return елемент, що був видалений
     * @throws NoSuchElementException якщо черга пуста
     */

    public T removeLast(){
        T removed = pollLast();
        if(removed == null){
            throw new NoSuchElementException("Неможливо видалити елемент, так як черга пуста");
        }

        return removed;
    }

    /**
     * Видаляє елемент із голови черги
     * @return елемент, що був видалений якщо черга не пуста, {@code null} - інакше
     */

    public T pollFirst(){
        if(isEmpty()){
            return null;
        }

        T item = head.item;
        if(head.prev != null){
            head.prev.next = null;
            head = head.prev;
        }
        size--;

        return item;
    }

    /**
     * Видаляє елемент із хвоста черги
     * @return елемент, що був видалений якщо черга не пуста, {@code null} - інакше
     */

    public T pollLast() {
        if (isEmpty()) {
            return null;
        }

        T item = tail.item;
        if (tail.next != null) {
            tail.next.prev = null;
            tail = tail.next;
        }
        size--;

        return item;
    }

    /**
     * Повертає елемент із голови черги, не видаляючи його
     * @return елемент, якщо черга не пуста, {@code null} - інакше
     */

    public T peekFirst(){
        if(isEmpty()){
            return null;
        }

        return head.item;
    }

    /**
     * Повертає елемент із хвоста черги, не видаляючи його
     * @return елемент, якщо черга не пуста, {@code null} - інакше
     */

    public T peekLast(){
        if(isEmpty()){
            return null;
        }

        return tail.item;
    }
}
