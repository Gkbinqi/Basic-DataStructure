public class Queue<T> {
    private class Node{
        private T value;
        private Node next;
        private Node prev;
        public Node(T val, Node next,Node prev){
            value = val;
            this.prev = prev;
            this.next = next;
        }
        public Node(){
            value = null;
            this.prev = this;
            this.next = this;
        }
    }
    final private Node sentinel;//we will build this queue a circular list.
    private int size;
    public Queue(){
        size = 0;
        sentinel = new Node();
    }
    public int size(){
        return size;
    }
    public void clear(){
        size =0;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }
    public void add(T value){
        size +=1;
        Node newNode = new Node(value, sentinel.next,sentinel);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
    }
    public T poll(){
        if(size == 0){
            return null;
        }
        size -=1;
        T returnItem = sentinel.prev.value;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        return returnItem;
    }
    public T peek(){
        if(size == 0){
            return null;
        }
        return sentinel.prev.value;
    }
}
