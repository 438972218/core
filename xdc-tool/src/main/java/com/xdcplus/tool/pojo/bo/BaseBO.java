package com.xdcplus.tool.pojo.bo;

import com.xdcplus.tool.validator.groupvlidator.IdGroupValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *  公共属性抽层类
 * @author Rong.Jia
 * @date 2019/08/14 14:57
 */
@Data
public class BaseBO implements Serializable {

    private static final long serialVersionUID = 9023375960996498537L;

    /**
     *  主键
     */
    @ApiModelProperty("主键ID")
    @NotNull(groups = IdGroupValidator.class, message = "id 不能为空")
    private Long id;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String updatedUser;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Long updatedTime;

    /**
     * 添加时间
     */
    @ApiModelProperty("添加时间")
    private Long createdTime;

    /**
     * 添加人
     */
    @ApiModelProperty("添加人")
    private String createdUser;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

}
