package com.networkerr.core.annotations;

import com.networkerr.core.database.SQLTypes;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlSchemaColumn {
    String column();
    SQLTypes dataType();
    String[] properties() default {};
}
