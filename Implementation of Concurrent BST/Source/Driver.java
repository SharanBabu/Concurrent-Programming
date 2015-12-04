import java.util.Random;

public class Driver{
    int numThreads;
    int eachThreadLoad = 1000000;
    int limit = 100;
    double initial_fill_up_factor = 0.5;//50% of keyspace
    double search_factor = 0.7;
    double insert_factor = search_factor + 0.2;
    double delete_factor = insert_factor + 0.1;
    Btree tree;
    public void StartProgram(){
        //tree = new Btree();
        int initial_size = (int) (initial_fill_up_factor * limit);
        Random rr = new Random();
        for(int i =0;i < initial_size;i++){
            tree.insert(tree.root, rr.nextInt(limit));
        }
    }
    public void StartAllThreads() throws InterruptedException {
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
    }
    public boolean verifyTheTree(){
        return tree.VerifyTheTree(tree.root);
    }

    public static void main(String[] args) {
        if(args.length != 4)
        {
            return;
        }

        Driver dr = new Driver();
        if(args[0].equals("coarse") ){
            dr.tree = new Btree_coarse();
        }
        else{
            dr.tree = new Btree();
        }
        dr.numThreads = Integer.parseInt(args[1]);
        dr.limit = Integer.parseInt(args[2]);
        switch(args[3]){
            case "A":
                dr.search_factor = 0.9;
                dr.insert_factor = dr.search_factor + 0.09;
                dr.delete_factor = dr.insert_factor + 0.01;
                break;
            case "B":
                dr.search_factor = 0.7;
                dr.insert_factor = dr.search_factor + 0.2;
                dr.delete_factor = dr.insert_factor + 0.1;
                break;
            case "C":
                dr.search_factor = 0.0;
                dr.insert_factor = dr.search_factor + 0.5;
                dr.delete_factor = dr.insert_factor + 0.5;
                break;
            default:
                System.out.println("Incorrect argument for work load distribution");
        }
        long total_time = 0;
        long total_num_operations = dr.numThreads * dr.eachThreadLoad;
        boolean result = true;
        try{
            for(int i=0; i < 10;i++){
                dr.StartProgram();//initial setup of btree
                long startTime = System.currentTimeMillis();
                dr.StartAllThreads();
                long endTime = System.currentTimeMillis();
                total_time += endTime - startTime;
                result &= dr.verifyTheTree();//verify the btree
            }
            if(result) {
                System.out.print("Valid execution! ");
                System.out.print(" || Total time taken (by all threads): " + (total_time/10) + " ms");
                System.out.print("|| Total number of operations (by all threads): " + total_num_operations);
                System.out.print("|| Throughput(number of operations in 1 ms) : " +   total_num_operations/(total_time/10));
                System.out.println();
            }
            else
                System.out.println("Invalid execution");
            //dr.tree.Inorder(dr.tree.root);
        }catch(Exception ex){
            System.out.println("Something bad happened");
        }
    }

    public class MyThread extends Thread {
        Random r = new Random();
        public void run(){
            //System.out.println("eachloopcount : " + eachThreadLoopCount  + " avgInterReq: " + avgInterRequestDelay);
            for(int i=0;i<eachThreadLoad;i++){
                try{
                        int decider = r.nextInt(1000);
                        if(decider < search_factor * 1000)
                        {
                            tree.contains(tree.root, r.nextInt(limit));
                        }
                        else if(decider < insert_factor * 1000){
                            tree.insert(tree.root, r.nextInt(limit));
                        }
                        else {
                            tree.delete(tree.root, r.nextInt(limit));
                        }
                }catch(Exception e){
                    System.out.println("Something bad happened");
                }
            }
        }

    }

}
