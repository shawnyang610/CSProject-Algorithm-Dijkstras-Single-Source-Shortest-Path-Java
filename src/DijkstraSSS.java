
import java.io.PrintWriter;

public class DijkstraSSS {
    private int numNodes, sourceNode, minNode, currentNode, newCost;
    private int[][] costMatrix;
    private int[] fatherAry;
    private int[] toDoAry;
    private int[] bestCostAry;
    private PrintWriter outFile1 = null;
    private PrintWriter outFile2 = null;
    public DijkstraSSS(int inNumNodes, int inSourceNode, PrintWriter inOutFile1, PrintWriter inOutFile2){
        outFile1 = inOutFile1;
        outFile2 = inOutFile2;
        numNodes = inNumNodes;
        sourceNode = inSourceNode;
        costMatrix = new int[numNodes+1][numNodes+1];
        fatherAry = new int [numNodes+1];
        toDoAry = new int [numNodes+1];
        bestCostAry= new int [numNodes+1];
        for (int i=1; i<numNodes+1; i++ ){
            fatherAry[i]=i;
            toDoAry[i]=1;
            bestCostAry[i]=99999;
            for (int j=1; j<numNodes+1; j++){
                if (i==j)
                    costMatrix[i][j]=0;
                else
                    costMatrix[i][j]=99999;
            }
        }
    }
    
    public void loadCostMatrix (int i, int j, int Cost){
        costMatrix [i][j]=Cost;
    }
    
    public void dijkstrasStart(){
        setBestCostAry(sourceNode);
        setFatherAry(sourceNode);
        setToDoAry(sourceNode);
        while (!isAllFinishedInToDoAry()){
            minNode = findMinNode();
            markMinNode(minNode);
            debugPrint();
            compareAndUpdateLowestCost();
        }
        printAllShortestPath();
    }
    
    private void compareAndUpdateLowestCost(){
        for (int i=1; i<numNodes+1; i++) {
            if (toDoAry[i]==1){
                currentNode=i;
                newCost=computeCost(minNode, currentNode);
                if (newCost<bestCostAry[currentNode]){
                    changeCost(currentNode, newCost);
                    changeFather(currentNode, minNode);
                    debugPrint();
                }
            }
        }
    }
    
    private void setBestCostAry(int inSourceNode){
        for (int i=1; i<numNodes+1; i++){
            bestCostAry[i] = costMatrix[inSourceNode][i];
        }
    }
    
    private void setFatherAry (int inSourceNode){
        for (int i=1; i< numNodes+1; i++){
            fatherAry[i]=inSourceNode;
        }
    }
    
    private void setToDoAry (int inSourceNode){
            toDoAry[inSourceNode]=0;
    }
    
    private int findMinNode(){
        int minIndex = -1;
        for (int i=1; i< numNodes+1; i++){
            if (toDoAry[i]==1){
                minIndex=i;
                break;
            }
        }
        for (int i=1; i<numNodes+1; i++){
            if (toDoAry[i]==1){
                if (bestCostAry[i]<bestCostAry[minIndex]){
                    minIndex=i;
                }
            }
        }
        if (minIndex == -1){
            System.out.println("Error, no more node in toDoAry[i] has value 1");
            System.exit(1);
        }
        
        return minIndex;
    }
    
    private int computeCost (int inMinNode, int inCurrentNode) {
        return (bestCostAry[inMinNode]+costMatrix[inMinNode][inCurrentNode]);
    }
    
    private void markMinNode (int inMinNode){
        toDoAry [inMinNode]=0;
    }
    
    private void changeFather (int inCurrentNode, int inMinNode){
        fatherAry[inCurrentNode]=inMinNode;
    }
    
    private void changeCost (int inCurrentNode, int inNewCost){
        bestCostAry[inCurrentNode]=inNewCost;
    }
    
    private boolean isAllFinishedInToDoAry (){
        boolean status = true;
        for (int i=1; i<numNodes+1; i++){
            if (toDoAry[i]==1){
                status = false;
            }
        }
        return status;
    }
    
    private void printAllShortestPath(){
        outFile1.println("===========================================================");
        outFile1.println("There are "+numNodes+" nodes in the input graph.");
        outFile1.println("==========================================================="); 

        outFile1.println("Source node =" + sourceNode);
        outFile1.println("The shortest paths are:" );
        int temp;
        for (int i = 1; i < numNodes + 1; i++) {
                temp = i;
                outFile1.print("The path from " + sourceNode + " to " + i + " is: ");
                printShortestPath(i);
                outFile1.println();
        }
    }
    
    private void printShortestPath (int inNode){
        int temp = inNode;
        outFile1.print(inNode);
        while (temp != sourceNode){
            temp = fatherAry[temp];
            outFile1.print(" <-- "+temp);
        }
        outFile1.println(" cost = "+bestCostAry[inNode]);
    }
      
    private void debugPrint(){
        outFile2.println("-----------------------------------debug log-----------------------------------");
        outFile2.println("sourceNode: " + sourceNode);

        outFile2.print("fatherAry: ");
        for (int i = 1; i < numNodes + 1; i++) {
                outFile2.print(fatherAry[i] + " ");
        }
        outFile2.println();

        outFile2.print("bestCostAry: ");
        for (int i = 1; i < numNodes + 1; i++) {
                outFile2.print(bestCostAry[i] + " ");	
        }
        outFile2.println();
        outFile2.print("toDoAry: ");
        for (int i = 1; i < numNodes + 1; i++) {
                outFile2.print(toDoAry[i] + " ");	
        }
        outFile2.println();
    }
      
}
