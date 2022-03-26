package main.java.com.company.models;

import java.util.ArrayList;
import java.util.List;

public class NTree {

    public static class Node {
        public int id;
        public List<Node> responsible;
        public List<Integer> valueAddress = new ArrayList<>();

        public Node(int index) {
            id = index;
            responsible = new ArrayList<>();
        }

        public Node(int index, List<Node> nodes) {
            id = index;
            responsible = nodes;
        }

        public Node(int id, List<Node> responsible, List<Integer> valueAddress) {
            this.id = id;
            this.responsible = responsible;
            this.valueAddress = valueAddress;
        }

        public int getId() {
            return id;
        }

        public List<Node> getResponsible() {
            return responsible;
        }

        public List<Integer> getValueAddress() {
            return valueAddress;
        }

        public void setValueAddress(List<Integer> valueAddress) {
            this.valueAddress = valueAddress;
        }
    }

    public static class ResponsibilityTree {
        public Node root;
        public boolean found;
        public List<Integer> valueAddress = new ArrayList<>();

        public ResponsibilityTree() {
        }

        public ResponsibilityTree(int id, List<Node> nodes) {
            root = new Node(id, nodes);
        }

        public ResponsibilityTree(int id, List<Node> nodes, List<Integer> valueAddress) {
            root = new Node(id, nodes, valueAddress);
        }

        public void addNodeIndex(Node node, int findIndex, int newNodeIndex) {
            if (node.responsible.size() > 0) {
                if (node.id == findIndex) {
                    found = node.responsible.add(new Node(newNodeIndex));
                    return;
                }
                for (Node n : node.responsible) {
                    if (n.id == findIndex) {
                        found = n.getResponsible().add(new Node(newNodeIndex));
                        return;
                    }
                    addNodeIndex(n, findIndex, newNodeIndex);
                }
            }
        }

        public void addNodeAfter(Node node, Node newNode) {
            if (node.responsible.size() > 0) {
                if (node.id == newNode.id) {
                    for (Node c : newNode.getResponsible()) {
                        found = node.responsible.add(c);
                    }
                    return;
                }
                for (Node n : node.responsible) {
                    if (n.id == newNode.getId()) {
                        for (Node c : newNode.getResponsible()) {
                            found = n.getResponsible().add(c);
                        }
                        return;
                    }
                    addNodeAfter(n, newNode);
                }
            }
        }

        public void findNode(Node node, int findIndex) {
            if (node.id == findIndex) {
                found = true;
                valueAddress = node.getValueAddress();
                return;
            }

            for (Node responsible : node.responsible) {
                findNode(responsible, findIndex);
            }
        }

        public void makeNodeIndexes(Node node) {
            if (node.responsible.size() > 0) {
                List<Node> responsibles = node.responsible;
                for (int i = 0; i < responsibles.size(); i++) {
                    if (responsibles.get(i).getValueAddress().size() == 0) {
                        List<Integer> valueAddress = new ArrayList<>(node.getValueAddress());
                        valueAddress.add(i + 1);
                        responsibles.get(i).setValueAddress(valueAddress);
                    }
                }
                for (Node responsible : responsibles) {
                    makeNodeIndexes(responsible);
                }
            }
        }
    }
}


