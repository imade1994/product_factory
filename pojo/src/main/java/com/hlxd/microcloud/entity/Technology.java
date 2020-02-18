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
 * 生产工艺 
 * </p>
 *
 * @author admin
 * @since 2019-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_technology")
public class Technology extends Model<Technology> {

    private static final long serialVersionUID = 1L;

    /**
     * 工艺代码
     */
    @TableId(value = "technology_code", type = IdType.UUID)
    private String technologyCode;
    /**
     * 工艺名称
     */
    @TableField("technology_name")
    private String technologyName;
    /**
     * 工艺车间 1制丝 / 2成型 / 3卷包
     */
    @TableField("technology_workshop")
    private Integer technologyWorkshop;
    /**
     * 工厂代码
     */
    @TableField("organize_code")
    private String organizeCode;
    
    /**
     * 工段代码
     */
    @TableField("workshop_section_code")
    private String workshopSectionCode;


    @Override
    protected Serializable pkVal() {
        return this.technologyCode;
    }

}
