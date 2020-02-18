package com.hlxd.microcloud.entity;

import java.math.BigDecimal;
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
@TableName("t_pro_technology_standard_value")
public class TechnologyStandardValue extends Model<TechnologyStandardValue> {

    private static final long serialVersionUID = 1L;

    /**
     * 指标代码
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 指标代码
     */
    @TableField("standard_code")
    private String standardCode;
  
    /**
     * 卷烟规格
     */
    @TableField("cigarette_code")
    private String cigaretteCode;
    
    /**
     * 标准值
     */
    @TableField("standard_value")
    private BigDecimal standardValue;
    /**
     * 最大值
     */
    private BigDecimal maximum;
    /**
     * 最小值
     */
    private BigDecimal minimum;
    /**
     * 分数
     */
    private BigDecimal fraction;


    @Override
    protected Serializable pkVal() {
        return this.standardCode;
    }

}
