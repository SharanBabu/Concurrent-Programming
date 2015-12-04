import java.util.concurrent.atomic.AtomicReference;

public class CLHLock {
	final private ThreadLocal<ListNode> mynode= new ThreadLocal<ListNode>(){

        protected ListNode initialValue(){
            return new ListNode();
        }
    };
	
    final private ThreadLocal<ListNode> mypred= new ThreadLocal<ListNode>(){

        protected ListNode initialValue(){
            return new ListNode();
        }
    };
    
    final private AtomicReference<ListNode> tail = new AtomicReference<ListNode>(new ListNode());
    
	public void lock(){
		final ListNode node = mynode.get();
		node.locked = true;
		ListNode pred = tail.getAndSet(node);
		mypred.set(pred);
		while(pred.locked){}
		
	}
	public void unlock(){
		final ListNode node = mynode.get();
		node.locked = false;
		mynode.set(mypred.get());
	}
	
	private static class ListNode{
		public volatile boolean locked = false;
	}
}
