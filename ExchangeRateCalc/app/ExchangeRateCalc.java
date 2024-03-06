package app;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

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

	private static Map<String, Country> countries;
	private static Country country;
	
    public static void main(String[] args) throws IOException {
    	ExchangeRateCalc calc = new ExchangeRateCalc();
    	Scanner sc = new Scanner(System.in);
        calc.init();
        calc.listCountries();
        try {
        	System.out.print("금액 > ");
        	int money = Integer.parseInt(sc.nextLine());

        	System.out.print("나라 > ");
            String countryName = sc.nextLine();
            
        	calc.findBy(countryName.toUpperCase());
        	
        	if(country != null) {
        		System.out.println(country.getName() + " -> " + (money / country.getCalcPrice()) + " " + country.getCurrencyName());
        	} else {
        		System.err.println("존재하지 않는 국가코드입니다. > " + countryName);
        	}
		} catch (NumberFormatException e) {
			System.err.println("숫자만 입력해주세요.");
		}
        
        sc.close();
    }
    
    private void init() throws IOException {
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
        
        // Gson을 사용하여 JSON을 Map<String, Country> 형태로 변환
        countries = gson.fromJson(resultArray, new TypeToken<Map<String, Country>>(){}.getType());
    }
    
    private void listCountries() {
    	StringBuilder sb = new StringBuilder();

    	int idx = 0;
    	String line = "==========================================================================";
    	
    	sb.append(line + "\n");
    	for(String key : countries.keySet()) {
    		idx++;
    		if(idx % 5 != 0) {
    			sb.append(countries.get(key).getName() + " / ");
    		} else {
    			sb.append(countries.get(key).getName() + "\n");
    		}
    	}
    	
    	if(idx % 5 != 0) {
    		sb.delete(sb.toString().length() - 3, sb.toString().length());
    		sb.append("\n");
    	}
    	
    	sb.append(line);
    	System.out.println(sb.toString());
    }
        
    private void findBy(String countryName) {
        countries.forEach((k, v) -> {
	            if(k.equals(countryName)) {
	            	country = v;
	            }
        });
    }
}
