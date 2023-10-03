/** BST Version 1.1
 * Gktwerk */
public class BST <T extends Comparable<T>>{
    private class TreeNode{
        private T value;
        private TreeNode leftNode;
        private TreeNode rightNode;
        public TreeNode(T val, TreeNode left, TreeNode right){
            value = val;
            leftNode = left;
            rightNode = right;
        }
    }
    private TreeNode rootNode;
    public BST(T rootValue){
        rootNode = new TreeNode(rootValue,null,null);
    }
    public TreeNode search(T value){
        return searchInside(rootNode,value);
    }
    public TreeNode searchInside(TreeNode node,T value){
        if(node == null){
            return null;
        }
        if(value.compareTo(node.value) > 0){
            return searchInside(node.rightNode,value);
        } else if (value.compareTo(node.value) < 0) {
            return searchInside(node.leftNode,value);
        }
        return node;
    }
    public TreeNode insert(T value){
        return insertInside(rootNode,value);
    }
    /**
     *
     * */
    private TreeNode insertInside(TreeNode node,T value){
        if(node == null){
            return new TreeNode(value, null,null);
        }
        if(value.compareTo(node.value) > 0){
            node.rightNode = insertInside(node.rightNode,value);
        }else if(value.compareTo(node.value) < 0){
            node.leftNode = insertInside(node.leftNode,value);
        }
        return node;//duplicate
    }
    public T delete(T value){
        return deleteInside(rootNode,value);
    }
    private T deleteInside(TreeNode node, T value) {
        return null;// to be implemented...
    }

    public void print(){
        printTree(rootNode);
    }
    private void printTree(TreeNode node){
        if(node == null){
            return;
        }
        printTree(node.leftNode);
        System.out.print(node.value + " ");
        printTree(node.rightNode);
    }
}
