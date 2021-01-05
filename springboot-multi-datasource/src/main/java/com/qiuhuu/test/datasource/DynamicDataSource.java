package com.qiuhuu.test.datasource;

import com.qiuhuu.test.type.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author : qiuhuu
 * @date : 2020-11-11 15:30
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceType.DataBaseType dataBaseType = DataSourceType.getDataBaseType();
        return dataBaseType;
    }
}
