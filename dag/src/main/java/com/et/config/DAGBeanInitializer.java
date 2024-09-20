package com.et.config;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class DAGBeanInitializer implements BeanFactoryPostProcessor {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            beanDefinitionMap.put(beanName, beanDefinition);
        }

        // build DAG
        DirectedAcyclicGraph<String, DefaultEdge> dag = buildDAG(beanDefinitionMap,beanFactory);

        // bean layers
        List<Set<String>> layers = getBeansByLayer(dag);
        System.out.println("layers:"+layers);
        // init bean by layers
        initializeBeansInLayers(layers, beanFactory);
    }

    // DAG Bean
    private DirectedAcyclicGraph<String, DefaultEdge> buildDAG(Map<String, BeanDefinition> beanDefinitionMap, ConfigurableListableBeanFactory beanFactory) {
        DependencyResolver resolver = new DependencyResolver(beanFactory);
        DirectedAcyclicGraph<String, DefaultEdge> dag = new DirectedAcyclicGraph<>(DefaultEdge.class);
        for (String beanName : beanDefinitionMap.keySet()) {
            if(shouldLoadBean(beanName)) {
                dag.addVertex(beanName);
                String[] dependencies = beanDefinitionMap.get(beanName).getDependsOn();
                if (dependencies != null) {
                    for (String dependency : dependencies) {
                        dag.addEdge(dependency, beanName); // 使用 addEdge 方法将依赖关系加到 DAG 中
                    }
                }
                // get @Autowired dependencies
                Set<String> autowireDependencies = resolver.getAllDependencies(beanName);
                for (String autowireDependency : autowireDependencies) {
                    // convert beanName
                    String autowireBeanName = convertToBeanName(autowireDependency);
                    dag.addVertex(autowireBeanName);
                    dag.addEdge(autowireBeanName, beanName);
                }
            }
        }
        return dag;
    }
    private String convertToBeanName(String className) {
        String simpleName = className.substring(className.lastIndexOf('.') + 1);
        return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }
    private List<Set<String>> getBeansByLayer(DirectedAcyclicGraph<String,DefaultEdge> dag) {
        List<Set<String>> layers = new ArrayList<>();
        Map<String, Integer> inDegree = new HashMap<>();
        Queue<String> queue = new LinkedList<>();

        // init all nodes degree
        for (String vertex : dag) {
            int degree = dag.inDegreeOf(vertex);
            inDegree.put(vertex, degree);
            if (degree == 0) {
                queue.offer(vertex);  //zero degree as the first layer
            }
        }

        // BFS process everyLayers
        while (!queue.isEmpty()) {
            Set<String> currentLayer = new HashSet<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currentBean = queue.poll();
                currentLayer.add(currentBean);

                // iterator layers
                for (String successor : getSuccessors(dag,currentBean)) {
                    inDegree.put(successor, inDegree.get(successor) - 1);
                    if (inDegree.get(successor) == 0) {
                        queue.offer(successor);  // add next layer when the degress is zero
                    }
                }
            }
            layers.add(currentLayer);
        }

        return layers;
    }
    // get next node
    private Set<String> getSuccessors(DirectedAcyclicGraph<String, DefaultEdge> dag, String vertex) {
        // get outgoingEdges
        Set<DefaultEdge> outgoingEdges = dag.outgoingEdgesOf(vertex);

        // find the next node
        return outgoingEdges.stream()
                .map(edge -> dag.getEdgeTarget(edge))
                .collect(Collectors.toSet());
    }
    // init beans by layer
    private void initializeBeansInLayers(List<Set<String>> layers, ConfigurableListableBeanFactory beanFactory) {
        for (Set<String> layer : layers) {
            // Beans of the same layer can be initialized in parallel
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (String beanName : layer) {
                // only load beans that  wrote by yourself
                if (shouldLoadBean(beanName)) {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        try {
                            beanFactory.getBean(beanName);  // init Bean
                        } catch (Exception e) {
                            System.err.println("Failed to initialize bean: " + beanName);
                            e.printStackTrace();
                        }
                    }, executorService);
                    futures.add(future);
                }
            }
            //Wait for all beans in the current layer to be initialized before initializing the next layer.
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.join();  // make sure to be done on current layer
        }
    }

    private boolean shouldLoadBean(String beanName) {
        return beanName.startsWith("helloWorldController")
                ||beanName.startsWith("serviceOne")
                ||beanName.startsWith("serviceTwo")
                ||beanName.startsWith("serviceThree");
    }
}
