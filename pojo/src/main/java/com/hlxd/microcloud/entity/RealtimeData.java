package com.hlxd.microcloud.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/***
 * -实时数据 
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_realtime_data")
public class RealtimeData extends Model<RealtimeData> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 采集点代码
     */
    @TableField("collection_point")
    private String collectionPoint;
    /**
     * 采集值
     */
    @TableField("collection_value")
    private BigDecimal collectionValue;
    /**
     * 采集时间
     */
    @TableField("collection_time")
    private String collectionTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
