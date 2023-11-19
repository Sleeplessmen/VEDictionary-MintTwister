package base;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.io.IOException;

public class Translator {
	public static String translate(String text) throws IOException {
		AsyncHttpClient client = new DefaultAsyncHttpClient();

		try {
			return client.prepare("POST", "https://google-translate1.p.rapidapi.com/language/translate/v2")
					.setHeader("content-type", "application/x-www-form-urlencoded")
					.setHeader("Accept-Encoding", "application/gzip")
					.setHeader("X-RapidAPI-Key", "95db2a00eemsh44e0eab1f9749cdp1681d7jsnbcdc0e12fb3f")
					.setHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
					.setBody("q=" + text + "&target=vi&source=en")
					.execute()
					.toCompletableFuture()
					.thenApply(response -> {
						// Parse the JSON content
						JsonObject jsonResponse = JsonParser.parseString(response.getResponseBody()).getAsJsonObject();

						// Extract the translated text
						return jsonResponse.getAsJsonObject("data")
								.getAsJsonArray("translations")
								.get(0)
								.getAsJsonObject()
								.get("translatedText")
								.getAsString();
					})
					.join();
		} finally {
			client.close();
		}
	}
	public static void main(String[] args) throws IOException {
		String result = translate("hello guys");
		System.out.println(result);
	}
}
