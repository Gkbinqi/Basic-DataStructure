public class Stack<T> {
    /** Since stack needn't scan through all the items in the stack,
     * a linked list can be qualified for this job.
     * And it doesn't seem profitable to implement this using array based list*/
    private class Node {
        public T value;
        public Node next;
        public Node(T item, Node next) {
            value = item;
            this.next = next;
        }
    }
    private final Node sentinel;
    private int size;
    public Stack() {
        sentinel = new Node(null, null);
        size = 0;
    }
    /** As a stack, we will only modify the first node that sentinel points to*/
    public void push(T item) {
        size += 1;
        sentinel.next = new Node(item, sentinel.next);
    }
    public T pop() {
        if (0 == size) {
            return null;
        }
        size -= 1;

        T popItem = sentinel.next.value;
        sentinel.next = sentinel.next.next;
        return popItem;
    }
    public T peek(){
        if (size == 0){
            return null;
        }
        return sentinel.next.value;
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
