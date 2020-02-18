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
 * -班组表 
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月21日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pro_team")
public class Team extends Model<Team> {

    private static final long serialVersionUID = 1L;

    /**
     * 班组CODE
     */
    @TableId(value = "team_code", type = IdType.UUID)
    private String teamCode;
    /**
     * 班组名称
     */
    @TableField("team_name")
    private String teamName;
    /**
     * 所属工厂
     */
    @TableField("organize_code")
    private String organizeCode;


    @Override
    protected Serializable pkVal() {
        return this.teamCode;
    }

}
