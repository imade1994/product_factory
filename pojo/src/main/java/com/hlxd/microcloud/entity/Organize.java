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
 * 组织机构
 * </p>
 *
 * @author admin
 * @since 2019-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_organize")
public class Organize extends Model<Organize> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织代码
     */
    @TableId(value = "organize_code", type = IdType.UUID)
    private String organizeCode;
    /**
     * 组织名称
     */
    @TableField("organize_name")
    private String organizeName;
    /**
     * 组织类型 1.中烟2.集团3.工厂
     */
    @TableField("organize_type")
    private Integer organizeType;
    /**
     * 上级组织代码
     */
    @TableField("superior_organize_code")
    private String superiorOrganizeCode;
    
    @Override
    protected Serializable pkVal() {
        return this.organizeCode;
    }

}
