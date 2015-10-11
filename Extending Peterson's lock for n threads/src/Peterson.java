import java.util.concurrent.atomic.AtomicBoolean;

public class Peterson {
	    
	/* due to java's memory model, use of normal boolean array wont work, 
    also volatile boolean array cannot be made in java*/
    final private AtomicBoolean[] flag = new AtomicBoolean[2]; 
    private volatile int victim;
    
    public Peterson(){
        for(int i=0 ; i<flag.length ; ++i)
            flag[i] = new AtomicBoolean();
    }
    
    public void lock(int i) {
        i %= 2;
        flag[i].set(true); 
        victim = i ;
        while ( flag[1-i].get() && victim == i) {}; 
    }
    
    public void unlock(int i){
        i %= 2;
        flag[i].set(false); 
    }
    
}
