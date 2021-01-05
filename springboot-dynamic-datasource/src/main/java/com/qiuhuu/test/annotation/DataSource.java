package com.qiuhuu.test.annotation;

import com.qiuhuu.test.type.DataBaseType;

import java.lang.annotation.*;

/**
 * @author : qiuhuu
 * @date : 2020-11-11 15:47
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DataBaseType value() default DataBaseType.Master;
    //String value() default "master";
}
