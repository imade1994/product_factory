package com.hlxd.microcloud.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 车间表 
 * </p>
 *
 * @author admin
 * @since 2019-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_workshop")
public class Workshop extends Model<Workshop> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 车间名称
     */
    @TableField("workshop_name")
    private String workshopName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    public Workshop() {};
    
    public Workshop(String workshopName) {
    	this.workshopName = workshopName;
    }

}
