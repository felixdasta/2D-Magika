package Game.Entities.Creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Game.Entities.EntityBase;
import Game.Entities.Statics.Cactus;
import Game.Entities.Statics.Cone;
import Game.GameStates.State;
import Game.Inventories.Inventory;
import Game.Items.Item;
import Game.SpellCast.SpellCastUI;
import Main.Handler;
import Resources.Animation;
import Resources.Images;
import Worlds.CaveWorld;
import Worlds.MazeWorld;
import Worlds.World1;

/**
 * Created by Elemental on 1/1/2017.
 */

public class Player extends CreatureBase {

    //Animations
    private Animation animDown, animUp, animLeft, animRight,animFireATT,animFireATTR,animFireATTU,animFireATTD;

    // Attack


    //Inventory
    private Inventory inventory;

    private SpellCastUI spellGUI;

    private int fcounter = 0;
    private Boolean fcactive=true;
    private Boolean FireBall=false;
    private Boolean LaunchedFireBall=false;
    private Boolean LaunchedFireBallL=false;
    private Boolean LaunchedFireBallR=false;
    private Boolean LaunchedFireBallU=false;
    private Boolean LaunchedFireBallD=false;
    private Boolean attacking=false;
    private Boolean healthBarVisibility = true;
    private Boolean summonAbility = false;

    private int animWalkingSpeed = 150;
    private int animFireSpeed = 250;
    private int FireSpeed = 2;
    private int FireMove = 0;
    private int movexp,moveyp,movexn,moveyn,tempmoveyp,tempmovexn,tempmoveyn,tempmovexp,fy,fx;
    private int internalGameTimer;

    //spells



    public Player(Handler handler, float x, float y) {
        super(handler, x, y, CreatureBase.DEFAULT_CREATURE_WIDTH, CreatureBase.DEFAULT_CREATURE_HEIGHT);

        bounds.x=8*2;
        bounds.y=18*2;
        bounds.width=16*2;
        bounds.height=14*2;
        health=75;
        attack=3;
        internalGameTimer = 0;

        animDown = new Animation(animWalkingSpeed,Images.player_front);
        animLeft = new Animation(animWalkingSpeed,Images.player_left);
        animRight = new Animation(animWalkingSpeed,Images.player_right);
        animUp = new Animation(animWalkingSpeed,Images.player_back);
        animFireATT = new Animation(animFireSpeed,Images.FireBallLeft);
        animFireATTR = new Animation(animFireSpeed,Images.FireBallRight);
        animFireATTU = new Animation(animFireSpeed,Images.FireBallUp);
        animFireATTD = new Animation(animFireSpeed,Images.FireBallDown);

        inventory= new Inventory(handler);
        spellGUI= new SpellCastUI(handler);
    }

