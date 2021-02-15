package com.outofbound.kdtreelib;

public class KdTree {

    private KdTree subtreeSx;
    private KdTree subtreeDx;
    private final Node root;
    private final int index;
    private Node parent;

    public KdTree(Node root){
        this(root,0);
    }

    private KdTree(Node root, int index){
        this.root = root;
        this.index = index;
        parent = null;
    }

    public void insert(Node node){
        if (node.size() != root.size()){
            throwDimensionError();
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
        return searchNearest(node,null);
    }

    private Node searchNearest(Node node, Node best){
        if (node.size() != root.size()){
            throwDimensionError();
        }
        if (best != null){
            /*if (node.distance(root) < node.distance(best)){
                best = root;
            }*/
            if ((node.get(index) - root.get(index)) * (node.get(index) - root.get(index)) >= node.distance(best)) {
                return best;
            }
            else {
                return getBest(node,best,searchNearest(node,null));
            }
        }
        if (node.get(index) < root.get(index)) {
            if (!hasSubtreeSx()) {
                best = root;
                if (hasSubtreeDx()){
                    return subtreeDx.searchNearest(node,best);
                }
                return best;
            }
            else {
                Node bestSx = subtreeSx.searchNearest(node,best);
                if (hasSubtreeDx()){
                    return subtreeDx.searchNearest(node,bestSx);
                }
                return bestSx;
            }
        } else {
            if (!hasSubtreeDx()) {
                best = root;
                if (hasSubtreeSx()){
                    return subtreeSx.searchNearest(node,best);
                }
                return best;
            }
            else {
                Node bestDx = subtreeDx.searchNearest(node,best);
                if (hasSubtreeSx()){
                    return subtreeSx.searchNearest(node,bestDx);
                }
                return bestDx;
            }
        }
    }

    private boolean hasSubtreeSx(){
        return subtreeSx != null;
    }

    private boolean hasSubtreeDx(){
        return subtreeDx != null;
    }

    private Node getBest(Node node, Node node1, Node node2){
        if (node.distance(node1) < node.distance(node2)){
            return node1;
        }
        return node2;
    }

    public Node searchNearestFast(Node node){
        return searchNearestFast(node,root);
    }

    private Node searchNearestFast(Node node, Node best){
        if (node.size() != root.size()){
            throwDimensionError();
        }
        if (node.distance(root) < node.distance(best)){
            best = root;
        }
        if (node.get(index) < root.get(index)) {
            if (subtreeSx == null) {
                return best;
            }
            else {
                return subtreeSx.searchNearestFast(node,best);
            }
        } else {
            if (subtreeDx == null) {
                return best;
            }
            else {
                return subtreeDx.searchNearestFast(node,best);
            }
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

    private void throwDimensionError(){
        throw new RuntimeException("node dimension != root dimension.");
    }
}
