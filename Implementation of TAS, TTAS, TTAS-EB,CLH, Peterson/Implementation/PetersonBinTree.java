import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PetersonBinTree {
	
	/* using ThreadLocal to locally assign each thread a unique id
	 * credits to http://javapapers.com/core-java/threadlocal/*/
	final private ThreadLocal<Integer> threadID = new ThreadLocal<Integer>(){
        final private AtomicInteger nextid = new AtomicInteger(0);

        protected Integer initialValue(){
            return nextid.getAndIncrement();
        }
    };
    
    private int height;
    private int numThreads;
    private Peterson peteTree[];
    
    
    /*private Peterson ConstructTree(Peterson node, int height){
    	if(height == 0){
    		node = new Peterson(id++);
    		return node;
    	}
    	node = new Peterson(-1);
    	node.left = ConstructTree(node.left, height - 1);
    	node.right = ConstructTree(node.right, height - 1);
    	return node;
    }
    public void PrintTree(){
    	System.out.println("Printing the tree by the thread : " + threadID.get());
    	PrintTree(this.root);
    }*/
    /*private void PrintTree(Peterson node){
    	if(node.left != null)
    		PrintTree(node.left);
    	if(node.right != null)
    		PrintTree(node.right);
    	if(node.left == null && node.right == null)
    		System.out.println(node.threadId);
    }*/
    private int FindNumNodes(int numThreads){
    	int i = numThreads;
    	while(numThreads > 1){
    		numThreads = (int)Math.ceil((double)numThreads/2);
    		i += numThreads;
    	}
    	return i;
    }
    public PetersonBinTree(int numThreads){
    	this.numThreads = numThreads;
    	this.height = (int)Math.ceil((Math.log(numThreads)/Math.log(2)));
    	int numNodes = FindNumNodes(numThreads);
    	this.peteTree = new Peterson[numNodes];
    	for(int i=0;i<numNodes;i++){
    		this.peteTree[i] = new Peterson();
    	}
    }
    
    public void lock(){
    	int i;
    	int id = threadID.get();
    	int shiftIndex = this.numThreads;
    	for(int h = 0; h < this.height; h++){
    		i = id;
    		id = (int)Math.floor(id/2) + shiftIndex;
    		this.peteTree[id].lock(i);
    	}
    }
    
    public void unlock(){
    	int id = threadID.get();
    	int shiftIndex = this.numThreads;
    	ArrayList<Integer> pathToRoot = new ArrayList<Integer>(this.height);
    	pathToRoot.add(id);
    	for(int h = 0; h < this.height; h++){
    		id = (int)Math.floor(id/2) + shiftIndex;
    		pathToRoot.add(id);
    	}
    	for(int j = pathToRoot.size()-1;j>=1;j--){
    		this.peteTree[pathToRoot.get(j)].unlock(pathToRoot.get(j-1));
    	}
    }

}
