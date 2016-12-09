package com.spring.datasource.repository;

import org.springframework.data.annotation.QueryAnnotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Documented
@QueryAnnotation
public @interface Query {

    String value() default "";

    String fields() default "";

    boolean count() default false;

    boolean delete() default false;

    boolean allowLiteral() default false;

}
