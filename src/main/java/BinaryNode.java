public class BinaryNode implements Comparable{

    private Comparable value;
    private BinaryNode left;
    private BinaryNode right;

    public BinaryNode(Comparable value){
        this.value = value;
    }

    public Comparable getValue() {
        return value;
    }

    public void setValue(Comparable x){
        value = x;
    }

    public BinaryNode left() {
        return left;
    }

    public BinaryNode right() {
        return right;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }

    public String toString(){
        return "Value: " + value +
                (left != null ? "; Left: " + left.getValue(): "") +
                (right != null ? "; Right: " + right.getValue(): "");
    }

    @Override
    public int compareTo(Object o) {
        BinaryNode on = (BinaryNode) o;
        return value.compareTo(on.value);
    }
}
