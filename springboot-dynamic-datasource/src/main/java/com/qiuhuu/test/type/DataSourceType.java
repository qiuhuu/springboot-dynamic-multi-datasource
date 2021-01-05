package com.qiuhuu.test.type;

import lombok.Data;

/**
 * @author : qiuhuu
 * @date : 2020-11-11 15:36
 */
@Data
public class DataSourceType {

    /**
     * 使用ThreadLocal保证线程安全
      */
    private static final ThreadLocal<DataBaseType> TYPE = new ThreadLocal<DataBaseType>();

    public static void setDataBaseType(DataBaseType dataBaseType) {
        if (dataBaseType == null) {
            throw new NullPointerException();
        }
        TYPE.set(dataBaseType);
    }

    /**
     * 获取当前使用数据源
     * @return
     */
    public static DataBaseType getDataBaseType() {
        DataBaseType dataBaseType = TYPE.get() == null ? DataBaseType.Master : TYPE.get();
        return dataBaseType;
    }

    /**
     * 移除数据源
     */
    public static void clearDataBaseType() {
        TYPE.remove();
    }
}

