package utils;

import java.net.HttpURLConnection;
import java.net.URL;

public class Network {

	public static boolean isInternetAvailable() {
        try {
            @SuppressWarnings("deprecation")
			URL url = new URL("https://www.google.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.connect();
            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
