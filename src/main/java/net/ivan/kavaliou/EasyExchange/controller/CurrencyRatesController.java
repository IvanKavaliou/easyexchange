package net.ivan.kavaliou.EasyExchange.controller;

import com.google.gson.Gson;
import net.ivan.kavaliou.EasyExchange.exceptions.NotFoundException;
import net.ivan.kavaliou.EasyExchange.model.dto.CurrencyRateDTO;
import net.ivan.kavaliou.EasyExchange.service.RestService;
import net.ivan.kavaliou.EasyExchange.utils.enums.CurrencyType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class CurrencyRatesController {

    @Autowired
    RestService restService;

    private static final String URL_NBP_CURRENCY_RATES = "https://api.nbp.pl/api/exchangerates/tables/C?format=JSON";
    private static final String URL_NBP_CURRENCY_RATE_CODE ="https://api.nbp.pl/api/exchangerates/rates/C/";

    @GetMapping("/rates")
    public ArrayList<CurrencyRateDTO> getRates(){
        try {
            String j = restService.getJSON(URL_NBP_CURRENCY_RATES);
            JSONObject jsonObject = new JSONObject(j.substring(1,j.length()-1));
            JSONArray arr = jsonObject.getJSONArray("rates");
            ArrayList<CurrencyRateDTO> result = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < arr.length(); i++)
            {
                result.add(gson.fromJson(arr.getJSONObject(i).toString(),CurrencyRateDTO.class));
            }
            return result;
        } catch (Exception e){

        }
        throw new NotFoundException("Rates not found!");
    }

    @GetMapping("/rates/{currencyType}")
    private CurrencyRateDTO getRate(@PathVariable CurrencyType currencyType){
            ArrayList<CurrencyRateDTO> rates = getRates();
        Optional<CurrencyRateDTO> rt = rates.stream().filter(r -> r.getCode().equals(currencyType)).findFirst();
        if (rt.isPresent()){
            return rt.get();
        }
        throw new NotFoundException("Rates not found!");
    }
}
