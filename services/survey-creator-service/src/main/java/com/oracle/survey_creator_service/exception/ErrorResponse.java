package com.oracle.survey_creator_service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	 private  String apiPath;


	    private HttpStatus errorCode;

	    private  String errorMessage;


	    private LocalDateTime errorTime;
	    
	    
	    public ErrorResponse() {
			// TODO Auto-generated constructor stub
		}
	    
	    


		public ErrorResponse(String apiPath, HttpStatus errorCode, String errorMessage, LocalDateTime errorTime) {
			super();
			this.apiPath = apiPath;
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
			this.errorTime = errorTime;
		}




		public String getApiPath() {
			return apiPath;
		}


		public void setApiPath(String apiPath) {
			this.apiPath = apiPath;
		}


		public HttpStatus getErrorCode() {
			return errorCode;
		}


		public void setErrorCode(HttpStatus errorCode) {
			this.errorCode = errorCode;
		}


		public String getErrorMessage() {
			return errorMessage;
		}


		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}


		public LocalDateTime getErrorTime() {
			return errorTime;
		}


		public void setErrorTime(LocalDateTime errorTime) {
			this.errorTime = errorTime;
		}
	    
	    




}
