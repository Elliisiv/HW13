package UserAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

public class UserAPI {
    static final String START_URL = "https://jsonplaceholder.typicode.com";
    public void testCreateUser() throws IOException {
        String newUserJson = "{\"name\": \"John Doe\", \"username\": \"johndoe\", \"email\": \"john@doe.com\"}";
        String createdUserJson = createUser(newUserJson);
        System.out.println("Створено нового користувача: " + createdUserJson);
    }

     public String createUser(String userJson) throws IOException {
        URL url = new URL(START_URL + "/users");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        con.getOutputStream().write(userJson.getBytes());
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        return response.toString();
    }
    public void testUpdateUser() throws IOException {
        int userId = 1;
        String updatedUserJson = "{\"id\": 1, \"name\": \"Jane Doe\", \"username\": \"janedoe\", \"email\": \"jane@doe.com\"}";
        String updatedUser = updateUser(userId, updatedUserJson);
        System.out.println("Оновлений користувач: " + updatedUser);
    }
    public String updateUser(int id, String updatedUserJson) throws IOException {
        URL url = new URL(START_URL + "/users" + "/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        con.getOutputStream().write(updatedUserJson.getBytes());
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        return response.toString();
    }

    public void testDeleteUser() throws IOException {
        int userId = 1;
        int responseCode = deleteUser(userId);
        System.out.println("Видалити користувача, код відповіді: " + responseCode);
    }

    public int deleteUser(int id) throws IOException {
        URL url = new URL(START_URL + "/users" + "/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        int responseCode = con.getResponseCode();
        return responseCode;
    }

    public String readUser() throws IOException {
        URL url = new URL(START_URL+ "/users");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            System.out.println("Користувачів не знайдено");
            return null;
        }
    }

    public String getUserById(int id) throws IOException {
        URL url = new URL(START_URL + "/users" + "/" + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return "Користувача не знайдено";
        }
    }
    public String getUserByName(String username) throws IOException {
        URL url = new URL(START_URL + "/users" + "?username=" + username);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return "Користувача не знайдено";
        }
    }

}
