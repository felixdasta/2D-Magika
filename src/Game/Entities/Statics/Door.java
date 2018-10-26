package Game.Entities.Statics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import Game.Entities.Creatures.Player;
import Game.Entities.Creatures.RickPickle;
import Game.Inventories.Inventory;
import Main.Handler;
import Resources.Images;
import Worlds.BaseWorld;
import Worlds.CaveWorld;
import Worlds.MazeWorld;

/**
 * Created by Elemental on 2/2/2017.
 */


public class Door extends StaticEntity {

    private Rectangle ir = new Rectangle();
    public Boolean EP = false, mazeWorldSpawn = false;

    private BaseWorld world;

    public Door(Handler handler, float x, float y,BaseWorld world) {
        super(handler, x, y, 64, 100);
        this.world=world;
        health=10000000;
        bounds.x=0;
        bounds.y=0;
        bounds.width = 100;
        bounds.height = 64;

        ir.width = bounds.width;
        ir.height = bounds.height;
        int irx=(int)(bounds.x-handler.getGameCamera().getxOffset()+x);
        int iry= (int)(bounds.y-handler.getGameCamera().getyOffset()+height);
        ir.y=iry;
        ir.x=irx;
    }

    @Override
    public void tick() {

        if(isBeinghurt()){
            setHealth(10000000);
        }

        if(handler.getKeyManager().attbut){
            EP=true;

        }else if(!handler.getKeyManager().attbut){
            EP=false;
        }

    }

    @Override
    public void render(Graphics g) {
        checkForPlayer(g, handler.getWorld().getEntityManager().getPlayer());
    }

    private void checkForPlayer(Graphics g, Player p) {
        Rectangle pr = p.getCollisionBounds(0,0);


        
        //tab key can be used to skip the world
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_TAB) && this.world != null){
            g.drawImage(Images.EP,(int) x+width,(int) y+10,32,32,null);
            g.drawImage(Images.loading,0,0,800,600,null);
            handler.setWorld(world);
        }
        
        if((RickPickle.itemsDeliveredToRickPickle() && this.world instanceof CaveWorld) || (this.world instanceof MazeWorld && Inventory.getKeyCount()>=1)){
            g.drawImage(Images.door,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
            g.setColor(Color.black);
            if(ir.contains(pr) && !EP){
                g.drawImage(Images.E,(int) x+width,(int) y+10,32,32,null);
            }
            else if(ir.contains(pr) && EP){
                g.drawImage(Images.EP,(int) x+width,(int) y+10,32,32,null);
                g.drawImage(Images.loading,0,0,800,600,null);
                handler.setWorld(world);
            }
        }
        if(!(this.world instanceof CaveWorld || this.world instanceof MazeWorld)){
        	if(!mazeWorldSpawn){
        		handler.getWorld().getEntityManager().getPlayer().setX(318);
        		handler.getWorld().getEntityManager().getPlayer().setY(250);
        		mazeWorldSpawn=true;
        	}
        	g.drawImage(Images.tunnel, (int)(x-handler.getGameCamera().getxOffset())+105,(int)(y-handler.getGameCamera().getyOffset())-4, 300, 260, null);
        }
    }

    @Override
    public void die() {

    }
}
