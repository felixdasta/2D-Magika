package Game.Entities.Statics;

import java.awt.Graphics;

import Game.Tiles.Tile;
import Main.Handler;
import Resources.Images;

public class Cactus extends StaticEntity{

    public Cactus(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);

        bounds.x=0;
        bounds.y=0;
        bounds.width = 85;
        bounds.height = 121;
        health=16;
    }

    @Override
    public void tick() {
    	
    }

    @Override
    public void render(Graphics g) {
        renderLife(g);
        g.drawImage(Images.blocks[21],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),85,121,null);

    }

    @Override
    public void die() {

    }
}
