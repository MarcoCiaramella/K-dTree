package com.outofbound.kdtreelib;

public class KdTree {

    private KdTree subtreeSx;
    private KdTree subtreeDx;
    private final Node root;
    private final int index;
    private Node parent;

    public KdTree(Node root, int index){
        this.root = root;
        this.index = index;
        parent = null;
    }

    public void insert(Node node){
        if (node.size() != root.size()){
            throw new RuntimeException("node dimension != root dimension.");
        }
        if (node.get(index) < root.get(index)) {
            if (subtreeSx == null) {
                subtreeSx = new KdTree(node,nextIndex(index));
                subtreeSx.parent = root;
            }
            else {
                subtreeSx.insert(node);
            }
        } else {
            if (subtreeDx == null) {
                subtreeDx = new KdTree(node,nextIndex(index));
                subtreeDx.parent = root;
            }
            else {
                subtreeDx.insert(node);
            }
        }
    }

    public Node searchNearest(Node node){
        if (node.size() != root.size()){
            throw new RuntimeException("node dimension != root dimension.");
        }
        if (node.get(index) < root.get(index)) {
            if (subtreeSx == null) {
                return getNearest(node,parent,root);
            }
            else {
                return subtreeSx.searchNearest(node);
            }
        } else {
            if (subtreeDx == null) {
                return getNearest(node,parent,root);
            }
            else {
                return subtreeDx.searchNearest(node);
            }
        }
    }

    private Node getNearest(Node node, Node root, Node leaf){
        float dist1 = node.distance(root);
        float dist2 = node.distance(leaf);
        if (dist1 < dist2){
            return root;
        }
        else {
            return leaf;
        }
    }

    private int nextIndex(int index){
        return (index + 1) % root.size();
    }

    public Node getRoot(){
        return root;
    }

    public KdTree getSubtreeSx() {
        return subtreeSx;
    }

    public KdTree getSubtreeDx() {
        return subtreeDx;
    }
}
