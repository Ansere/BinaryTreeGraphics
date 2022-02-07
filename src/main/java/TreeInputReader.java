import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TreeInputReader {

    private Scanner sc;
    private ArrayList<Boolean> instructionList;
    private ArrayList<ArrayList<Integer>> lists;
    private String treeType;
    private List<String> treeTypes = Arrays.asList("bin", "rbt", "avl");

    public TreeInputReader(File f) throws IOException, FileTooShortException, IllegalTreeTypeException {
        sc = new Scanner(f);
        long numLines = Files.lines(Paths.get(f.getAbsolutePath())).count();
        if (numLines < 3){
            throw new FileTooShortException(numLines);
        }
        instructionList = new ArrayList<>();
        lists = new ArrayList<>();
        readData();
    }

    private void readData() throws IllegalTreeTypeException {
        String treeType = sc.nextLine();
        if (!treeTypes.contains(treeType)){
            throw new IllegalTreeTypeException(treeType);
        }
        this.treeType = treeType;
        while (sc.hasNextLine()){
            instructionList.add(sc.nextLine().equals("insert"));
            String numbers = sc.nextLine();
            String[] nums = numbers.split(" ");
            ArrayList<Integer> tempList = new ArrayList<>();
            for (String k : nums){
                tempList.add(Integer.parseInt(k));
            }
            lists.add(tempList);
        }
    }

    public String getTreeType(){
        return treeType;
    }

    public ArrayList<Boolean> getInstructionList() {
        return instructionList;
    }

    public ArrayList<ArrayList<Integer>> getLists(){
        return lists;
    }
}

class FileTooShortException extends Exception{

    public FileTooShortException(long numLines){
        super("Number of lines in file too short! Expected at least 3, but got " + numLines);
    }

}

class IllegalTreeTypeException extends Exception {

    public IllegalTreeTypeException(String tree){
        super("Illegal tree type. Expected bin or rbt, but got " + tree);
    }

}
