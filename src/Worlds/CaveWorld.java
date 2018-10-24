package Worlds;
import Game.Entities.Creatures.HorribleMorty;
import Game.Entities.Creatures.Player;
import Game.Entities.Creatures.SkelyEnemy;
import Game.Entities.Creatures.ZombieJerry;
import Game.Items.Item;
import Main.Handler;

/**
 * Created by Elemental on 2/10/2017.
 */
public class CaveWorld extends BaseWorld{
    private Handler handler;
    private Player player;
    private static HorribleMorty horribleMorty;
    private static ZombieJerry zombieJerry;

    public CaveWorld(Handler handler, String path, Player player) {
        super(handler,path,player);
        this.handler = handler;
        this.player=player;
        horribleMorty = new HorribleMorty(handler, 1015, 1254);
        zombieJerry = new ZombieJerry(handler, 900, 857);
        
        entityManager.addEntity(horribleMorty);
        entityManager.addEntity(zombieJerry);
        entityManager.addEntity(new SkelyEnemy(handler, 1000, 1000));
        getItemManager().addItem(Item.monsterEnergy.createNew(500, 700, 1));
    }

	public static HorribleMorty getHorribleMorty() {
		return horribleMorty;
	}

	public static ZombieJerry getZombieJerry() {
		return zombieJerry;
	}


}