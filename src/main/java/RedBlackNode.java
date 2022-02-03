public class RedBlackNode extends BinaryNode{

    private int color;
    private RedBlackNode parent;

    public RedBlackNode(Comparable value) {
        super(value);
        color = 0;
        parent = null;
    }

    public RedBlackNode(Comparable value, int color){
        super(value);
        this.color = color;
        parent = null;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public RedBlackNode left() {
        return (RedBlackNode) super.left();
    }

    public RedBlackNode right() {
        return (RedBlackNode) super.right();
    }

    public void setLeft(RedBlackNode left) {
        super.setLeft(left);
    }

    public void setRight(RedBlackNode right) {
        super.setRight(right);
    }

    public RedBlackNode getParent() {
        return parent;
    }

    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return super.toString() + (parent != null ? "; Parent: " + parent.getValue() : "");
    }
}
