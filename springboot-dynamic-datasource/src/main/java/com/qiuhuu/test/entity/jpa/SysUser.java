package com.qiuhuu.test.entity.jpa;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : qiuhuu
 * @date : 2020-11-11 10:27
 */
@Data
@Entity(name = "SysUser")
public class SysUser implements Serializable {
    @Id
    @ApiModelProperty(value="")
    private Integer id;

    /**
    * 账号
    */
    @ApiModelProperty(value="账号")
    private String username;

    /**
    * 密码
    */
    @ApiModelProperty(value="密码")
    private String password;

    /**
    * 昵称
    */
    @ApiModelProperty(value="昵称")
    private String nickname;

    /**
    * 邮箱
    */
    @ApiModelProperty(value="邮箱")
    private String email;

    /**
    * 状态（0：锁定，1：解锁）
    */
    @ApiModelProperty(value="状态（0：锁定，1：解锁）")
    private Byte status;

    @ApiModelProperty(value="")
    private String createUser;

    @ApiModelProperty(value="")
    private Date createTime;

    @ApiModelProperty(value="")
    private String updateUser;

    @ApiModelProperty(value="")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}