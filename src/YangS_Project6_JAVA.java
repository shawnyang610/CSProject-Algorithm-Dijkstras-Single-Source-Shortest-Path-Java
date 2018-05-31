
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *CS700-34 Proj.6
 * Shawn Yang
 */
public class YangS_Project6_JAVA {

    public static void main(String[] args) {
        Scanner inFile = null;
        PrintWriter outFile1 = null;
        PrintWriter outFile2 = null;
        int numNodes, sourceNode, i, j, cost;
        DijkstraSSS dijkstra = null;
        
        if (args.length<3){
            System.out.println("Please retry and supply required file paths in the format of: input.txt output.txt output2.txt");
            System.exit(1);
        }
        try {
            inFile = new Scanner (new FileReader(args[0]));
            outFile1 = new PrintWriter(args[1]);
            outFile2 = new PrintWriter(args[2]);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
        
        numNodes =Integer.parseInt(inFile.next());
        sourceNode = Integer.parseInt(inFile.next());
        dijkstra = new DijkstraSSS(numNodes, sourceNode, outFile1, outFile2);
        while (inFile.hasNext()){
            i = Integer.parseInt(inFile.next());
            j = Integer.parseInt (inFile.next());
            cost = Integer.parseInt (inFile.next());
            dijkstra.loadCostMatrix(i, j, cost);
        }
        dijkstra.dijkstrasStart();
        
        inFile.close();
        outFile1.close();
        outFile2.close();
    }
    
}
