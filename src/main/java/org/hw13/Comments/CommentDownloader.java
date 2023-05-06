package org.hw13.Comments;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


public class CommentDownloader {
    private static final String URL = "https://jsonplaceholder.typicode.com";
    private static final Gson GSON = new Gson();

    public static void createFileWithComments(int userId) throws IOException {
        int postId = getIdOfTheLastPost(userId);
        List<Comment> comments = getAllUserComment(userId);
        String fileName = "user-" + userId + "-post-" + postId + "-comments.json";
        try (Writer writer = new FileWriter(fileName)) {
            GSON.toJson(comments, writer);
            System.out.println("Comments successfully written to file " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing comments to file: " + e.getMessage());
        }
    }

    public static List<Comment> getAllUserComment(int userId) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/posts/" + getIdOfTheLastPost(userId) + "/comments").openConnection();
        connection.setRequestMethod("GET");
        Comment[] result = GSON.fromJson(new InputStreamReader(connection.getInputStream()), Comment[].class);
        return Arrays.asList(result);
    }

    public static int getIdOfTheLastPost(int userId) throws IOException {
        Post[] result = getAllUsersPost(userId);
        return Arrays.stream(result)
                .map(Post::getId)
                .mapToInt(Integer::intValue)
                .max()
                .getAsInt();
    }

    public static Post[] getAllUsersPost(int userId) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/users/" + userId + "/posts").openConnection();
        connection.setRequestMethod("GET");
        Post[] result = GSON.fromJson(new InputStreamReader(connection.getInputStream()), Post[].class);
        return result;
    }
}

//  public static void downloadFile(int userId) throws IOException {
//        int postId = getLatestPostId(userId);
//        if (postId == -1) {
//            System.out.println("Користувач не має жодного поста");
//            return;
//        }
//
//        String fileName = "user-" + userId + "-post-" + postId + "-comments.json";
//        URL url = new URL(START_URL + "/posts/" + postId + "/comments");
//        try (InputStream in = url.openStream();
//             ReadableByteChannel rbc = Channels.newChannel(in);
//             FileOutputStream fos = new FileOutputStream(fileName)) {
//            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//            System.out.println("Коментарі успішно записані у файл " + fileName);
//        } catch (IOException e) {
//            System.out.println("Помилка з'єднання з сервером: " + e.getMessage());
//        }
//    }
//
//    public static int getLatestPostId(int userId) {
//        try {
//            URL latestPostURL = new URL(START_URL + "/users/" + userId + "/posts");
//            HttpURLConnection conn = (HttpURLConnection) latestPostURL.openConnection();
//            conn.setRequestMethod("GET");
//            conn.connect();
//
//            Scanner sc = new Scanner(conn.getInputStream());
//            StringBuilder sb = new StringBuilder();
//            while (sc.hasNext()) {
//                sb.append(sc.nextLine());
//            }
//            sc.close();
//
//            JSONArray posts = new JSONArray(sb.toString());
//            int maxId = -1;
//            for (int i = 0; i < posts.length(); i++) {
//                JSONObject item = posts.getJSONObject(i);
//                int id = item.getInt("id");
//                if (id > maxId) {
//                    maxId = id;
//                }
//            }
//            return maxId;
//        } catch (IOException | JSONException e) {
//            System.out.println("Помилка з'єднання з сервером: " + e.getMessage());
//            return -1;
//        }
//    }
//
//    public static void main (String[] args) throws Exception {
//            CommentDownloader cd = new CommentDownloader();
//            cd.downloadFile(1);
//        }