package engine;

import engine.InGamePlay.GamePlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MainMenu extends GameState {
    private int screenWidth;
    private int screenHeight;

    private GameButton enterButton;
    private GameButton escButton;

    private User currentUser;      // null -> vào thẳng GamePlay
    private LoadImage loadImage;   // có thể nhận từ ngoài hoặc tự tạo

    // --- Cách khởi tạo 1: giống bản đầu (tự tạo LoadImage, không có User, vào GamePlay) ---
    public MainMenu(GameManager gm) {
        super(gm);
        this.loadImage = new LoadImage();
        init(null);
    }

    // --- Cách khởi tạo 2: giống bản sau (nhận sẵn LoadImage + User, vào SelectMap) ---
    public MainMenu(GameManager gm, LoadImage loadImage, User currentUser) {
        super(gm, loadImage);
        // super(gm, loadImage) có thể gán field loadImage trong GameState;
        // nhưng để chắc chắn, vẫn giữ tham chiếu cục bộ:
        this.loadImage = (loadImage != null) ? loadImage : new LoadImage();
        init(currentUser);
    }

    private void init(User user) {
        this.currentUser = user;
        this.screenHeight = gameManager.getHeight();
        this.screenWidth  = gameManager.getWidth();

        // Vị trí/kích thước nút: dùng bố cục dễ bấm từ bản 2
        enterButton = new GameButton(
                screenWidth / 2 - 90,
                screenHeight / 2,
                180, 90,
                loadImage.getPlayNormal(),
                loadImage.getPlayHover()
        );

        escButton = new GameButton(
                screenWidth / 2 - 100,
                screenHeight / 2 + 50,
                180, 90,
                loadImage.getEscNormal(),
                loadImage.getEscHover()
        );

        // Click: vào SelectMap nếu có currentUser, ngược lại vào GamePlay
        enterButton.setOnClick(this::goPlay);
        escButton.setOnClick(() -> System.exit(0));
    }

    private void goPlay() {
        if (currentUser != null) {
            gameManager.changeState(new SelectMap(gameManager, loadImage, currentUser));
        } else {
            gameManager.changeState(new GamePlay(gameManager));
        }
    }

    @Override
    public void handleInput(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER  -> goPlay();     // hỗ trợ ENTER từ bản đầu
            case ESCAPE -> System.exit(3);
        }
    }

    @Override
    public void updateGame() {
        // menu tĩnh, không cần cập nhật gì thêm
    }

    @Override
    public void handleMouseMoved(MouseEvent e) {
        enterButton.checkHover(e.getX(), e.getY());
        escButton.checkHover(e.getX(), e.getY());
    }

    @Override
    public void handleMouseClicked(MouseEvent e) {
        // cập nhật hover trước để ảnh hover hiển thị đúng khi click
        enterButton.checkHover(e.getX(), e.getY());
        escButton.checkHover(e.getX(), e.getY());

        // xử lý click
        escButton.checkClick(e);
        enterButton.checkClick(e);
    }

    @Override
    public void renderer(GraphicsContext gc) {
        // nền
        if (loadImage.getBackgroundMain() != null) {
            gc.drawImage(loadImage.getBackgroundMain(), 0, 0, screenWidth, screenHeight);
        }

        // vẽ tiêu đề nếu có
        if (loadImage.getNameGame() != null) {
            gc.drawImage(loadImage.getNameGame(), 50, -80, 700, 400);
        }

        // vẽ bảng menu nếu có
        if (loadImage.getMenu() != null) {
            gc.drawImage(loadImage.getMenu(), 150, 220, 500, 150);
        }

        // vẽ các nút
        enterButton.draw(gc);
        escButton.draw(gc);
    }
}
