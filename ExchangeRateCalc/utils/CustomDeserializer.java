package utils;

import com.google.gson.*;

import dto.Result;

import java.lang.reflect.Type;

public class CustomDeserializer implements JsonDeserializer<Result> {
    @Override
    public Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    	JsonObject jsonObject = json.getAsJsonObject();
        Result result = null;
        
        if (jsonObject.has("result") && jsonObject.get("result").isJsonObject()) {
        	Gson gson = new Gson();
            JsonObject resultObject = jsonObject.getAsJsonObject("result");
            result = gson.fromJson(resultObject, Result.class);
        }
        
        return result;
    }
}
