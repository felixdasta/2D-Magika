package Game.GameStates;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Elemental on 2/3/2017.
 */
public class OptionsState extends State {

    private int count = 0;
    private UIManager uiManager;

    public OptionsState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton(56*2, 160, 256, 48, Images.MuteMusic, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getGame().stopMainAudio();
            handler.getGame().setBackgroundMusicMute(true);
        }));

        uiManager.addObjects(new UIImageButton(56*2, 160+(64+16), 256, 48, Images.UnmuteMusic, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getGame().playMainAudio();
            handler.getGame().setBackgroundMusicMute(false);
        }));

        uiManager.addObjects(new UIImageButton(56*2, 160+(64*3+16), 256, 96, Images.MuteSoundEffects, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getGame().setSoundEffectMute(true);
        }));
        
        uiManager.addObjects(new UIImageButton(56*2, 160+(64*5), 270, 96, Images.UnmuteSoundEffects, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getGame().setSoundEffectMute(false);
        }));
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;

            State.setState(handler.getGame().gameState);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.OptionsMenu,0,0,800,600,null);
        uiManager.Render(g);

    }
}
