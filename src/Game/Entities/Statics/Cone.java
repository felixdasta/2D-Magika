package Game.Entities.Statics;

import java.awt.Graphics;

import Game.Tiles.Tile;
import Main.Handler;
import Resources.Images;

public class Cone extends StaticEntity{

    public Cone(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);

        bounds.x=0;
        bounds.y=0;
        bounds.width = 58;
        bounds.height = 66;
        health=16;
    }

    @Override
    public void tick() {
    	
    }

    @Override
    public void render(Graphics g) {
        renderLife(g);
        g.drawImage(Images.blocks[27],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);

    }

    @Override
    public void die() {

    }
}
