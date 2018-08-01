package com.thoughtworks.httphuntrest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
	public String getValidUser(@RequestHeader HttpHeaders headers)
			throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>("parameters",
				headers);

		ResponseEntity<String> result = restTemplate.exchange(URL,
				HttpMethod.GET, entity, String.class);
		return result.getBody();
	}

	@GetMapping(value = "/input", produces = "application/json")
	public ResponseEntity<String> getChallengeInput(
			@RequestHeader HttpHeaders headers) throws Exception {
		final String uri = URL + "/input";
		HttpEntity<String> entity = new HttpEntity<String>("parameters",
				headers);

		ResponseEntity<String> result = restTemplate.exchange(uri,
				HttpMethod.GET, entity, String.class);
		return result;
	}

	@PostMapping(value = "/output/1")
	public String getChallengeoutput(@RequestHeader HttpHeaders headers)
			throws Exception {
		ResponseEntity<String> response = getChallengeInput(headers);
		HttpHuntRequest httpHuntRequest = new HttpHuntRequest();
		JSONObject obj = new JSONObject(response.getBody());
		String encryptedMsg = obj.getString("encryptedMessage");
		int key = obj.getInt("key");
		Encryption encryption = new Encryption();
		String msg = encryption.decrypt(encryptedMsg, key);
		System.out.println("<encryptedMsg>" + encryptedMsg);
		System.out.println("<msg>" + msg);
		httpHuntRequest.setMessage(msg);
		String uri = URL + "/output";
		HttpEntity<HttpHuntRequest> entity = new HttpEntity<HttpHuntRequest>(
				httpHuntRequest, headers);
		ResponseEntity<HttpHuntRequest> result = restTemplate.postForEntity(
				uri, entity, HttpHuntRequest.class);
		return result.getBody().getMessage();
	}

	@PostMapping(value = "/output/2")
	public String[] getChallengeStage2output(@RequestHeader HttpHeaders headers)
			throws Exception {
		ResponseEntity<String> response = getChallengeInput(headers);
		JSONObject obj = new JSONObject(response.getBody());
		String hiddenTools = obj.getString("hiddenTools");
		JSONArray array = obj.getJSONArray("tools");
		String[] result = new WordFinder()
				.getMatchedStrings(hiddenTools, array);
		String uri = URL + "/output";
		HttpHuntReqStageII iiRequest = new HttpHuntReqStageII();
		iiRequest.setToolsFound(result);
		HttpEntity<HttpHuntReqStageII> entity = new HttpEntity<HttpHuntReqStageII>(
				iiRequest, headers);
		ResponseEntity<HttpHuntReqStageII> responseJson = restTemplate
				.postForEntity(uri, entity, HttpHuntReqStageII.class);
		return responseJson.getBody().getToolsFound();
	}

	@SuppressWarnings("deprecation")
	@PostMapping(value = "/output")
	public HttpHuntReqSubStageIII[] getChallengeStage3output(
			@RequestHeader HttpHeaders headers) throws Exception {
		ResponseEntity<String> response = getChallengeInput(headers);
		JSONObject obj = new JSONObject(response.getBody());
		JSONArray array = obj.getJSONArray("toolUsage");
		Map<String, Integer> resultMap = new HashMap<>();
		for (Object object : array) {
			JSONObject jsonObject = (JSONObject) object;
			Map<String, Object> map = jsonObject.toMap();
			String name = (String) map.get("name");
			String useStartTime = (String) map.get("useStartTime");
			String useEndTime = (String) map.get("useEndTime");
			String pattern = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date startDate = simpleDateFormat.parse(useStartTime);
			Date endDate = simpleDateFormat.parse(useEndTime);
			int timeUsedInMinutes = endDate.getMinutes()
					- startDate.getMinutes();
			if (resultMap.containsKey(name)) {
				int usedTimes = (int) resultMap.get(name);
				resultMap.put(name, ++usedTimes);
			} else {
				resultMap.put(name, timeUsedInMinutes);
			}
		}
		HttpHuntRequestStageIII requestStageIII = new HttpHuntRequestStageIII();
		List<HttpHuntReqSubStageIII> list = new ArrayList<HttpHuntReqSubStageIII>();
		for (Entry<String, Integer> entry : resultMap.entrySet()) {
			if (entry.getValue() > 0) {
				HttpHuntReqSubStageIII subStageIII = new HttpHuntReqSubStageIII();
				subStageIII.setName(entry.getKey());
				subStageIII.setTimeUsedInMinutes(entry.getValue());
				list.add(subStageIII);
			}
		}
		HttpHuntReqSubStageIII[] reqSubStageIIIArray = new HttpHuntReqSubStageIII[list
				.size()];
		int index = 0;
		for (HttpHuntReqSubStageIII httpHuntReqSubStageIII : list) {
			reqSubStageIIIArray[index++] = httpHuntReqSubStageIII;
		}
		requestStageIII.setToolsSortedOnUsage(reqSubStageIIIArray);
		System.out.println(reqSubStageIIIArray);
		String uri = URL + "/output";

		HttpEntity<HttpHuntRequestStageIII> entity = new HttpEntity<HttpHuntRequestStageIII>(
				requestStageIII, headers);
		ResponseEntity<HttpHuntRequestStageIII> responseJson = restTemplate
				.postForEntity(uri, entity, HttpHuntRequestStageIII.class);
		return responseJson.getBody().getToolsSortedOnUsage();
	}
}
