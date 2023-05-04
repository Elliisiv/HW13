package UserAPI;

import java.io.IOException;

public class TestUserAPI {

    public static void main(String[] args) throws IOException {
        UserAPI api = new UserAPI();
        api.testCreateUser();
        api.testUpdateUser();
        api.testDeleteUser();

        String userID = api.getUserById(1);
        System.out.println("Пошук користувача за ідентифікатором: " + userID);

        String userName = api.getUserByName("Samantha");
        System.out.println("Пошук користувача за іменем: " + userName);

        String allUsers = api.readUser();
        System.out.println("Всі користувачі: " + allUsers);


    }
}
