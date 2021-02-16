package com.outofbound.kdtreelib;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class KdTreeTest {

    private static Random random = new Random();

    private static float mapValue(float val, float min, float max){
        return min+(max - min)*val;
    }

    private static float randomFloat(float min, float max){
        return mapValue(random.nextFloat(),min,max);
    }

    private static Node randomNode(){
        return new Node(randomFloat(-1,1),randomFloat(-1,1));
    }

    @Test
    public void insert_isCorrect(){
        KdTree kdTree = new KdTree(new Node(0,0));
        kdTree.insert(new Node(1,2));
        assertArrayEquals(new float[]{0,0},kdTree.getRoot().getValues(),0);
        assertNotNull(kdTree.getSubtreeDx());
        assertArrayEquals(new float[]{1,2},kdTree.getSubtreeDx().getRoot().getValues(),0);
    }

    @Test
    public void searchNearest1_isCorrect(){
        KdTree kdTree = new KdTree(new Node(0,0));
        kdTree.insert(new Node(1,2));
        kdTree.insert(new Node(-3,1));
        kdTree.insert(new Node(2,-2));
        kdTree.insert(new Node(4,9));
        kdTree.insert(new Node(-5,12));
        Node nearest = kdTree.searchNearest(new Node(-4,1));
        assertArrayEquals(new float[]{-3,1},nearest.getValues(),0);
    }

    @Test
    public void searchNearest2_isCorrect(){
        KdTree kdTree = new KdTree(new Node(0,0));
        kdTree.insert(new Node(100,200));
        kdTree.insert(new Node(-3,1));
        kdTree.insert(new Node(200,-200));
        kdTree.insert(new Node(400,900));
        kdTree.insert(new Node(-500,1200));
        kdTree.insert(new Node(100,-120));
        kdTree.insert(new Node(-200,-300));
        kdTree.insert(new Node(400,100));
        kdTree.insert(new Node(200,-500));
        kdTree.insert(new Node(-3,-1));
        kdTree.insert(new Node(-3,2));
        Node nearest = kdTree.searchNearest(new Node(-4,1));
        assertArrayEquals(new float[]{-3,1},nearest.getValues(),0);
    }

    @Test
    public void searchNearest3_isCorrect(){
        KdTree kdTree = new KdTree(randomNode());
        Node queryNode = randomNode();
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            Node node = randomNode();
            kdTree.insert(node);
            nodes.add(node);
        }
        Node nearestKdTree = kdTree.searchNearest(queryNode);
        Node nearestList = nodes.get(0);
        for (Node node : nodes){
            if (queryNode.distance(node) < queryNode.distance(nearestList)){
                nearestList = node;
            }
        }
        assertArrayEquals(nearestList.getValues(),nearestKdTree.getValues(),0);
    }
}
