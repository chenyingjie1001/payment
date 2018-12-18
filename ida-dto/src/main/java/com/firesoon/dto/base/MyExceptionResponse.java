package com.firesoon.dto.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class MyExceptionResponse implements Serializable{

	/**
	 * 
	 */
	private int httpCode;
	private String msg;
	private Date timestamp;
	
	
}