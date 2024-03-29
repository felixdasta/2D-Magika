package Resources;

import java.awt.image.BufferedImage;

/**
 * Created by Elemental on 12/19/2016.
 */
public class Animation {
    private int speed,index;
    private long lastTime,timer;
    private BufferedImage[] frames;

    public Animation(int speed,BufferedImage[] frames){
        this.speed=speed;
        this.frames=frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();

    }

    public void tick(){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if(timer > speed){
            index++;
            timer = 0;
            if(index == frames.length){
                index = 0;
            }
        }

    }
    public void setAnimation(BufferedImage[] frames){
    	this.frames = frames;
    }

    public BufferedImage getCurrentFrame(){
        return frames[index];
    }


}
