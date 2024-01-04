package toy;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import utils.CustomDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import dto.Result;
import dto.ScriptData;

public class ExchangeRateCalc {
	public static void main(String[] args) throws IOException {
		Connection conn = Jsoup.connect("https://m.stock.naver.com/marketindex/home/exchangeRate/exchange");
		Document doc = conn.get();
		Element element = doc.selectFirst("#__NEXT_DATA__");
		String str = element.data();
		System.out.println(str);
		
		Gson gson = new Gson();
		
		JsonElement rootElement = gson.fromJson(str, JsonElement.class);
		JsonObject rootObject = rootElement.getAsJsonObject();
		
		// 먼저 "props" > "pageProps" > "dehydratedState" > "queries"에서 result 배열을 가져옵니다.
        JsonArray queriesArray = rootObject.getAsJsonObject("props")
                                            .getAsJsonObject("pageProps")
                                            .getAsJsonObject("dehydratedState")
                                            .getAsJsonArray("queries");

        // 여기서는 첫 번째 query의 result를 가져옵니다.
        JsonObject resultArray = queriesArray.get(1)
                                           .getAsJsonObject()
                                           .getAsJsonObject("state")
                                           .getAsJsonObject("data")
                                           .getAsJsonObject("result");
        
        System.out.println(resultArray);
        // result 배열의 첫 번째 원소가 배열인지 객체인지 확인합니다.
        Map<String, Result> rs = gson.fromJson(resultArray, new TypeToken<Map<String, Result>>(){}.getType());
        
        rs.forEach((k, v) -> System.out.println(k + " : " + v.toString()));
//		Gson gson = new GsonBuilder()
//						.registerTypeAdapter(Result.class, new CustomDeserializer())
//						.create();
		
//		ScriptData sd = gson.fromJson(str, ScriptData.class);
		
//		Map<String, Result> rs = sd.getProps()
//				.getPageProps()
//				.getDehydratedState()
//				.getQueries()
//				.get(1)
//				.getState()
//				.getData()
//				.getResult();
		
		
//		rs.forEach((key, value) -> System.out.println(key + " : " + value));
//		for(Country c : sd.getCountries()) {
//			System.out.println(c.toString());
//		}
//		// Map 출력
//		for (Map.Entry<String, Object> entry : map.entrySet()) {
//			System.out.println(entry.getKey() + "=" + entry.getValue());
//			System.out.println();
//		}
//		
//		System.out.println(map.get("props").toString());
//		Map<String, Object> map2 = gson.fromJson(map.get("props").toString().replace('/', ' '), Map.class);
//		Map<String, Object> map3 = gson.fromJson(map2.get("dehydratedState").toString(), Map.class);
		
//		for (Map.Entry<String, Object> entry : map3.entrySet()) {
//  			System.out.println(entry.getKey() + "=" + entry.getValue());
//			System.out.println();
//		}
	}
}
