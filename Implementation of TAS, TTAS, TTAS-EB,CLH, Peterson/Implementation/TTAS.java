import java.util.concurrent.atomic.AtomicBoolean;

public class TTAS {
	final private AtomicBoolean lock = new AtomicBoolean(false);
	
	public void lock(){
		while(true){
			while(lock.get()){}
			if(!lock.getAndSet(true))
				return;
		}
	}
	public void unlock(){
		lock.set(false);
	}
}
