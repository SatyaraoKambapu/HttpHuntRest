package com.thoughtworks.httphuntrest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HttpHuntResponse {

	@JsonProperty("message")
	public String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
