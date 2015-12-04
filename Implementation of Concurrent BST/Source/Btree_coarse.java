/**
 * Created by fermi on 11/19/15.
 */
public class Btree_coarse extends Btree {
    public boolean insert(Node root, int newKey){
    	
    	  Node parent = null, child = null;
    	  this.root.lock();
          while(true) {
              child = root;
              while (true) {
                  //find the window
                  parent = child;
                  if (newKey < child.key)
                      child = child.left;
                  else
                      child = child.right;

                  if (child.left == null && child.right == null) {
                      break;
                  }
              }

              if(child.key == newKey) {
            	  
            	  this.root.unlock();
                  return false;

              }
              
              Node newNode = null;
              if (validate(parent, child)) {
                  if (newKey < child.key) {
                      newNode = CreateNew(child.key);
                      newNode.left = new Node(newKey);
                  } else {
                      newNode = CreateNew(newKey);
                      newNode.left = new Node(child.key);
                  }
                  if (newNode.key > parent.key) {
                      parent.right = newNode;
                  } else {
                      parent.left = newNode;
                  }

                  this.root.unlock();
                  return true;
              }
              this.root.unlock();
          }
    	
    }
    public boolean delete(Node root, int key){
    	  Node grandparent = null, parent = null, child = null;
    	  
    	  this.root.lock();
          while(true) {
              child = root;
              while (true) {
                  //find the window
                  Node temp = child;
                  if (key < child.key)
                      child = child.left;
                  else if (key > child.key)
                      child = child.right;
                  else if(key == child.key && !(child.left == null && child.right == null))//delete only the external nodes
                      child = child.right;
                  else
                      break;
                  if (child == null) {
                	  this.root.unlock();
                      return false;//key not found
                  }
                  grandparent = parent;
                  parent = temp;
              }

              if (validate(grandparent, parent, child)) {
                  parent.marked = true;
                  child.marked = true;
                  //deletion
                  Node sibling = (child.key < parent.key ? parent.right : parent.left);
                  if(key >= grandparent.key) {
                      grandparent.right = sibling;
                  }
                  else{
                      grandparent.left = sibling;
                  }
                  
                  this.root.unlock();
                  return true;
              }
              this.root.unlock();
          }
    }
}
