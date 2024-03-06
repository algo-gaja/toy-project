package vo;

public class Country {
	private String countryName;
	private String currencyName;
	private float calcPrice;
	private String name;
	
	public String getCountryName() {
		return countryName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public float getCalcPrice() {
		return calcPrice;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Result [countryName=" + countryName + ", currencyName="
				+ currencyName + ", calcPrice=" + calcPrice + ", name=" + name
				+ "]";
	}
	
	
}
