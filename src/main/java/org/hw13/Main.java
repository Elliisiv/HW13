package org.hw13;

import org.hw13.UserAPI.User;
import org.hw13.UserAPI.UserAPI;

import java.io.IOException;

import static org.hw13.Comments.CommentDownloader.createFileWithComments;
import static org.hw13.Todos.OpenTasksProgram.OpenTask;
import static org.hw13.UserAPI.UserAPI.*;


public class Main {
    public static void main(String[] args) throws IOException {
        User user = createUser();
        System.out.println(user);
        UserAPI.testUpdateUser();
        UserAPI.deleteUser(4);
        readUser();
        getUserById(5);
        getUserByName("Elwyn.Skiles");

        createFileWithComments(1);
        OpenTask(3);
    }
}