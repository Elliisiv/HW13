package org.hw13.UserAPI;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class UserAPI {
    public static final String START_URL = "https://jsonplaceholder.typicode.com";

    public static User createUser() throws IOException {
        int id = 11;
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@doe.com";
        Address address = new Address("123 St", "Apt 4", "Town", "12345-6789", new Geo(-35.3159, 51.1496));
        String phone = "+380-111-1111";
        String website = "https://johndoe.com";
        Company company = new Company("Any Inc", "Widgets Division", "Division");
        User user = new User(id, name, username, email, address, phone, website, company);

        URL url = new URL(START_URL + "/users");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        String jsonInputString = new Gson().toJson(user);
        OutputStream os = con.getOutputStream();
        byte[] input = jsonInputString.getBytes("utf-8");
        os.write(input, 0, input.length);

        int responseCode = con.getResponseCode();
        System.out.println("Response code: " + responseCode);

        return user;
    }

    public static User updateUser(int userId, String updatedUserJson) {
        try {
            Gson gson = new Gson();
            User user = gson.fromJson(updatedUserJson, User.class);
            user.setId(userId);
            URL url = new URL(START_URL + "/users" + "/" + userId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            byte[] input = updatedUserJson.getBytes("utf-8");
            con.getOutputStream().write(input, 0, input.length);

            int responseCode = con.getResponseCode();
            System.out.println("Response code: " + responseCode);
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User testUpdateUser() throws IOException {
        int userId = 1;
        String updatedUserJson = "{\"id\": 1, \"name\": \"Jane Doe\", \"username\": \"janedoe\", \"email\": \"jane@doe.com\"}";
        User updatedUser = updateUser(userId, updatedUserJson);
        return updatedUser;
    }

    public static User deleteUser(int userId) throws IOException {
        URL url = new URL(START_URL + "/users" + "/" + userId);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        int responseCode = con.getResponseCode();
        System.out.println("Delete user, response code: " + responseCode);
        return null;
    }

    public static User readUser() throws IOException {
        URL url = new URL(START_URL + "/users");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String responseString = response.toString();
        Gson gson = new Gson();
        User[] users = gson.fromJson(responseString, User[].class);
        List<User> userList = Arrays.asList(users);
        for (User user : userList) {
            System.out.println(user);
        }
        return null;
    }

    public static User getUserById(int userId) throws IOException {
        URL url = new URL(START_URL + "/users" + "/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String responseString = response.toString();
        Gson gson = new Gson();
        User user = gson.fromJson(responseString, User.class);
        System.out.println(user);
        return user;
    }
    public static User getUserByName(String username) throws IOException {
        URL url = new URL(START_URL + "/users?username=" + username);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String responseString = response.toString();
        Gson gson = new Gson();
        User[] users = gson.fromJson(responseString, User[].class);
        List<User> userList = Arrays.asList(users);
        for (User user : userList) {
            System.out.println(user);
        }
        return null;
    }
}




//    public void testUpdateUser() throws IOException {
//        int userId = 1;
//        String updatedUserJson = "{\"id\": 1, \"name\": \"Jane Doe\", \"username\": \"janedoe\", \"email\": \"jane@doe.com\"}";
//        String updatedUser = updateUser(userId, updatedUserJson);
//        System.out.println("Оновлений користувач: " + updatedUser);
//    }
//    public String updateUser(int id, String updatedUserJson) throws IOException {
//        URL url = new URL(START_URL + "/users" + "/" + id);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("PUT");
//        con.setRequestProperty("Content-Type", "application/json");
//        con.setRequestProperty("Accept", "application/json");
//        con.setDoOutput(true);
//        con.getOutputStream().write(updatedUserJson.getBytes());
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        StringBuilder response = new StringBuilder();
//        String line;
//        while ((line = in.readLine()) != null) {
//            response.append(line);
//        }
//        in.close();
//        return response.toString();
//    }
//
//    public void testDeleteUser() throws IOException {
//        int userId = 1;
//        int responseCode = deleteUser(userId);
//        System.out.println("Видалити користувача, код відповіді: " + responseCode);
//    }
//
//    public int deleteUser(int id) throws IOException {
//        URL url = new URL(START_URL + "/users" + "/" + id);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("DELETE");
//        int responseCode = con.getResponseCode();
//        return responseCode;
//    }
//
//    public String readUser() throws IOException {
//        URL url = new URL(START_URL+ "/users");
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        int responseCode = connection.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//            return response.toString();
//        } else {
//            System.out.println("Користувачів не знайдено");
//            return null;
//        }
//    }
//
//    public String getUserById(int id) throws IOException {
//        URL url = new URL(START_URL + "/users" + "/" + id);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        int responseCode = connection.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            BufferedReader in =
//                    new BufferedReader(
//                            new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//            return response.toString();
//        } else {
//            return "Користувача не знайдено";
//        }
//    }
//    public String getUserByName(String username) throws IOException {
//        URL url = new URL(START_URL + "/users" + "?username=" + username);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        int responseCode = connection.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            BufferedReader in =
//                    new BufferedReader(
//                            new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//            return response.toString();
//        } else {
//            return "Користувача не знайдено";
//        }
//    }

