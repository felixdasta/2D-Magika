package Worlds;
import java.awt.Graphics;
import Game.Entities.Creatures.ZombieJerry;
import Game.Entities.Creatures.RickPickle;
import Game.Entities.Creatures.Player;

import Game.Entities.Statics.Door;
import Game.Items.Item;
import Main.Handler;
import Resources.Images;

/**
 * Created by Elemental on 2/10/2017.
 */
public class MazeWorld extends BaseWorld{
    private Handler handler;
    private Player player;
    private static ZombieJerry zombieJerry;
    private static RickPickle rickPickle;


    public MazeWorld(Handler handler, String path, Player player) {
        super(handler,path,player);
        this.handler = handler;
        this.player=player;
        
        zombieJerry = new ZombieJerry(handler, 900, 857);
        rickPickle = new RickPickle(handler, 450, 450);
        
        getItemManager().addItem(Item.heart.createNew(450, 1000, 1));
        getItemManager().addItem(Item.rockItem.createNew(250, 600, 1));
        entityManager.addEntity(new Door(handler, 100, 0, null));
        getItemManager().addItem(Item.monsterEnergy.createNew(333, 575, 1));
        getItemManager().addItem(Item.pizzaBox.createNew(900, 876, 1));
        getItemManager().addItem(Item.heart.createNew(801, 801, 1));
        entityManager.addEntity(zombieJerry);
        entityManager.addEntity(rickPickle);
    }
}
