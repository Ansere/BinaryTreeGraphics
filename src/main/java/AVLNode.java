public class AVLNode extends BinaryNode{

    private AVLNode parent;

    public AVLNode(Comparable value) {
        super(value);
        parent = null;
    }

    public int getBalanceFactor(){
        return getHeight(left()) - getHeight(right());
    }

    private int getHeight(BinaryNode x){
        if (x == null)
            return -1;
        return 1 + Math.max(getHeight(x.left()), getHeight(x.right()));
    }

    public AVLNode left(){
        return ((AVLNode) super.left());
    }

    public AVLNode right(){
        return ((AVLNode) super.right());
    }

    public AVLNode getParent(){
        return parent;
    }

    public void setParent(AVLNode p){
        parent = p;
    }

}
