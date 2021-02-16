package com.outofbound.kdtreelib;

public class KdTree {

    private KdTree subtreeSx;
    private KdTree subtreeDx;
    private final Node root;
    private final int index;

    public KdTree(Node root){
        this(root,0);
    }

    private KdTree(Node root, int index){
        this.root = root;
        this.index = index;
    }

    public void insert(Node node){
        if (node.size() != root.size()){
            throwDimensionError();
        }
        if (node.get(index) < root.get(index)) {
            if (subtreeSx == null) {
                subtreeSx = new KdTree(node,nextIndex(index));
            }
            else {
                subtreeSx.insert(node);
            }
        } else {
            if (subtreeDx == null) {
                subtreeDx = new KdTree(node,nextIndex(index));
            }
            else {
                subtreeDx.insert(node);
            }
        }
    }

    public Node searchNearest(Node node){
        if (node.size() != root.size()){
            throwDimensionError();
        }
        if (node.get(index) < root.get(index)) {
            if (!hasSubtreeSx()) {
                Node bestSx = root;
                if (hasSubtreeDx()){
                    return getBest(node,bestSx,subtreeDx.searchNearest(node));
                }
                return bestSx;
            }
            else {
                Node bestSx = subtreeSx.searchNearest(node);
                if (hasSubtreeDx() && (node.get(index) - root.get(index)) * (node.get(index) - root.get(index)) < node.distance(bestSx)) {
                    Node bestDx = subtreeDx.searchNearest(node);
                    return getBest(node,root,getBest(node,bestSx,bestDx));
                }
                return getBest(node,root,bestSx);
            }
        } else {
            if (!hasSubtreeDx()) {
                Node bestDx = root;
                if (hasSubtreeSx()){
                    return getBest(node,bestDx,subtreeSx.searchNearest(node));
                }
                return bestDx;
            }
            else {
                Node bestDx = subtreeDx.searchNearest(node);
                if (hasSubtreeSx() && (node.get(index) - root.get(index)) * (node.get(index) - root.get(index)) < node.distance(bestDx)) {
                    Node bestSx = subtreeSx.searchNearest(node);
                    return getBest(node,root,getBest(node,bestDx,bestSx));
                }
                return getBest(node,root,bestDx);
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
