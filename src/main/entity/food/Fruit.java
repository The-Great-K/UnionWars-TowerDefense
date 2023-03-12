package main.entity.food;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.entity.Entity;
import main.entity.EntityEnum;
import main.entity.Player;
import main.window.GamePanel;

public class Fruit extends Entity {

	public GamePanel gp;

	public EntityEnum type;

	private int timer = 0;
	private boolean waited = false;

	private Random rand = new Random();

	public int xSpeed, ySpeed;

	public String[] fruitList = new String[6];
	private int fruitType = rand.nextInt(fruitList.length);

	public BufferedImage image_cut_top, image_cut_bottom;

	private boolean isCut;

	public Fruit(GamePanel gp, Player player, int x, int y) {
		this.gp = gp;

		fruitList[0] = "mango";
		fruitList[1] = "apple";
		fruitList[2] = "banana";
		fruitList[3] = "orange";
		fruitList[4] = "pineapple";
		fruitList[5] = "watermelon";

		setDefaultValues();
		getFruitImage();

		this.x = x;
		this.y = y;

		this.hitbox = new Rectangle(x, y);
	}

	public void setDefaultValues() {
		this.x = 0;
		this.y = 0;

		this.hitboxOn = true;
		this.isCut = false;

		this.type = EntityEnum.FOOD;
	}

	public void getFruitImage() {
		try {
			image = ImageIO
					.read(getClass().getResourceAsStream("/textures/foods/fruits/" + fruitList[fruitType] + ".png"));
			image_cut_top = ImageIO.read(
					getClass().getResourceAsStream("/textures/foods/fruits/" + fruitList[fruitType] + "_top.png"));
			image_cut_bottom = ImageIO.read(
					getClass().getResourceAsStream("/textures/foods/fruits/" + fruitList[fruitType] + "_bottom.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if (!waited) {
			if (timer == 1) {
				waited = true;
				ySpeed = (int) (gp.tileHeight / 3);
			}
		}

		if (gp.player.hitbox != null) {
			if (this.isTouching(gp.player) && !this.isCut) {
				this.isCut = true;
				this.hitboxOn = false;
			}
		}

		y -= ySpeed;
		if (timer == 5) {
			ySpeed -= 1;
			timer = 0;
		}

		timer++;
	}

	public void render(Graphics2D g2) {
		BufferedImage image = this.image;
		BufferedImage image_cut_top = this.image_cut_top;
		BufferedImage image_cut_bottom = this.image_cut_bottom;

		this.width = gp.tileWidth * 2;
		this.height = gp.tileHeight * 2;

		if (this.hitboxOn) {
			this.hitbox = new Rectangle(this.x, this.y, width, height);

			g2.setColor(Color.red);
			g2.drawRect(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height);
		}

		if (!this.isCut) {
			g2.drawImage(image, this.x, this.y, width, height, null);
		} else {
			g2.drawImage(image_cut_top, this.x, this.y, width, height, null);
		}
	}

	public void write() {
		System.out.println(this.isCut);
	}

}
