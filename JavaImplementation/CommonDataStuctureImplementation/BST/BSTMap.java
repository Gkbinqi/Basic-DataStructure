import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/** BSTMap Implementation
 * skeleton code from CS61b(methods to be implemented).
 * */
public class BSTMap <K extends Comparable<K>,V> implements Iterable<K> {
    private class Node {
        K key;
        V value;
        Node leftNode;
        Node rightNode;
        public Node (K key, V value) {
            this.key = key;
            this.value = value;
            leftNode = null;
            rightNode = null;
        }
        public Node(K key, V value, Node leftNode, Node rightNode) {
            this.key = key;
            this.value = value;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }
        public void replace (Node toBeReplace) {
            leftNode = toBeReplace.leftNode;
            rightNode = toBeReplace.rightNode;
        }
    }
    private class sentinelNode {
        public Node rootNode;
        public sentinelNode () {
            rootNode = null;
        }
    }
    /** Private Instance Variables
     * We need a sentinel, or there will be many ugly exception in our code.
     * */
    private final sentinelNode sentinel;
    int size;
    /** Construction method */
    public BSTMap () {
        sentinel = new sentinelNode();
        size = 0;
    }
    /**              Basic methods
     * ------------------------------------------
     * put(K key, V value)
     * Put a new value in the map
     * Null key is unaccepted.
     * If a key already exist, update the value.
     * */
    public void put(K key, V value) {
        nullKeyCheck(key);
        sentinel.rootNode = putInside(key,value,sentinel.rootNode);
    }
    private Node putInside(K key, V value, Node node){
        if (null == node) {
            size += 1;
            return new Node(key, value);
        }

        if (key.compareTo(node.key) > 0) {
            node.rightNode = putInside(key, value, node.rightNode);
        } else if (key.compareTo(node.key) < 0) {
            node.leftNode = putInside(key, value, node.leftNode);
        } else {//key already exist, update the value;
            node.value = value;
        }

        return node;
    }

    /** get(K key)
     * Search for the value bound with key.
     * Null key is unaccepted.
     * If value not found, return null.
     * */
    public V get(K key) {
        nullKeyCheck(key);
        return getInside(key, sentinel.rootNode);
    }
    private V getInside(K key, Node node) {
        if (null == node) {
            return null;//not found
        }

        if (key.compareTo(node.key) > 0){
            return getInside(key, node.rightNode);
        } else if (key.compareTo(node.key) < 0) {
            return getInside(key, node.leftNode);
        } else return node.value;//found
    }

    /** boolean containsKey(K key)
     * Search for the key
     * Null key is unaccepted.
     * Return true if key is found, otherwise return false
     * */
    public boolean containsKey(K key) {
        nullKeyCheck(key);
        return containsKeyInside(key, sentinel.rootNode);
    }
    private boolean containsKeyInside(K key, Node node) {
        if (null == node) {
            return false;
        }

        if (key.compareTo(node.key) > 0) {
            return containsKeyInside(key, node.rightNode);
        } else if (key.compareTo(node.key) < 0) {
            return containsKeyInside(key, node.leftNode);
        } else return true;
    }

    /**  V remove(K key)
     * Remove the key-value pair in the BST
     * Return the value deleted
     * Functionality:
     * Randomly apply predecessor or successor deletion
     * need debug
     * */
    public V remove(K key) {
        nullKeyCheck(key);
        V returnValue = get(key);
        if (containsKey(key)) {
            size -=1;
            sentinel.rootNode = removeInside(key, sentinel.rootNode);//assume we do have this node inside since we can enter here.
        }
        return returnValue;
    }
    private Node removeInside(K key, Node curNode) {
        if (key.compareTo(curNode.key) > 0) {
            curNode.rightNode = removeInside(key, curNode.rightNode);
        } else if (key.compareTo(curNode.key) < 0) {
            curNode.leftNode =  removeInside(key, curNode.leftNode);
        } else {//found
            int num = numOfChild(curNode);
            switch (num) {
                case 0:
                    return null;
                case 1:
                    if (curNode.leftNode != null) {
                        return curNode.leftNode;
                    } else return curNode.rightNode;
                case 2:
                    if( randomBooleanOnTime() ) {
                        //random case 1, using predecessor
                        Node predP = findPredecessorParent(curNode);
                        Node pred = null;

                        if (predP == curNode) {
                            pred = curNode.leftNode;
                            pred.rightNode = curNode.rightNode;
                        } else {
                            pred = predP.rightNode;
                            predP.rightNode = pred.leftNode;
                            pred.replace(curNode);
                        }
                        return pred;
                    } else {
                        //random case 2, using successor
                        Node succP = findSuccessorParent(curNode);
                        Node succ = null;

                        if (succP == curNode) {
                            succ = succP.rightNode;
                            succ.leftNode = curNode.leftNode;
                        } else {
                            succ = succP.leftNode;
                            succP.leftNode = succ.rightNode;
                            succ.replace(curNode);
                        }
                        return succ;
                    }
            }
        }
        return curNode;
    }

    /** Key Set
     * Return a set contains all the keys
     * */
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<>();
        keySetInside(keySet, sentinel.rootNode);
        return keySet;
    }
    private void keySetInside(Set<K> keySet, Node node) {
        if(null != node) {
            keySetInside(keySet,node.leftNode);
            keySet.add(node.key);
            keySetInside(keySet,node.rightNode);
        }
    }
    /**            Functional methods
     * --------------------------------------------*/
    public int size() {
        return size;
    }
    public void clear() {
        sentinel.rootNode = null;
        size = 0;
    }
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Under Construction...");
    }
    public void printInOrder() {
        printTree(sentinel.rootNode);
    }
    private void printTree(Node node) {
        if(node == null) {
            return;
        }
        printTree(node.leftNode);
        System.out.print(node.value + " ");
        printTree(node.rightNode);
    }

    /**            Helper methods
     * ----------------------------------------
     * Helper methods here
     * Useful
     * */
    private void nullKeyCheck (K key) {
        if (null == key) {
            throw new IllegalArgumentException("Can't input null value!");
        }
    }
    private int numOfChild(Node node) {
        int num = 0;
        if (node.rightNode != null) {
            num +=1;
        }
        if (node.leftNode != null) {
            num +=1;
        }
        return num;
    }
    private Node findPredecessorParent(Node curNode) {
        //find the rightmost node's parent from the left side.
        //predecessor can only have a leftNode.
        Node iteratorNode = curNode.leftNode;
        Node tempNode = curNode;
        while (iteratorNode.rightNode != null) {
            tempNode = iteratorNode;
            iteratorNode = iteratorNode.rightNode;
        }
        return tempNode;
    }
    private Node findSuccessorParent(Node curNode) {
        // find the rightmost node from the right side.
        Node iteratorNode = curNode.rightNode;
        Node tempNode = curNode;
        while (iteratorNode.leftNode != null) {
            tempNode = iteratorNode;
            iteratorNode = iteratorNode.leftNode;
        }
        return tempNode;
    }
    private boolean randomBooleanOnTime() {
        return (0 == (System.currentTimeMillis() % 2));
    }
}
