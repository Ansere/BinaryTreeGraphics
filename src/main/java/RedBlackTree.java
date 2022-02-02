public class RedBlackTree extends BinaryTree{

    private RedBlackNode root;
    private boolean silence;

    private int colorSwapNum = 0;
    private int leftLeftNum = 0;
    private int leftRightNum = 0;
    private int rightLeftNum = 0;
    private int rightRightNum = 0;

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
                if (!silence) {
                    System.out.println("Added " + x + " under " + p.getValue());
                }
                checkRotations(a, g, p, node);
                if (!silence)
                    System.out.println();
            }
        } else {
            if (p.right() != null){
                add(a, g, p, p.right(), x);
            } else {
                RedBlackNode node = new RedBlackNode(x);
                p.setRight(node);
                if (!silence){
                    System.out.println("Added " + x + " under " + p.getValue());
                }
                checkRotations(a, g, p, node);
                if (!silence)
                    System.out.println();
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
    
    private void checkRoot(){
        if (root.getColor() == 0){
            root.setColor(1);
        }
    }

    private void colorSwap(RedBlackNode par){
        par.setColor(par.getColor() - 1);
        par.left().setColor(par.left().getColor() + 1);
        par.right().setColor(par.right().getColor() + 1);
    }

    public RedBlackNode getRoot(){
        return root;
    }

    /*
    public RedBlackNode remove(Comparable x){
        if (root == null){
            return null;
        } else if (root.getValue() == x){
            if (root.left() == null && root.right() == null){
                RedBlackNode temp = root;
                root = null;
                return temp;
            } else if (root.right() == null){
                RedBlackNode temp = root;
                root = root.left();
                temp.setLeft(null);
                return temp;
            } else if (root.left() == null){
                RedBlackNode temp = root;
                root = root.right();
                temp.setRight(null);
                return temp;
            } else {
            }
        } else {
            return remove(root, x);
        }
    }

    private RedBlackNode remove(RedBlackNode par, Comparable x){
        return null;
    }

    /*
    
    private void add(RedBlackNode par, Comparable x){
        checkColorSwap(par);
        if (x.compareTo(par.getValue()) < 0){
            if (par.left() != null){
                add(par.left(), x);
            } else {
                RedBlackNode node = new RedBlackNode(x);
                par.setLeft(node);
                node.setParent(par);
                if (!silence) {
                    System.out.println("Added " + x + " under " + par.getValue());
                }
                checkRotations(node);
                if (!silence)
                    System.out.println();
            }
        } else {
            if (par.right() != null){
                add(par.right(), x);
            } else {
                RedBlackNode node = new RedBlackNode(x);
                par.setRight(node);
                node.setParent(par);
                if (!silence){
                    System.out.println("Added " + x + " under " + par.getValue());
                }
                checkRotations(node);
                if (!silence)
                    System.out.println();
            }
        }
    }


    private void checkColorSwap(RedBlackNode par){
        if (par.getColor() == 0 || par.left() == null || par.right() == null){
            return;
        }
        if (par.left().getColor() == 0 && par.right().getColor() == 0){
            if (!silence){
                System.out.println("Color swap!");
            }
            colorSwap(par);
            checkRoot();
            checkRotations(par);
        }
    }

    private void checkRotations(RedBlackNode x){
        RedBlackNode p = x.getParent();
        if (p == null){
            return;
        }
        RedBlackNode g = p.getParent();
        if (g == null){
            return;
        }
        if (x.getColor() == 1 || p.getColor() == 1){
            return;
        }
        if (!silence) {
            System.out.println(g);
            System.out.println(p);
            System.out.println(x);
        }
        boolean parLeft = g.left() == p;
        boolean xLeft = p.left() == x;

        if (parLeft ^ xLeft){ //outside-inside
            if (parLeft){
                leftRightRotation(g, p, x);
                if (!silence)
                    System.out.println("Left-right!");
            } else {
                rightLeftRotation(g, p, x);
                if (!silence)
                    System.out.println("Right-left!");
            }
        } else { //outside-outside
            if (parLeft){
                leftLeftRotation(g, p);
                if (!silence)
                    System.out.println("Left-left!");
            } else {
                rightRightRotation(g, p);
                if (!silence)
                    System.out.println("Right-right!");
            }
        }
    }

    private void rightRightRotation(RedBlackNode g, RedBlackNode p){
        if (!silence) {
            System.out.println(g);
            System.out.println(p);
        }
        g.setRight(p.left());
        if (p.left() != null){
            p.left().setParent(g);
        }
        p.setLeft(g);
        p.setParent(a);
        if (a == null){
            root = p;
        } else if (a.left() == g){
            a.setLeft(p);
        } else {
            a.setRight(p);
        }
        g.setParent(p);
        p.setColor(p.getColor() + 1);
        g.setColor(g.getColor() - 1);
    }

    private void leftLeftRotation(RedBlackNode g, RedBlackNode p){
        if (!silence) {
            System.out.println(g);
            System.out.println(p);
        }
        g.setLeft(p.right());
        if (p.right() != null) {
            p.right().setParent(g);
        }
        p.setRight(g);
        p.setParent(a);
        if (a == null){
            root = p;
        } else if (a.left() == g){
            a.setLeft(p);
        } else {
            a.setRight(p);
        }
        g.setParent(p);
        p.setColor(p.getColor() + 1);
        g.setColor(g.getColor() - 1);
    }

    private void leftRightRotation(RedBlackNode g, RedBlackNode p, RedBlackNode x){
        if (!silence) {
            System.out.println(g);
            System.out.println(p);
            System.out.println(x);
        }
        p.setRight(x.left());
        if (x.left() != null){
            x.left().setParent(p);
        }
        x.setLeft(p);
        x.setParent(g);
        g.setLeft(x);
        p.setParent(x);
        leftLeftRotation(g, x);
    }

    private void rightLeftRotation(RedBlackNode g, RedBlackNode p, RedBlackNode x){
        if (!silence) {
            System.out.println(g);
            System.out.println(p);
            System.out.println(x);
        }
        p.setLeft(x.right());
        if (x.right() != null){
            x.right().setParent(p);
        }
        x.setRight(p);
        x.setParent(g);
        g.setRight(x);
        p.setParent(x);
        rightRightRotation(g, x);
    }


     */
}
