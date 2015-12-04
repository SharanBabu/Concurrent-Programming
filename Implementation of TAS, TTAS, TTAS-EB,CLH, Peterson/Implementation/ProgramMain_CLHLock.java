import org.apache.commons.math3.distribution.ExponentialDistribution;

public class ProgramMain_CLHLock{
	int numThreads;
	int avgInterRequestDelay;
	int data = 0;
	int eachThreadLoopCount;
	int COUNT;
	CLHLock clh;
	ExponentialDistribution exp;
	public void StartProgram() throws InterruptedException{
		clh = new CLHLock();
		data = 0;
		Thread[] threads = new Thread[numThreads]; 
		for(int i=0;i<numThreads;i++)
		{
			threads[i] = new MyThread();
		}
		for(int i=0;i<numThreads;i++)
		{
			threads[i].start();
		}
		for (int i = 0; i < numThreads; i++) {
			threads[i].join();
		}
		      
        if (data != COUNT) {
    	  System.out.println("Wrong execution. Expected value : " + data + " Actual Output : " + COUNT);
        }else{
        	//System.out.println("Correct execution. Expected value : " + data + " Actual Output : " + COUNT);
        }
	}
	
	public double SampleRandomValue(int mean)
	{
		return exp.sample();
	}
	
	public static void main(String[] args) {
		if(args.length != 2)
		{
				return;
		}
		
		ProgramMain_CLHLock pm = new ProgramMain_CLHLock();	
			pm.numThreads = Integer.parseInt(args[0]);
			pm.avgInterRequestDelay = Integer.parseInt(args[1]);//50 TU, 1 TU = 0.1 ms
			pm.eachThreadLoopCount = 1000;
			pm.COUNT = pm.numThreads * pm.eachThreadLoopCount;
			if(pm.avgInterRequestDelay > 0){
				pm.exp = new ExponentialDistribution((double)pm.avgInterRequestDelay);
			}
			try{
				long total_time = 0;
				for(int i=0; i < 10;i++){
					long startTime = System.currentTimeMillis();
	
					pm.StartProgram();
	
					long endTime = System.currentTimeMillis();
					total_time += endTime - startTime;
				}
				System.out.print("\nNum Threads: " + pm.numThreads);
				System.out.print("\tInter Request delay: " + pm.avgInterRequestDelay);
				System.out.print("\tExpected counter value: " + pm.data);
				System.out.print("\tProgram output: " + pm.COUNT);
				System.out.print("\tAverage Execution time : " + (total_time/10) + " milliseconds\n");
			}catch(Exception ex){}
	}

	public class MyThread extends Thread {
		public void run(){
			//System.out.println("eachloopcount : " + eachThreadLoopCount  + " avgInterReq: " + avgInterRequestDelay);
			for(int i=0;i<eachThreadLoopCount;i++){
				try{
					clh.lock();
					data = data + 1;
					clh.unlock();
					if(avgInterRequestDelay > 0){
						long waitTime = (long)(SampleRandomValue(avgInterRequestDelay) * 0.1); //scaling down from ms to 1 TU (0.1 ms)
						//System.out.println("Sleeping for " + waitTime);
						Thread.sleep(waitTime);
					}
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}
		
	}
	
}
