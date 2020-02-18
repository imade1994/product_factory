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
 * 数采点 
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_collection_point")
public class CollectionPoint extends Model<CollectionPoint> {

    private static final long serialVersionUID = 1L;
    /**
     * 数采点代码
     */
    @TableId(value = "collection_point", type = IdType.UUID)
    private String collectionPoint;
    /**
     * 指标代码
     */
    @TableField("standard_code")
    private String standardCode;
    /**
     * 设备代码
     */
    @TableField("equipment_code")
    private String equipmentCode;


    @Override
    protected Serializable pkVal() {
        return this.collectionPoint;
    }

    public CollectionPoint() {}

	public CollectionPoint(String collectionPoint, String standardCode, String equipmentCode) {
		super();
		this.collectionPoint = collectionPoint;
		this.standardCode = standardCode;
		this.equipmentCode = equipmentCode;
	}
    
}
