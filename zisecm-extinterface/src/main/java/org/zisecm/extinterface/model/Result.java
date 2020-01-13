package org.zisecm.extinterface.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="返回类")
public class Result {
	@ApiModelProperty(value="code")
	private int code;
	@ApiModelProperty(value="message")
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="data")
	private Object data;
	
	public Result(int code,String message,Object data) {
		this.code=code;
		this.message=message;
		this.data=data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
