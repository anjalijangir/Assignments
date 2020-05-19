package com.assignment.one;

public class BinarySearchTree {

  private TreeNode root;

  public TreeNode getRoot() {
    return root;
  }

  public void setRoot(TreeNode root) {
    this.root = root;
  }

  public void insert(int value) {
    root = insertRecursive(root, value);
  }

  public void traverseLeaves() {
    printLeafRToL(root);
  }

  private void printLeafRToL(TreeNode node) {
    if (node == null) {
      return;
    }
    // Check if its a leaf node
    if (node.getLeft() == null && node.getRight() == null) {
      System.out.print(node.getData() + " ");
      return;
    }
    // Go right first
    printLeafRToL(node.getRight());
    // If nothing in right then go left
    printLeafRToL(node.getLeft());
  }

  private TreeNode insertRecursive(TreeNode current, int value) {
    if (current == null) {
      // When -1 comes mark node as null
      if (value == -1) {
        return null;
      }
      return new TreeNode(value);
    }
    if (value < current.getData()) {
      // Create left tree
      TreeNode left = insertRecursive(current.getLeft(), value);
      current.setLeft(left);
    } else if (value > current.getData()) {
      // Create right tree
      TreeNode right = insertRecursive(current.getRight(), value);
      current.setRight(right);
    } else {
      // value already exists
      return current;
    }
    return current;
  }
}
