package com.tarcisio.cursomc.services.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.tarcisio.cursomc.resources.exception.StandardError;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> list = new ArrayList<>();
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(
				status, msg, timeStamp);
		
	}
	public List<FieldMessage> getBalela() {
		return list;
	}
	public void addError(String fieldName, String message) {
		list.add(new FieldMessage(fieldName,message));
	}

}
