package com.thoughtworks.httphuntrest;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author skambapu
 * 
 */
@RestController
public class ThoutworksController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public HttpStatus getValidUser() throws Exception {
		final String uri = "https://http-hunt.thoughtworks-labs.net/challenge";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("userid", "H1oHpI4EQ");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters",
				headers);

		ResponseEntity<String> result = restTemplate.exchange(uri,
				HttpMethod.GET, entity, String.class);
		return result.getStatusCode();
	}

	@RequestMapping(value = "/input", method = RequestMethod.POST)
	public ResponseEntity<String> getChallengeInput() throws Exception {
		HttpStatus status = getValidUser();
		if (status.value() != 200) {
			return new ResponseEntity<>(status);
		}
		final String uri = "https://http-hunt.thoughtworks-labs.net/challenge/input";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("userid", "H1oHpI4EQ");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters",
				headers);

		ResponseEntity<String> result = restTemplate.exchange(uri,
				HttpMethod.GET, entity, String.class);
		return result;
	}

	@RequestMapping(value = "/output", method = RequestMethod.POST)
	public String getChallengeoutput() throws Exception {
		ResponseEntity<String> response = getChallengeInput();
		final String uri = "https://http-hunt.thoughtworks-labs.net/challenge/output";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("userid", "H1oHpI4EQ");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(response.getBody(),
				headers);

		ResponseEntity<String> result = restTemplate.exchange(uri,
				HttpMethod.POST, entity, String.class);
		return result.getBody();
	}
}
