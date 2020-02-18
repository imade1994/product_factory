package com.hlxd.microcloud.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 工艺生产线工艺 
 * </p>
 *
 * @author admin
 * @since 2019-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_production_line_technology")
public class ProductionLineTechnology extends Model<ProductionLineTechnology> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 工艺线代码
     */
    @TableField("production_line_code")
    private String productionLineCode;
    /**
     * 工艺代码
     */
    @TableField("technology_code")
    private String technologyCode;
    /**
     * 工序号
     */
    @TableField("serial_number")
    private Integer serialNumber;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
