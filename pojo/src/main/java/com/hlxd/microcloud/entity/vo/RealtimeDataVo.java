package com.hlxd.microcloud.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class RealtimeDataVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String collectionPoint;
	private BigDecimal collectionValue;
	private String collectionTime;
	private String standardCode;
	private String standardName;
	private String unit;
	private String equipmentCode;
	private String equipmentName;
	private String technologyCode;
	private String technologyName;
}
