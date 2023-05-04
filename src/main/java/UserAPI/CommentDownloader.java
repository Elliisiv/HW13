package UserAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;
import static UserAPI.UserAPI.START_URL;

public class CommentDownloader {
    public static void downloadFile(int userId) throws IOException {
        int postId = getLatestPostId(userId);
        if (postId == -1) {
            System.out.println("Користувач не має жодного поста");
            return;
        }

        String fileName = "user-" + userId + "-post-" + postId + "-comments.json";
        URL url = new URL(START_URL + "/posts/" + postId + "/comments");
        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            System.out.println("Коментарі успішно записані у файл " + fileName);
        } catch (IOException e) {
            System.out.println("Помилка з'єднання з сервером: " + e.getMessage());
        }
    }

    public static int getLatestPostId(int userId) {
        try {
            URL latestPostURL = new URL(START_URL + "/users/" + userId + "/posts");
            HttpURLConnection conn = (HttpURLConnection) latestPostURL.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner sc = new Scanner(conn.getInputStream());
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
            sc.close();

            JSONArray posts = new JSONArray(sb.toString());
            int maxId = -1;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject item = posts.getJSONObject(i);
                int id = item.getInt("id");
                if (id > maxId) {
                    maxId = id;
                }
            }
            return maxId;
        } catch (IOException | JSONException e) {
            System.out.println("Помилка з'єднання з сервером: " + e.getMessage());
            return -1;
        }
    }

    public static void main (String[] args) throws Exception {
            CommentDownloader cd = new CommentDownloader();
            cd.downloadFile(1);
        }
}