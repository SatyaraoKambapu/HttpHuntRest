package com.thoughtworks.httphuntrest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public class TimerUtil {

	@SuppressWarnings("deprecation")
	public static HttpHuntRequestStageIII getHttpHuntRequest(String response)
			throws ParseException {
		JSONObject obj = new JSONObject(response);
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
			long timeUsedInms = endDate.getTime() - startDate.getTime();
			int timeUsedInMinutes = (int) (timeUsedInms / 60000);
			if (resultMap.containsKey(name)) {
				int usedTimes = (int) resultMap.get(name);
				resultMap.put(name, timeUsedInMinutes + usedTimes);
			} else {
				resultMap.put(name, timeUsedInMinutes);
			}
		}
		HttpHuntRequestStageIII requestStageIII = new HttpHuntRequestStageIII();
		List<HttpHuntReqSubStageIII> list = new ArrayList<HttpHuntReqSubStageIII>();
		
		for (Entry<String, Integer> entry : resultMap.entrySet()) {
			// if (entry.getValue() > 0) {
			HttpHuntReqSubStageIII subStageIII = new HttpHuntReqSubStageIII();
			subStageIII.setName(entry.getKey());
			subStageIII.setTimeUsedInMinutes(entry.getValue());
			list.add(subStageIII);
			// }
		}
		sort(list);
		HttpHuntReqSubStageIII[] reqSubStageIIIArray = new HttpHuntReqSubStageIII[list
				.size()];
		int index = 0;
		for (HttpHuntReqSubStageIII httpHuntReqSubStageIII : list) {
			reqSubStageIIIArray[index++] = httpHuntReqSubStageIII;
		}
		requestStageIII.setToolsSortedOnUsage(reqSubStageIIIArray);
		return requestStageIII;
	}

	private static void sort(List<HttpHuntReqSubStageIII> list) {
		Collections.sort(list, new Comparator<HttpHuntReqSubStageIII>() {

			@Override
			public int compare(HttpHuntReqSubStageIII o1,
					HttpHuntReqSubStageIII o2) {
				// TODO Auto-generated method stub
				return o2.getTimeUsedInMinutes() - o1.getTimeUsedInMinutes();
			}
		});
	}
/*
	public static void main(String[] args) throws ParseException {
		String s = "{\"toolUsage\":[{\"name\":\"knife\",\"useStartTime\":\"2017-01-30 10:00:00\",\"useEndTime\":\"2017-01-30 10:10:00\"},{\"name\":\"guns\",\"useStartTime\":\"2017-01-30 10:15:00\",\"useEndTime\":\"2017-01-30 10:20:00\"},{\"name\":\"guns\",\"useStartTime\":\"2017-01-30 11:00:00\",\"useEndTime\":\"2017-01-30 11:10:00\"},{\"name\":\"knife\",\"useStartTime\":\"2017-01-30 11:10:00\",\"useEndTime\":\"2017-01-30 11:20:00\"},{\"name\":\"rope\",\"useStartTime\":\"2017-01-30 13:00:00\",\"useEndTime\":\"2017-01-30 14:00:00\"}]}";
		System.out.println(getHttpHuntRequest(s));
	}*/
}
