package com.networkerr.core.routers;

import com.networkerr.core.annotations.HttpEndpoint;
import com.networkerr.core.http.DerivedEndpoint;
import com.networkerr.core.http.FallbackDerivedEndpoint;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

class AnnotationRouter {
    public static final FallbackDerivedEndpoint $500 = new FallbackDerivedEndpoint("/500", "GET", 500);
    public static final FallbackDerivedEndpoint $302 = new FallbackDerivedEndpoint("/302", "GET", 302);

    private Set<Method> methods;
    private ArrayList<DerivedEndpoint> endpoints = new ArrayList<>();
    protected AnnotationRouter findAnnotation() {
        Reflections reflections = new Reflections(
          new ConfigurationBuilder().setUrls(
                  ClasspathHelper.forPackage("com.networkerr.app")
          ).setScanners(new MethodAnnotationsScanner())
        );
        this.methods = reflections.getMethodsAnnotatedWith(HttpEndpoint.class);
        return this;
    }

    protected void getRouteMapFromMethods() {
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
           DerivedEndpoint derivedEndpoint = new DerivedEndpoint(route, httpMethod, code, clazz, m);
           this.endpoints.add(derivedEndpoint);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected DerivedEndpoint queryEndpoints(String route, String method) {
        DerivedEndpoint[] found = {null};
        this.endpoints.forEach(endpoint -> {
            if(route.equals(endpoint.getRoute())) {
                found[0] = endpoint;
            }
        });
        if (found[0] != null) {
            if(method.equals(found[0].getMethod())) {
                return found[0];
            }
            if(found[0].getCode() == 302) {
                System.out.println("302");
                return $302;
            }
            return this.queryEndpoints("/403", "GET");
        }
        return this.queryEndpoints("/404", "GET");
    }
}
