/**
 * Gktwerk 2023
 *
 * Since we just care about `push` and `pop` this two operation,
 * a linked list is qualified for this job.
 * And it doesn't seem profitable to implement this using other data structure.
 * */
public class Stack<T> {
    private class Node {
        public T data;
        public Node next;
        public Node(T item, Node next) {
            data = item;
            this.next = next;
        }
    }

    private final Node sentinel;
    private int size;

    public Stack() {
        sentinel = new Node(null, null);
        size = 0;
    }
    /** push(T item)
     *  it will add a new item into the stack.
     * */
    public void push(T item) {
        size += 1;
        sentinel.next = new Node(item, sentinel.next);
    }
    public T pop() {
        if (0 == size) {
            return null;
        }
        size -= 1;

        T popItem = sentinel.next.data;
        sentinel.next = sentinel.next.next;
        return popItem;
    }
    public T peek(){
        if (size == 0){
            return null;
        }
        return sentinel.next.data;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return (0 == size);
    }
    public void clear() {
        size = 0;
        sentinel.next = null;
    }
}
