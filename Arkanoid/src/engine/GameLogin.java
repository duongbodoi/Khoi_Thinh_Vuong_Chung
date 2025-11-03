package engine;

import engine.InGamePlay.GamePlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class GameLogin extends GameState {
    private UserManager userManager;
    private String  username;
    private String password;
    GameButton UnameButton;
    GameButton passButton;
    GameButton loginButton;
    GameButton signUpButton;
    private int screenWidth;
    private int screenHeight;
    boolean enterUsername = false;
    boolean enterPassword = false;
    boolean loginSuccess = false;
    List<User> userList;
    public GameLogin(GameManager gameManager,LoadImage loadImage,LoadSound loadSound,UserManager userManager) {
        super(gameManager,loadImage,loadSound);
        this.userManager=userManager;
        userList=userManager.getUsers();
        screenWidth = gameManager.getWidth();
        screenHeight = gameManager.getHeight();
        username="";
        password="";
        loadSound.getLoginPlay().play();;
        UnameButton = new GameButton(
                190,
                300,
                400,
                50,
                loadImage.getUsername(),
                loadImage.getUsername()
        );

        loginButton = new GameButton(
                screenWidth / 2.0 - (screenWidth * 0.1) / 2-150,
                screenHeight * 0.6,
                100,
                50,
                loadImage.getLogin(),
                loadImage.getLogin()
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
        });
        passButton.setOnClick(() -> {
            enterPassword = true;
            enterUsername = false;
        });
        loginButton.setOnClick(() ->{
            password = password.strip();
            username = username.strip();
            if(checkAccount() != null) {
                gameManager.changeState(new MainMenu(gameManager,loadImage,loadSound,checkAccount()));
            }
            else{
                gameManager.changeState(new GameSignUp(gameManager,loadImage,loadSound,userManager));
            }
        });
        signUpButton.setOnClick(() ->gameManager.changeState(new GameSignUp(gameManager,loadImage,loadSound,userManager)));
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
                if (e.getCode()==KeyCode.ENTER) {
                    password = password.strip();
                    username = username.strip();
                    if(checkAccount() != null) {
                        gameManager.changeState(new MainMenu(gameManager,loadImage,loadSound,checkAccount()));
                    }
                    else{
                        gameManager.changeState(new GameLogin(gameManager,loadImage,loadSound,userManager));
                    }

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
        loginButton.checkHover(e.getX(), e.getY());
        signUpButton.checkHover(e.getX(), e.getY());
    }

    @Override
    public void handleMouseClicked(MouseEvent e) {
        UnameButton.checkHover(e.getX(), e.getY());
        passButton.checkHover(e.getX(), e.getY());
        loginButton.checkHover(e.getX(), e.getY());
        signUpButton.checkHover(e.getX(), e.getY());

        UnameButton.checkClick(e);
        passButton.checkClick(e);
        loginButton.checkClick(e);
        signUpButton.checkClick(e);
    }

    @Override
    public void renderer(GraphicsContext gc) {
        gc.drawImage(loadImage.getBgrlogin(), 0, 0, screenWidth, screenHeight);
        UnameButton.draw(gc);
        passButton.draw(gc);
        loginButton.draw(gc);
        signUpButton.draw(gc);
        gc.fillText("Username:" + username, 255, 325);
        gc.fillText("Password:" + password, 255, 418);
    }
    public User checkAccount() {
        for(User user:userList){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
}
