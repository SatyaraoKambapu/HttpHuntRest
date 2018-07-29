package com.thoughtworks.httphuntrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
	RestTemplate restTemplate = new RestTemplate();
	private static final String URL = "https://http-hunt.thoughtworks-labs.net/challenge";

	@GetMapping(value = "/")
	public HttpStatus getValidUser(HttpHeaders headers) throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>("parameters",
				headers);

		ResponseEntity<String> result = restTemplate.exchange(URL,
				HttpMethod.GET, entity, String.class);
		return result.getStatusCode();
	}

	@GetMapping(value = "/input", produces = "application/json")
	public ResponseEntity<String> getChallengeInput(HttpHeaders headers)
			throws Exception {
		HttpStatus status = getValidUser(headers);
		if (status.value() != 200) {
			return new ResponseEntity<>(status);
		}
		final String uri = URL + "/input";
		HttpEntity<String> entity = new HttpEntity<String>("parameters",
				headers);

		ResponseEntity<String> result = restTemplate.exchange(uri,
				HttpMethod.GET, entity, String.class);
		return result;
	}

	@PostMapping(value = "/output")
	public String getChallengeoutput(@RequestHeader HttpHeaders headers)
			throws Exception {
		ResponseEntity<String> response = getChallengeInput(headers);
		String uri = URL + "/output";
		HttpEntity<String> entity = new HttpEntity<String>(response.getBody(),
				headers);
		ResponseEntity<String> result = restTemplate.postForEntity(uri, entity,
				String.class);
		return result.getBody();
	}
}
