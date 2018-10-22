package Game.Entities.Creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.sun.glass.events.KeyEvent;

import Game.Inventories.Inventory;
import Game.Items.Item;
import Main.Handler;
import Resources.Animation;
import Resources.Fonts;
import Resources.Images;

/**
 * Created by Elemental on 2/7/2017.
 */
public class RickPickle extends CreatureBase  {


    private Animation animDown, animUp, animLeft, animRight;

    private int animWalkingSpeed = 150;
    private Inventory rickPickleInventory;
    private Rectangle rickPickleCam;
    private Rectangle ar = new Rectangle();

    private int healthcounter =0;

    private Random randint;
    private boolean playerInteraction;
    private static boolean deliveredToRickPickle;
	private boolean keyDelivered;
	private int coinsLeft = 3;
    private int moveCount=0;
    private int direction;

    public RickPickle(Handler handler, float x, float y) {
        super(handler, x, y, CreatureBase.DEFAULT_CREATURE_WIDTH, CreatureBase.DEFAULT_CREATURE_HEIGHT);
        bounds.x=8*2;
        bounds.y=18*2;
        bounds.width=16*2;
        bounds.height=14*2;
        speed=1.0f;
        health=50;
        keyDelivered = false;
        deliveredToRickPickle = false;

        rickPickleCam= new Rectangle();

        randint = new Random();
        direction = randint.nextInt(4) + 1;

        animDown = new Animation(animWalkingSpeed, Images.PickleRick_front);
        animLeft = new Animation(animWalkingSpeed,Images.PickleRick_left);
        animRight = new Animation(animWalkingSpeed,Images.PickleRick_right);
        animUp = new Animation(animWalkingSpeed,Images.PickleRick_back);

        rickPickleInventory= new Inventory(handler);
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

        playerInteraction = handler.getKeyManager().attbut && handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(ar);
        rickPickleInventory.tick();


    }


    private void checkIfMove() {
        xMove = 0;
        yMove = 0;

        rickPickleCam.x = (int) (x - handler.getGameCamera().getxOffset() - (64 * 3));
        rickPickleCam.y = (int) (y - handler.getGameCamera().getyOffset() - (64 * 3));
        rickPickleCam.width = 64 * 7;
        rickPickleCam.height = 64 * 7;

        if (rickPickleCam.contains(handler.getWorld().getEntityManager().getPlayer().getX() - handler.getGameCamera().getxOffset(), handler.getWorld().getEntityManager().getPlayer().getY() - handler.getGameCamera().getyOffset())
                || rickPickleCam.contains(handler.getWorld().getEntityManager().getPlayer().getX() - handler.getGameCamera().getxOffset() + handler.getWorld().getEntityManager().getPlayer().getWidth(), handler.getWorld().getEntityManager().getPlayer().getY() - handler.getGameCamera().getyOffset() + handler.getWorld().getEntityManager().getPlayer().getHeight())) {

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
        }else{


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
    
    public static Boolean itemsDeliveredToRickPickle(){
    	return deliveredToRickPickle;
    }

    @Override
    public void render(Graphics g) {
    	g.drawImage(getCurrentAnimationFrame(animDown,animUp,animLeft,animRight,Images.PickleRick_front,Images.PickleRick_back,Images.PickleRick_left,Images.PickleRick_right), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    	if (!deliveredToRickPickle){
    		if(playerInteraction && (coinsLeft > 0 || !keyDelivered)){
    			
    			handler.getWorld().getEntityManager().getPlayer().setHealthBarVisibility(false);
    			g.setColor(Color.yellow);
    			g.setFont(Fonts.codeFont);
    			g.drawString("I need the following items to open you the door:",(int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()-30));
    			
    			if(!keyDelivered && Inventory.getKeyCount() >= 1){
    				if(Inventory.getKeyCount()==1){
    					handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems().remove(Item.key);
        				Inventory.setKeyCount(0);
    				}else{
        				Inventory.setKeyCount(Inventory.getKeyCount() - 1);
        				Item.key.setCount(Inventory.getKeyCount());				
    				}
    				keyDelivered = true;
    			}	

    			if(Inventory.getCoinCount() >= coinsLeft){
    				if(Inventory.getCoinCount()==coinsLeft){
        				handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems().remove(Item.coin);
        				Inventory.setCointCount(0);
    				}else{
        				Inventory.setCointCount(Inventory.getCoinCount() - coinsLeft);
        				Item.coin.setCount(Inventory.getCoinCount());
    				}
    				coinsLeft = 0;
    			}
    			
    			else if(Inventory.getCoinCount() < 3){
    					if(Inventory.getCoinCount()==2 && coinsLeft == 3){
    						coinsLeft = 3 - 2;
    						Inventory.setCointCount(0);
    						handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems().remove(Item.coin);
    					}else if (Inventory.getCoinCount()==1 && coinsLeft == 3){
    						coinsLeft = 3 - 1;
    						Inventory.setCointCount(0);
    						handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems().remove(Item.coin);
    					}else if (Inventory.getCoinCount()==1 && coinsLeft == 2){
    						coinsLeft = 2 - 1;
    						Inventory.setCointCount(0);
    						handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems().remove(Item.coin);
    					}
    			}

    			if(coinsLeft > 0){
    				g.drawImage(Images.coin, (int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()-30), 30, 30, null);
    				g.drawString(coinsLeft + " coins",(int) (x-handler.getGameCamera().getxOffset())+35,(int) (y-handler.getGameCamera().getyOffset()-10));	
    			}
    			
    			if(!keyDelivered){
    				g.drawImage(Images.key, (int) (x-handler.getGameCamera().getxOffset()) + 100,(int) (y-handler.getGameCamera().getyOffset()-30), 18, 44, null);
    				g.drawString("1 key",(int) (x-handler.getGameCamera().getxOffset()) + 100 + 23,(int) (y-handler.getGameCamera().getyOffset()-10));
    			}
    		}
    		else if(playerInteraction && (coinsLeft <= 0 && keyDelivered)){
    			deliveredToRickPickle=true;
    		}

    	}else if(playerInteraction){
    		g.setColor(Color.yellow);
    		g.setFont(Fonts.codeFont);
    		g.drawString("Items delivered! Now you can go to the next world",(int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()-30));
    	}
    	else{
    		handler.getWorld().getEntityManager().getPlayer().setHealthBarVisibility(true);
    	}
    }

    @Override
    public void die() {
    	
    }
}
