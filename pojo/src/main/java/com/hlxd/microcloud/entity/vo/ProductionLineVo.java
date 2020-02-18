package com.hlxd.microcloud.entity.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductionLineVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String productionLineCode;
	private String technologyCode;
	private Integer serialNumber;
	private String technologyName;
	private String organizeCode;
	private String workshopSectionCode;
	private String workshopSectionName;
	
}
