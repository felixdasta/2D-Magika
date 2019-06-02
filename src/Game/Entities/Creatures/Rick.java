package Game.Entities.Creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import Game.GameStates.State;
import Game.Inventories.Inventory;
import Game.Items.Item;
import Main.Handler;
import Resources.Animation;
import Resources.Fonts;
import Resources.Images;
import Worlds.MazeWorld;

/**
 * Created by Elemental on 2/7/2017.
 */
public class Rick extends CreatureBase  {


    private Animation animDown, animUp, animLeft, animRight;

    private int animWalkingSpeed = 150;
    private Inventory rickInventory;
    private Rectangle rickCam;
    private Rectangle ar = new Rectangle();

    private int healthcounter =0;

    private Random randint;
    private boolean playerInteraction;
    private static boolean deliveredToRick;
	private boolean trophyDelivered;
	private int coinsLeft = 3;
    private int moveCount=0;
    private int direction;

    public Rick(Handler handler, float x, float y) {
        super(handler, x, y, CreatureBase.DEFAULT_CREATURE_WIDTH, CreatureBase.DEFAULT_CREATURE_HEIGHT);
        bounds.x=8*2;
        bounds.y=18*2;
        bounds.width=16*2;
        bounds.height=14*2;
        speed=1.0f;
        health=50;
        trophyDelivered = false;
        deliveredToRick = false;

        rickCam= new Rectangle();

        randint = new Random();
        direction = randint.nextInt(4) + 1;

        animDown = new Animation(animWalkingSpeed, Images.Rick_front);
        animLeft = new Animation(animWalkingSpeed,Images.Rick_left);
        animRight = new Animation(animWalkingSpeed,Images.Rick_right);
        animUp = new Animation(animWalkingSpeed,Images.Rick_back);

        rickInventory= new Inventory(handler);
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
        rickInventory.tick();


    }


    private void checkIfMove() {
        xMove = 0;
        yMove = 0;

        rickCam.x = (int) (x - handler.getGameCamera().getxOffset() - (64 * 3));
        rickCam.y = (int) (y - handler.getGameCamera().getyOffset() - (64 * 3));
        rickCam.width = 64 * 7;
        rickCam.height = 64 * 7;

        if (rickCam.contains(handler.getWorld().getEntityManager().getPlayer().getX() - handler.getGameCamera().getxOffset(), handler.getWorld().getEntityManager().getPlayer().getY() - handler.getGameCamera().getyOffset())
                || rickCam.contains(handler.getWorld().getEntityManager().getPlayer().getX() - handler.getGameCamera().getxOffset() + handler.getWorld().getEntityManager().getPlayer().getWidth(), handler.getWorld().getEntityManager().getPlayer().getY() - handler.getGameCamera().getyOffset() + handler.getWorld().getEntityManager().getPlayer().getHeight())) {

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
    
    public static Boolean itemsDeliveredToRick(){
    	return deliveredToRick;
    }

    @Override
    public void render(Graphics g) {
    	g.drawImage(getCurrentAnimationFrame(animDown,animUp,animLeft,animRight,Images.Rick_front,Images.Rick_back,Images.Rick_left,Images.Rick_right), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    	if (!deliveredToRick){
    		if(playerInteraction && (coinsLeft > 0 || !trophyDelivered)){
    			
    			handler.getWorld().getEntityManager().getPlayer().setHealthBarVisibility(false);
    			g.setColor(Color.MAGENTA);
    			g.drawString("We need the following items to go to our universe: ",(int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()-30));
    			
    			if(!trophyDelivered && Inventory.getTrophyCount() >= 1){
    				if(Inventory.getTrophyCount()==1){
    					handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems().remove(Item.trophy);
        				Inventory.setTrophyCount(0);
    				}else{
        				Inventory.setTrophyCount(Inventory.getTrophyCount() - 1);
        				Item.trophy.setCount(Inventory.getTrophyCount());				
    				}
    				trophyDelivered = true;
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
    			
    			if(!trophyDelivered){
    				g.drawImage(Images.trophy, (int) (x-handler.getGameCamera().getxOffset()) + 100,(int) (y-handler.getGameCamera().getyOffset()-30), 18, 30, null);
    				g.drawString("1 trophy",(int) (x-handler.getGameCamera().getxOffset()) + 100 + 23,(int) (y-handler.getGameCamera().getyOffset()-10));
    			}
    		}
    		else if(playerInteraction && (coinsLeft <= 0 && trophyDelivered)){
    			deliveredToRick=true;
    		}    	
    		else{
        		handler.getWorld().getEntityManager().getPlayer().setHealthBarVisibility(true);
        	}

    	}else if(playerInteraction && !(MazeWorld.getSkely1().isDead() && MazeWorld.getSkely2().isDead())){
    		g.setColor(Color.MAGENTA);
    		g.drawString("Morty! Make sure you kill",(int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()-45));
    		g.drawString("those monsters before we arrive to our universe...",(int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()-30));
    	}else if(playerInteraction){
    		g.setColor(Color.MAGENTA);
    		g.drawString("Hooray Morty!",(int) (x-handler.getGameCamera().getxOffset()),(int) (y-handler.getGameCamera().getyOffset()-30));
    		State.setState(handler.getGame().gameBeatenState);
    	}
    	else{
    		handler.getWorld().getEntityManager().getPlayer().setHealthBarVisibility(true);
    	}
    }

    @Override
    public void die() {
    	
    }
}
