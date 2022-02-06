public class RedBlackTreeParented extends RedBlackTree{


    public RedBlackTreeParented(boolean b) {
        super(b);
    }

    public void add(Comparable x){
        if (root == null){
            root = new RedBlackNode(x, 1);
            System.out.println("Added " + x + " as root" );
        } else {
            add(root, x);
        }
    }

    private void add(RedBlackNode par, Comparable x){
        checkColorSwap(par);
        if (x.compareTo(par.getValue()) < 0){
            if (par.left() != null){
                add(par.left(), x);
            } else {
                RedBlackNode node = new RedBlackNode(x);
                par.setLeft(node);
                node.setParent(par);
                checkRotations(node);
                if (!silence) {
                    System.out.println("Added " + x + " under " + par.getValue());
                }
            }
        } else {
            if (par.right() != null){
                add(par.right(), x);
            } else {
                RedBlackNode node = new RedBlackNode(x);
                par.setRight(node);
                node.setParent(par);
                checkRotations(node);
                if (!silence){
                    System.out.println("Added " + x + " under " + par.getValue());
                }
            }
        }
    }

    private void checkColorSwap(RedBlackNode p){
        checkColorSwap(p, silence);
    }

    private void checkColorSwap(RedBlackNode p, boolean silence){
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
            if (!silence) {
                System.out.println("Color swap caused: (ignore if a non-method statement is below)");
            }
            checkRotations(p);
        }
    }

    private void checkRotations(RedBlackNode x){
        checkRotations(x, silence);
    }

    private void checkRotations(RedBlackNode x, boolean silence){
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
        boolean parLeft = g.left() == p;
        boolean xLeft = p.left() == x;

        if (parLeft ^ xLeft){ //outside-inside
            if (parLeft){
                leftRightRotation(g, p, x);
                if (!silence)
                    if (!silence){
                        System.out.println("Left-right #" + ++leftRightNum);
                        System.out.println("Grandparent: " + g);
                        System.out.println("Parent: " + p);
                        System.out.println("X: " + x);
                        System.out.println("Sibling: " +  g.left());
                    }
            } else {
                rightLeftRotation(g, p, x);
                if (!silence){
                    System.out.println("Right-left #" + ++rightLeftNum);
                    System.out.println("Grandparent: " + g);
                    System.out.println("Parent: " + p);
                    System.out.println("X: " + x);
                    System.out.println("Sibling: " +  g.left());
                }
            }
        } else { //outside-outside
            if (parLeft){
                leftLeftRotation(g, p);
                if (!silence){
                    System.out.println("Left-left #" + ++leftLeftNum);
                    System.out.println("Grandparent: " + g);
                    System.out.println("Parent: " + p);
                    System.out.println("X: " + x);
                    System.out.println("Sibling: " +  g.left());
                }
            } else {
                rightRightRotation(g, p);
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

    private void rightRightRotation(RedBlackNode g, RedBlackNode p){
        g.setRight(p.left());
        if (p.left() != null){
            p.left().setParent(g);
        }
        p.setLeft(g);
        RedBlackNode a = g.getParent();
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
        g.setLeft(p.right());
        if (p.right() != null) {
            p.right().setParent(g);
        }
        p.setRight(g);
        RedBlackNode a = g.getParent();
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
                    checkColor(successor);
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

    private RedBlackNode remove(RedBlackNode par, Comparable x){
        RedBlackNode parent = search(par, x);
        if (parent == null){
            return null;
        }

        boolean isLeft = parent.left() != null && parent.left().getValue().compareTo(x) == 0;

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

    protected void checkColor(RedBlackNode x){
        checkColor(x, true);
    }

    protected void checkColor(RedBlackNode x, boolean delete){
        if (x.getParent() == null){
            checkRoot();
            return;
        }
        boolean isLeft = x.getParent().left() == x;
        RedBlackNode s = isLeft ? x.getParent().right() : x.getParent().left();
        System.out.println(x);
        if (x.getColor() != 2 && (x.getColor() == 0 || x.left() != null && x.left().getColor() == 0 || x.right() != null && x.right().getColor() == 0)){
            simpleCaseDeletion(x, (x.left() == null ? x.right() : x.left()));
        } else if (x.getColor() >= 1){
            x.setColor(2);
            if (delete){
                x.setValue(null);
            }
            if (x.getParent().getColor() == 0){
                RedBlackNode p = x.getParent();
                if (!silence) {
                    System.out.println("Recoloring #" + ++recolor);
                    System.out.println("Parent: " + p);
                    System.out.println("Parent's left: " + p.left());
                    System.out.println("Parent's right: " + p.right());
                }
                recolor(p);
                if (!silence){
                    System.out.println("Recoloring caused: (ignore if a non-method statement is below)");
                }
                if (s != null){
                    if (s.right() != null && s.right().getColor() == 0){
                        checkRotations(s.right(), true);
                    } else if (s.left() != null && s.left().getColor() == 0){
                        checkRotations(s.left(), true);
                    }
                }
                checkColorSwap(p.getParent(), true);
            } else if (s != null && s.getColor() == 1){
                if (s.right() != null && s.right().getColor() == 0 && (isLeft || s.left() == null)){
                    if (isLeft){
                        if (!silence) {
                            System.out.println("rightRightRestructure #" + ++rightRightRestructure);
                            System.out.println("Parent: " + x.getParent());
                            System.out.println("Sibling: " + s);
                            System.out.println("R: " + s.right());
                        }
                        rightRightRestructure(x.getParent(), s);
                    } else {
                        if (!silence) {
                            System.out.println("leftRightRestructure #" + ++leftRightRestructure);
                            System.out.println("Parent: " + x.getParent());
                            System.out.println("Sibling: " + s);
                            System.out.println("R: " + s.right());
                        }
                        leftRightRestructure(x.getParent(), s, s.right());
                    }
                } else if (s.left() != null && s.left().getColor() == 0){
                    if (isLeft){
                        if (!silence) {
                            System.out.println("rightLeftRestructure #" + ++rightLeftRestructure);
                            System.out.println("Parent: " + x.getParent());
                            System.out.println("Sibling: " + s);
                            System.out.println("R: " + s.right());
                        }
                        rightLeftRestructure(x.getParent(), s, s.left());
                    } else {
                        if (!silence) {
                            System.out.println("leftLeftRestructure #" + ++leftLeftRestructure);
                            System.out.println("Parent: " + x.getParent());
                            System.out.println("Sibling: " + s);
                            System.out.println("R: " + s.right());
                        }
                        leftLeftRestructure(x.getParent(), s);
                    }
                } else {
                    RedBlackNode p = x.getParent();
                    if (!silence) {
                        System.out.println("Recoloring #" + ++recolor);
                        System.out.println("Parent: " + p);
                        System.out.println("Parent's left: " + p.left());
                        System.out.println("Parent's right: " + p.right());
                    }
                    recolor(p);
                    checkColor(p, false);
                }
            } else if (s.getColor() == 0){
                if (isLeft){
                    adjustmentLeft(x.getParent(), s);
                } else {
                    adjustmentRight(x.getParent(), s);
                }
            }
        }
    }

    private void adjustmentLeft(RedBlackNode p, RedBlackNode s){
        if (!silence) {
            System.out.println("adjustmentLeft #" + ++adjustmentLeft);
            System.out.println("Parent: " + p);
            System.out.println("Sibling: " + s);
        }
        rightRightRotation(p, s);
        recolor(p);
    }

    private void adjustmentRight(RedBlackNode p, RedBlackNode s){
        if (!silence) {
            System.out.println("adjustmentRight #" + ++adjustmentRight);
            System.out.println("Parent: " + p);
            System.out.println("Sibling: " + s);
        }
        leftLeftRotation(p, s);
        recolor(p);
    }

    private void recolor(RedBlackNode p){
        p.setColor(p.getColor() + 1);
        RedBlackNode temp = p.right();
        if (temp != null){
            if (temp.getValue() == null){
                p.right().setParent(null);
                p.setRight(null);
            } else {
                temp.setColor(temp.getColor() - 1);
            }
        }
        temp = p.left();
        if (temp != null){
            if (temp.getValue() == null){
                p.left().setParent(null);
                p.setLeft(null);
            } else {
                temp.setColor(temp.getColor() - 1);
            }
        }
    }

    private void simpleCaseDeletion(RedBlackNode x, RedBlackNode c){
        x.setLeft(null);
        x.setRight(null);
        if (c != null) {
             c.setColor(1);
        }
        if (x.getParent().left() == x) {
            x.getParent().setLeft(c);
        } else {
            x.getParent().setRight(c);
        }
        c.setParent(x.getParent());
    }

    private void rightRightRestructure(RedBlackNode p, RedBlackNode s){
        p.setRight(s.left());
        if (s.left() != null){
            s.left().setParent(p);
        }
        s.setLeft(p);
        if (p.getParent().left() == p){
            p.getParent().setLeft(s);
        } else {
            p.getParent().setRight(s);
        }
        s.setParent(p.getParent());
        p.setParent(s);
        s.setColor(2);
        p.setColor(0);
        colorSwap(s);
        p.left().setColor(1);
        if (p.left().getValue() == null){
            p.left().setParent(null);
            p.setLeft(null);
        }
    }

    private void rightLeftRestructure(RedBlackNode p, RedBlackNode s, RedBlackNode r){
        s.setLeft(r.right());
        if (r.right() != null){
            r.right().setParent(s);
        }
        r.setRight(s);
        s.setParent(r);
        r.setColor(1);
        s.setColor(0);
        p.setRight(r);
        r.setParent(p);
        rightRightRestructure(p, s);
    }

    private void leftLeftRestructure(RedBlackNode p, RedBlackNode s){
        p.setLeft(s.right());
        if (s.right() != null){
            s.right().setParent(p);
        }
        s.setRight(p);
        if (p.getParent().left() == p){
            p.getParent().setLeft(s);
        } else {
            p.getParent().setRight(s);
        }
        s.setParent(p.getParent());
        p.setParent(s);
        s.setColor(2);
        p.setColor(0);
        colorSwap(s);
        p.right().setColor(1);
        if (p.right().getValue() == null){
            p.right().setParent(null);
            p.setRight(null);
        }
    }

    private void leftRightRestructure(RedBlackNode p, RedBlackNode s, RedBlackNode r){
        s.setRight(r.left());
        if (r.left() != null){
            r.left().setParent(s);
        }
        r.setLeft(s);
        s.setParent(r);
        r.setColor(1);
        s.setColor(0);
        p.setLeft(r);
        r.setParent(p);
        leftLeftRestructure(p, s);
    }

}
