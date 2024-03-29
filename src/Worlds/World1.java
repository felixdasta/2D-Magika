package Worlds;

import Game.Entities.Creatures.Player;
import Game.Entities.Creatures.RickPickle;
import Game.Entities.Creatures.SkelyEnemy;
import Game.Entities.Statics.Brick;
import Game.Entities.Statics.Door;
import Game.Entities.Statics.Rock;
import Game.Entities.Statics.Tree;
import Game.Inventories.Inventory;
import Game.Items.Item;
import Main.Handler;

/**
 * Created by Elemental on 1/2/2017.
 */
public class World1 extends BaseWorld{

    private Handler handler;
    private BaseWorld caveWorld;
    private static RickPickle rickPickle;

    public World1(Handler handler, String path, Player player){
        super(handler,path,player);
        this.handler = handler;
        caveWorld = new CaveWorld(handler,"res/Maps/caveMap.map",player);
        rickPickle = new RickPickle(handler, 1000, 1000);
        
        
        //this for loop will restart the items in the inventory every time the game restarts
        for(int i = 0; i < Item.items.length; i++){
        	if(Item.items[i] != null){
        		Item.items[i].setCount(1);
        	}
        }
        
        Inventory.setCointCount(0);
        Inventory.setKeyCount(0);

        entityManager.addEntity(new Tree(handler, 100, 250));
        entityManager.addEntity(new Rock(handler, 100, 450));
        entityManager.addEntity(new Tree(handler, 533, 276));
        entityManager.addEntity(new Rock(handler, 684, 1370));
        entityManager.addEntity(new Tree(handler, 765, 888));
        entityManager.addEntity(new Rock(handler, 88, 1345));
        entityManager.addEntity(new Tree(handler, 77, 700));
        entityManager.addEntity(new Rock(handler, 700, 83));
        entityManager.addEntity(new Door(handler, 100, 0,caveWorld));
        entityManager.addEntity(new SkelyEnemy(handler, 1250, 500));
        entityManager.addEntity(new Brick(handler, 536, 1024));
        entityManager.addEntity(new Brick(handler, 1015, 1254));
        entityManager.addEntity(new Brick(handler, 1267, 618));
        entityManager.addEntity(new Brick(handler, 301, 448));
        entityManager.addEntity(new Brick(handler, 382, 1363));
        entityManager.addEntity(rickPickle);
        getItemManager().addItem(Item.heart.createNew(457, 657, 1));
        getItemManager().addItem(Item.heart.createNew(848, 1364, 1));

        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);
    }
    
    public static RickPickle getRickPickle(){
    	return rickPickle;
    }

}