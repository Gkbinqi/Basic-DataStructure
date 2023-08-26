import java.util.ArrayList;
import java.util.List;
public class MyArrayList<T> implements MyList<T> {
    /** Be aware that we have two lists,
     * one is abstract, ideal, users' list
     * another is concrete, complicated implement list
     * sometime we need to switch our perspective to find the point.*/
    private T[] items;
    /** Here are our loyal and reliable invariants.*/
    private int size;//always equal to the number of the valid elements in the list, makes it possible to implement many convenient and time-saving tricks
    private int nextFrontIndex;//always points to the box before the logically indexed '0' box. It provides the invariant that get() relies on.
    private int nextBackIndex;

    /** Private Methods:
     * resize(): Invoked only when the list is fully filled, size == length*/
    private void resize(){
        T[] newItems = (T[]) new Object[items.length * 2];
        int iterator = newItems.length /4;
        int temp = iterator - 1;

        for(int i = 0;i<size;i++){//since the list may be circular, we use a for loop to copy all the items.
            newItems[iterator] = get(i);
            iterator+=1;
        }
        //initialize the index pointer
        //Because get() method is rely on the index we set, we need to do the copy section first.
        nextBackIndex = iterator;
        nextFrontIndex = temp;
        //copy finished
        items = newItems;
    }
    private void resizeJudge(){
        if (size == items.length){
            resize();//will also fix the index variables
        }
    }
    /** resize(): invoked when we delete an item and find that size <= length / 4
     * it will half the length of items[]
     * it's core is similar to resize() */
    private void resizeDown(){
        T[] newItems = (T[]) new Object[ items.length /2 ];
        int iterator = newItems.length /4;
        int temp = iterator - 1;
        for(int i = 0;i<size;i++){
            newItems[iterator] = get(i);
            iterator+=1;
        }
        nextBackIndex = iterator;
        nextFrontIndex = temp;
        items = newItems;
    }
    private void resizeDownJudge(){
        if (size<=items.length/4 && items.length >8){
            resizeDown();
        }
    }
    /** backIndexDecrease(): used to perform back index decrease movement*/
    private int backIndexDecrease(){
        if (nextBackIndex == 0){
            nextBackIndex = items.length - 1;
        }else{
            nextBackIndex -= 1;
        }
        return nextBackIndex;
    }

    /** Public Methods:
     * Constructor. Default length: 8 */
    public MyArrayList() {
        items = (T[]) new Object[8];
        size = 0;
        nextFrontIndex = items.length/2 -1;
        nextBackIndex = nextFrontIndex +1;
    }
    /** get(): Get the logical list's ith element.
     * It offers an invariant that we can always get the logical ith element using get().
     * Noted that make sure our frontIndex points to the right place
     * (one "box" before the logical indexed '0' box, the '-1' box)*/
    @Override
    public T get(int index) {
        return items[(nextFrontIndex + 1 + index)%(items.length)];
    }

    /** addFirst(), addLast(): Add an item at the head(tail) of the logical list.
     * Assume that the frontIndex is always valid(point to a valid spare space).
     * We can always use it first then check it
     * Then we need to check whether the index is still valid.
     * Here we should always make sure that frontIndex points to the box
     * just behind the true first item, the logical indexed '0' box.
     * That means our frontIndex points to '-1' box, which is ready to be the next '0' box.
     * That's how we make sure that our get() method operating smoothly
     * Besides, if the list is full, this method will invoke resize() to update the items[].
     * It's offering an invariant that since size is checked, the index system is guaranteed*/
    @Override
    public void addFirst(T x) {
        size += 1;
        items[nextFrontIndex] = x;

        if(nextFrontIndex == 0 ){
            nextFrontIndex = items.length-1;
        } else {
            nextFrontIndex -=1;
        }
        resizeJudge();
    }
    @Override
    public void addLast(T x) {
        size += 1;
        items[nextBackIndex] = x;

        if(nextBackIndex == items.length - 1 ){
            nextBackIndex = 0;
        } else {
            nextBackIndex +=1;
        }
        resizeJudge();
    }
    /** insert(): one of the most time-consuming operations for our array list.
     * It will insert an item ahead of the logically indexed 'index' item
     * That means inserted item will be the (index)th item.
     * We will move the items in the array first to get the space box, then put the new item in.
     * And the way we move these items is from items' tail to front.*/
    @Override
    public void insert(int index, T item) {
        if ( index >= items.length || index < 0){
            throw new IndexOutOfBoundsException("Invalid Index");
        }

        size +=1;
        int originBackIndex = nextBackIndex;
        int targetIndex = (nextFrontIndex + 1 + index)%(items.length);//the (index)th item, target place.

        while(nextBackIndex != targetIndex){
            items[nextBackIndex] = items[backIndexDecrease()];
        }
        items[targetIndex] = item;

        nextBackIndex = (originBackIndex + 1)%items.length;
        resizeJudge();
    }

    /** removeFirst(), removeLast(): return and remove the item selected. return null when the list is empty.
     * It will also resizeDown the list when it finds size <= items.length/4 */
    @Override
    public T removeFirst() {
        if ( size ==0 ){
            return null;
        }

        size -=1;
        T returnItem;
        if (nextFrontIndex == items.length -1){
            returnItem = items[0];
            items[0] = null;
            nextFrontIndex = 0;
        }else {
            returnItem = items[nextFrontIndex + 1];
            items[nextFrontIndex + 1] = null;
            nextFrontIndex = nextFrontIndex + 1;
        }
        resizeDownJudge();
        return returnItem;
    }
    @Override
    public T removeLast() {
        if ( size ==0 ){
            return null;
        }

        size -=1;
        T returnItem;
        if (nextBackIndex == 0){
            returnItem = items[items.length -1];
            items[items.length - 1] = null;
            nextBackIndex = items.length-1;
        }else {
            returnItem = items[nextBackIndex -1];
            items[nextBackIndex -1] = null;
            nextBackIndex = nextBackIndex -1;
        }
        resizeDownJudge();
        return returnItem;
    }
    /** remove(): remove the ith item, and return it.*/
    @Override
    public T remove(int index){
        if ( index >= items.length || index < 0){
            throw new IndexOutOfBoundsException("Invalid Index");
        }

        size -=1;
        int targetIndex = (nextFrontIndex + 1 + index)%(items.length);//the (index)th item, to be removed.
        T removedItem = items[targetIndex];

        while(targetIndex != nextBackIndex){
            items[targetIndex] = items[(targetIndex + 1) % items.length];
            targetIndex = (targetIndex + 1) % items.length;
        }
        backIndexDecrease();
        resizeDownJudge();

        return removedItem;
    }
    /**Functional methods:
     * isEmpty(): return true when the list is empty*/
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }
    /** size(): return size*/
    @Override
    public int size() {
        return size;
    }
    /** clear(): clear the list*/
    @Override
    public void clear(){
        items = (T[]) new Object[8];
        size = 0;
        nextFrontIndex = items.length/2 -1;
        nextBackIndex = nextFrontIndex +1;
    }
    /** toList(): return the logical list in List type.*/
    @Override
    public List<T> toList() {
        if(size == 0){
            return null;
        }
        List<T> newList = new ArrayList<>();
        for(int i = 0;i<size;i++){
            newList.add(get(i));
        }
        return newList;
    }
    @Override
    public boolean contain(T item){
        for(int i=0;i<size;i++){
            if(get(i) == item){//encapsulation, great.
                return true;
            }
        }
        return false;
    }
}
