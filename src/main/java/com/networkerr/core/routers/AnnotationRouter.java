package com.networkerr.core.routers;

import com.networkerr.core.annotations.HttpEndpoint;
import com.networkerr.core.http.Endpoint;
import com.networkerr.core.http.NotFoundEndpoint;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationRouter {
    private Set<Method> methods;
    private ArrayList<Endpoint> endpoints = new ArrayList<>();
    public AnnotationRouter findAnnotation() {
        Reflections reflections = new Reflections(
          new ConfigurationBuilder().setUrls(
                  ClasspathHelper.forPackage("com.networkerr.app")
          ).setScanners(new MethodAnnotationsScanner())
        );
        this.methods = reflections.getMethodsAnnotatedWith(HttpEndpoint.class);
        return this;
    }

    public void getRouteMapFromMethods() {
        this.methods.forEach(method -> {
            for (Annotation anno: method.getAnnotations()) {
                if (anno.annotationType().equals(HttpEndpoint.class)) {
                    this.getEndpointsFromAnnotation(anno, method.getDeclaringClass().toString(), method.getName());
                }
            }
        });
    }

    private void getEndpointsFromAnnotation(Annotation anno, String clazz, String m) {
        try {
           String route = (String) anno.annotationType().getMethod("route").invoke(anno);
           String httpMethod = (String) anno.annotationType().getMethod("method").invoke(anno);
           int code = (int) anno.annotationType().getMethod("statusCode").invoke(anno);
           Endpoint endpoint = new Endpoint(route, httpMethod, code, clazz, m);
           this.endpoints.add(endpoint);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected Endpoint queryEndpoints(String route) {
        final Endpoint[] found = {null};
        this.endpoints.forEach(endpoint -> {
            if(route.equals(endpoint.getRoute())) {
                found[0] = endpoint;
            }
        });
        if (found[0] != null) {
            return found[0];
        }
        return new NotFoundEndpoint();
    }
}
