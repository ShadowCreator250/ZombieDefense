import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greenfoot.Greenfoot;
import greenfoot.World;

public class GameWorld extends World {

	public static final int GRID_SIZE_X = 15;
	public static final int GRID_SIZE_Y = 9;
	public static final int CELL_SIZE = 64;
	public static final int DEFAULT_SPEED = 50;
	private static final char NORMAL_CELL_PARSE_CHAR = '0';
	private static final char PATH_CELL_PARSE_CHAR = 'P';
	private static final char START_CELL_PARSE_CHAR = 'S';
	private static final char END_CELL_PARSE_CHAR = 'E';
	private static final char TOWER_CELL_PARSE_CHAR = 'T';
	private static final int TIME_TILL_SPAWN_WAVE = 500;
	private static final int MAX_WAVE_COUNT = 10;
	private static final int ZOMBIES_ADDED_PER_WAVE = 2;
	private static final int TIME_BETWEEN_SPAWNING_INTERVALLS = 40;
	private static final int INITIAL_AMOUNT_OF_COINS = 25;

	private Cell[][] grid;
	private boolean isPaused = true;
	private int executionSpeed = DEFAULT_SPEED;
	private List<List<PathCell>> pathsList;
	private List<List<Zombie>> waves;
	private List<BaseGate> gates;
	private int currentWaveIndex = -1;
	private int timeTillWaveCountdown = TIME_TILL_SPAWN_WAVE;
	private boolean isInSpawningProcess = false;
	private int numberOfZombiesSpawned = 0;
	private int spawningIntervallCountdown = 0;

	private PauseResumeButton pauseResumeButton = new PauseResumeButton();
	private CursorImage cursorImage = new CursorImage();
	private Counter coinsCounter = new Counter("Coins: ");
	private Counter lifePointsCounter = new Counter("HP: ");

	public GameWorld() {
		super(GRID_SIZE_X * CELL_SIZE, GRID_SIZE_Y * CELL_SIZE, 1);
		Greenfoot.setSpeed(DEFAULT_SPEED);
		this.grid = new Cell[GRID_SIZE_X][GRID_SIZE_Y];
		fillGridArrayWithEmptyCells();
		definePaintOrder();
		loadWorldFromTextFile("testworld2");
		this.pathsList = computeAllPossiblePaths();
		this.waves = prepareWaves();
		initCounter();
	}

	private void initCounter() {
		coinsCounter.setValue(INITIAL_AMOUNT_OF_COINS);
		lifePointsCounter.setValue(calculateLifePoints());
	}

	@Override
	public void act() {
		if(!isPaused()) {
			if(getObjects(Zombie.class).size() == 0) {
				countdownTillNextWave();
			}
			if(isInSpawningProcess) {
				spawningIntervalls();
			}
			lifePointsCounter.setValue(calculateLifePoints());
		}
	}

	private void countdownTillNextWave() {
		if(timeTillWaveCountdown > 0) {
			timeTillWaveCountdown--;
		} else if(timeTillWaveCountdown == 0 && currentWaveIndex < MAX_WAVE_COUNT) {
			isInSpawningProcess = true;
			currentWaveIndex++;
			timeTillWaveCountdown = TIME_TILL_SPAWN_WAVE;
		}
	}

	private void spawningIntervalls() {
		if(spawningIntervallCountdown > 0) {
			spawningIntervallCountdown--;
		} else if(spawningIntervallCountdown == 0 && numberOfZombiesSpawned < numberOfZombiesInWave(currentWaveIndex + 1)) {
			spawningIntervallCountdown = TIME_BETWEEN_SPAWNING_INTERVALLS;
			for (StartPathCell startCell : findAllCellsWithCellType(StartPathCell.class)) {
				startCell.spawnZombie(waves.get(currentWaveIndex).get(numberOfZombiesSpawned));
				numberOfZombiesSpawned++;
			}
		} else {
			numberOfZombiesSpawned = 0;
			spawningIntervallCountdown = 0;
			isInSpawningProcess = false;
		}
	}

	private int calculateLifePoints() {
		int result = 0;
		if(gates.size() > 0) {
			for (BaseGate baseGate : gates) {
				result += Math.rint(baseGate.getDurability());
			}
		}
		return result / gates.size();
	}

	private void fillWorld() {
		removeAllObjects();
		placeCells();
		placeGUI();
		placeAllGates();
	}

