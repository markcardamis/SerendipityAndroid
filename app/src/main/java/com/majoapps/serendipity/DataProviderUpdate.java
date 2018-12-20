package com.majoapps.serendipity;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Mark on 13/02/2016.
 */
public class DataProviderUpdate extends AsyncTask<Void, Void, LinkedHashMap<String, List<String>>>{

    private static final String TAG = HttpHandler.class.getSimpleName();
    LinkedHashMap<String, List<String>> LevelsDetails = new LinkedHashMap<String, List<String>>();

    public interface AsyncResponse {
        void processFinish(LinkedHashMap<String, List<String>> output);
    }
    public AsyncResponse delegate = null;

    public DataProviderUpdate(AsyncResponse delegate){
        this.delegate = delegate;
    }


            @Override
            protected LinkedHashMap<String, List<String>> doInBackground(Void... arg0) {
                HttpHandler sh = new HttpHandler();
                // Making a request to url and getting response
                String url = "https://majoapps.com/files/levels.html";
                String jsonStr = sh.makeServiceCall(url);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
                        JSONArray LevelsJSONArray = jsonObj.getJSONArray("Levels");

                        // looping through All Contacts
                        for (int i = 0; i < LevelsJSONArray.length(); i++) {
                            JSONObject c = LevelsJSONArray.getJSONObject(i);

                            Iterator<String> keys = c.keys();
                            String LevelString = keys.next();


                            // Phone node is JSON Object
                            JSONObject phone = c.getJSONObject(LevelString);

                            Log.i("phone ", phone.toString());

                            String Option1 = phone.getString("Option 1");
                            String Option2 = phone.getString("Option 2");
                            String Option3 = phone.getString("Option 3");
                            String Option4 = phone.getString("Option 4");
                            String Option5 = phone.getString("Option 5");
                            String Option6 = phone.getString("Option 6");

                            List<String> Level = new ArrayList<String>();

                            Level.add(Option1);
                            Level.add(Option2);
                            Level.add(Option3);
                            Level.add(Option4);
                            Level.add(Option5);
                            Level.add(Option6);

                            LevelsDetails.put(LevelString, Level);

                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, e.toString());
                    }

                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                }

                return LevelsDetails;
            }

    @Override
    protected void onPostExecute(LinkedHashMap<String, List<String>> stringListLinkedHashMap) {
        super.onPostExecute(stringListLinkedHashMap);
        delegate.processFinish(stringListLinkedHashMap);
    }
}



