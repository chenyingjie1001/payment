package com.firesoon.dto.base;

import lombok.Data;

@Data
public class ReMsg {

	/**
	 * 
	 */
	private Integer httpCode;
	private String msg;
	private Object data;

}
