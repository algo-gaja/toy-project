package toy;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import vo.ScriptData;

import com.google.gson.Gson;

public class ExchangeRateCalc {
	public static void main(String[] args) throws IOException {
		Connection conn = Jsoup.connect("https://m.stock.naver.com/marketindex/home/exchangeRate/exchange");
		Document doc = conn.get();
		Element element = doc.selectFirst("#__NEXT_DATA__");
		String str = element.data();
		System.out.println(str);
		
		Gson gson = new Gson();
		ScriptData sd = gson.fromJson(str, ScriptData.class);
		System.out.println(sd.getCountries());
		
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
