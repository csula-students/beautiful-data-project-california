package edu.csula.population;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class Tools {

    public static JsonNode requestJson(String url) {

        try {
            JsonNode response = Unirest.get(url)
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .asJson()
                    .getBody();

            return response;
        } catch (UnirestException e) {
            throw new IllegalStateException("Server may not be up and running.", e);
        }

    }
}
