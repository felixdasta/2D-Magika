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
    			addHealth(item);
    			if(item.getId()==4 && !handler.getKeyManager().additem){
    				handler.getGame().playAudio("res/music/Coin.wav", false);
    			}			

    			i.setCount(i.getCount() + 1);
    			return;
    		}
    	}
    	if(item.getId()==2){
    		handler.getWorld().getEntityManager().getPlayer().getSpellGUI().addSpell(new FireBallSpell(handler));
    	}
    	
    	addHealth(item);
    	
		if(item.getId()==4 && !handler.getKeyManager().additem){
			handler.getGame().playAudio("res/music/Coin.wav", false);
		}		

    	//toAdd verifies that if the item was consumed, it will not be added to the inventory. Otherwise, it will be added.
    	if(toAdd){
    		inventoryItems.add(item);
    	}
    	toAdd=true;
    }
    
    private void addHealth(Item item){
    	if(item.getId()==3 && !handler.getKeyManager().additem){
			handler.getGame().playAudio("res/music/Powerup.wav", false);
    		if(handler.getWorld().getEntityManager().getPlayer().getHealth() == 75){
    			toAdd = true;
    		}
    		else if(handler.getWorld().getEntityManager().getPlayer().getHealth() > 60){
    			handler.getWorld().getEntityManager().getPlayer().setHealth(75);
    			toAdd = false;
    		}else{
    			handler.getWorld().getEntityManager().getPlayer().setHealth(handler.getWorld().getEntityManager().getPlayer().getHealth() + 15);
    			toAdd = false;
    		}
    	}
    }

    //GET SET
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<Item> getInventoryItems(){
        return inventoryItems;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
