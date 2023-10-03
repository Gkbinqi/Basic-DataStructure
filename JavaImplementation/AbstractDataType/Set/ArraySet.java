import java.util.Iterator;
public class ArraySet<T> implements Iterable<T>{
    private T[] items;
    private int size;
    /**
     * Since Set don't have logical order, we can simply store items in sequence and
     * use size to track the index*/
    private void resizeUp(){
        if(size == items.length){
            T[] newItems = (T[]) new Object[size * 2];
            System.arraycopy(items,0,newItems,0,size);
            items = newItems;
        }
    }
    private void resizeDown(){
        if(size == items.length / 4){
            T[] newItems = (T[]) new Object[items.length/2];
            System.arraycopy(items,0,newItems,0,size);
            items = newItems;
        }
    }
    public ArraySet(){
        size = 0;
        items = (T[]) new Object[8];
    }
    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return (size == 0);
    }
    public boolean contains(T item){
        for(int i=0;i<size;i++){
            if(items[i] == item){
                return true;
            }
        }
        return false;
    }
    public void clear(){
        size = 0;
        items = (T[]) new Object[8];
    }
    /**
     * Assume that we always have spare box to store item, since we will check at end of every operation*/
    public boolean add(T item){
        if(contains(item)){
            return false;
        }

        items[size] = item;
        size +=1;

        resizeUp();
        return true;
    }
    public boolean delete(T item){
        for(int i=0;i<size;i++){
            if(items[i] == item){
                items[i] = items[size - 1];
                items[size - 1] = null;
                
                size -=1;
                return true;
            }
        }
        return false;
    }
    public T[] toArray(){
        if(size == 0){
            return null;
        }

        T[] newItems = (T[]) new Object[size];
        System.arraycopy(items,0,newItems,0,size);
        return newItems;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }
    private class ArraySetIterator implements Iterator<T> {
        private int index;

        public ArraySetIterator(){
            index =0;
        }
        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            T returnItem = items[index];
            index += 1;
            return returnItem;
        }
    }
}
