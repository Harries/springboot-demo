package com.et.neo4j.service;

import com.et.neo4j.entity.Node;
import com.et.neo4j.entity.Relation;
import com.et.neo4j.hanlp.MainPart;
import com.et.neo4j.hanlp.MainPartExtractor;
import com.et.neo4j.repository.NodeRepository;
import com.et.neo4j.repository.RelationRepository;
import com.et.neo4j.util.GraphUtil;
import edu.stanford.nlp.trees.TreeGraphNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sun.plugin.dom.core.Attr;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;
    private final RelationRepository relationRepository;


    @Override
    public Node save(Node node) {
        Node save = nodeRepository.save(node);
        return save;
    }
    @Override
    public void bind(String name1, String name2, String relationName) {
        Node start = nodeRepository.findByName(name1);
        Node end = nodeRepository.findByName(name2);

        Relation relation =new Relation();
        relation.setStartNode(start);
        relation.setEndNode(end);
        relation.setRelation(relationName);

        relationRepository.save(relation);
    }
    private Node addNode(TreeGraphNode treeGraphNode){

        String nodeName = GraphUtil.getNodeValue(treeGraphNode);

        Node existNode = nodeRepository.findByName(nodeName);
        if (Objects.nonNull(existNode))
            return existNode;

        Node node =new Node();
        node.setName(nodeName);
        return nodeRepository.save(node);
    }
    @Override
    public List<Relation> parseAndBind(String sentence) {
        MainPart mp = MainPartExtractor.getMainPart(sentence);

        TreeGraphNode subject = mp.getSubject();    //主语
        TreeGraphNode predicate = mp.getPredicate();//谓语
        TreeGraphNode object = mp.getObject();      //宾语

        if (Objects.isNull(subject) || Objects.isNull(object))
            return null;

        Node startNode = addNode(subject);
        Node endNode = addNode(object);
        String relationName = GraphUtil.getNodeValue(predicate);//关系词

        List<Relation> oldRelation = relationRepository
                .findRelation(startNode, endNode,relationName);
        if (!oldRelation.isEmpty())
            return oldRelation;

        Relation botRelation=new Relation();
        botRelation.setStartNode(startNode);
        botRelation.setEndNode(endNode);
        botRelation.setRelation(relationName);
        Relation relation = relationRepository.save(botRelation);

        return Arrays.asList(relation);
    }

}
