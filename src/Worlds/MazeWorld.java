package Worlds;
import Game.Entities.Creatures.Player;
import Game.Entities.Statics.Door;
import Game.GameStates.GameState;
import Game.Items.Item;
import Main.Handler;

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
    }
}
