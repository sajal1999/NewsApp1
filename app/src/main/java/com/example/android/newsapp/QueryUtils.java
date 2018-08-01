package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    private QueryUtils() {


    }

    public static List<News> fetchNewsData(String requestUrl) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Create URL object


        URL url = createUrl(requestUrl);


        String jsonResponse = null;


        try {


            jsonResponse = makeHttpRequest(url);


        } catch (IOException e) {


            Log.e(LOG_TAG, "Problem making the HTTP request.", e);


        }

        List<News> news = extractFeatureFromJson(jsonResponse);


        // Return the list of {@link Earthquake}s


        return news;
    }
    private static URL createUrl(String stringUrl) {


        URL url = null;


        try {


            url = new URL(stringUrl);


        } catch (MalformedURLException e) {


            Log.e(LOG_TAG, "Problem building the URL ", e);


        }


        return url;


    }
    private static String formatDate(String rawDate) {

        String jsonDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.US);

        try {

            Date parsedJsonDate = jsonFormatter.parse(rawDate);

            String finalDatePattern = "MMM d, yyy";

            SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);

            return finalDateFormatter.format(parsedJsonDate);

        } catch (ParseException e) {

            Log.e("QueryUtils", "Error parsing JSON date: ", e);

            return "";

        }

    }




    private static String makeHttpRequest(URL url) throws IOException {


        String jsonResponse = "";


        // If the URL is null, then return early.


        if (url == null) {


            return jsonResponse;


        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {

                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());

            }


        } catch (IOException e) {

            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);

        } finally {

            if (urlConnection != null) {

                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);


                line = reader.readLine();


            }


        }


        return output.toString();


    }

    static List<News> extractFeatureFromJson(String response) {

        ArrayList<News> news = new ArrayList<>();

        try {

            JSONObject jsonResponse = new JSONObject(response);

            JSONObject jsonResults = jsonResponse.getJSONObject("response");

            JSONArray resultsArray = jsonResults.getJSONArray("results");



            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject oneResult = resultsArray.getJSONObject(i);

                String webTitle = oneResult.getString("webTitle");

                String url = oneResult.getString("webUrl");

                String date = oneResult.getString("webPublicationDate");

                date = formatDate(date);

                String section = oneResult.getString("sectionName");

                String author = "";


                news.add(new News(webTitle, author, url, date, section));

            }

        } catch (JSONException e) {

            Log.e("Queryutils", "Error parsing JSON response", e);

        }

        return news;

    }

}