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
 * -品牌表 
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月21日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_brand")
public class Brand extends Model<Brand> {

    private static final long serialVersionUID = 1L;

    /**
     * 品牌CODE
     */
    @TableId(value = "brand_code", type = IdType.UUID)
    private String brandCode;
    /**
     * 品牌名称
     */
    @TableField("brand_name")
    private String brandName;
    /**
     * 所属工厂
     */
    @TableField("organize_code")
    private String organizeCode;

    /**
     * 是否正在生产
     */
    @TableField("is_produce")
    private Integer isProduce;

    @Override
    protected Serializable pkVal() {
        return this.brandCode;
    }

    public Brand() {}
    public Brand(String brandCode, String brandName, String organizeCode, Integer isProduce) {
    	this.brandCode = brandCode;
    	this.brandName = brandName;
    	this.organizeCode = organizeCode;
    	this.isProduce = isProduce;
    }
}
