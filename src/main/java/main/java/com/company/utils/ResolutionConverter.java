package main.java.com.company.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.java.com.company.models.NTree.Node;
import main.java.com.company.models.NTree.ResponsibilityTree;
import main.java.com.company.models.Resolution;

public class ResolutionConverter {
    public static ResponsibilityTree convertResolutionListToResponsibilitiesTree(List<Resolution> resolutionList) {
        List<ResponsibilityTree> additionalTrees = new ArrayList<>();
        Resolution firstResolutionAdd = resolutionList.get(0);
        ResponsibilityTree responsibilityTree = new ResponsibilityTree(firstResolutionAdd.getIdFrom(),
            new ArrayList<>(Arrays.asList(new Node(firstResolutionAdd.getIdTo()))), new ArrayList<>(Arrays.asList(1)));
        resolutionList.remove(firstResolutionAdd);
        while (resolutionList.size() != 0) {
            Resolution resolutionAdd = resolutionList.get(0);
            ResponsibilityTree addTree = new ResponsibilityTree();
            addTree.addNodeIndex(responsibilityTree.root, resolutionAdd.getIdFrom(), resolutionAdd.getIdTo());
            if (!addTree.found) {
                additionalTrees.add(new ResponsibilityTree(resolutionAdd.getIdFrom(),
                    new ArrayList<>(Arrays.asList(new Node(resolutionAdd.getIdTo())))));
            }
            resolutionList.remove(resolutionList.get(0));
        }
        return additionalTrees.size() != 0 ? collectIndividualNodesToOne(responsibilityTree, additionalTrees)
            : responsibilityTree;
    }

    public static ResponsibilityTree collectIndividualNodesToOne(ResponsibilityTree responsibilityTree,
                                                                 List<ResponsibilityTree> additionalTrees) {
        int maxCounter = additionalTrees.size() * additionalTrees.size();
        int currentCounter = 0;
        while (additionalTrees.size() != 0) {
            for (int i = 0; i < additionalTrees.size(); i++) {
                ResponsibilityTree resolutionAdd = additionalTrees.get(i);
                ResponsibilityTree addTree = new ResponsibilityTree();
                addTree.addNodeAfter(responsibilityTree.root, resolutionAdd.root);
                if (addTree.found) {
                    additionalTrees.remove(additionalTrees.get(i));
                } else {
                    addTree.addNodeAfter(resolutionAdd.root, responsibilityTree.root);
                    if (addTree.found) {
                        responsibilityTree = resolutionAdd;
                        additionalTrees.remove(additionalTrees.get(i));
                        break;
                    }
                }
                currentCounter++;
            }
            if (currentCounter >= maxCounter) {
                break;
            }
        }
        return additionalTrees.size() > 0 ? null : responsibilityTree;
    }
}
