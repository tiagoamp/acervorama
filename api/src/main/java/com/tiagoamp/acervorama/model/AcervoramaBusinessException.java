package com.tiagoamp.acervorama.model;

public class AcervoramaBusinessException extends Exception {

	private static final long serialVersionUID = 3997639169442070308L;
	
	private String businessMessage;
	
	
	public AcervoramaBusinessException(String message) {
		this.businessMessage = message;
	}
	
	public AcervoramaBusinessException(String businessMessage, Exception exceptionCause){
		super(exceptionCause);
		this.businessMessage = businessMessage;
	}
	
	
	public String getBusinessMessage() {
		return businessMessage;
	}

}
