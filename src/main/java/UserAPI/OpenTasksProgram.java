package UserAPI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import org.json.*;

import static UserAPI.UserAPI.START_URL;

public class OpenTasksProgram {
public static void ScannerText (int userId) throws IOException, JSONException {
    //сканере тексту за id
    URL url = new URL(START_URL + "/users/" + userId + "/todos");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuilder response = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
    }
    in.close();

    //пошук false
    JSONArray tasks = new JSONArray(response.toString());
    int count = 0;
    for (int i = 0; i < tasks.length(); i++) {
        JSONObject task = tasks.getJSONObject(i);
        if (!task.getBoolean("completed")) {
            count ++;
            //вивід заголовка
            System.out.println("Відкрита задача № " + count +", tittle: "+ task.getString("title"));
        }
    }
}
    public static void main(String[] args) throws Exception {
        OpenTasksProgram otp = new OpenTasksProgram();
        otp.ScannerText(4);
    }
}