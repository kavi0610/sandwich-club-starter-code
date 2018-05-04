package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                //added a place holder to show the default app icon if no image is returned
                .placeholder(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /*
     * A helper method to group and set the values to the TextViews
     * using the sandwich object created
     */

    private void populateUI(Sandwich sandwich) {

        // Get reference from xml for the TextViews that need to be set
        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView IngredientsTv = findViewById(R.id.ingredients_tv);
        TextView placeOfOriginTv = findViewById(R.id.origin_tv);

        // Create empty Strings for use in formatting values stored in Lists
        String alsoKnownAsText = "";
        String ingredientsText = "";

        //Build the text for the also_Known_As TextView to display
        if (sandwich.getAlsoKnownAs().size() > 0) {
            alsoKnownAsText = sandwich.getAlsoKnownAs().toString();
            //remove the opening and closing braces from the string
            alsoKnownAsText = alsoKnownAsText.substring(1, alsoKnownAsText.length() - 1);
            alsoKnownAsTv.setText(alsoKnownAsText);

        }
        // Handle empty values
        else {
            alsoKnownAsText = "N/A";
            alsoKnownAsTv.setText(alsoKnownAsText);

        }

        //Build the text for ingredient_tv TextView to display
        if (sandwich.getIngredients().size() > 0) {
            ingredientsText = sandwich.getIngredients().toString();
            //remove the opening and closing braces from the string
            ingredientsText = ingredientsText.substring(1, ingredientsText.length() - 1);
            IngredientsTv.setText(ingredientsText);
        }
        // Handle empty values
        else {
            ingredientsText = "N/A";
            IngredientsTv.setText(ingredientsText);

        }

        // Handle empty values in placeOfOrigin
        if (sandwich.getPlaceOfOrigin().toString().isEmpty()) {
            placeOfOriginTv.setText("N/A");

        } else {
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin().toString());
        }

        // Handle empty values in description_tv TextView
        if(sandwich.getDescription().isEmpty()){
            placeOfOriginTv.setText("N/A");
        }
        else {
            descriptionTv.setText(sandwich.getDescription().toString());
        }

    }
}

