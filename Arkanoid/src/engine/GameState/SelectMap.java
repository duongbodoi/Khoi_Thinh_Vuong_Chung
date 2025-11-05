package engine.GameState;

import engine.*;
import engine.User.User;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class SelectMap extends GameState {
    GameButton Map1;
    GameButton Map2;
    GameButton Map3;
    GameButton Map4;
    GameButton Map5;
    private int screenWidth;
    private int screenHeight;
    private User currentUser;
    private boolean[] isLock = new boolean[6];
    public SelectMap(GameManager gameManager, LoadImage loadImage, LoadSound loadSound, User currentUser) {
        super(gameManager,loadImage,loadSound);
        this.currentUser=currentUser;
        screenWidth = gameManager.getWidth();
        screenHeight = gameManager.getHeight();
        for(int i=1;i<=5;i++) {
            if(i>currentUser.getCurrentLevel()) {
                isLock[i]=true;
            }
        }
        Map1 = new GameButton(
                215,
                485,
                50,
                50,
                loadImage.getBall()[0],
                ( isLock[1] ? loadImage.getLock() : loadImage.getBall()[1])
        );

        Map2 = new GameButton(
                205,
                275,
                50,
                50,
                loadImage.getBall()[0],
                ( isLock[2] ? loadImage.getLock() : loadImage.getBall()[1])
        );

        Map3 = new GameButton(
                360,
                210,
                50,
                50,
                loadImage.getBall()[0],
                ( isLock[3] ? loadImage.getLock() : loadImage.getBall()[1])
        );
        Map4 = new GameButton(
                470,
                370,
                50,
                50,
                loadImage.getBall()[0],
                ( isLock[4] ? loadImage.getLock() : loadImage.getBall()[1])
        );
        Map5 = new GameButton(
                430,
                590,
                50,
                50,
                loadImage.getBall()[0],
                ( isLock[5] ? loadImage.getLock() : loadImage.getBall()[1])
        );

        if(!isLock[1]) Map1.setOnClick(() -> gameManager.changeState(new GamePlay(gameManager,loadImage,loadSound,"assets/map1.txt",currentUser,1)));
        if(!isLock[2]) Map2.setOnClick(() -> gameManager.changeState(new GamePlay(gameManager,loadImage,loadSound,"assets/map2.txt",currentUser,2)));
        if(!isLock[3]) Map3.setOnClick(() -> gameManager.changeState(new GamePlay(gameManager,loadImage,loadSound,"assets/map3.txt",currentUser,3)));
        if(!isLock[4]) Map4.setOnClick(() -> gameManager.changeState(new GamePlay(gameManager,loadImage,loadSound,"assets/map4.txt",currentUser,4)));
        if(!isLock[5]) Map5.setOnClick(() -> gameManager.changeState(new GamePlay(gameManager,loadImage,loadSound,"assets/map5.txt",currentUser,5)));

    }

    @Override
    public void handleInput(KeyEvent e) {
        switch (e.getCode()) {
            case E:
                gameManager.changeState(new MainMenu(gameManager,loadImage,loadSound,currentUser));
                break;
            case X:
                System.exit(19);
            case R:
                //gameManager.changeState(new GamePlay(gameManager,loadImage));
                break;
        }
    }

    @Override
    public void updateGame() {

    }

    @Override
    public void handleMouseMoved(MouseEvent e) {
        Map1.checkHover(e.getX(), e.getY());
        Map2.checkHover(e.getX(), e.getY());
        Map3.checkHover(e.getX(), e.getY());
        Map4.checkHover(e.getX(), e.getY());
        Map5.checkHover(e.getX(), e.getY());

    }

    @Override
    public void handleMouseClicked(MouseEvent e) {
        Map1.checkHover(e.getX(), e.getY());
        Map2.checkHover(e.getX(), e.getY());
        Map3.checkHover(e.getX(), e.getY());
        Map4.checkHover(e.getX(), e.getY());
        Map5.checkHover(e.getX(), e.getY());


        Map1.checkClick(e);
        Map2.checkClick(e);
        Map3.checkClick(e);
        Map4.checkClick(e);
        Map5.checkClick(e);


    }

    @Override
    public void renderer(GraphicsContext gc) {
        gc.drawImage(loadImage.getSelectMap(), 0, 0, screenWidth, screenHeight);
        Map1.draw(gc);
        Map2.draw(gc);
        Map3.draw(gc);
        Map4.draw(gc);
        Map5.draw(gc);



    }
}
