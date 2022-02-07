public class RedBlackTree extends BinaryTree{

    protected RedBlackNode root;
    protected boolean silence;

    protected int colorSwapNum = 0;
    protected int leftLeftNum = 0;
    protected int leftRightNum = 0;
    protected int rightLeftNum = 0;
    protected int rightRightNum = 0;
    protected int rightRightRestructure = 0;
    protected int rightLeftRestructure = 0;
    protected int leftRightRestructure = 0;
    protected int leftLeftRestructure = 0;
    protected int recolor = 0;
    protected int adjustmentLeft = 0;
    protected int adjustmentRight = 0;

    public RedBlackTree(boolean s){
        root = null;
        silence = s;
    }

    public RedBlackTree(){
        this(true);
    }

    public void add(Comparable... x){
        for (Comparable c : x){
            add(c);
        }
    }

    @Override
    public void add(Comparable x) {
        if (root == null){
            root = new RedBlackNode(x);
            checkRoot();
        } else {
            add(null, null, null, root, x);
        }
    }

    private void add(RedBlackNode s, RedBlackNode a, RedBlackNode g, RedBlackNode p, Comparable x){
        checkColorSwap(s, a, g, p);
        if (x.compareTo(p.getValue()) < 0){
            if (p.left() != null){
                add(a, g, p, p.left(), x);
            } else {
                RedBlackNode node = new RedBlackNode(x);
                p.setLeft(node);
                checkRotations(a, g, p, node);
                if (!silence) {
                    System.out.println("Added " + x + " under " + p.getValue());
                    System.out.println();
                }
            }
        } else {
            if (p.right() != null){
                add(a, g, p, p.right(), x);
            } else {
                RedBlackNode node = new RedBlackNode(x);
                p.setRight(node);
                checkRotations(a, g, p, node);
                if (!silence) {
                    System.out.println("Added " + x + " under " + p.getValue());
                    System.out.println();
                }
            }
        }
    }
    private void checkColorSwap(RedBlackNode s, RedBlackNode a, RedBlackNode g, RedBlackNode p){
        if (p.getColor() == 0 || p.left() == null || p.right() == null){
            return;
        }
        if (p.left().getColor() == 0 && p.right().getColor() == 0){
            if (!silence){
                System.out.println("Color swap #" + ++colorSwapNum);
                System.out.println("Parent: " + p);
                System.out.println("Left Child: " + p.left());
                System.out.println("Right Child: " + p.right());
            }
            colorSwap(p);
            checkRoot();
            checkRotations(s, a, g, p);
            System.out.println();
        }
    }

    private void checkRotations(RedBlackNode a, RedBlackNode g, RedBlackNode p, RedBlackNode x){
        if (p == null){
            return;
        }
        if (g == null){
            return;
        }
        if (x.getColor() == 1 || p.getColor() == 1){
            return;
        }
        boolean parLeft = g.left() == p;
        boolean xLeft = p.left() == x;

        if (parLeft ^ xLeft){ //outside-inside
            if (parLeft){
                leftRightRotation(a, g, p, x);
                if (!silence) {
                    System.out.println("Left-right #" + ++leftRightNum);
                    System.out.println("Grandparent: " + g);
                    System.out.println("Parent: " + p);
                    System.out.println("X: " + x);
                    System.out.println("Sibling: " + g.right());
                }
            } else {
                rightLeftRotation(a, g, p, x);
                if (!silence) {
                    System.out.println("Right-left #" + ++rightLeftNum);
                    System.out.println("Grandparent: " + g);
                    System.out.println("Parent: " + p);
                    System.out.println("X: " + x);
                    System.out.println("Sibling: " + g.left());
                }

            }
        } else { //outside-outside
            if (parLeft){
                leftLeftRotation(a, g, p);
                if (!silence) {
                    System.out.println("Left-left #" + ++leftLeftNum);
                    System.out.println("Grandparent: " + g);
                    System.out.println("Parent: " + p);
                    System.out.println("X: " + x);
                    System.out.println("Sibling: " +  g.right());
                }
            } else {
                rightRightRotation(a, g, p);
                if (!silence){
                    System.out.println("Right-right #" + ++rightRightNum);
                    System.out.println("Grandparent: " + g);
                    System.out.println("Parent: " + p);
                    System.out.println("X: " + x);
                    System.out.println("Sibling: " +  g.left());
                }
            }
        }
    }

    private void rightRightRotation(RedBlackNode a, RedBlackNode g, RedBlackNode p){
        g.setRight(p.left());
        p.setLeft(g);
        if (a == null){
            root = p;
        } else if (a.left() == g){
            a.setLeft(p);
        } else {
            a.setRight(p);
        }
        p.setColor(p.getColor() + 1);
        g.setColor(g.getColor() - 1);
    }

    private void leftLeftRotation(RedBlackNode a, RedBlackNode g, RedBlackNode p){
        g.setLeft(p.right());
        p.setRight(g);
        if (a == null){
            root = p;
        } else if (a.left() == g){
            a.setLeft(p);
        } else {
            a.setRight(p);
        }
        p.setColor(p.getColor() + 1);
        g.setColor(g.getColor() - 1);
    }

    private void leftRightRotation(RedBlackNode a, RedBlackNode g, RedBlackNode p, RedBlackNode x){
        p.setRight(x.left());
        x.setLeft(p);
        g.setLeft(x);
        leftLeftRotation(a, g, x);
    }

    private void rightLeftRotation(RedBlackNode a, RedBlackNode g, RedBlackNode p, RedBlackNode x){
        p.setLeft(x.right());
        x.setRight(p);
        g.setRight(x);
        rightRightRotation(a, g, x);
    }
    
    protected void checkRoot(){
        if (root != null && root.getColor() != 1){
            root.setColor(1);
        }
    }

    protected void colorSwap(RedBlackNode par){
        par.setColor(par.getColor() - 1);
        par.left().setColor(par.left().getColor() + 1);
        par.right().setColor(par.right().getColor() + 1);
    }

    public RedBlackNode getRoot(){
        return root;
    }

    public RedBlackNode remove(Comparable x){
        if (root == null){
            return null;
        } else if (root.getValue() == x){
            RedBlackNode temp = root;
            if (root.left() == null && root.right() == null){
                root = null;
            } else if (root.right() == null){
                root = root.left();
                temp.setLeft(null);
            } else if (root.left() == null){
                root = root.right();
                temp.setRight(null);
            } else {
                RedBlackNode successor = successor(root);
                swap(successor, root);
                if (successor == root.right()){
                    root.setRight(successor.right());
                    successor.setRight(null);
                    if (!silence)
                        System.out.println("Removed " + x);
                    return successor;
                } else {
                    return remove(root.right(), successor.getValue());
                }
            }
            if (!silence)
                System.out.println("Removed " + x);
            checkRoot();
            return temp;
        } else {
            return remove(root, x);
        }
    }

    protected RedBlackNode successor(RedBlackNode x){
        return ((RedBlackNode) super.successor(x));
    }

    private RedBlackNode remove(RedBlackNode par, Comparable x){
        RedBlackNode parent = search(par, x);
        if (parent == null){
            return null;
        }
        boolean isLeft = parent.left() != null && parent.left() == x;

        System.out.println(isLeft);

        RedBlackNode node = isLeft ? parent.left() : parent.right();

        if (node.left() == null || node.right() == null){
            checkColor(node);
        } else {
            RedBlackNode successor = successor(node);
            swap(successor, node);
            if (successor == node.right()){
                node.setRight(successor.right());
                successor.setRight(null);
                return successor;
            }
            return remove(node.right(), x);
        }
        System.out.println("Removed " + x);
        return node;
    }

    protected void checkColor(RedBlackNode node) {

    }

    protected RedBlackNode search(RedBlackNode par, Comparable x){
        return ((RedBlackNode) super.search(par, x));
    }
    /*




     */
}
