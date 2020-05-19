package com.assignment.one;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    BinarySearchTree bTree = new BinarySearchTree();
    populateTree(bTree);
    // takeInputFromUser(bTree);
    bTree.traverseLeaves();
  }

  private static void populateTree(BinarySearchTree bTree) {
    // Root
    bTree.setRoot(new TreeNode(8));
    // 1st level
    bTree.getRoot().setLeft(new TreeNode(3));
    bTree.getRoot().setRight(new TreeNode(10));
    // 2nd level
    bTree.getRoot().getLeft().setLeft(new TreeNode(1));
    bTree.getRoot().getLeft().setRight(new TreeNode(6));
    bTree.getRoot().getRight().setRight(new TreeNode(14));
    // 3rd level
    bTree.getRoot().getLeft().getRight().setLeft(new TreeNode(4));
    bTree.getRoot().getLeft().getRight().setRight(new TreeNode(7));
    bTree.getRoot().getRight().getRight().setLeft(new TreeNode(13));
  }

  private static void takeInputFromUser(BinarySearchTree bTree) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter total number of tree nodes");
    int totalNodes = sc.nextInt();
    System.out.println(
        "Enter tree values(Parent Left Right) separated by space. eg. 8 3 10 1 6 -1 14 -1 -1 4 7 13 -1 \nEnter -1 for null child");
    while (totalNodes > 0) {
      int nodeValue = sc.nextInt();
      bTree.insert(nodeValue);
      totalNodes--;
    }
    sc.close();
  }
}
