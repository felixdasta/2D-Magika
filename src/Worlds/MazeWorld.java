package Worlds;
import java.awt.Graphics;

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
    

    public MazeWorld(Handler handler, String path, Player player) {
        super(handler,path,player);
        this.handler = handler;
        this.player=player;
        
        entityManager.addEntity(new Door(handler, 100, 0, null));
        getItemManager().addItem(Item.monsterEnergy.createNew(333, 575, 1));
    }
}
