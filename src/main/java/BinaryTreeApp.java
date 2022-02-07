import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BinaryTreeApp extends Application {

    private static final double HEIGHT = 960;
    private static final double WIDTH = 1920;

    private static double CELL_HEIGHT;

    private static double VERT_SPACING;
    private static double HORIZ_SPACING;

    private static boolean preferZeroHoriz = true;
    private static boolean preferZeroVert = true;

    private static BinaryTree tree;

    private static Stage primaryStage;

    private static ArrayList<Integer> customNumList = new ArrayList<>();
    private static int index = 0;

    private static ArrayList<Boolean> instructionType = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
    private static int listIndex = 0;
    private static int inListIndex = 0;

    private static Set<Integer> set;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        this.primaryStage = primaryStage;

        FileChooser fc = new FileChooser();
        //fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Input files", ".in"));
        File selectedFile = fc.showOpenDialog(primaryStage);

        try {
            TreeInputReader ti = new TreeInputReader(selectedFile);
            instructionType = ti.getInstructionList();
            lists = ti.getLists();
            if (ti.getTreeType().equals("rbt")){
                tree = new RedBlackTreeParented(false);
            } else if (ti.getTreeType().equals("bin")) {
                tree = new BinaryTree();
            } else {
                tree = new AVLTree();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


        set = new LinkedHashSet<>();

        if (lists.size() == 0){
            addRandomValue();
        } else {
            doInstruction();
        }
        refreshDisplay();


        primaryStage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT){
                if (lists.size() == 0){
                    addRandomValue();
                } else {
                    doInstruction();
                }
                refreshDisplay();
            }
        });

        primaryStage.show();

    }

    private static void doInstruction() {
        if (listIndex == lists.size()){
            return;
        }
        if (instructionType.get(listIndex)){
            System.out.println("Adding: " + lists.get(listIndex).get(inListIndex));
            tree.add(lists.get(listIndex).get(inListIndex));
            System.out.println();
        } else {
            System.out.println("Removing: " + lists.get(listIndex).get(inListIndex));
            tree.remove(lists.get(listIndex).get(inListIndex));
            System.out.println();
        }
        inListIndex++;
        if (inListIndex == lists.get(listIndex).size()) {
            listIndex++;
            inListIndex = 0;
        }
    }

    private static double rowToY(int row){
        return row * (CELL_HEIGHT + VERT_SPACING) + CELL_HEIGHT /2;
    }

    private static double colToX(int col){
        return col * (CELL_HEIGHT + HORIZ_SPACING) + CELL_HEIGHT /2;
    }

    public static void addRandomValue(){
        Integer h = null;
        while (h == null || set.contains(h)){
            h = (int) (Math.random() * 100);
        }
        System.out.println("Value Inputted: " + h);
        set.add(h);
        tree.add(h);
    }

    public static void refreshDisplay(){
        List<BinaryNode> nodeList = tree.getNodes();

        int treeHeight = tree.getHeight();
        int treeSize = tree.getSize();

        CELL_HEIGHT = preferZeroHoriz && preferZeroVert ? Math.min(HEIGHT / (treeHeight + 1), WIDTH / treeSize) : Math.min(HEIGHT / (treeHeight + 1) * 0.6, WIDTH / treeSize * 0.6);

        VERT_SPACING = preferZeroVert ? 0 : (HEIGHT - CELL_HEIGHT * (treeHeight + 1))/treeHeight;
        HORIZ_SPACING = preferZeroHoriz ? 0 : (WIDTH - CELL_HEIGHT * (treeSize))/(treeSize - 1);

        AnchorPane ap = new AnchorPane();
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(tree.getRoot());
        while (!queue.isEmpty()){
            BinaryNode node = queue.remove();
            if (node.left() != null){
                ap.getChildren().add(new Line(colToX(nodeList.indexOf(node)), rowToY(tree.getLevel(node.getValue())), colToX(nodeList.indexOf(node.left())), rowToY(tree.getLevel(node.left().getValue()))));
                queue.add(node.left());
            }
            if (node.right() != null){
                ap.getChildren().add(new Line(colToX(nodeList.indexOf(node)), rowToY(tree.getLevel(node.getValue())), colToX(nodeList.indexOf(node.right())), rowToY(tree.getLevel(node.right().getValue()))));
                queue.add(node.right());
            }
        }

        int columnIndex = 0;
        for (BinaryNode node : nodeList){
            StackPane sp = new StackPane();
            Circle c = new Circle(CELL_HEIGHT/2, Color.WHITE);
            c.setStroke(Color.BLACK);
            Text t = new Text(node.getValue() + " ");
            sp.getChildren().addAll(c, t);
            if (node instanceof RedBlackNode){
                Paint color = ((RedBlackNode) node).getColor() == 0 ? Color.RED : Color.BLACK;
                c.setStroke(color);
                if (((RedBlackNode) node).getColor() == 2){
                    c.setStrokeWidth(5);
                }
                t.setFill(color);
            } else if (node instanceof AVLNode){
                Text bf = new Text(((AVLNode) node).getBalanceFactor() + "");
                sp.getChildren().add(bf);
                StackPane.setAlignment(bf, Pos.TOP_CENTER);
            }
            sp.setLayoutX(colToX(columnIndex++) - CELL_HEIGHT/2.);
            sp.setLayoutY(rowToY(tree.getLevel(node.getValue())) - CELL_HEIGHT/2.);
            ap.getChildren().add(sp);
        }

        Scene s = new Scene(ap, WIDTH, HEIGHT);
        s.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT){
                if (lists.size() == 0){
                    addRandomValue();
                } else {
                    doInstruction();
                }
                refreshDisplay();
            }
        });
        primaryStage.setScene(s);
        primaryStage.show();
    }
    /*

     public static void refreshDisplay1(){
        Set<BinaryNode> nodeSet = tree1.getNodes();

        int treeHeight = tree1.getHeight();
        int treeSize = tree1.getSize();

        CELL_HEIGHT = preferZeroHoriz && preferZeroVert ? Math.min(HEIGHT / (treeHeight + 1), WIDTH / treeSize) : Math.min(HEIGHT / (treeHeight + 1) * 0.6, WIDTH / treeSize * 0.6);

        VERT_SPACING = preferZeroVert ? 0 : (HEIGHT - CELL_HEIGHT * (treeHeight + 1))/treeHeight;
        HORIZ_SPACING = preferZeroHoriz ? 0 : (WIDTH - CELL_HEIGHT * (treeSize))/(treeSize - 1);

        AnchorPane ap = new AnchorPane();
        Queue<BinaryNode> queue = new LinkedList<>();
        List<BinaryNode> nodeList = new ArrayList<>(nodeSet);
        queue.add(tree1.getRoot());
        while (!queue.isEmpty()){
            BinaryNode node = queue.remove();
            if (node.left() != null){
                ap.getChildren().add(new Line(colToX(nodeList.indexOf(node)), rowToY(tree1.getLevel(node.getValue())), colToX(nodeList.indexOf(node.left())), rowToY(tree1.getLevel(node.left().getValue()))));
                queue.add(node.left());
            }
            if (node.right() != null){
                ap.getChildren().add(new Line(colToX(nodeList.indexOf(node)), rowToY(tree1.getLevel(node.getValue())), colToX(nodeList.indexOf(node.right())), rowToY(tree1.getLevel(node.right().getValue()))));
                queue.add(node.right());
            }
        }

        int columnIndex = 0;
        for (BinaryNode node : nodeSet){
            Paint color = ((RedBlackNode) node).getColor() == 0 ? Color.RED : Color.BLACK;
            StackPane sp = new StackPane();
            Circle c = new Circle(CELL_HEIGHT/2, Color.WHITE);
            c.setStroke(color);
            Text t = new Text(node.getValue() + " ");
            t.setFill(color);
            sp.getChildren().addAll(c, t);
            sp.setLayoutX(colToX(columnIndex++) - CELL_HEIGHT/2.);
            sp.setLayoutY(rowToY(tree1.getLevel(node.getValue())) - CELL_HEIGHT/2.);
            ap.getChildren().add(sp);
        }

        Scene s = new Scene(ap, WIDTH, HEIGHT);
        s.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT){
                addValue();
                refreshDisplay();
                refreshDisplay1();
            }
        });
        secondaryStage.setScene(s);
        secondaryStage.show();
    }

     */

}
