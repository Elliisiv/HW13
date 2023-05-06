package org.hw13.Todos;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import static org.hw13.UserAPI.UserAPI.START_URL;

public class OpenTasksProgram {

public static List<Todos> OpenTask (int userId) throws IOException, JSONException {
    List<Todos> openTasks = new ArrayList<>();
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
            Todos openTask = new Todos();
            openTask.setTitle(task.getString("title"));
            openTask.setId(task.getInt("id"));
            openTask.setUserId(task.getInt("userId"));
            openTask.setCompleted(task.getBoolean("completed"));
            openTasks.add(openTask);
            System.out.println("Open task: " + openTask.getTitle());
        }

    }
    return openTasks;
}
}