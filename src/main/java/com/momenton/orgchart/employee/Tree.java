package com.momenton.orgchart.employee;

import java.util.List;

public interface Tree<T> {
  /**
   * prints out the hierarchy in expected format
   */
  void print();

  /**
   * Adds node of type T to the tree
   * @param args - arguments supplied to build the T object
   * @return
   */
  T addChild(List<String> args);

  T getRoot();


}
