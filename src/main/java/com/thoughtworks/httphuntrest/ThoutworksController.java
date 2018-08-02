package com.thoughtworks.httphuntrest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.httphuntrest.comparators.ToolValueComparator;
import com.thoughtworks.httphuntrest.comparators.ToolWeightComparator;
import com.thoughtworks.httphuntrest.comparators.ToolsChainedComparator;

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

	@PostMapping(value = "/output/3")
	public HttpHuntReqSubStageIII[] getChallengeStage3output(
			@RequestHeader HttpHeaders headers) throws Exception {
		ResponseEntity<String> response = getChallengeInput(headers);
		System.out.println("<response>" + response);

		HttpHuntRequestStageIII requestStageIII = TimerUtil
				.getHttpHuntRequest(response.getBody());
		String uri = URL + "/output";
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(requestStageIII);
		System.out.println("<requestStageIII-json>" + json);

		HttpEntity<HttpHuntRequestStageIII> entity = new HttpEntity<HttpHuntRequestStageIII>(
				requestStageIII, headers);
		ResponseEntity<HttpHuntRequestStageIII> responseJson = restTemplate
				.postForEntity(uri, entity, HttpHuntRequestStageIII.class);
		return responseJson.getBody().getToolsSortedOnUsage();
	}

	@PostMapping(value = "/output")
	public String[] getChallengeStage4output(@RequestHeader HttpHeaders headers)
			throws Exception {
		ResponseEntity<String> response = getChallengeInput(headers);
		System.out.println("<response>" + response);
		JSONObject obj = new JSONObject(response.getBody());
		List<Tool> tools = getTools(obj);
		int max_weight = obj.getInt("maximumWeight");
		Collections.sort(tools, new ToolsChainedComparator(new ToolValueComparator(),
				new ToolWeightComparator()));
		List<Tool> toolsSelected = ToolsSelector.selectTools(tools, max_weight);
		String[] names = ToolsSelector.sortedToolNames(toolsSelected);
		HttpHuntRequestStageIV requestStageIV = new HttpHuntRequestStageIV();
		requestStageIV.setToolsToTakeSorted(names);
		String uri = URL + "/output";
		System.out.println("<requestStageIV>" + requestStageIV);
		HttpEntity<HttpHuntRequestStageIV> entity = new HttpEntity<HttpHuntRequestStageIV>(
				requestStageIV, headers);
		ResponseEntity<HttpHuntRequestStageIV> responseJson = restTemplate
				.postForEntity(uri, entity, HttpHuntRequestStageIV.class);
		return responseJson.getBody().getToolsToTakeSorted();
	}

	private List<Tool> getTools(JSONObject obj) {
		JSONArray array = obj.getJSONArray("tools");
		List<Tool> tools = new ArrayList<Tool>();
		for (Object object : array) {
			JSONObject jsonObj = (JSONObject) object;
			Map<String, Object> map = jsonObj.toMap();
			Tool tool = new Tool();
			tool.setName((String) map.get("name"));
			tool.setWeight((Integer) map.get("weight"));
			tool.setValue((Integer) map.get("value"));
			tools.add(tool);
		}
		return tools;
	}

}
