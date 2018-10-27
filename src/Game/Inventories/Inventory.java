package Game.Inventories;

import Game.Items.Item;
import Game.SpellCast.FireBallSpell;
import Resources.Images;
import UI.UIInventory;
import UI.UIManager;
import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Elemental on 1/3/2017.
 */
public class Inventory {

    private Handler handler;
    private boolean active = false;
    private boolean toAdd = true;
    private static boolean energyDrinkConsumed = false;
    private static int coinCount;
    private static int keyCount;
    private static int trophyCount;
    private UIManager uiManager;
    private ArrayList<Item> inventoryItems;

    public Inventory(Handler handler){

        this.handler=handler;
        inventoryItems = new ArrayList<>();

        uiManager = new UIManager(handler);

        uiManager.addObjects(new UIInventory(0,0, 329, 265, Images.inventory,() -> {
        }));
    }

    public void tick() {

        for(Item i : inventoryItems){
            if(i.getCount()==0){
                inventoryItems.remove(inventoryItems.indexOf(i));
                return;
            }
        }

        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_Q)){
            active=!active;
            handler.getWorld().getEntityManager().getPlayer().getSpellGUI().setActive(false);

        }

        if(!active){
            return;
        }

        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();



    }

    public void render(Graphics g) {
        if(!active){
            uiManager.isActive(uiManager.getObjects(),false);
            return;
        }

        uiManager.isActive(uiManager.getObjects(),true);
        uiManager.Render(g);
        g.setColor(Color.white);
        renderItems(g);


    }

    //Inventory Methods
    private void renderItems(Graphics g) {
    	int inventoryItemXpos = 25;
    	int inventoryItemYpos = 24;
    	for(int i = 0; i < inventoryItems.size(); i++){
    		g.drawImage(inventoryItems.get(i).getTexture(), inventoryItemXpos, inventoryItemYpos, inventoryItems.get(i).getWidth(), inventoryItems.get(i).getHeight(), null);
    		g.drawString(String.valueOf(inventoryItems.get(i).getCount()), inventoryItemXpos+33, inventoryItemYpos);
    		if(i==4 || i == 9){
    			inventoryItemYpos +=60;
        		inventoryItemXpos = 25;
    		}else{
        		inventoryItemXpos+=61;  			
    		}		
    	}
    }

    public void addItem(Item item){
    	for(Item i : inventoryItems){
    		if(i.getId() == item.getId()){
    			
    			itemVerifier(item);	

    			i.setCount(i.getCount() + 1);
    			return;
    		}
    	}
    	if(item.getId()==2){
    		handler.getWorld().getEntityManager().getPlayer().getSpellGUI().addSpell(new FireBallSpell(handler));
    	}
    	
    	itemVerifier(item);
    	
    	/*toAdd verifies that if the item was consumed, it will not be added to the inventory. 
    	 Otherwise, it will be added. (Applies to consumable items such as heart potion
    	 and energy drink)*/
    	if(toAdd){
    		inventoryItems.add(item);
    	}
    	toAdd=true;
    }
    
    private void itemVerifier(Item item){
    	if(item.getId()==3 && !handler.getKeyManager().additem){
			handler.getGame().playAudio("res/music/Powerup.wav", false);
    		if(handler.getWorld().getEntityManager().getPlayer().getHealth() == 75){
    			toAdd = true;
    		}
    		else if(handler.getWorld().getEntityManager().getPlayer().getHealth() > 45){
    			handler.getWorld().getEntityManager().getPlayer().setHealth(75);
    			toAdd = false;
    		}else{
    			handler.getWorld().getEntityManager().getPlayer().setHealth(handler.getWorld().getEntityManager().getPlayer().getHealth() + 30);
    			toAdd = false;
    		}
    	}
		if(item.getId()==4){
			if(!handler.getKeyManager().additem){
				handler.getGame().playAudio("res/music/Coin.wav", false);		
			}
			coinCount++;
		}
		if(item.getId()==5){
			keyCount++;
		}
		if(item.getId()==6){
			handler.getWorld().getEntityManager().getPlayer().setSummonAbility(true);
		}
		
		/*if the player just got the pizza and his attack level is less or equal than 14, 
		 his new attack level will be his previous attack level plus three, 
		 but keep in mind that if the "X" button was pressed or if his attack level
		 is greater than 14, it will simply be added to the inventory
		 without increasing the attack level*/
		if(item.getId()==7 && !handler.getKeyManager().additem){
			if(handler.getWorld().getEntityManager().getPlayer().getAttack()<=15){
				handler.getWorld().getEntityManager().getPlayer().setAttack(handler.getWorld().getEntityManager().getPlayer().getAttack()+3);
				handler.getGame().playAudio("res/music/Powerup.wav", false);
				toAdd=false;
			}else{
				toAdd=true;
			}
		}
		
		//monster energy drink, this increases the player's speed
		if(item.getId()==8 && !handler.getKeyManager().additem){
			if(handler.getWorld().getEntityManager().getPlayer().getSpeed()<5){
				energyDrinkConsumed=true;
				handler.getWorld().getEntityManager().getPlayer().setSpeed(handler.getWorld().getEntityManager().getPlayer().getSpeed()+2);
				handler.getGame().playAudio("res/music/Powerup.wav", false);
				toAdd=false;
			}else{
				toAdd=true;
			}
		}
		
		if(item.getId()==9){
			trophyCount++;
		}
    }

    //GET SET
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
    
    public static void setCointCount(int assignedCoinCount){
    	coinCount = assignedCoinCount;
    }
    
    public static void setEnergyDrinkConsumed(boolean energyDrinkConsumed) {
		Inventory.energyDrinkConsumed = energyDrinkConsumed;
	}

	public static void setKeyCount(int assignedKeyCount){
    	keyCount = assignedKeyCount;
    }
	
	public static void setTrophyCount(int assignedTrophyCount){
    	trophyCount = assignedTrophyCount;
    }
    
    public static int getCoinCount(){
    	return coinCount;
    }
    
    public static int getKeyCount(){
    	return keyCount;
    }
    
    public static int getTrophyCount(){
    	return trophyCount;
    }
    
    public static boolean isEnergyDrinkConsumed() {
		return energyDrinkConsumed;
	}

    public ArrayList<Item> getInventoryItems(){
        return inventoryItems;
    }

	public void setActive(boolean active) {
        this.active = active;
    }
}