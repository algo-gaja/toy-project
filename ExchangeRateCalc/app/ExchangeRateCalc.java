package app;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import vo.Country;

public class ExchangeRateCalc {
    public static void main(String[] args) throws IOException {
        // 네이버 환율 정보 페이지에서 데이터 가져오기
        Connection conn = Jsoup.connect("https://m.stock.naver.com/marketindex/home/exchangeRate/exchange");
        Document doc = conn.get();
        Element element = doc.selectFirst("#__NEXT_DATA__");
        String str = element.data();
        System.out.println(str);
        
        Gson gson = new Gson();
        
        // JSON 문자열을 JsonElement 객체로 변환
        JsonElement rootElement = gson.fromJson(str, JsonElement.class);
        JsonObject rootObject = rootElement.getAsJsonObject();
        
        // 필요한 데이터에 접근
        JsonArray queriesArray = rootObject.getAsJsonObject("props")
                                            .getAsJsonObject("pageProps")
                                            .getAsJsonObject("dehydratedState")
                                            .getAsJsonArray("queries");
        
        JsonObject resultArray = queriesArray.get(2)
                                           .getAsJsonObject()
                                           .getAsJsonObject("state")
                                           .getAsJsonObject("data")
                                           .getAsJsonObject("result");
        
        System.out.println(resultArray);
        
        // Gson을 사용하여 JSON을 Map<String, Country> 형태로 변환
        Map<String, Country> rs = gson.fromJson(resultArray, new TypeToken<Map<String, Country>>(){}.getType());
        
        // 변환된 데이터 출력
        rs.forEach((k, v) -> {
            System.out.println(k + " : " + v.toString());
            System.out.println(v.getCountryName());
        });
    }
}
