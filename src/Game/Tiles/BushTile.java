package Game.Tiles;

import Resources.Images;

public class BushTile extends Tile{
	public BushTile(int id) {
        super(Images.blocks[17], id);

    }

    @Override
    public boolean isSolid(){
        return true;
    }
}
