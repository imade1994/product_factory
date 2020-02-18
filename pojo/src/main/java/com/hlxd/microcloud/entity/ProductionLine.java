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

/***
 * 工艺生产线 
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月19日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_production_line")
public class ProductionLine extends Model<ProductionLine> {

    private static final long serialVersionUID = 1L;

    /**
     * 产线代码
     */
    @TableId(value = "production_line_code", type = IdType.UUID)
    private String productionLineCode;
    /**
     * 产线名称
     */
    @TableField("production_line_name")
    private String productionLineName;
    /**
     * 工厂代码
     */
    @TableField("organize_code")
    private String organizeCode;
    /**
     * 工艺车间 1制丝 / 2成型 / 3卷包
     */
    @TableField("technology_workshop")
    private Integer technologyWorkshop;
    /**
     * 排序号
     */
    @TableField("serial_number")
    private Integer serialNumber;


    @Override
    protected Serializable pkVal() {
        return this.productionLineCode;
    }

}
