package engine;

import engine.InGamePlay.GamePlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class GameSignUp extends GameState {
    private UserManager userManager;
    private String  username;
    private String password;
    private String nickname;
    GameButton UnameButton;
    GameButton passButton;
    GameButton nicknameButton;
    GameButton signUpButton;
    private int screenWidth;
    private int screenHeight;
    boolean enterUsername = false;
    boolean enterPassword = false;
    boolean enterNickname = false;
    List<User> userList;
    public GameSignUp(GameManager gameManager,LoadImage loadImage,UserManager userManager) {
        super(gameManager,loadImage);
        this.userManager=userManager;
        userList=userManager.getUsers();
        screenWidth = gameManager.getWidth();
        screenHeight = gameManager.getHeight();
        username="";
        password="";
        nickname="";
        UnameButton = new GameButton(
                190,
                300,
                400,
                50,
                loadImage.getUsername(),
                loadImage.getUsername()
        );

        nicknameButton = new GameButton(
                190,
                200,
                400,
                50,
                loadImage.getUsername(),
                loadImage.getUsername()
        );
        signUpButton = new GameButton(
                screenWidth / 2.0 - (screenWidth * 0.1) / 2+50,
                screenHeight * 0.6,
                100,
                50,
                loadImage.getSignup(),
                loadImage.getSignup()
        );

        passButton = new GameButton(
                190,
                400,
                400,
                50,
                loadImage.getPassword(),
                loadImage.getPassword()
        );

        UnameButton.setOnClick(() -> {
            enterUsername = true;
            enterPassword = false;
            enterNickname = false;
        });
        passButton.setOnClick(() -> {
            enterPassword = true;
            enterUsername = false;
            enterNickname = false;
        });
        nicknameButton.setOnClick(() ->{
            enterNickname = true;
            enterPassword = false;
            enterUsername = false;

        });
        signUpButton.setOnClick(() -> {
            password = password.strip();
            username = username.strip();
            User newUser= new User(username,password,nickname,0,1);
            userManager.AddUser(newUser);
            gameManager.changeState(new MainMenu(gameManager,loadImage,newUser));
        });
    }

    @Override
    public void handleInput(KeyEvent e) {
        switch (e.getEventType().getName()) {
            case "KEY_PRESSED":
                String c = e.getText();
                if (enterUsername) {
                    username+=c;
                    if(e.getCode()== KeyCode.BACK_SPACE){
                        username = username.substring(0, username.length() - 1);
                    }
                }
                if (enterPassword) {
                    password+=c;
                    if(e.getCode()== KeyCode.BACK_SPACE){
                        password = password.substring(0, password.length() - 1);
                    }
                }
                if(enterNickname) {
                    nickname+=c;
                    if(e.getCode()== KeyCode.BACK_SPACE){
                        nickname = nickname.substring(0, nickname.length() - 1);
                    }
                }
                if (e.getCode()==KeyCode.ENTER) {
                    password = password.strip();
                    username = username.strip();


                }
                break;
            case "KEY_RELEASED":
                break;
        }


    }

    @Override
    public void updateGame() {

    }

    @Override
    public void handleMouseMoved(MouseEvent e) {
        UnameButton.checkHover(e.getX(), e.getY());
        passButton.checkHover(e.getX(), e.getY());
        nicknameButton.checkHover(e.getX(), e.getY());
        signUpButton.checkHover(e.getX(), e.getY());
    }

    @Override
    public void handleMouseClicked(MouseEvent e) {
        UnameButton.checkHover(e.getX(), e.getY());
        passButton.checkHover(e.getX(), e.getY());
        nicknameButton.checkHover(e.getX(), e.getY());
        signUpButton.checkHover(e.getX(), e.getY());

        UnameButton.checkClick(e);
        passButton.checkClick(e);
        nicknameButton.checkClick(e);
        signUpButton.checkClick(e);
    }

    @Override
    public void renderer(GraphicsContext gc) {
        gc.drawImage(loadImage.getBgrlogin(), 0, 0, screenWidth, screenHeight);
        UnameButton.draw(gc);
        passButton.draw(gc);
        nicknameButton.draw(gc);
        signUpButton.draw(gc);
        gc.fillText("Username:" + username, 255, 325);
        gc.fillText("Password:" + password, 255, 418);
        gc.fillText("Nickname:" + nickname, 255, 232);

    }

}
