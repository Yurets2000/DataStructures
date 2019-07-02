/**
 * Реалізація контейнера, що дозволяє використовувати типізовані масиви.
 * Представляє собою допоміжний клас, для типізованих структур даних
 * @param <E> параметр типу елементів масиву
 */

@SuppressWarnings("unchecked")
public class GenericContainer<E> {
    private Object[] arr;

    public GenericContainer(int s) {
        arr = new Object[s];
    }

    public E get(int i) {
        final E e = (E) arr[i];
        return e;
    }

    public void set(int position, E value){
        arr[position] = value;
    }

    public int size(){
        return arr.length;
    }
}