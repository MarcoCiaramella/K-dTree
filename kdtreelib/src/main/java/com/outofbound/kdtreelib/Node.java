package com.outofbound.kdtreelib;

public class Node {

    private final float[] values;
    private Node res;

    public Node(float... values){
        this.values = values;
        res = new Node(values.length);
    }

    private Node(int size){
        values = new float[size];
    }

    public float get(int index){
        return values[index];
    }

    public int size(){
        return values.length;
    }

    public float[] getValues() {
        return values;
    }

    public float distance(Node node){
        return sub(node).length();
    }

    private Node sub(Node node){
        for (int i = 0; i < res.values.length; i++){
            res.values[i] = values[i] - node.values[i];
        }
        return res;
    }

    private float length(){
        float sum = 0;
        for (float value : values){
            sum += value*value;
        }
        return sum;
    }
}
