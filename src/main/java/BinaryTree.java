import java.util.*;

public class BinaryTree {

    private BinaryNode root;

    public BinaryTree(){
        root = null;
    }

    public void add(Comparable x){
        add(new BinaryNode(x));
    }

    private void add(BinaryNode x){
        if (root == null){
            root = x;
        } else {
            add(root, x);
        }
    }

    private void add(BinaryNode par, BinaryNode x){
        if (par.getValue().compareTo(x.getValue()) > 0){
            if (par.left() == null){
                par.setLeft(x);
            } else {
                add(par.left(), x);
            }
        } else {
            if (par.right() == null){
                par.setRight(x);
            } else {
                add(par.right(), x);
            }
        }
    }

    public int getHeight(){
        return getHeight(getRoot());
    }

    private int getHeight(BinaryNode x){
        if (x == null){
            return -1;
        } else {
            return 1 + Math.max(getHeight(x.left()), getHeight(x.right()));
        }
    }

    public int getSize(){
        return getSize(getRoot());
    }

    private int getSize(BinaryNode x){
        if (x == null) {
            return 0;
        } else {
            return 1 + getSize(x.left()) + getSize(x.right());
        }
    }

    public List<BinaryNode> getNodes(){
        if (getRoot() == null){
            return new ArrayList<>();
        }
        return inOrderTraversal(getRoot());
        /*
        Queue<BinaryNode> queue = new LinkedList<>();
        Set<BinaryNode> nodes = new TreeSet<>();
        nodes.add(getRoot());
        queue.add(getRoot());
        while (!queue.isEmpty()){
            BinaryNode node = queue.remove();
            if (node.left() != null){
                queue.add(node.left());
                nodes.add(node.left());
            }
            if (node.right() != null){
                queue.add(node.right());
                nodes.add(node.right());
            }
        }
        return nodes;

         */
    }

    private List<BinaryNode> inOrderTraversal(BinaryNode par){
        List<BinaryNode> list = new ArrayList<>();
        if (par == null){
            return list;
        }
        list.addAll(inOrderTraversal(par.left()));
        list.add(par);
        list.addAll(inOrderTraversal(par.right()));
        return list;
    }

    public int getWidth(){
        Queue<BinaryNode> queue;
        Queue<BinaryNode> tempQueue = new LinkedList<>();

        int maxWidth = 0;

        tempQueue.add(getRoot());

        while(!tempQueue.isEmpty()){
            if (maxWidth < tempQueue.size()){
                maxWidth = tempQueue.size();
            }
            queue = tempQueue;
            tempQueue = new LinkedList<>();
            while (!queue.isEmpty()){
                BinaryNode node = queue.remove();
                if (node.left() != null){
                    tempQueue.add(node.left());
                }
                if (node.right() != null){
                    tempQueue.add(node.right());
                }
            }
        }

        return maxWidth;
    }

    public BinaryNode getRoot() {
        return root;
    }

    public int getLevel(Comparable x){
        Queue<BinaryNode> queue;
        Queue<BinaryNode> tempQueue = new LinkedList<>();

        tempQueue.add(getRoot());

        int level = 0;

        while(!tempQueue.isEmpty()){
            queue = tempQueue;
            tempQueue = new LinkedList<>();
            while (!queue.isEmpty()){
                BinaryNode node = queue.remove();
                if (node.getValue() == x){
                    return level;
                }
                if (node.left() != null){
                    tempQueue.add(node.left());
                }
                if (node.right() != null){
                    tempQueue.add(node.right());
                }
            }
            level++;
        }
        return -1;
    }

    public BinaryNode remove(Comparable target) {
        if (root == null) {
            return null;
        }

        if (root.getValue().equals(target)) {
            if (root.left() == null && root.right() == null) {
                BinaryNode temp = root;
                root = null;
                return temp;
            } else if (root.left() == null) {
                BinaryNode temp = root;
                BinaryNode right = root.right();
                root.setRight(null);
                root = right;
                return temp;
            } else if (root.right() == null) {
                BinaryNode temp = root;
                BinaryNode left = root.left();
                root.setLeft(null);
                root = left;
                return temp;
            } else {
                BinaryNode successor = successor(root);
                swap(root, successor);
                if (root.right() == successor) {
                    root.setRight(successor.right());
                    successor.setRight(null);
                    return successor;
                }
                return remove(root.right(), target);
            }
        } else {
            return remove(root, target);
        }
    }


    public BinaryNode remove(BinaryNode par, Comparable target){
        BinaryNode parent = search(par, target);
        if (parent == null){
            return null;
        }

        boolean isLeft = parent.left() != null && parent.left().getValue().compareTo(target) == 0;

        BinaryNode node = isLeft ? parent.left() : parent.right();

        if (node.left() == null && node.right() == null){
            if (isLeft){
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        } else if (node.left() == null){
            if (isLeft){
                parent.setLeft(node.right());
            } else {
                parent.setRight(node.right());
            }
            node.setRight(null);
        } else if (node.right() == null){
            if (isLeft){
                parent.setLeft(node.left());
            } else {
                parent.setRight(node.left());
            }
            node.setLeft(null);
        } else {
            BinaryNode successor = successor(node);
            swap(successor, node);
            if (successor == node.right()){
                node.setRight(successor.right());
                successor.setRight(null);
                return successor;
            }
            return remove (node.right(), target);
        }
        return node;
    }

    protected BinaryNode successor(BinaryNode x){
        BinaryNode temp = x.right();
        while (temp.left() != null){
            temp = temp.left();
        }
        return temp;
    }

    protected void swap(BinaryNode x, BinaryNode y){
        Comparable value = x.getValue();
        x.setValue(y.getValue());
        y.setValue(value);
    }


    public BinaryNode search(BinaryNode par, Comparable target){
        if (par == null){
            return null;
        }
        if (par.left() != null && par.left().getValue().compareTo(target) == 0|| par.right() != null && par.right().getValue().compareTo(target) == 0){
            return par;
        } else if (par.getValue().compareTo(target) > 0){
            return search(par.left(), target);
        } else {
            return search(par.right(), target);
        }
    }

}
