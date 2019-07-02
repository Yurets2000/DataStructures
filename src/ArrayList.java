import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/**
 * Реалізація списку
 * @param <T> параметр типу елементів списку
 */

@SuppressWarnings("unchecked")
public class ArrayList<T> {

    private int size = 0;
    private T[] elementData;

    /**
     * Створює список з місткістю в 10 елементів
     */

    public ArrayList(){
        this(10);
    }

    /**
     * Створює список з заданою місткістю
     * @param capacity місткість створюваного списку
     */

    public ArrayList(int capacity){
        elementData = (T[]) new Object[capacity];
    }

    /**
     * Створює список на основі заданого масиву
     * @param data масив, на основі якого створюється список
     */

    public ArrayList(T[] data){
        size = data.length;
        elementData = data;
    }

    /**
     * Створює список на основі заданого списку
     * @param list список, на основі якого створюється новий список
     */

    public ArrayList(ArrayList<T> list){
        this(list.size());
        size = list.size;
        elementData = list.toArray(elementData);
    }

    /**
     * Повертає кількість елементів в списку
     * @return кількість елементів в списку
     */

    public int size(){
        return size;
    }

    /**
     * Перевіряє чи список містить елементи
     * @return логічне {@code true}, якщо даний список пустий, {@code false} - інакше
     */

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Змінює місткість списку, якщо нинішня місткість менша за {@code capacity}
     * @param capacity нова місткість списку
     */

    public void ensureCapacity(int capacity){
        if(capacity >= elementData.length) {
            int newSize = elementData.length;
            while (capacity > newSize) {
                newSize = 2 * newSize + 1;
            }

            T[] newElementData = (T[]) new Object[newSize];
            System.arraycopy(elementData, 0, newElementData, 0, size);
            elementData = newElementData;
        }
    }

    /**
     * Обрізає список до заданого розміру
     * @param size розмір, до якого необхідно обрізати список
     * @throws IndexOutOfBoundsException якщо значення {@param size}
     * не менше за розмір списку, або менше за 0
     */

    public void trimToSize(int size){
        if(size >= this.size || size < 0){
            throw new IndexOutOfBoundsException();
        }
        T[] newElementData = (T[]) new Object[size];
        System.arraycopy(elementData, 0, newElementData, 0, size);
        elementData = newElementData;
    }

    /**
     * Додає заданий елемент в список на задану позицію
     * @param element елемент, який додається
     * @param index позиція в списку, на яку додається елемент
     * @throws IndexOutOfBoundsException якщо значення {@param index}
     * не менше за розмір списку, або менше за 0
     */

    public void add(T element, int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        ensureCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    /**
     * Додає всі елементи заданого списку в список
     * на задану позицію
     * @param list список, елементи якого додаються
     * @param index позиція в списку, на яку додається елемент
     * @throws IndexOutOfBoundsException якщо значення {@param index}
     * не менше за розмір списку, або менше за 0
     */

    public void addAll(ArrayList<T> list, int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        ensureCapacity(size + list.size);
        System.arraycopy(elementData, index, elementData, index + list.size, size - index);
        System.arraycopy(list.elementData, 0, elementData, index, list.size);
        size+= list.size;
    }

    /**
     * Додає заданий елемент в кінець списку
     * @param element елемент, який додається
     */

    public void add(T element){
        ensureCapacity(size + 1);
        elementData[size++] = element;
    }

    /**
     * Повертає елемент, що знаходиться на відповідній позиції
     * @param index позиція елемента в списку
     * @return елемент на позиції {@param index}
     * @throws IndexOutOfBoundsException якщо значення {@param index}
     * не менше за розмір списку, або менше за 0
     */

    public T get(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }

        return elementData[index];
    }

    /**
     * Перевіряє чи список список містить заданий елемент
     * @param element елемент, наявність якого потрібно знайти
     * @return логічне {@code true} якщо елемент наявний списку,
     * {@code false} - інакше
     */

    public boolean contains(T element){
        for (int i = 0; i < size; i++) {
            if(elementData[i].equals(element)){
                return true;
            }
        }

        return false;
    }

    /**
     * Знаходить позицію елемента в списку
     * @param element елемент, позицію в списку якого потрібно знайти
     * @return позицію в списку, на якій знаходиться {@param element}
     * @throws NoSuchElementException якщо елемент не знайдено
     */

    public int find(T element){
        for (int i = 0; i < size; i++) {
            if(elementData[i].equals(element)){
                return i;
            }
        }

        throw new NoSuchElementException("Даний елемент не знайдено");
    }

    /**
     * Видаляє елемент на відповідній позиції зі списку
     * @param index позиція, на якій видаляється елемент
     * @throws IndexOutOfBoundsException якщо значення {@param index}
     * не менше за розмір списку, або менше за 0
     * @throws NoSuchElementException якщо список пустий
     */

    public void remove(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        if(size == 0){
            throw new NoSuchElementException("Неможливо видалити елемент, так як список пустий");
        }

        System.arraycopy(elementData, index + 1, elementData, index, size - (index + 1));
        size--;
    }

    /**
     * Видаляє всі елементи зі списку в заданому діапазоні
     * @param start індекс початку діапазону(включно)
     * @param end індекс кінця діапазону(виключно)
     * @throws IllegalArgumentException якщо значення {@param start}
     * не менше за значення {@param end}
     * @throws IndexOutOfBoundsException якщо значення {@param end}
     * не менше за розмір списку, або значення {@param start}
     * менше за 0
     */

    public void removeRange(int start, int end){
        if(end <= start){
            throw new IllegalArgumentException();
        }
        if(end >= size || start < 0){
            throw new IndexOutOfBoundsException();
        }
        if(start == end - 1) return;

        System.arraycopy(elementData, start, elementData, end, size - (end - start));
        size-= start - end;
    }

    /**
     * Повертає частину списку в заданому діапазоні
     * @param start індекс початку діапазону(включно)
     * @param end індекс кінця діапазону(виключно)
     * @throws IllegalArgumentException якщо значення {@param start}
     * не менше за значення {@param end}
     * @throws IndexOutOfBoundsException якщо значення {@param end}
     * не менше за розмір списку, або значення {@param start}
     * менше за 0
     */

    public ArrayList<T> getSubList(int start, int end){
        if(end <= start){
            throw new IllegalArgumentException();
        }
        if(end >= size || start < 0){
            throw new IndexOutOfBoundsException();
        }
        T[] newElementData = (T[]) new Object[end - start];
        System.arraycopy(elementData, start, newElementData, 0, end - start);

        return new ArrayList<>(newElementData);
    }

    /**
     * Повертає масив елементів списку, з розміром та типом
     * еталонного масиву
     * @param arr еталонний масив
     * @return масив елементів списку розміром {@param arr}.length та типом {@code T}
     * @throws IndexOutOfBoundsException якщо розмір еталонного масиву
     * більший за розмір списку
     */

    public T[] toArray(T[] arr){
        if(arr.length > size){
            throw new IndexOutOfBoundsException();
        }
        return (T[]) Array.newInstance(arr.getClass().getComponentType(), arr.length);
    }

    @Override
    public String toString() {
        if(size == 0) return "[ ]";
        String result = "[ ";
        for (int i = 0; i < size - 1; i++) {
            result += get(i) + ", ";
        }
        result+= get(size - 1) + " ]";

        return result;
    }
}
