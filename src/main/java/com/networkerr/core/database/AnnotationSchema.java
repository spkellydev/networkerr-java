package com.networkerr.core.database;

import com.networkerr.core.annotations.AnnotationsScanner;
import com.networkerr.core.annotations.SqlSchemaColumn;
import com.networkerr.core.annotations.SqlSchemaTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Set;

public class AnnotationSchema extends AnnotationsScanner {
    private Set types;

    @Override
    protected void getMapFromMethods() {
        this.findAnnotations("com.networkerr.app", SqlSchemaColumn.class);
        Collection<String> allModels = this.getAllOfClass(Model.class);
        Annotation[] modelAnnotations = this.getAnnotationsFromClassCollection(allModels, SqlSchemaTable.class);
        Annotation[] fieldAnnotations = this.getAnnotationsFromFields(allModels);
        String tableName = null;
        String columnName = null;
        SQLTypes dataType = null;
        String[] flags = null;
        MySqlWriter writer = new MySqlWriter();
        for(Annotation ann: modelAnnotations) {
           if(ann.annotationType().equals(SqlSchemaTable.class)) {
               try {
                   tableName = (String) ann.annotationType().getMethod("table").invoke(ann);
                   writer.createTableBegin(tableName);
                   System.out.println(tableName);
               } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                   e.printStackTrace();
               }
           }
        }

        for(int i = 0; i < fieldAnnotations.length; i++) {
            Annotation ann = fieldAnnotations[i];
            boolean isLast = i == fieldAnnotations.length - 1;
            if(ann.annotationType().equals(SqlSchemaColumn.class)) {
                try {
                    columnName = (String) ann.annotationType().getMethod("column").invoke(ann);
                    dataType = (SQLTypes) ann.annotationType().getMethod("dataType").invoke(ann);
                    flags = (String[]) ann.annotationType().getMethod("properties").invoke(ann);
                    writer.createTableColumn(columnName, isLast, dataType, flags);
                    writer.createTableEnd();
                    String sql = writer.getStatement();
                    writer.addToTables(sql);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
