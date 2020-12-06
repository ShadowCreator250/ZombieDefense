import greenfoot.Color;
import greenfoot.Greenfoot;

public class TowerCell extends Cell {

	public TowerCell(int gridX, int gridY) {
		super(gridX, gridY);
		// TODO: Monochrome img is temp
		setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(64, 64, 64)));
	}

	@Override
	public void act() {
		checkAddTowerClick();
	}

	private void checkAddTowerClick() {
		if(Greenfoot.mouseClicked(this) && Greenfoot.getMouseInfo().getButton() == 1) {
			if(!(getWorld().getObjectsAt(getX(), getY(), Tower.class).size() > 0)) {
				GameState.MouseState mouseState = getWorld().getGameState().getMouseState();
				if(mouseState == GameState.MouseState.PLACE_ARCHER_TOWER && getWorld().getGameState().haveEnoughCoins(ArcherTower.PRICE)) {
					getWorld().addObject(new ArcherTower(), getX(), getY());
					getWorld().getGameState().getCoinsCounter().add(-ArcherTower.PRICE);
				} else if(mouseState == GameState.MouseState.PLACE_BOMB_TOWER && getWorld().getGameState().haveEnoughCoins(BombTower.PRICE)) {
					getWorld().addObject(new BombTower(), getX(), getY());
					getWorld().getGameState().getCoinsCounter().add(-BombTower.PRICE);
				} else if(mouseState == GameState.MouseState.PLACE_SNIPER_TOWER && getWorld().getGameState().haveEnoughCoins(SniperTower.PRICE)) {
					getWorld().addObject(new SniperTower(), getX(), getY());
					getWorld().getGameState().getCoinsCounter().add(-SniperTower.PRICE);
				}
			}
		}
	}

}
