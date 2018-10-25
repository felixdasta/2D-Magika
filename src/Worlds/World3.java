package Worlds;
import Game.Entities.Creatures.Player;
import Game.Items.Item;
import Main.Handler;

/**
 * Created by Elemental on 2/10/2017.
 */
public class World3 extends BaseWorld{
    private Handler handler;
    private Player player;


    public World3(Handler handler, String path, Player player) {
        super(handler,path,player);
        this.handler = handler;
        this.player=player;

        
        getItemManager().addItem(Item.monsterEnergy.createNew(500, 700, 1));
        getItemManager().addItem(Item.heart.createNew(457, 657, 1));
    }


}
