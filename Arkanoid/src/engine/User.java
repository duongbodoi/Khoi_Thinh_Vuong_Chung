package engine;

public class User implements Comparable<User> {
    private String username;
    private String password;
    private String nickname;
    private int maxScore;
    private int currentLevel;
    public User(String username, String password, String nickname, int maxScore, int currentLevel) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.maxScore = maxScore;
        this.currentLevel = currentLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int compareTo(User o) {
        if(this.maxScore < o.getMaxScore()) {
            return -1;
        }
        else if(this.maxScore > o.getMaxScore()) {
            return 1;
        }
        return 0;
    }
    public String toString(){
        return nickname + "-------" + maxScore + "\n";
    }
}
