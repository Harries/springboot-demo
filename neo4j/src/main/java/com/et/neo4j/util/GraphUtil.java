package com.et.neo4j.util;

import edu.stanford.nlp.trees.TreeGraphNode;

import java.util.Objects;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName GraphUtil
 * @Description todo
 * @date 2024年03月06日 13:33
 */

public class GraphUtil {

    public static String getNodeValue(TreeGraphNode treeGraphNode){
        if (Objects.nonNull(treeGraphNode))
            return treeGraphNode.toString("value");
        return null;
    }


}
