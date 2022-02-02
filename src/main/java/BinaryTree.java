import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

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
        if (par.getValue().compareTo(x.getValue()) < 0){
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

    public Set<BinaryNode> getNodes(){
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

}
