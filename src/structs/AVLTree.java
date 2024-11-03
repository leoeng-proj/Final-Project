package structs;

import java.io.Serializable;

public class AVLTree <T extends Comparable<T>> implements Serializable{
	private class Node{
		T item;
		int height;
		Node left;
		Node right;
		Node(T item) {
			this.item = item;
			this.left = null;
			this.right = null;
			height = 0;
		}
		public String toString() {
			return item.toString();
		}
	}
	private Node root;
	
	private int size;
	public AVLTree() {
		this.root = null;
		size = 0;
	}
	public AVLTree(T item) {
		this.root = new Node(item);
		size = 1;
	}
	public boolean add(T item) {
//		if(root == null) {
//			this.root = new Node(item);
//		}
		this.root = addRecur(item, root);
		size++;
		return true;
	}	
	public void addArray(T[] array) {
		for(T o : array) {
			add(o);
		}
	}
	public boolean balanced() {
		return isBalanced(root);
	}
	public boolean contains(T item) {
		return searchRecur(item, root) != null;
	}
	public boolean isEmpty() {
		return size() == 0;
	}
	public void remove(T item) {
		Node node = searchRecur(item, root);
		removeRecur(node);
	}
	public int size() {
		return size;
	}
	public String toString() {
		if(root == null) {
			return "Empty";
		}
		StringBuilder sb = new StringBuilder();
		toStringRecur(root, sb, 0);		
		return sb.toString();
	}
	private Node addRecur(T item, Node root) {
		if (root == null) {
			root = new Node(item);
		}
		else if(root.item.compareTo(item) == 0) {
			addRecur(item, root.right);
		}
		else if(root.item.compareTo(item) > 0) {
			root.left = addRecur(item, root.left);
		}
		else {
			root.right = addRecur(item, root.right);			
		}
		root.height = (Math.max(height(root.left), height(root.right)) + 1);
		return rotate(root);
	}
	private Node findPredecessor(Node root) {
		if(root.right == null) {
			return root;
		}
		return findPredecessor(root.right);
	}
	private int height(Node node) {
		if(node == null) {
			return -1;
		}
		return node.height;
	}
	
	private boolean isBalanced(Node root) {
		if(root == null) {
			return true;
		}
		return Math.abs(height(root.left) - height(root.right)) <= 1 && 
				isBalanced(root.left) &&
				isBalanced(root.right);
	}
	private void removeRecur(Node root) {
		if(root == null) {
			return;
		}
		if(root.left != null && root.right != null) {
			//both children present
			//find rightmost node in left tree
			Node predecessor = findPredecessor(root.left);
			//set roots value to above nodes value
			root.item = predecessor.item;
			return;
		}
		if(root.left == null) {
			if(root.right == null) {
				//no children
				root = null;
				return;
			}
			//right child present left child absent
			root = root.right;
			return;
		}
		//left child present right child absent
		root = root.left;
	}
	private Node rotate(Node root) {
		if(!isBalanced(root)) {
			if(height(root.left) - height(root.right) > 0) {
				if((height(root.left.left) - height(root.left.right)) < 0) {
					//left-right
					root.left = rotateLeft(root.left);
					return rotateRight(root);
				}
				//left-left
				return rotateRight(root);
			}
			if((height(root.right.left) - height(root.right.right)) < 0) {
				//right-right
				return rotateLeft(root);
			}
			//right-left
			root.right = rotateRight(root.right);
			return rotateLeft(root);
		}
		return root;
	}
	private Node rotateLeft(Node pivot) {
		Node child = pivot.right;
		Node save = child.left;
		child.left = pivot;
		pivot.right = save;
		
		pivot.height = (Math.max(height(pivot.left), height(pivot.right)) + 1);
		child.height = (Math.max(height(child.left), height(child.right)) + 1);
		return child;
	}
	private Node rotateRight(Node pivot) {
		Node child = pivot.left;
		Node save = child.right;
		child.right = pivot;
		pivot.left = save;
		
		pivot.height = (Math.max(height(pivot.left), height(pivot.right)) + 1);
		child.height = (Math.max(height(child.left), height(child.right)) + 1);
		
		return child;
	}
	private Node searchRecur(T item, Node root) {
		if(root == null) {
			return null;
		}
		if(root.item.equals(item)) {
			return root;
		}
		else if(root.item.compareTo(item) > 0) {
			return searchRecur(item, root.left);
		}
		else {
			return searchRecur(item, root.right);
		}
	}
	private void toStringRecur(Node root, StringBuilder sb, int count) {
		if(root == null) {
			sb.append("");
			return;
		}
		for(int i = 0; i < count; i++) {
			sb.append("   ");
		}
		sb.append(root + "\n");
		count++;
		toStringRecur(root.left, sb, count);
		toStringRecur(root.right, sb, count);
	}
}