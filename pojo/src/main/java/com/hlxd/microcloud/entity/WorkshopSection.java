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
 * 生产工段 
 * </p>
 *
 * @author admin
 * @since 2019-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_workshop_section")
public class WorkshopSection extends Model<WorkshopSection> {

    private static final long serialVersionUID = 1L;

    /**
     * 工段代码
     */
    @TableId(value = "workshop_section_code", type = IdType.UUID)
    private String workshopSectionCode;
    /**
     * 工段名称
     */
    @TableField("workshop_section_name")
    private String workshopSectionName;

    /**
     * 所属工厂
     */
    @TableField("organize_code")
    private String organizeCode;
    
    /**
     * 所属车间
     */
    @TableField("technology_workshop")
    private Integer technologyWorkshop;
    
    @Override
    protected Serializable pkVal() {
        return this.workshopSectionCode;
    }

}
