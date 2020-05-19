package com.assignment.three;

public class Main {

  public static void main(String[] args) {

    BinarySearchTree bTree = new BinarySearchTree();
    populateTree(bTree);
    bTree.printBFS();
  }

  private static void populateTree(BinarySearchTree bTree) {
    // Root
    bTree.setRoot(new TreeNode(1));
    // 1st level
    bTree.getRoot().setRight(new TreeNode(2));
    // 2nd level
    bTree.getRoot().getRight().setRight(new TreeNode(5));
    // 3rd level
    bTree.getRoot().getRight().getRight().setLeft(new TreeNode(3));
    bTree.getRoot().getRight().getRight().setRight(new TreeNode(6));
    // 4th level
    bTree.getRoot().getRight().getRight().getLeft().setRight(new TreeNode(4));
  }
}
