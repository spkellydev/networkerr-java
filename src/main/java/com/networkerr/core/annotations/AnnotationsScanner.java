package com.networkerr.core.annotations;

import com.networkerr.core.database.Model;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AnnotationsScanner should house the logic for finding annotations via the Reflections library API.
 */
public abstract class AnnotationsScanner {
    private Reflections reflections;
    protected void findAnnotations(String namespace, Class<? extends Annotation> clazz) {
        this.reflections = new Reflections(
          new ConfigurationBuilder().setUrls(
                  ClasspathHelper.forPackage(namespace)
          ).setScanners(new MethodAnnotationsScanner()).setScanners(new SubTypesScanner(false))
        );
    }

    protected Set<Method> getMethods(Class<? extends Annotation> clazz) {
        return this.reflections.getMethodsAnnotatedWith(clazz);
    }

    protected Collection<String> getAllOfClass(Class<? extends Model> clazz) {
        Reflections reflectionss = new Reflections("com.networkerr.app", new SubTypesScanner(false));
        return reflectionss.getStore().get(SubTypesScanner.class).get(clazz.getName());
    }

    protected Annotation[][] getAnnotationsFromClassCollection(Collection<String> allClasses, Class<? extends Annotation> clazz) {
        final Annotation[][] annotations = new Annotation[allClasses.size()][1];
        AtomicInteger i = new AtomicInteger();
        int l = allClasses.size();
        allClasses.forEach(a -> {
           try {
               annotations[i.get()] = Class.forName(a).getDeclaredAnnotations();
               i.getAndIncrement();
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }
       });
        return annotations;
    }

    protected Annotation[][] getAnnotationsFromFields(Collection<String> allModels) {
        final Field[][] extended = new Field[allModels.size()][1];
        final Annotation[][] annotations = new Annotation[allModels.size()][1];
        AtomicInteger i = new AtomicInteger();
        allModels.forEach(model -> {
            try {
                extended[i.get()] = Class.forName(model).getDeclaredFields();
                for(Field e : extended[i.get()]) {
                    annotations[i.get()] = e.getDeclaredAnnotations();
                }
                i.incrementAndGet();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return annotations;
    }

    protected abstract void getMapFromMethods();
}
