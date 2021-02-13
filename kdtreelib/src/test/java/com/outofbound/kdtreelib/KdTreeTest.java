package com.outofbound.kdtreelib;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class KdTreeTest {

    @Test
    public void insert_isCorrect(){
        KdTree kdTree = new KdTree(new Node(0,0),0);
        kdTree.insert(new Node(1,2));
        assertArrayEquals(new float[]{0,0},kdTree.getRoot().getValues(),0);
        assertNotNull(kdTree.getSubtreeDx());
        assertArrayEquals(new float[]{1,2},kdTree.getSubtreeDx().getRoot().getValues(),0);
    }

    @Test
    public void searchNearest_isCorrect(){
        KdTree kdTree = new KdTree(new Node(0,0),0);
        kdTree.insert(new Node(1,2));
        kdTree.insert(new Node(-3,1));
        kdTree.insert(new Node(2,-2));
        kdTree.insert(new Node(4,9));
        kdTree.insert(new Node(-5,12));
        Node nearest = kdTree.searchNearest(new Node(-4,1));
        assertArrayEquals(new float[]{-3,1},nearest.getValues(),0);
    }
}
