import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Random;

public class TTAS_EB {
	final private AtomicBoolean lock = new AtomicBoolean(false);
	final private Random rg = new Random();
	public void lock() throws InterruptedException{
		int limit = 1, max_delay = 16;
		while(true){
			while(lock.get()){
				
			}
			if(!lock.getAndSet(true))
				return;
			try{
				int delay = rg.nextInt(limit) + 1;
				limit = Math.min(2*limit, max_delay);
				Thread.sleep((long)delay);
			}
			catch(Exception ex){
				System.out.println(ex.getMessage());
			}
			
		}
	}
	   
	public void unlock(){
		lock.set(false);
	}
}
