package com.qiuhuu.test.entity.mybatis.slave;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author : qiuhuu
 * @date : 2020-11-11 10:29
 */
@ApiModel(value="com-qiuhuu-test-entity-mybatis-slave-User")
@Data
public class User implements Serializable {
    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="")
    private Integer age;

    @ApiModelProperty(value="")
    private String userName;

    private static final long serialVersionUID = 1L;
}