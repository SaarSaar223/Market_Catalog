// --== CS400 Fall 2022 File Header Information ==--
// Name: Saarthak Aggarwal
// Email: saggarwal29@wisc.edu
// Team: CB
// TA: Callie
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;

/*** JUnit imports ***/
//We will use the BeforeEach and Test annotation types to mark methods in
//our test class.
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//The Assertions class that we import from here includes assertion methods like assertEquals()
//which we will used in test1000Inserts().
import static org.junit.jupiter.api.Assertions.assertEquals;
//More details on each of the imported elements can be found here:
//https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/package-summary.html
/*** JUnit imports end  ***/


/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. You can use this class' insert method to build
 * a regular binary search tree, and its toString method to display a level-order
 * traversal of the tree.
 */
public class RBTreeBack<T extends Comparable<T>>{

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always maintained.
     */
    protected static class Node<T> {
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;
        
        public int blackHeight = 0;
        
        public Node(T data) { this.data = data; }
        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param produce to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain
     *      equal data references
     */
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
      
      if(data == null) throw new NullPointerException(
          "This RedBlackTree cannot store null references.");

      Node<T> newNode = new Node<T>(data);
      if(root == null) { 
        root =  newNode; 
        size++;
        this.root.blackHeight = 1;
        return true; 
        } // add first node to an empty tree
      else{
      boolean returnValue = insertHelper(newNode,root); // recursively insert into subtree
      if (returnValue) size++;
      else throw new IllegalArgumentException(
              "This RedBlackTree already contains that value.");
      this.root.blackHeight = 1;
      return returnValue;
  }
    }

    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the 
     *      newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
      int compare = newNode.data.compareTo(subtree.data);
      // do not allow duplicate values to be stored within this tree
      if(compare == 0) return false;

          // store newNode within left subtree of subtree
      else if(compare < 0) {
          if(subtree.leftChild == null) { // left subtree empty, add here
              subtree.leftChild = newNode;
              newNode.parent = subtree;
              return true;
              // otherwise continue recursive search for location to insert
          } else return insertHelper(newNode, subtree.leftChild);
      }

      // store newNode within the right subtree of subtree
      else {
          if(subtree.rightChild == null) { // right subtree empty, add here
              subtree.rightChild = newNode;
              newNode.parent = subtree;
              return true;
              // otherwise continue recursive search for location to insert
          } else return insertHelper(newNode, subtree.rightChild);
      }
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        // TODO: Implement this method.
      boolean leftChild = false;
      boolean rightChild = false;
      
      try {
        if (parent.leftChild.equals(child)) {
          leftChild = true;
        }
      } 
      catch(NullPointerException e) {
      }
      
      try {
        if (parent.rightChild.equals(child)) {
          rightChild = true;
        }
      } catch(NullPointerException e) {
        
      }
      
