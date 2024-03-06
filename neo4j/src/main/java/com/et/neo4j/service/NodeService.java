package com.et.neo4j.service;

import com.et.neo4j.entity.Node;
import com.et.neo4j.entity.Relation;
import edu.stanford.nlp.trees.TreeGraphNode;

import java.util.List;

public interface NodeService {
    public Node save(Node node);
    public void bind(String name1, String name2, String relationName);
    public List<Relation> parseAndBind(String sentence);
}