    @Override
    public void tick() {
        //Animations
        animDown.tick();
        animUp.tick();
        animRight.tick();
        animLeft.tick();
        animFireATT.tick();
        animFireATTR.tick();
        animFireATTU.tick();
        animFireATTD.tick();

        //Movement
        getInput();
        move();
        handler.getGameCamera().centerOnEntity(this);

        if(!fcactive){
            fcounter++;
        }
        if(fcounter>=60){
            fcounter=0;
            fcactive=true;
            FireBall=true;
        }

        if(FireBall){
            FireMove++;
        }


        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)){
            readyFireAttack();
        }

        // Attack
        if(handler.getKeyManager().attbut) {
        	
            checkAttacks();
            
        }else if(handler.getKeyManager().fattbut){

            fireAttack();

        }

        //Verifies Energy Drink Consumption (30 seconds of effect)
        if(Inventory.isEnergyDrinkConsumed()){
        	internalGameTimer++;
        	if(internalGameTimer/60 > 30){
        		this.setSpeed(getSpeed()-2);
        		internalGameTimer = 0;
        		Inventory.setEnergyDrinkConsumed(false);
        	}
        }
        
        // Adds health
        addHealth();

        //Inventory
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_X)){
			for(int i=0; i<Item.items.length;i++){
				if (Item.items[i]!=null && Item.items[i].getCount() < 64){ 
					this.inventory.addItem(Item.items[i]);} 
			} 
        }
        inventory.tick();

        //spellgui
        spellGUI.tick();
    }

    @Override
    public void render(Graphics g) {
        if(this.getHealth() > 15){
            g.drawImage(getCurrentAnimationFrame(animDown,animUp,animLeft,animRight,Images.player_front,Images.player_back,Images.player_left,Images.player_right), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            animDown.setAnimation(Images.player_front);
            animLeft.setAnimation(Images.player_left);
            animRight.setAnimation(Images.player_right);
            animUp.setAnimation(Images.player_back);
        }else{
            g.drawImage(getCurrentAnimationFrame(animDown,animUp,animLeft,animRight,Images.playerTired_front,Images.playerTired_back,Images.playerTired_left,Images.playerTired_right), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            animDown.setAnimation(Images.playerTired_front);
            animLeft.setAnimation(Images.playerTired_left);
            animRight.setAnimation(Images.playerTired_right);
            animUp.setAnimation(Images.playerTired_back);
        }
    	
    	if(FireBall){
            FireBallAttack(g);
        }
        
        if(healthBarVisibility){
            g.setColor(Color.BLACK);
            g.drawRect((int)(x-handler.getGameCamera().getxOffset()-1),(int)(y-handler.getGameCamera().getyOffset()-21),76,11);
            if(this.getHealth()>50){
                g.setColor(Color.GREEN);
                g.fillRect((int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-20),getHealth(),10);
            }else if(this.getHealth()>=15 && getHealth()<=50){
                g.setColor(Color.YELLOW);
                g.fillRect((int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-20),getHealth(),10);

            }else if(this.getHealth() < 15){
                g.setColor(Color.RED);
                g.fillRect((int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-20),getHealth(),10);

            }
            g.setColor(Color.white);
            g.drawString("Health: " + getHealth(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-10));
        }
       }

    public void readyFireAttack(){
        LaunchedFireBall=true;
        movexp =(int) (x - handler.getGameCamera().getxOffset()) + 48;
        moveyp =(int) (y - handler.getGameCamera().getyOffset()) + 64;
        movexn =(int) (x - handler.getGameCamera().getxOffset()) - 48;
        moveyn =(int) (y - handler.getGameCamera().getyOffset()) - 64;
        tempmovexp =(int) (x - handler.getGameCamera().getxOffset()) + 48;
        tempmoveyp =(int) (y - handler.getGameCamera().getyOffset()) + 64;
        tempmovexn =(int) (x - handler.getGameCamera().getxOffset()) - 48;
        tempmoveyn =(int) (y - handler.getGameCamera().getyOffset()) - 64;
        LaunchedFireBallL=false;
        LaunchedFireBallR=false;
        LaunchedFireBallU=false;
        LaunchedFireBallD=false;
        fy=(int) (y - handler.getGameCamera().getyOffset()) + (height / 2);
        fx=(int) (x - handler.getGameCamera().getxOffset()) + 16;
    }
    public void fireAttack() {

        for (Item i : getInventory().getInventoryItems()) {
            if (i.getName() == "Fire Rune"&&fcactive) {
                attacking=true;
                System.out.println("Burn");
                i.setCount(i.getCount() - 1);
                fcactive=false;
                return;

            }
        }
    }

    
    public void addHealth() { 
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT) && (health < 75)){
    		if(health>70){
    			health=75;
    		}else{
    			health += 5;
    		}
    	}
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void checkAttacks(){
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if(attackTimer < attackCooldown)
            return;

        Rectangle cb = getCollisionBounds(0, 0);
        Rectangle ar = new Rectangle();
        int arSize = 20;
        ar.width = arSize;
        ar.height = arSize;

        if(lu && handler.getKeyManager().attbut){
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y - arSize;
        }else if(ld && handler.getKeyManager().attbut){
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y + cb.height;
        }else if(ll && handler.getKeyManager().attbut){
            ar.x = cb.x - arSize;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        }else if(lr && handler.getKeyManager().attbut){
            ar.x = cb.x + cb.width;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        }else{
            return;
        }

        attackTimer = 0;

        for(EntityBase e : handler.getWorld().getEntityManager().getEntities()){
            if(e.equals(this))
                continue;
            if(e.equals(World1.getRickPickle())){
            	continue;
            }if(e.equals(MazeWorld.getRick())){
            	continue;
            }if(e.equals(CaveWorld.getHorribleMorty())  && CaveWorld.getHorribleMorty().isSummoned()){
            	continue;
            }if(e.equals(CaveWorld.getZombieJerry()) && CaveWorld.getZombieJerry().isSummoned()){
            	continue;
            }if(e instanceof Cone || e instanceof Cactus){
            	continue;
            }
            if(e.getCollisionBounds(0, 0).intersects(ar)){
                e.hurt(attack);
                System.out.println(e + " has " + e.getHealth() + " lives.");
                return;
            }
        }
    }

    @Override
    public void die(){
        System.out.println("You lose");
        State.setState(handler.getGame().menuState);
    }

    private void getInput(){
        xMove = 0;
        yMove = 0;

        if(handler.getKeyManager().up &&! attacking)
            yMove = -speed;
        if(handler.getKeyManager().down&&! attacking)
            yMove = speed;				
        if(handler.getKeyManager().left&&! attacking)
            xMove = -speed;
        if(handler.getKeyManager().right&&! attacking)
            xMove = speed;
    }


    private void FireBallAttack(Graphics g) {


        if (lr&&LaunchedFireBall&&!LaunchedFireBallL&&!LaunchedFireBallR&&!LaunchedFireBallD&&!LaunchedFireBallU) {
            LaunchedFireBall=false;
            LaunchedFireBallL=false;
            LaunchedFireBallR=true;
            LaunchedFireBallU=false;
            LaunchedFireBallD=false;

        } else if (ld&&LaunchedFireBall&&!LaunchedFireBallL&&!LaunchedFireBallR&&!LaunchedFireBallD&&!LaunchedFireBallU) {
            LaunchedFireBall=false;
            LaunchedFireBallL=false;
            LaunchedFireBallR=false;
            LaunchedFireBallU=false;
            LaunchedFireBallD=true;

        } else if (lu&&LaunchedFireBall&&!LaunchedFireBallL&&!LaunchedFireBallR&&!LaunchedFireBallD&&!LaunchedFireBallU) {
            LaunchedFireBall=false;
            LaunchedFireBallL=false;
            LaunchedFireBallR=false;
            LaunchedFireBallU=true;
            LaunchedFireBallD=false;

        } else if(ll&&LaunchedFireBall&&!LaunchedFireBallL&&!LaunchedFireBallR&&!LaunchedFireBallD&&!LaunchedFireBallU) {
            LaunchedFireBall=false;
            LaunchedFireBallL=true;
            LaunchedFireBallR=false;
            LaunchedFireBallU=false;
            LaunchedFireBallD=false;

        }
        if (LaunchedFireBallR) {
            movexp+=FireSpeed;
            g.drawImage(getCurrentFireAnimationFrame(), movexp, fy, 64, 32, null);
            if(movexp >= tempmovexp + 64*2){
                FireBall=false;
                attacking=false;
            }
        } else if (LaunchedFireBallD) {
            moveyp+=FireSpeed;
            g.drawImage(getCurrentFireAnimationFrame(), fx-6, moveyp, 32, 64, null);
            if(moveyp >= tempmoveyp + 64*2){
                FireBall=false;
                attacking=false;
            }
        } else if (LaunchedFireBallU) {
            moveyn-=FireSpeed;
            g.drawImage(getCurrentFireAnimationFrame(), fx, moveyn, 32, 64, null);
            if(moveyn <= tempmoveyn - 64*2){
                FireBall=false;
                attacking=false;
            }
        } else if(LaunchedFireBallL) {   
            movexn-=FireSpeed;
            g.drawImage(getCurrentFireAnimationFrame(), movexn, fy, 64, 32, null);
            if(movexn <= tempmovexn - 64*2){
                FireBall=false;
                attacking=false;
            }
        }





    }

    private BufferedImage getCurrentFireAnimationFrame(){

        if(LaunchedFireBallR){

            return animFireATTR.getCurrentFrame();

        }else if(LaunchedFireBallD){

            return animFireATTD.getCurrentFrame();

        }else if(LaunchedFireBallU){

            return animFireATTU.getCurrentFrame();

        }else{

            return animFireATT.getCurrentFrame();
        }


    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    
    public void setHealthBarVisibility(Boolean visibility){
    	this.healthBarVisibility = visibility;
    }

	public Boolean getSummonAbility() {
		return summonAbility;
	}

	public void setSummonAbility(Boolean summonAbility) {
		this.summonAbility = summonAbility;
	}

	public SpellCastUI getSpellGUI() {
        return spellGUI;
    }
}