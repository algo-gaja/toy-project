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

import dto.Result;
import dto.ScriptData;

public class ExchangeRateCalc {
	public static void main(String[] args) throws IOException {
		Connection conn = Jsoup.connect("https://m.stock.naver.com/marketindex/home/exchangeRate/exchange");
		Document doc = conn.get();
		Element element = doc.selectFirst("#__NEXT_DATA__");
		String str = element.data();
		System.out.println(str);
		
		Gson gson = new GsonBuilder()
						.registerTypeAdapter(Result.class, new CustomDeserializer())
						.create();
		
		ScriptData sd = gson.fromJson(str, ScriptData.class);
		
		Map<String, Result> rs = sd.getProps()
				.getPageProps()
				.getDehydratedState()
				.getQueries()
				.get(1)
				.getState()
				.getData()
				.getResult();
		
		rs.forEach((key, value) -> System.out.println(key + " : " + value));
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
