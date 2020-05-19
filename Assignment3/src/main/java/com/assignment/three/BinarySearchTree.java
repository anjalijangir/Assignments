package com.assignment.three;

import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree {
  private TreeNode root;

  public TreeNode getRoot() {
    return root;
  }

  public void setRoot(TreeNode root) {
    this.root = root;
  }

  public void printBFS() {
    Queue<TreeNode> queue = new LinkedList<>();
    // Add root level node to queue
    System.out.print("Level Order: ");
    queue.add(root);
    while (!queue.isEmpty()) {
      TreeNode currentNode = queue.poll();
      System.out.print(currentNode.getData() + " ");
      // add next level nodes to queue
      if (currentNode.getLeft() != null) {
        queue.add(currentNode.getLeft());
      }
      if (currentNode.getRight() != null) {
        queue.add(currentNode.getRight());
      }
    }
  }
}
