import java.util.List;

public interface MyList<T> {

    /** Core method*/
    T get(int index);
    /** Modify the list*/
    void addFirst(T x);
    void addLast(T x);
    void insert(int index, T item);

    T removeFirst();
    T removeLast();
    T remove(int index);
    /** Functional methods*/
    boolean isEmpty();
    int size();
    void clear();
    List<T> toList();
    boolean contain(T item);
}