	private void placeGUI() {
		addObject(pauseResumeButton, getWidth() / 2, pauseResumeButton.getImage().getHeight() + 4);
		addObject(cursorImage, 0, 0);
		addObject(coinsCounter, getWidth() / 4, coinsCounter.getImage().getHeight() / 2 + 4);
		GameSpeedControlButton speed20Button = new GameSpeedControlButton(20, GameSpeedControlButton.IDLE_BUTTON_IMAGE_NAMES[0],
				GameSpeedControlButton.ACTIVE_BUTTON_IMAGE_NAMES[0]);
		GameSpeedControlButton speed80Button = new GameSpeedControlButton(80, GameSpeedControlButton.IDLE_BUTTON_IMAGE_NAMES[1],
				GameSpeedControlButton.ACTIVE_BUTTON_IMAGE_NAMES[1]);
		MenuExpendButton menuExpander = new MenuExpendButton();
		addObject(speed80Button, (getWidth() / 2 + pauseResumeButton.getImage().getWidth() + 8), speed80Button.getImage().getHeight() / 2 + 4);
		addObject(speed20Button, (getWidth() / 2 - pauseResumeButton.getImage().getWidth() - 8), speed20Button.getImage().getHeight() / 2 + 4);
		addObject(menuExpander, getWidth() / 2, getHeight() - (menuExpander.getImage().getHeight() / 2 + 4));
	}

	private void definePaintOrder() {
		setPaintOrder(CursorImage.class, Counter.class, Button.class, PriceTag.class, Menu.class, Projectile.class, Zombie.class, Tower.class,
				Obstacle.class, BaseGate.class, PathCell.class, TowerCell.class, NormalCell.class);
	}

	private void placeAllGates() {
		for (EndPathCell endCell : findAllCellsWithCellType(EndPathCell.class)) {
			endCell.spawnBaseGate();
			gates.add(endCell.getGate());
		}
	}

	@Override
	public void started() {
		super.started();
		resume();
		pauseResumeButton.updatePauseResumeButton();
	}

	@Override
	public void stopped() {
		super.stopped();
		pause();
		pauseResumeButton.updatePauseResumeButton();
	}

