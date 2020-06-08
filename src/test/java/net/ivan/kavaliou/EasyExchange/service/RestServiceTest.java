package net.ivan.kavaliou.EasyExchange.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestServiceTest {

    @Autowired
    RestService restService;

    private static final String URL_NBP_GET_RATE = "https://api.nbp.pl/api/exchangerates/rates/C/USD";

    @Test
    public void getJsonTest() throws JSONException {
        JSONObject jsonObject = new JSONObject(restService.getJSON(URL_NBP_GET_RATE));
        JSONArray arr = jsonObject.getJSONArray("rates");
        String bid ="";
        for (int i = 0; i < arr.length(); i++)
        {
            bid = arr.getJSONObject(i).getString("bid");
        }
        System.out.println("USD Bid: "+ bid);
    }


}