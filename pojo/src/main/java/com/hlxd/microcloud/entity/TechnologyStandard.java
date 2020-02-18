package com.hlxd.microcloud.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 生产工艺标准 
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_technology_standard")
public class TechnologyStandard extends Model<TechnologyStandard> {

    private static final long serialVersionUID = 1L;

    /**
     * 指标代码
     */
    @TableId(value = "standard_code", type = IdType.UUID)
    private String standardCode;
    /**
     * 指标名称
     */
    @TableField("standard_name")
    private String standardName;
    /**
     * 所属工艺
     */
    @TableField("technology_code")
    private String technologyCode;
    
    /**
     * 指标类型 1.定量2.定性
     */
    @TableField("standard_type")
    private Integer standardType;
    /**
     * 单位
     */
    private String unit;

    @Override
    protected Serializable pkVal() {
        return this.standardCode;
    }

}
