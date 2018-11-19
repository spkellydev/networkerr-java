package com.networkerr.core.database;

import com.networkerr.core.annotations.AnnotationsScanner;
import com.networkerr.core.annotations.SqlSchemaColumn;
import com.networkerr.core.annotations.SqlSchemaTable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotationSchema extends AnnotationsScanner {
    private Set types;
    private static String seed;

    @Override
    protected void getMapFromMethods() {
        this.findAnnotations("com.networkerr.app", SqlSchemaColumn.class);
        Collection<String> allModels = this.getAllOfClass(Model.class);
        Annotation[][] modelAnnotations = this.getAnnotationsFromClassCollection(allModels, SqlSchemaTable.class);
        Annotation[] fieldAnnotations = this.getAnnotationsFromFields(allModels);
        String tableName = null;
        String references = null;
        String foreignKey = null;
        String columnName = null;
        SQLTypes dataType = null;
        String[] flags = null;
        MySqlWriter writer = new MySqlWriter();
        // create hashmap for all of the available tables
        Collection<Schema> tables = new ArrayList<>();
        for(Annotation[] annotate: modelAnnotations) {
            for(Annotation ann: annotate) {
                if (ann.annotationType().equals(SqlSchemaTable.class)) {
                    try {
                        tableName = (String) ann.annotationType().getMethod("table").invoke(ann);
                        references = (String) ann.annotationType().getMethod("references").invoke(ann);
                        foreignKey = (String) ann.annotationType().getMethod("foreignKey").invoke(ann);
                        // add to tables Collection
                        Schema t = new Schema(tableName, references, foreignKey);
                        tables.add(t);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // create final string for lambda reference check
        final String finalReferences = references;
        // filter current collection state to see if a table name equals current "references" annotation
        Stream<Schema> referenced = tables.stream()
                .filter((Schema table) -> table
                        .getTableName()
                        .equals(finalReferences)
                );
        // create immutable list of existing references
        List<Schema> existingRef = Collections.unmodifiableList(
                // filter out nulls and collect referenced annotations
                referenced.filter(Objects::nonNull).collect(Collectors.toList())
        );
        if (!existingRef.isEmpty()) {
            existingRef.forEach((Schema referencedSchema) -> {
//                System.out.println(referencedSchema.getTableName());
                
            });
        }

//
//        for(int i = 0; i < fieldAnnotations.length; i++) {
//            Annotation ann = fieldAnnotations[i];
//            boolean isLast = i == fieldAnnotations.length - 1;
//            if(ann.annotationType().equals(SqlSchemaColumn.class)) {
//                try {
//                    columnName = (String) ann.annotationType().getMethod("column").invoke(ann);
//                    dataType = (SQLTypes) ann.annotationType().getMethod("dataType").invoke(ann);
//                    flags = (String[]) ann.annotationType().getMethod("properties").invoke(ann);
//                    writer.createTableColumn(columnName, isLast, dataType, flags);
//                    writer.createTableEnd();
//                    String sql = writer.getStatement();
//                    writer.addToTables(sql);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        this.setSeed(writer.getTableQuery());
    }

    protected String getSeed() {
        return seed;
    }

    private void setSeed(String seed) {
        AnnotationSchema.seed = seed;
    }
}
