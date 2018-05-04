package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        ArrayList<String> alsoKnownAsList = new ArrayList<String>();
        ArrayList<String> ingredientsList = new ArrayList<String>();

        try {

            JSONObject jsonParser = new JSONObject(json);

            //Create another JSONObject to read the name which is a level lower than parent jsonParser
            JSONObject name = jsonParser.getJSONObject("name");
            //Parse the name JSONObject to get the mainName
            String mainName = name.getString("mainName");

            // Use jsonParser to get other elements at same level
            JSONArray alsoKnownAsJson = name.getJSONArray("alsoKnownAs");
            if (alsoKnownAsJson.length() > 0) {
                for (int i = 0; i < alsoKnownAsJson.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsJson.getString(i));
                }
            }
            String placeOfOrigin = jsonParser.getString("placeOfOrigin");
            String description = jsonParser.getString("description");
            String image = jsonParser.getString("image");

            JSONArray ingredientJson = jsonParser.getJSONArray("ingredients");
            if (ingredientJson.length()>0){
                for(int i=0;i< ingredientJson.length();i++ ){
                    ingredientsList.add(ingredientJson.getString(i));
                }
            }
            // create a new instance of Sandwich object and set the values parsed respectively
            Sandwich sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