	private void fillGridArrayWithEmptyCells() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				grid[x][y] = new NormalCell(x, y);
			}
		}
	}

	private void placeCells() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				addObject(grid[x][y], x * CELL_SIZE + CELL_SIZE / 2, y * CELL_SIZE + CELL_SIZE / 2);
			}
		}
		computePathSectionTypes();
	}

	private void removeAllObjects() {
		removeObjects(getObjects(null));
	}

	private List<List<PathCell>> computeAllPossiblePaths() {
		List<List<PathCell>> result = new ArrayList<>();
		for (StartPathCell startCell : findAllCellsWithCellType(StartPathCell.class)) {
			for (EndPathCell endCell : findAllCellsWithCellType(EndPathCell.class)) {
				List<PathCell> path = new AStarAlgorithm(startCell, endCell).getPath();
				path.add(0, startCell);
				result.add(path);
			}
		}
		return result;
	}

	public List<PathCell> getOneRandomPath() {
		if(isThereAnyPath()) {
			return pathsList.get(new Random().nextInt(pathsList.size()));
		} else {
			return new ArrayList<>();
		}
	}

	public boolean isThereAnyPath() {
		return pathsList.size() > 0;
	}

	/**
	 * Finds the first {@link Cell} with a given Cell type in the grid.
	 * 
	 * @param type
	 * @return The first {@link Cell} with the given type.<br>
	 *         Returns <code>null<code> if no {@link Cell} is found.
	 */
	private <T extends Cell> T findFirstCellWithCellType(Class<T> cls) {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				if(cls.isInstance(grid[x][y])) {
					return (T) grid[x][y];
				}
			}
		}
		return null;
	}

	/**
	 * Finds all {@link Cell}s with a given Cell type in the grid.
	 * 
	 * @param type
	 * @return A List of {@link Cell}s with the given type.<br>
	 *         Returns an empty List if no {@link Cell} is found.
	 */
	private <T extends Cell> List<T> findAllCellsWithCellType(Class<T> cls) {
		List<T> result = new ArrayList<>();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				if(cls.isInstance(grid[x][y])) {
					result.add((T) grid[x][y]);
				}
			}
		}
		return result;
	}

	/**
	 * Gets the cell on a specific world position (if its grid position is not
	 * known).
	 * 
	 * @param x the positions x value
	 * @param y the positions y value
	 * @return the {@link Cell} found on this position <br>
	 *         returns <code>null</code> if no {@link Cell} is found on this
	 *         position
	 */
	public Cell cellFromWorldPos(int x, int y) {
		if(x < 0 || x > getWidth() || y < 0 || y > getHeight()) {
			return null;
		}
		return grid[x / CELL_SIZE][y / CELL_SIZE];
	}

	/**
	 * replace a Cell in the Cell grid with another
	 * 
	 * @param cell
	 */
	public void replaceCellInGrid(Cell cell) {
		int gridX = cell.getGridX();
		int gridY = cell.getGridY();
		if(gridX >= 0 && gridX < GRID_SIZE_X && gridY >= 0 && gridY < GRID_SIZE_Y) {
			grid[gridX][gridY] = cell;
		}
	}

	/**
	 * lets all PathCells compute their path section type to be displayed correctly
	 */
	public void computePathSectionTypes() {
		List<PathCell> cells = findAllCellsWithCellType(PathCell.class);
		for (Cell cell : cells) {
			((PathCell) cell).evaluatePathSectionType();
		}
	}

	/**
	 * Prepares the waves for this game
	 */
	private List<List<Zombie>> prepareWaves() {
		List<List<Zombie>> result = new ArrayList<>();
		for (int i = 1; i <= MAX_WAVE_COUNT; i++) {
			result.add(createWave(i));
		}
		return result;
	}

	/**
	 * calculate the number of zombies that should spawn in a wave<br>
	 * for every wave a specific amount more Zombies are spawned than the last wave
	 * 
	 * @param waveNumber
	 * @return the number of zombies
	 * @see ZOMBIES_ADDED_PER_WAVE
	 */
	private int numberOfZombiesInWave(int waveNumber) {
		return waveNumber * ZOMBIES_ADDED_PER_WAVE;
	}

	/**
	 * Creates a List of Zombies with randomized attributes
	 * 
	 * @param amountOfZombies how many Zombies should there be in the wave
	 * @return a List of Zombies
	 */
	private List<Zombie> createWave(int waveNumber) {
		List<Zombie> wave = new ArrayList<Zombie>();
		int numberOfZombies = numberOfZombiesInWave(waveNumber);
		for (int i = 0; i < numberOfZombies; i++) {
			double strength = 0 + rint(new Random().nextDouble() * 0.6, 1);
			double resistance = 0 + rint(new Random().nextDouble() * 0.6, 1);
			double speed = 0.5 + rint(new Random().nextDouble() * 0.5 - 0.25, 1);
			double health = 100 + (new Random().nextInt(76) - 25);
			Zombie z = new Zombie(strength, resistance, speed, health);
			wave.add(z);
		}
		return wave;
	}

	/**
	 * Rounds the given value to the given decimal places
	 *
	 * @param value         the value to be rounded
	 * @param decimalPoints the amount of decimal places
	 */
	private static double rint(double value, int decimalPoints) {
		double d = Math.pow(10, decimalPoints);
		return Math.rint(value * d) / d;
	}

	/**
	 * get a {@link BufferedWriter} for the given file
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private BufferedWriter getBufferedWriter(String filePath) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(filePath);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
		return new BufferedWriter(outputStreamWriter);
	}

	/**
	 * Saves the current worlds grid in a text file from where it can be loaded
	 * again.
	 * 
	 * Saves the file in the "worlds" folder
	 * 
	 * @param fileName name of the file without file type (no ".txt")
	 */
	public void saveWorldToTextFile(String fileName) {
		String filePath = "./worlds/" + fileName + ".txt";
		try (BufferedWriter bufferedWriter = getBufferedWriter(filePath)) {
			for (int y = 0; y < grid[0].length; y++) {
				char[] chars = new char[grid.length];
				for (int x = 0; x < grid.length; x++) {
					Cell cell = grid[x][y];
					if(cell instanceof StartPathCell) {
						chars[x] = START_CELL_PARSE_CHAR;
					} else if(cell instanceof EndPathCell) {
						chars[x] = END_CELL_PARSE_CHAR;
					} else if(cell instanceof NormalPathCell) {
						chars[x] = PATH_CELL_PARSE_CHAR;
					} else if(cell instanceof TowerCell) {
						chars[x] = TOWER_CELL_PARSE_CHAR;
					} else if(cell instanceof NormalCell) {
						chars[x] = NORMAL_CELL_PARSE_CHAR;
					}
				}
				bufferedWriter.write(chars);
				bufferedWriter.newLine();
			}
		} catch (IOException e) {
			System.err.println("Unable to write file " + filePath);
			e.printStackTrace();
		} // BufferedWriter, FileOutputStream and OutputStreamWriter auto close themselves
	}

	/**
	 * get a {@link BufferedReader} for a given file
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private BufferedReader getBufferedReader(String filePath) throws IOException {
		FileInputStream inputStream = new FileInputStream(filePath);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
		return new BufferedReader(inputStreamReader);
	}

	/**
	 * Loads a grid from a text file into the current world.
	 * 
	 * Loads the file from the "worlds" folder
	 * 
	 * @param fileName name of the file without file type (no ".txt")
	 */
	public void loadWorldFromTextFile(String fileName) {
		String filePath = "./worlds/" + fileName + ".txt";
		try (BufferedReader bufferedReader = getBufferedReader(filePath)) {
			String line;

			List<List<String>> chars = new ArrayList<>();
			while ((line = bufferedReader.readLine()) != null) {
				String[] letters = line.split("");
				chars.add(Arrays.asList(letters));
			}
			int lineCount = chars.size();
			int maxCharCount = 0;
			for (int i = 0; i < lineCount; i++) {
				maxCharCount = Math.max(maxCharCount, chars.get(i).size());
			}
			if(maxCharCount > GRID_SIZE_X || lineCount > GRID_SIZE_Y) {
				System.err.println("The grid that is in file " + fileName + " is too big." + maxCharCount + "x" + lineCount + ">" + GRID_SIZE_X + "x"
						+ GRID_SIZE_Y);
				return;
			}
			Cell[][] cells = new Cell[GRID_SIZE_X][GRID_SIZE_Y];
			for (int y = 0; y < GRID_SIZE_Y; y++) {
				for (int x = 0; x < GRID_SIZE_X; x++) {
					if(x < maxCharCount && y < lineCount) { // as long as grid in file smaller than world grid, parse it
						char c = chars.get(y).get(x).charAt(0);
						if(c == PATH_CELL_PARSE_CHAR) {
							cells[x][y] = new NormalPathCell(x, y);
						} else if(c == START_CELL_PARSE_CHAR) {
							cells[x][y] = new StartPathCell(x, y);
						} else if(c == END_CELL_PARSE_CHAR) {
							cells[x][y] = new EndPathCell(x, y);
						} else if(c == TOWER_CELL_PARSE_CHAR) {
							cells[x][y] = new TowerCell(x, y);
						} else { // includes c == NORMAL_CELL_PARSE_CHAR
							cells[x][y] = new NormalCell(x, y);
						}
					} else { // if grid in file is smaller than world grid fill the rest up
						cells[x][y] = new NormalCell(x, y);
					}
				}
			}
			this.grid = cells;
			fillWorld();
		} catch (IOException e) {
			System.err.println("Unable to read file " + filePath);
			e.printStackTrace();
		} // BufferedReader, InputStreamReader and FileInputStream auto close themselves
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public boolean isDefaultSpeed() {
		return getExecutionSpeed() == DEFAULT_SPEED;
	}

	public int getExecutionSpeed() {
		return executionSpeed;
	}

	public void setExecutionSpeed(int speed) {
		if(speed <= 0) {
			this.executionSpeed = 1;
		} else if(speed > 100) {
			this.executionSpeed = 100;
		} else {
			this.executionSpeed = speed;
			Greenfoot.setSpeed(executionSpeed);
		}
	}

	public boolean isPaused() {
		return isPaused;
	}

	private void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public void pause() {
		setPaused(true);
	}

	public void resume() {
		setPaused(false);
	}

	public Counter getCoinsCounter() {
		return coinsCounter;
	}

	/**
	 * checks if the player has enough coins to by an item for a certain price
	 * 
	 * @param price the price of the item
	 * @return if the player can buy that item
	 */
	public boolean haveEnoughCoins(int price) {
		if(coinsCounter.getValue() >= price) {
			return true;
		} else {
			return false;
		}
	}

	public PauseResumeButton getPauseResumeButton() {
		return pauseResumeButton;
	}

	public CursorImage getCursorImage() {
		return cursorImage;
	}

}
