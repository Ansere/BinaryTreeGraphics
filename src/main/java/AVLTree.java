public class AVLTree extends BinaryTree {

    private AVLNode root;

    public AVLTree(){
        root = null;
    }

    public void add(Comparable x){
        if (root == null){
            root = new AVLNode(x);
        } else {
            add(root, x);
        }
    }

    private void add(AVLNode p, Comparable x){
        if (p == null){
            return;
        }
        if (p.getValue().compareTo(x) >= 0){
            if (p.left() == null){
                AVLNode n = new AVLNode(x);
                n.setParent(p);
                p.setLeft(n);
            } else {
                add(p.left(), x);
            }
        } else {
            if (p.right() == null){
                AVLNode n = new AVLNode(x);
                n.setParent(p);
                p.setRight(n);
            } else {
                add(p.right(), x);
            }
        }
        checkTree(root);
    }

    private void checkTree(AVLNode p){
        if (p == null){
            return;
        }
        if (p.getBalanceFactor() > 1){
            if (p.left().getBalanceFactor() > 1 || p.left().getBalanceFactor() < -1) {
                checkTree(p.left());
            }
        } else if (p.getBalanceFactor() < -1){
             if (p.right().getBalanceFactor() > 1 || p.right().getBalanceFactor() < -1) {
                checkTree(p.right());
            }
        }
        if (p.getBalanceFactor() > 1){
            if (root == p){
                root = p.left();
            }
            if (p.left().getBalanceFactor() > 0){
                leftLeftRotation(p);
            } else {
                leftRightRotation(p);
            }
        } else if (p.getBalanceFactor() < -1) {
            if (root == p){
                root = p.right();
            }
            if (p.right().getBalanceFactor() > 0){
                rightLeftRotation(p);
            } else {
                rightRightRotation(p);
            }
        } else {
            checkTree(p.left());
            checkTree(p.right());
        }
    }

    private void rightRightRotation(AVLNode p){
        AVLNode x = p.right();
        p.setRight(x.left());
        x.setLeft(p);
        if (x.left() != null){
            x.left().setParent(p);
        }
        x.setParent(p.getParent());
        p.setParent(x);
    }

    private void rightLeftRotation(AVLNode p){
        AVLNode x = p.right();
        p.setRight(x.left());
        x.left().setParent(p);
        x.left().setRight(x);
        x.setParent(x.left());
        x.setLeft(null);
        rightRightRotation(p);
    }

    private void leftLeftRotation(AVLNode p) {
        AVLNode x = p.left();
        p.setLeft(x.right());
        x.setRight(p);
        if (x.right() != null){
            x.right().setParent(p);
        }
        x.setParent(p.getParent());
        p.setParent(x);
    }

    private void leftRightRotation(AVLNode p){
        AVLNode x = p.left();
        p.setLeft(x.right());
        x.right().setParent(p);
        x.right().setLeft(x);
        x.setParent(x.right());
        x.setRight(null);
        rightRightRotation(p);
    }

    public AVLNode getRoot() {
        return root;
    }

    public AVLNode remove(){
        return null;
    }

}
