package Worlds;
import Game.Entities.Creatures.HorribleMorty;
import Game.Entities.Creatures.Player;
import Game.Entities.Creatures.SkelyEnemy;
import Game.Entities.Creatures.ZombieJerry;
import Game.Entities.Statics.Door;
import Game.Items.Item;
import Main.Handler;

/**
 * Created by Elemental on 2/10/2017.
 */
public class CaveWorld extends BaseWorld{
    private BaseWorld mazeWorld;
    private static HorribleMorty horribleMorty;
    private static ZombieJerry zombieJerry;
    private static SkelyEnemy skelyenemy1;
    private static SkelyEnemy skelyenemy2;

    public CaveWorld(Handler handler, String path, Player player) {
        super(handler,path,player);
        mazeWorld = new MazeWorld(handler, "res/Maps/map3.map", player);
        
        horribleMorty = new HorribleMorty(handler, 1015, 1254);
        zombieJerry = new ZombieJerry(handler, 900, 857);
        skelyenemy1 = new SkelyEnemy(handler, 1000, 1000);
        skelyenemy2 = new SkelyEnemy(handler, 800, 75);
        
        skelyenemy2.setDropKey(false);
        
        entityManager.addEntity(horribleMorty);
        entityManager.addEntity(zombieJerry);
        entityManager.addEntity(skelyenemy1);
        entityManager.addEntity(skelyenemy2);
        entityManager.addEntity(new Door(handler, 100, 0,mazeWorld));
        getItemManager().addItem(Item.pizzaBox.createNew(590, 915, 1));
        getItemManager().addItem(Item.heart.createNew(457, 657, 1));
        getItemManager().addItem(Item.heart.createNew(912, 1040, 1));
    }

	public static HorribleMorty getHorribleMorty() {
		return horribleMorty;
	}

	public static ZombieJerry getZombieJerry() {
		return zombieJerry;
	}

}