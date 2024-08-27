package com.et.k8s.client;

import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.openapi.apis.NetworkingV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


@Slf4j
public class K8sClient {

    private ApiClient apiClient;

    /**
     * 构建集群POD内通过SA访问的客户端
     * loading the in-cluster config, including:
     * 1. service-account CA
     * 2. service-account bearer-token
     * 3. service-account namespace
     * 4. master endpoints(ip, port) from pre-set environment variables
     */
    public K8sClient() {
        try {
            this.apiClient = ClientBuilder.cluster().build();
        } catch (IOException e) {
            log.error("构建K8s-Client异常", e);
            throw new RuntimeException("构建K8s-Client异常");
        }
    }

    /**
     * 构建集群外通过UA访问的客户端
     * loading the out-of-cluster config, a kubeconfig from file-system
     *
     * @param kubeConfigPath kube连接配置文件
     */
    public K8sClient(String kubeConfigPath) {
        try {
            this.apiClient = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        } catch (IOException e) {
            log.error("读取kubeConfigPath异常", e);
            throw new RuntimeException("读取kubeConfigPath异常");
        } catch (Exception e) {
            log.error("构建K8s-Client异常", e);
            throw new RuntimeException("构建K8s-Client异常");
        }
    }

    /**
     * 获取所有的Pod
     *
     * @return podList
     */
    public V1PodList getAllPodList() {
        // new a CoreV1Api
        CoreV1Api api = new CoreV1Api(apiClient);

        // invokes the CoreV1Api client
        try {
            V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
            return list;
        } catch (ApiException e) {
            log.error("获取podlist异常:" + e.getResponseBody(), e);
        }
        return null;
    }

    /**
     * 创建k8s service
     *
     * @param namespace   命名空间
     * @param serviceName 服务名称
     * @param port        服务端口号（和目标pod的端口号一致）
     * @param selector    pod标签选择器
     * @return 创建成功的service对象
     */
    public V1Service createService(String namespace, String serviceName, Integer port, Map<String, String> selector) {
        //构建service的yaml对象
        V1Service svc = new V1ServiceBuilder()
                .withNewMetadata()
                .withName(serviceName)
                .endMetadata()
                .withNewSpec()
                .addNewPort()
                .withProtocol("TCP")
                .withPort(port)
                .withTargetPort(new IntOrString(port))
                .endPort()
                .withSelector(selector)
                .endSpec()
                .build();

        // Deployment and StatefulSet is defined in apps/v1, so you should use AppsV1Api instead of CoreV1API
        CoreV1Api api = new CoreV1Api(apiClient);
        V1Service v1Service = null;
        try {
            v1Service = api.createNamespacedService(namespace, svc, null, null, null);
        } catch (ApiException e) {
            log.error("创建service异常:" + e.getResponseBody(), e);
        } catch (Exception e) {
            log.error("创建service系统异常:", e);
        }
        return v1Service;
    }

    /**
     * 创建k8s V1Ingress
     *
     * @param namespace   命名空间
     * @param ingressName ingress名称
     * @param annotations ingress注解
     * @param path        匹配的路径
     * @param serviceName 路由到的服务名称
     * @param servicePort 路由到的服务端口
     * @return 创建成功的ingress对象
     */
    public V1Ingress createV1Ingress(String namespace, String ingressName, Map<String, String> annotations, String path,
                                     String serviceName, Integer servicePort) {
        //构建ingress的yaml对象
        V1Ingress ingress = new V1IngressBuilder()
                .withNewMetadata()
                .withName(ingressName)
                .withAnnotations(annotations)
                .endMetadata()
                .withNewSpec()
                .addNewRule()
                .withHttp(new V1HTTPIngressRuleValueBuilder().addToPaths(new V1HTTPIngressPathBuilder()
                        .withPath(path)
                        .withPathType("Prefix")
                        .withBackend(new V1IngressBackendBuilder()
                                .withService(new V1IngressServiceBackendBuilder()
                                        .withName(serviceName)
                                        .withPort(new V1ServiceBackendPortBuilder()
                                                .withNumber(servicePort).build()).build()).build()).build()).build())
                .endRule()
                .endSpec()
                .build();

        //调用对应的API执行创建ingress的操作
        NetworkingV1Api api = new NetworkingV1Api(apiClient);
        V1Ingress v1Ingress = null;
        try {
            v1Ingress = api.createNamespacedIngress(namespace, ingress, null, null, null);
        } catch (ApiException e) {
            log.error("创建ingress异常:" + e.getResponseBody(), e);
        } catch (Exception e) {
            log.error("创建ingress系统异常:", e);
        }
        return v1Ingress;
    }


    /**
     * 创建k8s ExtensionIngress
     *
     * @param namespace   命名空间
     * @param ingressName ingress名称
     * @param annotations ingress注解
     * @param path        匹配的路径
     * @param serviceName 路由到的服务名称
     * @param servicePort 路由到的服务端口
     * @return 创建成功的ingress对象
     */
    public ExtensionsV1beta1Ingress createExtensionIngress(String namespace, String ingressName, Map<String, String> annotations, String path,
                                                           String serviceName, Integer servicePort) {
        //构建ingress的yaml对象
        ExtensionsV1beta1Ingress ingress = new ExtensionsV1beta1IngressBuilder()
                .withNewMetadata()
                .withName(ingressName)
                .withAnnotations(annotations)
                .endMetadata()
                .withNewSpec()
                .addNewRule()
                .withHttp(new ExtensionsV1beta1HTTPIngressRuleValueBuilder().addToPaths(new ExtensionsV1beta1HTTPIngressPathBuilder()
                        .withPath(path)
                        .withBackend(new ExtensionsV1beta1IngressBackendBuilder()
                                .withServiceName(serviceName)
                                .withServicePort(new IntOrString(servicePort)).build()).build()).build())
                .endRule()
                .endSpec()
                .build();

        //调用对应的API执行创建ingress的操作
        ExtensionsV1beta1Api api = new ExtensionsV1beta1Api(apiClient);
        ExtensionsV1beta1Ingress extensionsV1beta1Ingress = null;
        try {
            extensionsV1beta1Ingress = api.createNamespacedIngress(namespace, ingress, null, null, null);
        } catch (ApiException e) {
            log.error("创建ingress异常:" + e.getResponseBody(), e);
        } catch (Exception e) {
            log.error("创建ingress系统异常:", e);
        }
        return extensionsV1beta1Ingress;
    }
}