package Game.Entities.Creatures;

import Game.Entities.EntityBase;
import Game.Inventories.Inventory;
import Game.Items.Item;
import Main.Handler;
import Resources.Animation;
import Resources.Images;
import Worlds.CaveWorld;

import java.awt.*;
import java.util.Random;

import com.sun.glass.events.KeyEvent;

import java.awt.*;
import java.util.Random;

import com.sun.glass.events.KeyEvent;

/**
 * Created by Elemental on 2/7/2017.
 */
public class HorribleMorty extends CreatureBase  {


	private Animation animDown, animUp, animLeft, animRight;

	private Boolean attacking=false;
	private Boolean isSummoned=false;

	private int animWalkingSpeed = 150;
	private Inventory horribleMortyInventory;
	private Rectangle horribleMortyCam;
	private Rectangle ar = new Rectangle();
	private EntityBase targetAttack;

	private int healthcounter =0;

	private Random randint;
	private int moveCount=0;
	private int direction;

	public HorribleMorty(Handler handler, float x, float y) {
		super(handler, x, y, CreatureBase.DEFAULT_CREATURE_WIDTH, CreatureBase.DEFAULT_CREATURE_HEIGHT);
		bounds.x=8*2;
		bounds.y=18*2;
		bounds.width=16*2;
		bounds.height=14*2;
		speed=1.5f;
		health=50;
		targetAttack = this;

		horribleMortyCam= new Rectangle();

		randint = new Random();
		direction = randint.nextInt(4) + 1;

		animDown = new Animation(animWalkingSpeed, Images.horribleMorty_front);
		animLeft = new Animation(animWalkingSpeed,Images.horribleMorty_left);
		animRight = new Animation(animWalkingSpeed,Images.horribleMorty_right);
		animUp = new Animation(animWalkingSpeed,Images.horribleMorty_back);

		horribleMortyInventory= new Inventory(handler);
	}

	@Override
	public void tick() {
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();

		moveCount ++;
		
		if(moveCount>=60){
			moveCount=0;
			direction = randint.nextInt(4) + 1;
		}
		
		checkIfMove();

		move();


		if(isBeinghurt()){
			healthcounter++;
			if(healthcounter>=120){
				setBeinghurt(false);
				System.out.print(isBeinghurt());
			}
		}
		if(healthcounter>=120&& !isBeinghurt()){
			healthcounter=0;
		}

		/*Verifies the following condition:
          if the player is intersecting with this entity, the player has a wand
          and the player pressed the "G" button, then the enemy will become your companion */
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_G) && 
				handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(ar)
				&& handler.getWorld().getEntityManager().getPlayer().getSummonAbility()){
			isSummoned = true;
			handler.getWorld().getEntityManager().getPlayer().setSummonAbility(false);
			Item.magicWand.setCount(Item.magicWand.getCount()-1);
			this.setSpeed(handler.getWorld().getEntityManager().getPlayer().getSpeed()-(float)0.9);
		}

		horribleMortyInventory.tick();


	}

	public Boolean isSummoned() {
		return isSummoned;
	}

	private void checkIfMove() {
		xMove = 0;
		yMove = 0;

		horribleMortyCam.x = (int) (x - handler.getGameCamera().getxOffset() - (64 * 3));
		horribleMortyCam.y = (int) (y - handler.getGameCamera().getyOffset() - (64 * 3));
		horribleMortyCam.width = 64 * 7;
		horribleMortyCam.height = 64 * 7;

		if (horribleMortyCam.contains(handler.getWorld().getEntityManager().getPlayer().getX() - handler.getGameCamera().getxOffset(), handler.getWorld().getEntityManager().getPlayer().getY() - handler.getGameCamera().getyOffset())
				|| horribleMortyCam.contains(handler.getWorld().getEntityManager().getPlayer().getX() - handler.getGameCamera().getxOffset() + handler.getWorld().getEntityManager().getPlayer().getWidth(), handler.getWorld().getEntityManager().getPlayer().getY() - handler.getGameCamera().getyOffset() + handler.getWorld().getEntityManager().getPlayer().getHeight())) {

			Rectangle cb = getCollisionBounds(0, 0);
			int arSize = 13;
			ar.width = arSize;
			ar.height = arSize;

			if (lu) {
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y - arSize;
			} else if (ld) {
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;
			} else if (ll) {
				ar.x = cb.x - arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			} else if (lr) {
				ar.x = cb.x + cb.width;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			}

			for (EntityBase e : handler.getWorld().getEntityManager().getEntities()) {
				if (e.equals(this))
					continue;
				else if (!isSummoned && e.getCollisionBounds(0, 0).intersects(ar) && e.equals(handler.getWorld().getEntityManager().getPlayer())) {

					targetAttack = handler.getWorld().getEntityManager().getPlayer();
					checkAttacks();
					return;

				}else if((this.getX() >= e.getX() - 15 && this.getX() <= e.getX() + 15) || (this.getY() >= e.getY() - 15 && this.getY() <= e.getY() + 15)){
					targetAttack = e;
					if(isSummoned && e.getCollisionBounds(0, 0).intersects(ar) && !(e.equals(handler.getWorld().getEntityManager().getPlayer()))){

						checkAttacks();
						return;

					}
				}else{
					targetAttack = handler.getWorld().getEntityManager().getPlayer();	
				}
			}
			
			if(!handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(ar)){
				if (x >= targetAttack.getX() - 8 && x <= targetAttack.getX() + 8) {//nada
					xMove = 0;
				} else if (x < targetAttack.getX()) {//move right

					xMove = speed;

				} else if (x > targetAttack.getX()) {//move left

					xMove = -speed;
				}

				if (y >= targetAttack.getY() - 8 && y <= targetAttack.getY() + 8) {//nada
					yMove = 0;
				} else if (y < targetAttack.getY()) {//move down
					yMove = speed;

				} else if (y > targetAttack.getY()) {//move up
					yMove = -speed;
				}	
			}

		} else if(!isSummoned){

			switch (direction) {
			case 1://up
				yMove = -speed;
				break;
			case 2://down
				yMove = speed;
				break;
			case 3://left
				xMove = -speed;
				break;
			case 4://right
				xMove = speed;
				break;

			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(animDown,animUp,animLeft,animRight,Images.horribleMorty_front,Images.horribleMorty_back,Images.horribleMorty_left,Images.horribleMorty_right), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		if(isBeinghurt() && healthcounter<=120){
			g.setColor(Color.white);
			g.drawString("Cronenberg Morty: " + getHealth(),(int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()-20));
		}
	}




	@Override
	public void die() {
		isDead=true;
		if(!CaveWorld.getZombieJerry().isSummoned() || !CaveWorld.getZombieJerry().isDead()){
			handler.getWorld().getItemManager().addItem(Item.magicWand.createNew((int)x + bounds.x,(int)y + bounds.y,1));		
		}
	}
}
