package com.et.k8s;

import com.et.k8s.client.K8sClient;
import io.kubernetes.client.openapi.models.*;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class K8sClientTest {


    @Test
    public void getAllPodListTest() {
        String kubeConfigPath = "C:\\Users\\Dell\\.kube\\config";
        if (!new File(kubeConfigPath).exists()) {
            System.out.println("kubeConfig not exist，jump over");
            return;
        }
        K8sClient k8sClient = new K8sClient(kubeConfigPath);
        V1PodList podList = k8sClient.getAllPodList();
        for (V1Pod item : podList.getItems()) {
            System.out.println(item.getMetadata().getNamespace() + ":" + item.getMetadata().getName());
        }
    }


    @Test
    public void createServiceTest() {
        String kubeConfigPath = "C:\\Users\\Dell\\.kube\\config";
        if (!new File(kubeConfigPath).exists()) {
            System.out.println("kubeConfig不存在，跳过");
            return;
        }
        K8sClient k8sClient = new K8sClient(kubeConfigPath);
        String namespace = "default";
        String serviceName = "my-nginx-service";
        Integer port = 80;
        Map<String, String> selector = new HashMap<>();
        selector.put("run", "my-nginx");
        V1Service v1Service = k8sClient.createService(namespace, serviceName, port, selector);
        System.out.println(v1Service != null ? v1Service.getMetadata() : null);
    }

    @Test
    public void createV1IngressTest() {
        String kubeConfigPath = "C:\\Users\\Dell\\.kube\\config";
        if (!new File(kubeConfigPath).exists()) {
            System.out.println("kubeConfig不存在，跳过");
            return;
        }
        K8sClient k8sClient = new K8sClient(kubeConfigPath);
        String namespace = "default";
        String ingressName = "my-nginx-ingress";
        Map<String, String> annotations = new HashMap<>();
        annotations.put("nginx.ingress.kubernetes.io/rewrite-target", "/");
        String path = "/my-nginx";
        String serviceName = "my-nginx-service";
        Integer servicePort = 80;
        V1Ingress v1Ingress = k8sClient.createV1Ingress(namespace, ingressName, annotations, path, serviceName, servicePort);
        System.out.println(v1Ingress != null ? v1Ingress.getMetadata() : null);
    }

    @Test
    public void createExtensionIngressTest() {
        String kubeConfigPath = "C:\\Users\\Dell\\.kube\\config";
        if (!new File(kubeConfigPath).exists()) {
            System.out.println("kubeConfig不存在，跳过");
            return;
        }
        K8sClient k8sClient = new K8sClient(kubeConfigPath);
        String namespace = "default";
        String ingressName = "my-nginx-ingress";
        Map<String, String> annotations = new HashMap<>();
        annotations.put("nginx.ingress.kubernetes.io/rewrite-target", "/");
        String path = "/my-nginx";
        String serviceName = "my-nginx-service";
        Integer servicePort = 80;
        ExtensionsV1beta1Ingress extensionsV1beta1Ingress = k8sClient.createExtensionIngress(namespace, ingressName, annotations, path, serviceName, servicePort);
        System.out.println(extensionsV1beta1Ingress != null ? extensionsV1beta1Ingress.getMetadata() : null);
    }

}