package com.hlxd.microcloud.entity.vo;

import java.io.Serializable;
import lombok.Data;

@Data
public class CollectionPointVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String equipmentCode;
	private String equipmentName;
	private String technologyCode;
	private String technologyName;
	private String standardCode;
	private String standardName;
	private String collectionPoint;
}
