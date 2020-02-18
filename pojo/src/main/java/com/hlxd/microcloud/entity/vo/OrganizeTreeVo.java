package com.hlxd.microcloud.entity.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class OrganizeTreeVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String parentId;
	private List<OrganizeTreeVo> children;
	private Boolean spread=true;
	
}
