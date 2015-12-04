import java.util.concurrent.locks.ReentrantLock;

public class Node{
    public int key;
    public Node left;
    public Node right;
    public boolean marked;
    public ReentrantLock lock;

    Node(int k){
        this.key = k;
        this.left = null;
        this.right = null;
        this.marked = false;
        lock = new ReentrantLock();
    }

    public void lock(){
        this.lock.lock();
    }
    public void unlock(){
        this.lock.unlock();
    }
}
