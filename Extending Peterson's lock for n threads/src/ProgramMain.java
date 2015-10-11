import org.apache.commons.math3.distribution.ExponentialDistribution;

public class ProgramMain{
	int numThreads;
	int avgInterRequestDelay;
	int data = 0;
	int eachThreadLoopCount;
	int COUNT;
	PetersonBinTree pt;
	public void StartProgram() throws InterruptedException{
		pt = new PetersonBinTree(numThreads);
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
    	  System.out.print("Wrong execution. Expected value : " + data + " Actual Output : " + COUNT);
        }else{
        	System.out.print("Correct execution. Expected value : " + data + " Actual Output : " + COUNT);
        }
	}
	
	public double SampleRandomValue(int mean)
	{
		ExponentialDistribution exp = new ExponentialDistribution((double)mean);
		return exp.sample();
	}
	
	public static void main(String[] args) {
		if(args.length != 2)
		{
				return;
		}
		
		ProgramMain pm = new ProgramMain();	
			pm.numThreads = Integer.parseInt(args[0]);
			pm.avgInterRequestDelay = Integer.parseInt(args[1]);//50 TU, 1 TU = 0.1 ms
			pm.eachThreadLoopCount = 1000;
			pm.COUNT = pm.numThreads * pm.eachThreadLoopCount;
			try{
				long startTime = System.currentTimeMillis();

				pm.StartProgram();

				long endTime = System.currentTimeMillis();

				System.out.println(" | NumThreads : "+ args[0]+" | AvgInter-request delay : "+ args[1]+" | Execution time : " + (endTime - startTime) + " milliseconds");
			}catch(Exception ex){}
	}

	public class MyThread extends Thread {
		public void run(){
			//System.out.println("eachloopcount : " + eachThreadLoopCount  + " avgInterReq: " + avgInterRequestDelay);
			for(int i=0;i<eachThreadLoopCount;i++){
				try{
					pt.lock();
					data = data + 1;
					pt.unlock();
					long waitTime = (long)(SampleRandomValue(avgInterRequestDelay) * 0.1); //scaling down from ms to 1 TU (0.1 ms)
					//System.out.println("Thread gonna sleep for " + waitTime);
					Thread.sleep(waitTime);
				}catch(Exception e){
					
				}
			}
		}
		
	}
	
}
