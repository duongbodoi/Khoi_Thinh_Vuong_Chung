# Arkanoid

## Giới thiệu chung

- **Thành viên nhóm:**
  - **Nguyễn Tùng Dương** — *Leader*  
    - **Mã sinh viên:**  
    - **Lớp:**  
    - **Nhiệm vụ:**
  - **Trần Mạnh Chiến**  
    - **Mã sinh viên:** 24021395  
    - **Lớp:** CS7  
    - **Nhiệm vụ:**
  - **Bành Văn Hiệp**  
    - **Mã sinh viên:**  
    - **Lớp:**  
    - **Nhiệm vụ:**
  - **Nguyễn Ngọc Anh**  
    - **Mã sinh viên:**  
    - **Lớp:**  
    - **Nhiệm vụ:**

- **Project:** Arkanoid  
- **Link giới thiệu game:** *(thêm link nếu có)*

#### Mục lục

- [Arkanoid](#arkanoid)
  - [Giới thiệu chung](#giới-thiệu-chung)
  - [1. Lời nói đầu](#1-lời-nói-đầu)
  - [2. Cấu trúc chương trình](#2-cấu-trúc-chương-trình)
  - [3. Các thành phần của game](#3-các-thành-phần-của-game)

---

## 1. Lời nói đầu

**Arkanoid** là tựa game kinh điển dạng *brick breaker*, nơi người chơi điều khiển thanh chắn (paddle) để bật bóng phá hủy toàn bộ gạch trên màn hình.  
Trong phiên bản này, nhóm phát triển đã thêm các hiệu ứng, vật phẩm hỗ trợ và nhiều cấp độ thử thách hơn, mang đến trải nghiệm hấp dẫn hơn bản gốc.

---

## 2. Cấu trúc chương trình
```
/ (root)
├─ base/
│ ├─ GameObject.java // Lớp cơ sở cho mọi đối tượng trong game
│ ├─ MovableObject.java // Kế thừa GameObject, thêm khả năng di chuyển
│ └─ Renderer.java // Giao diện hoặc lớp dùng để vẽ đối tượng lên màn hình
│
├─ brick/
│ ├─ Brick.java // Lớp gạch cơ bản
│ ├─ BrickLoadMap.java // Chịu trách nhiệm load bản đồ gạch từ file
│ ├─ FireBrick.java // Gạch hệ lửa (Fire)
│ ├─ IceBrick.java // Gạch hệ băng (Ice)
│ ├─ LeafBrick.java // Gạch hệ lá (Plant)
│ ├─ SoilBrick.java // Gạch hệ đất (Soil)
│ └─ UnbreakBrick.java // Gạch không thể phá
│
├─ engine/
│ ├─ ExplosionControl/
│ │ ├─ Explosion.java
│ │ ├─ NormalExplosion.java
│ │ ├─ PowerUpExplosion.java
│ │ ├─ SoilExplosion.java
│ │ └─ StrongExplosion.java
│ │
│ └─ InGamePlay/
│ ├─ GameButton.java
│ ├─ GameLogin.java
│ ├─ GameManager.java
│ ├─ GameOver.java
│ ├─ GameSignUp.java
│ ├─ GameState.java
│ ├─ GameVictory.java
│ ├─ LoadImage.java
│ ├─ LoadSound.java
│ ├─ Main.java
│ ├─ MainMenu.java
│ ├─ SelectMap.java
│ ├─ User.java
│ └─ UserManager.java
│
├─ entity/
│ ├─ Angle.java
│ ├─ Ball.java
│ ├─ BallProvider.java
│ ├─ Effect.java
│ ├─ Elemental.java
│ └─ Paddle.java
│
├─ powerup/
│ ├─ Fire/
│ │ ├─ FastBall.java
│ │ └─ FireBall.java
│ │
│ ├─ Plant/
│ │ ├─ LeafBall.java
│ │ └─ StunPaddle.java
│ │
│ ├─ Soid/
│ │ ├─ SlowPaddle.java
│ │ └─ SoilBall.java
│ │
│ └─ Water/
│ ├─ DoubleBall.java
│ ├─ ExpandPaddle.java
│ └─ PowerUp.java
│
├─ users.txt
```

---
## 3. Cấu trúc chương trình

Cấu trúc chương trình của **"Arkanoid"** được tổ chức một cách rõ ràng và dễ mở rộng, giúp cho việc quản lý mã nguồn, thêm tính năng và sửa lỗi trở nên thuận tiện hơn. Các thư mục và tệp được chia theo từng chức năng chính như sau:

- **`base/`**: Chứa các lớp cơ sở trừu tượng, cung cấp nền tảng cho hầu hết các đối tượng trong game.  
  - **`GameObject`**: Lớp cha của mọi đối tượng trong game, định nghĩa các thuộc tính cơ bản như vị trí, kích thước, hình ảnh.  
  - **`MovableObject`**: Kế thừa từ `GameObject`, bổ sung khả năng di chuyển cho các đối tượng.  
  - **`Renderer`**: Chịu trách nhiệm hiển thị các đối tượng lên màn hình.  

- **`brick/`**: Quản lý các loại gạch trong trò chơi.  
  - **`Brick`**: Lớp cơ sở cho mọi loại gạch.  
  - **`BrickLoadMap`**: Quản lý việc tải bản đồ (bố trí gạch) từ tệp dữ liệu.  
  - **`FireBrick`, `IceBrick`, `LeafBrick`, `SoilBrick`, `UnbreakBrick`**: Các loại gạch đặc biệt có hiệu ứng riêng (lửa, băng, lá, đất, không thể phá).  

- **`engine/`**: Chứa các thành phần điều khiển chính của trò chơi.  
  - **`ExplosionControl/`**: Quản lý hiệu ứng nổ khi gạch bị phá hủy.  
    - **`Explosion`**, **`NormalExplosion`**, **`PowerUpExplosion`**, **`SoilExplosion`**, **`StrongExplosion`**: Các loại hiệu ứng nổ khác nhau tương ứng với từng loại gạch hoặc vật phẩm.  
  - **`InGamePlay/`**: Quản lý các trạng thái và logic tổng thể của trò chơi.  
    - **`GameState`**: Xử lý các trạng thái khác nhau của game (menu, chơi, tạm dừng, thắng, thua).  
    - **`GameManager`**: Điều phối hoạt động giữa các phần của trò chơi.  
    - **`MainMenu`, `SelectMap`, `GameLogin`, `GameSignUp`, `GameVictory`, `GameOver`**: Quản lý từng màn hình giao diện trong game.  
    - **`LoadImage`, `LoadSound`**: Chịu trách nhiệm tải ảnh và âm thanh cần thiết.  
    - **`Main`**: Điểm bắt đầu chạy game.  

- **`entity/`**: Chứa các đối tượng chính có trong gameplay.  
  - **`Ball`**: Quản lý quả bóng, bao gồm hướng di chuyển, va chạm và tốc độ.  
  - **`Paddle`**: Thanh chắn do người chơi điều khiển.  
  - **`Angle`, `Effect`, `Elemental`**: Hỗ trợ xử lý vật lý, hiệu ứng và đặc tính nguyên tố cho bóng.  
  - **`BallProvider`**: Cung cấp và quản lý các loại bóng đặc biệt.  

- **`powerup/`**: Quản lý các vật phẩm tăng sức mạnh cho người chơi.  
  - **`Fire/`**: Chứa các power-up liên quan đến nguyên tố lửa như `FastBall`, `FireBall`.  
  - **`Plant/`**: Chứa các power-up thuộc hệ thực vật như `LeafBall`, `StunPaddle`.  
  - **`Soid/`**: (Đất) bao gồm `SoilBall`, `SlowPaddle`.  
  - **`Water/`**: Gồm `DoubleBall`, `ExpandPaddle`, `PowerUp` — mở rộng hoặc nhân đôi khả năng của người chơi.  

- **`users.txt`**: Tệp lưu trữ thông tin người chơi như tên đăng nhập, mật khẩu, hoặc điểm cao.  

Nhờ cách tổ chức này, mã nguồn của Arkanoid dễ dàng bảo trì, mở rộng thêm nhiều loại vật phẩm, hiệu ứng hoặc màn chơi mới mà không ảnh hưởng đến cấu trúc tổng thể.

---
# 4. Các thành phần của game

## a. Player (Người chơi)

Người chơi điều khiển paddle để đỡ bóng và phá gạch.

Paddle được định nghĩa trong `entity/Paddle.java`.

Có thể nhận `effect` hoặc `power-up` để tạm thời thay đổi tính năng, ví dụ: mở rộng paddle, giảm tốc paddle, choáng paddle, được xử lý bởi các lớp trong `entity/Effect.java` và `powerup/`.

Thanh điểm của người chơi được lưu và quản lý trong `engine/InGamePlay/User.java` và `UserManager.java`.

<div style="text-align: center;">
  <img src="img/paddle.png" width="200" height="100">
</div>

---

## b. Ball (Quả bóng)

Quả bóng di chuyển liên tục và nảy khi va chạm với paddle, gạch hoặc tường.

Lớp `entity/Ball.java` mô tả vị trí, tốc độ, góc đánh của bóng.

Bóng có thể nhận elemental hoặc power-up, ví dụ:

- `FireBall.java` (bóng lửa)  
- `LeafBall.java` (bóng hệ lá)  
- `DoubleBall.java` (nhân đôi bóng)  
- `FastBall.java` (tăng tốc bóng)

<div style="text-align: center;">
  <img src="img/ball.png" width="100" height="100">
</div>

---

## c. Brick (Gạch)

Gạch được định nghĩa trong thư mục `brick/` và có các loại:

- `Brick.java` – gạch cơ bản  
- `FireBrick.java` – gạch hệ lửa  
- `IceBrick.java` – gạch hệ băng  
- `LeafBrick.java` – gạch hệ lá  
- `SoilBrick.java` – gạch hệ đất  
- `UnbreakBrick.java` – gạch không thể phá

Bản đồ gạch được load từ file bởi `BrickLoadMap.java`.

Khi phá hủy, gạch có thể tạo ra `Explosion` hoặc `Power-up`.

<div style="text-align: center;">
  <img src="img/brick.png" width="150" height="50">
</div>

---

## d. Explosion (Hiệu ứng nổ)

Quản lý hiệu ứng khi gạch bị phá trong `engine/ExplosionControl/`.

Các lớp chính:

- `Explosion.java` – lớp cơ sở  
- `NormalExplosion.java` – nổ thường  
- `PowerUpExplosion.java` – kèm power-up  
- `SoilExplosion.java`, `StrongExplosion.java` – nổ đặc biệt

<div style="text-align: center;">
  <img src="img/explosion.png" width="150" height="150">
</div>

---

## e. Power-up

Power-up xuất hiện khi phá gạch, giúp người chơi tạm thời thay đổi gameplay:

- Mở rộng paddle: `ExpandPaddle.java`  
- Nhân đôi bóng: `DoubleBall.java`  
- Tăng tốc bóng: `FastBall.java`  
- Bóng đặc biệt: `FireBall.java`, `LeafBall.java`, `SoilBall.java`  
- Hiệu ứng lên paddle: `StunPaddle.java`, `SlowPaddle.java`

<div style="text-align: center;">
  <img src="img/power-up1.png" width="100" height="100">
  <img src="img/power-up2.png" width="100" height="100">
  <img src="img/power-up3.png" width="100" height="100">
</div>

---

## f. Game Engine & Quản lý

- `engine/InGamePlay/GameManager.java` – điều khiển logic game chính, cập nhật trạng thái paddle, bóng, gạch, power-up.  
- `LoadImage.java` và `LoadSound.java` – quản lý tải ảnh và âm thanh.  
- `GameState.java`, `MainMenu.java`, `SelectMap.java`, `GameOver.java`, `GameVictory.java` – quản lý giao diện và trạng thái game.  
- `User.java` và `UserManager.java` – quản lý dữ liệu người chơi, lưu vào `users.txt`.