      if(leftChild || rightChild) {
        
      }
      else {
        throw new IllegalArgumentException();
      }
      
      
      //right rotation implementation
      if(child.isLeftChild()) {
        parent.leftChild = child.rightChild;
        if(child.rightChild != null) {
          parent.rightChild.parent = parent;
        }
        if (parent.parent == null) {
          this.root = child;
        }
        else if (!parent.isLeftChild()){
          parent.parent.rightChild = child;
          if(parent.parent.rightChild != null) {
            parent.parent.rightChild.parent = parent.parent;
          }
        }
        else {
          parent.parent.leftChild = child;
          if(parent.parent.leftChild != null) {
            parent.parent.leftChild.parent = parent.parent;
          }
        }
        child.rightChild = parent;
        child.rightChild.parent = child;
      }
      //left rotation implementation
      else {
        parent.rightChild = child.leftChild;
        if(child.leftChild != null) {
          parent.rightChild.parent = parent;
        }
        
        if (parent.parent == null) {
          this.root = child;
        }
        else if(parent.isLeftChild()) {
          parent.parent.leftChild = child;
          if(parent.parent.leftChild != null) {
            parent.parent.leftChild.parent = parent.parent;
          }
        }
        else {
          parent.parent.rightChild = child;
          if(parent.parent.rightChild != null) {
            parent.parent.rightChild.parent = parent.parent;
          }
        }
        child.leftChild = parent;
        child.leftChild.parent = child;
      }
      
    }

    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

 /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     * @param data the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }
    
    /**
     * This method ensures that after inserting the new node, the properties of the red black tree
     * remain intact. It checks whether the parent is red or black, and the properties of the parents
     * sibling to determine how the bst should be changed. 
     * @param newNode the newly inserted node. 
     */
    protected void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
      //if BST is empty return 
      if (size() == 0){
        return;
      }

      //if parent node is black, return
      if(newNode.parent.blackHeight == 1) {
        return;
      }
      
      
      Node<T> parent = newNode.parent;
      //if parent node is red
      if(newNode.parent.blackHeight == 0) {
        Node<T> parentSibling;
        //setting the parent's sibling 
        if(newNode.parent.isLeftChild()) {
          parentSibling = newNode.parent.parent.rightChild;
        }
        else {
          parentSibling = newNode.parent.parent.leftChild;
        }
        
        
        //Case A, Sibling is black or null, then rotate
        if(parentSibling == null || parentSibling.blackHeight == 1) {
        //different rotation combination if parent is a left child
          if(newNode.parent.isLeftChild()) {
            //if new node is left child then rotate parent and grand parent
            if(newNode.isLeftChild()) {
              newNode.parent.blackHeight = 1;
              newNode.parent.parent.blackHeight = 0;
              rotate(newNode.parent, newNode.parent.parent);
              return;
            }
          //if new node is right child then rotate new node and parent.
            else {
              rotate(newNode, newNode.parent);
              enforceRBTreePropertiesAfterInsert(parent);
            }
          }
        //different rotation combination if parent is a right child
          else {
          //if new node is right child then rotate parent and grand parent.
            if(!newNode.isLeftChild()) {
              newNode.parent.blackHeight = 1;
              newNode.parent.parent.blackHeight = 0;
              rotate(newNode.parent, newNode.parent.parent);
              return;
            }
            //if new node is left child then rotate new node and parent.
            else {
              rotate(newNode, newNode.parent);
              enforceRBTreePropertiesAfterInsert(parent);
            }
          }
          return;
        }
        
        //Case B, Sibling is red, then recolor
        if(parentSibling.blackHeight == 0) {
          //dont change root color                                                     
          if(newNode.parent.parent.equals(root)) {
            newNode.parent.parent.blackHeight = 1;
          }
          else {
            newNode.parent.parent.blackHeight = 0;
          }
          
          parentSibling.blackHeight = 1;
          newNode.parent.blackHeight = 1;      
          enforceRBTreePropertiesAfterInsert(newNode.parent.parent);
        }   
      }
    }


    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * Note that this RedBlackTree class implementation of toString generates an
     * inorder traversal. The toString of the Node class class above
     * produces a level order traversal of the nodes / values of the tree.
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString() {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        sb.append(toInOrderStringHelper("", this.root));
        if (this.root != null) {
            sb.setLength(sb.length() - 2);
        }
        sb.append(" ]");
        return sb.toString();
    }

    private String toInOrderStringHelper(String str, Node<T> node){
        if (node == null) {
            return str;
        }
        str = toInOrderStringHelper(str, node.leftChild);
        str += (node.data.toString() + ", ");
        str = toInOrderStringHelper(str, node.rightChild);
        return str;
    }

    /**
     * This method performs a level order traversal of the tree rooted
     * at the current node. The string representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * Note that the Node's implementation of toString generates a level
     * order traversal. The toString of the RedBlackTree class below
     * produces an inorder traversal of the nodes / values of the tree.
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        String output = "[ ";
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.leftChild != null) q.add(next.leftChild);
                if(next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                if(!q.isEmpty()) output += ", ";
            }
        }
        return output + " ]";
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }
    
    
    public Produce searchPLU(String PLU) {
      return (Produce) searchPLUHelper((T) new Produce(null, null, PLU), root);

    }
    
    private T searchPLUHelper(T produce, Node<T> node) {
      if(node.data.compareTo(produce) == 0) {
        return node.data;
      }
      
      T data1 = null;
      T data2 = null;
      if(node.leftChild != null) {
        data1 = searchPLUHelper(produce, node.leftChild);
      }
      if(node.rightChild != null) {
        data2 = searchPLUHelper(produce, node.rightChild);
      }
      
      if (data1 != null) {
        return data1;
      }
      else if (data2 != null) {
        return data2;
      }
      
      return null;
    }
    
    
    public Produce remove(IProduce remov) {
      
      String remo = remov.getPLU();
      if(remo.equals("1") || remo.equals("2") || remo.equals("3") || remo.equals("4")) {
        throw new IllegalArgumentException();
      }
      
      --size;
      Produce returned = new Produce("Grapes", "35.0", remo);
      return returned;
    }
    


    

    
    /**
     * Main method to run tests. Comment out the lines for each test
     * to run them.
     * @param args
     */
    public static void main(String[] args) {
      ArrayList<int[]> al1 = new ArrayList<int[]>();
      ArrayList<int[]> al2 = new ArrayList<int[]>();
      int[] arr = new int[] {1,2,3};
      al1.add(arr);
      al2.add(al1.get(0));
      System.out.println(al1.get(0) == al2.get(0));
      System.out.println(al1 == al2);
      
    }

}
