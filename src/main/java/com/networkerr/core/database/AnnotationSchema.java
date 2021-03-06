package com.networkerr.core.database;

import com.networkerr.core.annotations.AnnotationsScanner;
import com.networkerr.core.annotations.SqlSchemaColumn;
import com.networkerr.core.annotations.SqlSchemaTable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Deprecated
public class AnnotationSchema extends AnnotationsScanner {
    private Set types;
    private static String seed;

    @Override
    protected void getMapFromMethods() {
        this.findAnnotations("com.networkerr.app", SqlSchemaColumn.class);
        // Get all Models annotated with SqlSchemaTable
        Collection<String> allModels = this.getAllOfClass(Model.class);
        Annotation[][] modelAnnotations = this.getAnnotationsFromClassCollection(allModels, SqlSchemaTable.class);
        // Get all Fields annotated
        Annotation[][] fieldAnnotations = this.getAnnotationsFromFields(allModels);
        String tableName = null;
        String references = null;
        String foreignKey = null;
        // create collection for all of the available tables
        int f = 0;
        Collection<Schema> tables = new ArrayList<>();
        for(Annotation[] annotate: modelAnnotations) {
            Annotation[] field = fieldAnnotations[f];
            for(Annotation ann: annotate) {
                if (ann.annotationType().equals(SqlSchemaTable.class)) {
                    try {
                        tableName = (String) ann.annotationType().getMethod("table").invoke(ann);
                        references = (String) ann.annotationType().getMethod("references").invoke(ann);
                        foreignKey = (String) ann.annotationType().getMethod("foreignKey").invoke(ann);
                        // add to tables Collection
                        Schema t = new Schema(tableName, references, foreignKey, field);
                        tables.add(t);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
            f++;
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
        // referenced tables need to be seeded first, to avoid MySql tables not exist exception on foreign keys
        Collection<Schema> tablesToSeed = new ArrayList<>();
        if (!existingRef.isEmpty()) {
            existingRef.forEach((Schema referencedSchema) -> {
                // add tables to seed list which have a reference
                tables.stream()
                        .filter(table -> table.getTableName().equals(referencedSchema.getTableName()))
                        .forEach((tablesToSeed)::add);
                // add non referenced tables
                tables.stream()
                        .filter(table -> !table.getTableName().equals(referencedSchema.getTableName()))
                        .forEach((tablesToSeed)::add);
            });
        }

        final String[] columnName = {null};
        final SQLTypes[] dataType = {null};
        final String[][] flags = {null};
        MySqlWriter writer = new MySqlWriter();
        // create writable sql string based on sorted collection
        tablesToSeed.forEach(table -> {
            writer.createTableBegin(table.getTableName());
            Annotation[] fields = table.getFields();
            boolean isLast = false;
            for(int i = 0; i < fields.length; i++) {
                isLast = i == fields.length - 1;
                Annotation ann = fields[i];
                System.out.println(ann.toString());
                if(ann.annotationType().equals(SqlSchemaColumn.class)) {
                    try {
                        columnName[0] = (String) ann.annotationType().getMethod("column").invoke(ann);
                        dataType[0] = (SQLTypes) ann.annotationType().getMethod("dataType").invoke(ann);
                        flags[0] = (String[]) ann.annotationType().getMethod("properties").invoke(ann);
                        writer.createTableColumn(columnName[0], isLast, dataType[0], flags[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (isLast) {
                writer.createTableEnd();
            }
        });
        String sql = writer.getStatement();
        System.out.println(sql);
//        this.setSeed(writer.getTableQuery());
    }

    protected String getSeed() {
        return seed;
    }

    private void setSeed(String seed) {
        AnnotationSchema.seed = seed;
    }
}
