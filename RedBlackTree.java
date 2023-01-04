// --== CS400 Fall 2022 File Header Information ==--
// Name: Benjamin Braiman
// Email: braiman@wisc.edu
// Team: CB
// TA: Callie Kim
// Lecturer: Gary Dahl
// Notes to Grader: Hello!

import java.util.*;


/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. You can use this class' insert method to build
 * a regular binary search tree, and its toString method to display a level-order
 * traversal of the tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements IRBTree<T> {


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
    public Node(T data) {
      this.data = data;
      leftChild = new Node<>();
      rightChild = new Node<>();
      leftChild.parent = this; // creating NIL nodes for the remove operation
      rightChild.parent = this;
    }

    private Node() {
      blackHeight = 1;
    }
    /**
     * @return true when this node has a parent and is the left child of
     * that parent, otherwise return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }
    boolean isNIL() {return data==null;}
  }

  protected Node<T> root; // reference to root node of tree, null when empty
  public int size = 0; // the number of values in the tree

  /**
   * Performs a naive insertion into a binary search tree: adding the input
   * data value to a new node in a leaf position within the tree. After
   * this insertion, no attempt is made to restructure or balance the tree.
   * This tree will not hold null references, nor duplicate data values.
   * @param data to be added into this binary search tree
   * @return true if the value was inserted, false if not
   * @throws NullPointerException when the provided data argument is null
   * @throws IllegalArgumentException when the newNode and subtree contain
   *      equal data references
   */
  public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if(data == null) throw new NullPointerException(
            "This RedBlackTree cannot store null references.");

    Node<T> newNode = new Node<>(data);
    if(root == null) { root = newNode; size++; root.blackHeight = 1; return true; } // add
    // first
    // node to an empty tree
    else{
      boolean returnValue = insertHelper(newNode,root); // recursively insert into subtree
      if (returnValue) size++;
      else throw new IllegalArgumentException(
              "This RedBlackTree already contains that value.");
      root.blackHeight = 1;
      enforceRBTreePropertiesAfterInsert(newNode);
      return returnValue;
    }
  }

  /**
   * Searches for PLU
   */
   public T find(T data) {
     if(data==null) return null;
     Node<T> toFind = get(data, root);
     return toFind.data;
   }

  /**
   * Maintains the RBTree structure during insert calls
   * @param newNode Node to be inserted
   */
  protected void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
    // if parent is black, we do nothing
    if(!(newNode.parent == null || newNode.parent.blackHeight == 1)) {
      // Since the parent node is red, we may always assume there is a grandparent node.
      Node<T> parent = newNode.parent;
      Node<T> uncle = parent.isLeftChild() ? parent.parent.rightChild : parent.parent.leftChild;
      if(uncle.isNIL() || uncle.blackHeight == 1) {
        if(newNode.isLeftChild()) {
          if(parent.isLeftChild()) {
            // child < parent < grandparent, so new level order is parent, child, grandparent
            parent.blackHeight = 1;
            parent.parent.blackHeight = 0;
            rotate(parent, parent.parent);
          } else {
            // grandparent < child < parent, so new level order is child, grandparent, parent
            newNode.blackHeight = 1;
            parent.parent.blackHeight = 0;
            rotate(newNode, parent);
            rotate(newNode, newNode.parent);
          }
        } else {
          if(parent.isLeftChild()) {
            newNode.blackHeight = 1;
            parent.parent.blackHeight = 0;
            rotate(newNode, parent);
            rotate(newNode, newNode.parent);
          } else {
            parent.blackHeight = 1;
            parent.parent.blackHeight = 0;
            rotate(parent, parent.parent);
          }
        }
      } else {
        uncle.blackHeight = 1;
        parent.blackHeight = 1;
        parent.parent.blackHeight = 0;
        enforceRBTreePropertiesAfterInsert(parent.parent);
      }
    }
    root.blackHeight = 1; // just a little safety check
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
      if(subtree.leftChild.isNIL()) { // left subtree empty, add here
        subtree.leftChild = newNode;
        newNode.parent = subtree;
        return true;
        // otherwise continue recursive search for location to insert
      } else return insertHelper(newNode, subtree.leftChild);
    }

    // store newNode within the right subtree of subtree
    else {
      if(subtree.rightChild.isNIL()) { // right subtree empty, add here
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
    if(child == null || parent == null) throw new IllegalArgumentException("Child is " +
            "illegitimate");
    if(child.isLeftChild()) rightRotate(child, parent); else leftRotate(child, parent);
  }

  private void leftRotate(Node<T> child, Node<T> parent) {
    Node<T> temp = child.leftChild;
    child.leftChild = parent;
    parent.rightChild = temp;
    if(parent.parent != null) {
      if(parent.isLeftChild())  parent.parent.leftChild = child;
      else parent.parent.rightChild = child;
    } else root = child;
    child.parent = parent.parent;
    parent.parent = child;
  }

  private void rightRotate(Node<T> child, Node<T> parent) {
    Node<T> temp = child.rightChild;
    child.rightChild = parent;
    parent.leftChild = temp;
    if(parent.parent != null) {
      if(parent.isLeftChild()) parent.parent.leftChild = child;
      else parent.parent.rightChild = child;
    } else root = child;
    child.parent = parent.parent;
    parent.parent = child;
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
    if (subtree.isNIL()) {
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

  private Node<T> get(T data, Node<T> subtree) {
    if (subtree.isNIL()) {
      return subtree;
    } else {
      int compare = data.compareTo(subtree.data);
      if (compare < 0) {
        return get(data, subtree.leftChild);
      } else if (compare > 0) {
        return get(data, subtree.rightChild);
      } else {
        return subtree;
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
    if (node.isNIL()) {
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
        if(!next.leftChild.isNIL()) q.add(next.leftChild);
        if(!next.rightChild.isNIL()) q.add(next.rightChild);
        output += next.data.toString();
        if(!q.isEmpty()) output += ", ";
      }
    }
    return output + " ]";
  }

  public String toColoredLevelOrderString() {
    String output = "[ ";
    if (this.root != null) {
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this.root);
      while(!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if(!next.leftChild.isNIL()) q.add(next.leftChild);
        if(!next.rightChild.isNIL()) q.add(next.rightChild);
        output += next.data.toString();
        if(next.blackHeight == 0) output += " red";
        else if(next.blackHeight == 1) output += " black";
        if(!q.isEmpty()) output += ", ";
      }
    }
    return output + " ]";
  }

  public String toString() {
    return "level order: " + this.toLevelOrderString() +
            "\nin order: " + this.toInOrderString();
  }


  private void transplant(Node<T> node, Node<T> successor) {
    assert node != null;
    if(node.parent == null) root = successor;
    else if(node.isLeftChild()) node.parent.leftChild = successor;
    else node.parent.rightChild = successor;
    successor.parent = node.parent;
  }

  /**
   * Removes the data entry from the tree
   *
   * @return the removed entry, null else
   */
  public T remove(T data) {
    Node<T> subtree = get(data, root);
    if(subtree.isNIL()) return null;
    remove(subtree);
    size--;
    return data;
  }

  /**
   * Remove helper method
   * @param subtree
   */
  private void remove(Node<T> subtree) {
    assert subtree != null;
    Node<T> toDel, dummy = subtree;
    int c = dummy.blackHeight;
    // Standard BST delete, though we will log if node we end up removing is black to not
    // violate the RBTree property
    // Case 1/2 deals with a null branch
    if(dummy.leftChild.isNIL()) {
      toDel = dummy.rightChild;
      transplant(subtree, subtree.rightChild);
    } else if(dummy.rightChild.isNIL()) {
      toDel = dummy.leftChild;
      transplant(subtree, subtree.leftChild);
    } else {
      // we have two children
      Node<T> temp = dummy.rightChild;
      while(!temp.leftChild.isNIL()) temp = temp.leftChild; // getting successor
      dummy = temp;
      c = dummy.rightChild.blackHeight; // since we're pulling up the successor,
      // we need to log the black height.
      toDel = dummy.rightChild;
      if(dummy.parent != subtree) {
        transplant(dummy, dummy.rightChild);
        dummy.rightChild = subtree.rightChild;
        subtree.rightChild.parent = dummy;
      }
      transplant(subtree, dummy);
      dummy.leftChild = subtree.leftChild;
      dummy.leftChild.parent = dummy;
      dummy.blackHeight = subtree.blackHeight;

    }
    if(c==1) {//attempting to delete a black node, uh oh
      toDel.blackHeight++;
      fix(toDel);
    }
  }

  /**
   * This god-forsaken method fixes the tree
   * (This method did not at all frustrate me)
   * @param subtree double black subtree that made me implement this method >:(
   */
  private void fix(Node<T> subtree) {
    assert subtree != null;
    Node<T> t;
    if(subtree == root || subtree.isNIL() || subtree.blackHeight == 1) {
      subtree.blackHeight = 1;
      return;
    }

    if(subtree.isLeftChild()) {
      t = subtree.parent.rightChild;
      if(t.blackHeight == 0) {
        rotate(t, subtree.parent);
        fix(subtree);
      } else if(t.blackHeight == 1) {
        if((t.leftChild == null || t.leftChild.blackHeight == 1) &&
                (t.rightChild == null || t.rightChild.blackHeight == 1)) {
          subtree.parent.blackHeight++;
          t.blackHeight = 0;
          subtree.parent.leftChild = new Node<>(); // replacing the node by NIL
          fix(subtree.parent);
        } else {
          if(t.leftChild != null && t.leftChild.blackHeight == 0) {
            t.leftChild.blackHeight = 1;
            t.blackHeight = 0;
            rotate(t.leftChild, t);
          }

          Node<T> newSibling = subtree.parent.rightChild;
          colorSwap(newSibling, subtree.parent);
          subtree.parent.blackHeight = 1;
          if(newSibling.leftChild != null) newSibling.blackHeight = 1;
          rotate(newSibling, subtree.parent);
        }
      }
    } else {
      t = subtree.parent.leftChild;
      if(t.blackHeight == 0) {
        rotate(t, subtree.parent);
        fix(subtree);
      } else if(t.blackHeight == 1) {
        if((t.leftChild == null || t.leftChild.blackHeight == 1) &&
                (t.rightChild == null || t.rightChild.blackHeight == 1)) {
          subtree.parent.blackHeight++;
          t.blackHeight = 0;
          subtree.parent.rightChild = new Node<>(); // replacing the node by NIL
          fix(subtree.parent);
        } else {
          if(t.rightChild != null && t.rightChild.blackHeight == 0) {
            t.rightChild.blackHeight = 1;
            t.blackHeight = 0;
            rotate(t.rightChild, t);
          }

          Node<T> newSibling = subtree.parent.leftChild;
          colorSwap(newSibling, subtree.parent);
          subtree.parent.blackHeight = 1;
          if(newSibling.rightChild != null) newSibling.blackHeight = 1;
          rotate(newSibling, subtree.parent);
        }
      }
    }
  }

  private void colorSwap(Node<T> node1, Node<T> node2) {
    int color1 = node1.blackHeight;
    node1.blackHeight = node2.blackHeight;
    node2.blackHeight = color1;
  }

  /**
   * Returns an iterator over elements of type {@code T}.
   *
   * @return an Iterator.
   */
  @Override
  public Iterator<T> iterator() {
    return iteratorHelper(new ArrayList<>(size), root).iterator();
  }

  /**
   * Helper class for iterator
   * @param list storage list
   * @param node node list
   * @return list of nodes
   */
  private ArrayList<T> iteratorHelper(ArrayList<T> list, Node<T> node) {
    if(node.isNIL()) return list;
    list = iteratorHelper(list, node.leftChild);
    list.add(node.data);
    list = iteratorHelper(list, node.rightChild);
    return list;
  }


}
