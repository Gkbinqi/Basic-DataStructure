/** BST Version 1.0
 * Gktwerk */
public class BST <T extends Comparable<T>>{
    public class TreeNode{
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
    private TreeNode insertInside(TreeNode node,T value){
        if(node == null){
            node = new TreeNode(value, null,null);
            return node;
        }
        if(value.compareTo(node.value) > 0){
            return insertInside(node.rightNode,value);
        }else if(value.compareTo(node.value) < 0){
            return insertInside(node.leftNode,value);
        }
        return null;//duplicate
    }
    public T delete(T value){
        return deleteInside(rootNode,value);
    }

    private T deleteInside(TreeNode node, T value) {
        return null;// to be implemented...
    }
}
