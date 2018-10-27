package Worlds;
import Game.Entities.Creatures.HorribleMorty;
import Game.Entities.Creatures.PinkSkelyEnemy;
import Game.Entities.Creatures.Player;
import Game.Entities.Creatures.Rick;
import Game.Entities.Creatures.SkelyEnemy;
import Game.Entities.Creatures.ZombieJerry;
import Game.Entities.Statics.Brick;
import Game.Entities.Statics.Cactus;
import Game.Entities.Statics.Cone;
import Game.Entities.Statics.Door;
import Game.Items.Item;
import Main.Handler;

/**
 * Created by Elemental on 2/10/2017.
 */
public class MazeWorld extends BaseWorld{
    private static Rick rick;
    private static SkelyEnemy skely1;
    private static SkelyEnemy skely2;

    public MazeWorld(Handler handler, String path, Player player) {
        super(handler,path,player);
        skely1 = new SkelyEnemy(handler, 1178, 274);
        skely2 = new SkelyEnemy(handler, 1409, 1276);
        rick = new Rick(handler, 450, 450);
        skely1.setDropKey(false);
        skely2.setDropKey(false);
       
        getItemManager().addItem(Item.heart.createNew(450, 1000, 1));
        entityManager.addEntity(skely1);
        entityManager.addEntity(skely2);
        entityManager.addEntity(new Door(handler, 100, 0, null));
        entityManager.addEntity(new PinkSkelyEnemy(handler,814,814));
        entityManager.addEntity(new Cone(handler, 195, 1528-66));
        entityManager.addEntity(new Cone(handler, 267, 1528));
        entityManager.addEntity(new Cone(handler, 318, 1528-66));
        entityManager.addEntity(new Cone(handler, 369, 1528));
        entityManager.addEntity(new Cone(handler, 447, 1528-66));
        entityManager.addEntity(new Cone(handler, 130, 1528-66));
        entityManager.addEntity(new Cone(handler, 513, 1528-66));
        entityManager.addEntity(new Cactus(handler, 884, 1389));
        entityManager.addEntity(new Cactus(handler, 1160, 1305));
        entityManager.addEntity(new Cactus(handler, 637, 310));
        entityManager.addEntity(new Brick(handler, 948, 273));   
        entityManager.addEntity(new Brick(handler, 859, 731));   
        getItemManager().addItem(Item.monsterEnergy.createNew(333, 575, 1));
        getItemManager().addItem(Item.pizzaBox.createNew(900, 826, 1));
        getItemManager().addItem(Item.heart.createNew(801, 801, 1));
        entityManager.addEntity(rick);
    }
    
    public static Rick getRick(){
    	return rick;
    }
    
    public static SkelyEnemy getSkely1(){
    	return skely1;
    }
    
    public static SkelyEnemy getSkely2(){
    	return skely2;
    }
}
