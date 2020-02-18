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
 * 设备 
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_equipment")
public class Equipment extends Model<Equipment> {

    private static final long serialVersionUID = 1L;
    /**
     * 设备代码
     */
    @TableId(value = "equipment_code",type = IdType.UUID)
    private String equipmentCode;
    /**
     * 设备名称
     */
    @TableField("equipment_name")
    private String equipmentName;
    /**
     * 父级代码
     */
    @TableField("superior_equipment_code")
    private String superiorEquipmentCode;
    /**
     * 设备类型
     */
    @TableField("equipment_type")
    private Integer equipmentType;
    
    /**
     * 所属工厂
     */
    @TableField("organize_code")
    private String organizeCode;

    @Override
    protected Serializable pkVal() {
        return this.equipmentCode;
    }
    
	public Equipment() {}

	public Equipment(String equipmentCode, String equipmentName) {
		super(); 
		this.equipmentCode = equipmentCode;
		this.equipmentName = equipmentName;
	}
	
	public Equipment(String equipmentName, String superiorEquipmentCode, Integer equipmentType,
			String organizeCode) {
		super(); 
		this.equipmentName = equipmentName;
		this.superiorEquipmentCode = superiorEquipmentCode;
		this.equipmentType = equipmentType;
		this.organizeCode = organizeCode;
	}

    
}
