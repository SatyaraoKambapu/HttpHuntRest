package com.thoughtworks.httphuntrest;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HttpHuntRequest {

	@JsonProperty("encryptedMessage")
	public String encryptedMessage;
	@JsonProperty("key")
	public int key;

	public String getEncryptedMessage() {
		return encryptedMessage;
	}

	public void setEncryptedMessage(String encryptedMessage) {
		this.encryptedMessage = encryptedMessage;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

}
