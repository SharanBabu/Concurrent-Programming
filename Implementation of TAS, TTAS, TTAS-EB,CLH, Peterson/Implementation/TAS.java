import java.util.concurrent.atomic.*;

public class TAS {
	final private AtomicBoolean lock = new AtomicBoolean(false);
	
	public void lock(){
		while(lock.getAndSet(true)){}
	}
	public void unlock(){
		lock.set(false);
	}
}
