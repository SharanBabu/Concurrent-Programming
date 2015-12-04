import java.util.ArrayList;

/**
 * Created by Sharan on 11/11/15.
 */

// /fine grained lock

public class Btree {
    int sent1 = 999999997,sent2 = 999999998,sent3 = 999999999;//three keys for 6 sentinel nodes
    public Node root;
    ArrayList<Integer> elements = new ArrayList<Integer>();
    Btree(){
        root = CreateNew(sent3);
        Node sent2 = CreateNew(this.sent2);
        Node sent1 = new Node(this.sent1);
        sent2.left = sent1;
        root.left = sent2;
    }
    public Node CreateNew(int newKey){
            Node n = new Node(newKey);
            Node nr = new Node(newKey);
            n.right = nr;
            return n;
    }
    public boolean validate(Node parent, Node child){
        return ((parent.marked == false) && (child.marked == false) && (child.left == null && child.right == null) &&
                (parent.key > child.key ? parent.left == child : parent.right == child));
    }
    public boolean validate(Node grandparent, Node parent, Node child){
        return ((grandparent.marked == false) && (parent.marked == false) && (child.marked == false) &&
                (child.left == null && child.right == null) &&
                (grandparent.key > parent.key ? grandparent.left == parent : grandparent.right == parent) &&
                (parent.key > child.key ? parent.left == child : parent.right == child));
    }
    public boolean contains(Node root, int key){
        Node temp = root;
        while (true) {
            if (key < temp.key)
                temp = temp.left;
            else
                temp = temp.right;
            if(temp.left == null && temp.right == null)
                break;
        }
        if(temp.marked || temp.key != key)
            return false;
        return true;
    }
    public boolean delete(Node root, int key){
        Node grandparent = null, parent = null, child = null;
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
                    return false;//key not found
                }
                grandparent = parent;
                parent = temp;
            }
            grandparent.lock();
            parent.lock();
            child.lock();

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
                child.unlock();
                parent.unlock();
                grandparent.unlock();
                return true;
            }
            child.unlock();
            parent.unlock();
            grandparent.unlock();
        }

    }

    public boolean insert(Node root, int newKey){
        Node parent = null, child = null;
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

            if(child.key == newKey)
                return false;
            parent.lock();
            child.lock();
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

                child.unlock();
                parent.unlock();
                return true;
            }
            child.unlock();
            parent.unlock();
        }
    }
    public void Inorder(Node n){
        if(n == null)
            return;
        Inorder(n.left);
        elements.add(n.key);
        //System.out.println(n.key + " ");
        Inorder(n.right);
    }

    public boolean verifyBSTProperty(Node root, int min, int max){
        if(root==null)
            return true;
        if(root.key < min || root.key > max || (root.left != null && root.right == null) || (root.left == null && root.right != null))
            return false;
        return verifyBSTProperty(root.left, min, root.key - 1) && verifyBSTProperty(root.right, root.key, max);
    }

    public boolean countSanityCheck(Node root){
        Inorder(root);
        for(int i=1;i< elements.size() - 1 ;i++){
            if(elements.get(i-1) == elements.get(i) && elements.get(i) == elements.get(i+1) )
                return false;
        }
        return true;
    }

    public boolean VerifyTheTree(Node root){
        return verifyBSTProperty(root, Integer.MIN_VALUE, Integer.MAX_VALUE) && countSanityCheck(root);
    }
}
