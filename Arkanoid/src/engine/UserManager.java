package engine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    List<User> users;
    public UserManager() {
        users = new ArrayList<>();
    }
    public void LoadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/engine/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 5) {
                    String username = parts[0];
                    String password = parts[1];
                    String nickname = parts[2];
                    int maxScore = Integer.parseInt(parts[3]);
                    int currentLevel = Integer.parseInt(parts[4]);
                    users.add(new User(username, password, nickname, maxScore, currentLevel));
                }
            }
        } catch (IOException e) {
            System.out.println("LoadUsers bị lỗi");
        }
    }
    public void AddUser(User user) {
        users.add(user);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/engine/users.txt", true))) {
            bw.write(user.getUsername() + ":" + user.getPassword() + ":" + user.getNickname()
                    + ":" + user.getMaxScore() + ":" + user.getCurrentLevel());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Ghi vào file để lưu bị lỗi");
        }
    }
    public void saveAllUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/engine/users.txt", false))) {
            for (User user : users) {
                bw.write(user.getUsername() + ":" + user.getPassword() + ":" + user.getNickname()
                        + ":" + user.getMaxScore() + ":" + user.getCurrentLevel());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu danh sách users: " + e.getMessage());
        }
    }
    public List<User> getUsers() {
        return users;
    }
}
