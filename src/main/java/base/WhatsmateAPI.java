package base;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * only available 10 times
 */

public class WhatsmateAPI {
    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";
    private static final String VIETNAMESE = "vi";
    private static final String ENGLISH = "en";

    /**
     * Sends out a WhatsApp message via WhatsMate WA Gateway.
     */
    private static void translate(String sourceLang, String targetLang, String text) throws Exception {
        // TODO: Should have used a 3rd party library to make a JSON string from an object
        String jsonPayload = "{\"fromLang\":\"" + sourceLang + "\"," + "\"toLang\":\"" + targetLang +
                "\"," + "\"text\":\"" + text + "\"}";

        URL url = URI.create(ENDPOINT).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        connection.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        connection.setRequestProperty("Content-Type", "application/json");

        OutputStream os = connection.getOutputStream();
        os.write(jsonPayload.getBytes());
        os.flush();
        os.close();

        int statusCode = connection.getResponseCode();
        // System.out.println("Status Code: " + statusCode);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (statusCode == 200) ? connection.getInputStream() : connection.getErrorStream()
        ));
        String output;
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
        connection.disconnect();
    }

    public static void translateViToEn(String text) {
        try {
            translate(VIETNAMESE, ENGLISH, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void translateEnToVi(String text) {
        try {
            translate(ENGLISH, VIETNAMESE, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String text = "I am 8 years old";
        String text1 = "Cho em xin một vé về tuổi thơ";
        translateEnToVi(text);
        translateViToEn(text1);
    }

}