package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Reference: "<a href="https://stackoverflow.com/questions/8147284/how-to-use-google-translate-api-in-my-java-application">...</a>"
 */

public class GoogleScriptAPI {
    private static final String VIETNAMESE = "vi";
    private static final String ENGLISH = "en";

    private static String translate(String sourceLang, String targetLang, String text) throws IOException {
        String urlStr  = "https://script.google.com/macros/s/AKfycbwzlKRftVMgsANJrKnkRI8D_gy8kYTzU5yoO7OjS3OVwDB20WH8bR7ZSPfmO2_6hN9KNQ/exec"
                + "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8)
                + "&target=" + targetLang
                + "&source" + sourceLang;
        // Create url using uri
        URL url = URI.create(urlStr).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static String translateEnToVi(String text) {
        try {
            return translate(ENGLISH, VIETNAMESE, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error occurred";
    }

    public static String translateViToEn(String text) {
        try {
            return translate(VIETNAMESE, ENGLISH, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error occurred";
    }


    public static void main(String[] args) {
        String text = "I am 8 years old";
        String text1 = "Cho em xin một vé về tuổi thơ";
        System.out.println("Translated text: " + translateEnToVi(text));
        System.out.println("Translated text: " + translateViToEn(text1));
    }
}
