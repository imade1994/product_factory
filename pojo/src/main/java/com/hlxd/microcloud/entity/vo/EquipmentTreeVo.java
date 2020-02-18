package com.hlxd.microcloud.entity.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/***
 * -设备层级
 * @version 1.0
 * @author SmallOath
 * @date 2019年12月10日
 */
@Data
public class EquipmentTreeVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String parentId;
	private List<EquipmentTreeVo> children;
	private Boolean spread=false;
	
}
