package com.qiuhuu.test.entity.jpa;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author : qiuhuu
 * @date : 2020-11-11 10:29
 */
@Data
@Entity(name = "User")
public class User implements Serializable {
    @Id
    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="")
    private Integer age;

    @ApiModelProperty(value="")
    private String userName;

    private static final long serialVersionUID = 1L;
}