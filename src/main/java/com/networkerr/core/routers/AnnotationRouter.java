package com.networkerr.core.routers;

import com.networkerr.core.annotations.HttpEndpoint;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.Set;

public class AnnotationRouter {
    public void findAnnotation() {
        Reflections reflections = new Reflections(
          new ConfigurationBuilder().setUrls(
                  ClasspathHelper.forPackage("com.networkerr.app")
          ).setScanners(
                  new MethodAnnotationsScanner()
          )
        );
        Set<Method> methods = reflections.getMethodsAnnotatedWith(HttpEndpoint.class);
        System.out.println(methods);
    }
}
