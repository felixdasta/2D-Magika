package Game.GameStates;

import java.awt.Graphics;

import Main.Handler;
import Resources.Images;
import UI.UIManager;

/**
 * Created by Elemental on 2/3/2017.
 */
public class GameBeatenState extends State {

    private UIManager uiManager;
    private int timer = 0;

    public GameBeatenState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        if(timer/60 < 30){
        	timer++;
        }else{
        	System.exit(0);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.gameBeaten,0,0,800,600,null);
        uiManager.Render(g);
//    	while(timer < 30) {
//    		timer++;
//    		try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    	}
//    	System.exit(0);

    }
}
