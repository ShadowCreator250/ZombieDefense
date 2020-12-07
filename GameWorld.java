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

    private Cell[][] grid;
    private boolean isPaused = true;
    private int executionSpeed = DEFAULT_SPEED;

    private PauseResumeButton pauseResumeButton = new PauseResumeButton();
    private GameState gameState = new GameState(100);
    
    private int amountOfZombies = 2;
    private int wave = 1;
    private int maxWave = 10;
    private int waveSpawnTime = 300;
    

    public GameWorld() {
        super(GRID_SIZE_X * CELL_SIZE, GRID_SIZE_Y * CELL_SIZE, 1);
        Greenfoot.setSpeed(DEFAULT_SPEED);
        this.grid = new Cell[GRID_SIZE_X][GRID_SIZE_Y];
        fillGridArrayWithEmptyCells();
        definePaintOrder();
        fillWorld();
        prepare();
    }
    
    public void act() {
    	if(!isPaused()) {
			if(getObjects(Zombie.class).size() == 0) {
				if(waveSpawnTime > 0) {
					waveSpawnTime--;
					if(waveSpawnTime == 0 && wave < maxWave) {
						startNextWave();
						wave++;
						waveSpawnTime = 300;
					}
				}
			}
		}
    }

    private void fillWorld() {
        removeAllObjects();
        placeCells();
        loadWorldFromTextFile("testworld1");
        placeGUI();
    }

    private void placeGUI() {
        addObject(pauseResumeButton, getWidth() / 2, pauseResumeButton.getImage().getHeight() + 4);
        addObject(gameState, 0, 0);
        addObject(gameState.getCoinsCounter(), getWidth() / 4, gameState.getCoinsCounter().getImage().getHeight() / 2 + 4);
        GameSpeedControlButton speed20Button = new GameSpeedControlButton(20, GameSpeedControlButton.IDLE_BUTTON_IMAGE_NAMES[0],
                GameSpeedControlButton.ACTIVE_BUTTON_IMAGE_NAMES[0]);
        GameSpeedControlButton speed80Button = new GameSpeedControlButton(80, GameSpeedControlButton.IDLE_BUTTON_IMAGE_NAMES[1],
                GameSpeedControlButton.ACTIVE_BUTTON_IMAGE_NAMES[1]);
        MenuExpendButton menuExpander = new MenuExpendButton();
        addObject(speed80Button, (getWidth() / 2 + pauseResumeButton.getImage().getWidth() + 8), speed80Button.getImage().getHeight() / 2 + 4);
        addObject(speed20Button, (getWidth() / 2 - pauseResumeButton.getImage().getWidth() - 8), speed20Button.getImage().getHeight() / 2 + 4);
        addObject(menuExpander, getWidth() / 2, getHeight() - (menuExpander.getImage().getHeight() / 2 + 4));
    }

    @Override
    public void started() {
        super.started();
        resume();
        getPauseResumeButton().updatePauseResumeButten();
    }

    @Override
    public void stopped() {
        super.stopped();
        pause();
        getPauseResumeButton().updatePauseResumeButten();
    }

    private void definePaintOrder() {
        setPaintOrder(GameState.class, Counter.class, Button.class, PriceTag.class, Menu.class, Projectile.class, Zombie.class, Tower.class,
            Obstacle.class, PathCell.class, TowerCell.class, NormalCell.class);
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
    public Cell CellFromWorldPos(int x, int y) {
        if(x < 0 || x > getWidth() || y < 0 || y > getHeight()) {
            return null;
        }
        return grid[x / CELL_SIZE][y / CELL_SIZE];
    }

    public void replaceCellInGrid(Cell cell) {
        int gridX = cell.getGridX();
        int gridY = cell.getGridY();
        if(gridX >= 0 && gridX < GRID_SIZE_X && gridY >= 0 && gridY < GRID_SIZE_Y) {
            grid[gridX][gridY] = cell;
        }
    }

    public void computePathSectionTypes() {
        List<PathCell> cells = findAllCellsWithCellType(PathCell.class);
        for (Cell cell : cells) {
            ((PathCell) cell).evaluatePathSectionType();
        }
    }

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
            placeCells();
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

    public PauseResumeButton getPauseResumeButton() {
        return pauseResumeButton;
    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * Bereite die Welt f�r den Programmstart vor.
     * Das hei�t: Erzeuge die Anfangs-Objekte und f�ge sie der Welt hinzu.
     */
    private void prepare() {
    	for(Zombie zombie: createWaveOne()) {
    		addObject(zombie, 0, 0);
    	}
    	createWaveOne().clear();  	
    }
    
    private List<Zombie> createWaveOne() {
    	List<Zombie> waveOne = new ArrayList<Zombie>();
    	for(int i = 0; i < amountOfZombies; i++) {
    		double strength = 1 - ((Math.rint(new Random().nextDouble() * 10)) / 10);
    		double resistance = 0 + ((Math.rint(new Random().nextDouble() * 10)) / 10);
    		double speed = 1 - ((Math.rint(new Random().nextDouble() * 10)) / 10);
    		double health = 100 + ((Math.rint(new Random().nextDouble() * 500)) / 10);
    		Zombie z = new Zombie(strength, resistance, speed, health);
    		waveOne.add(z);
    	}
    	return waveOne;
    }
    
    private List<Zombie> createNextWave() {
    	List<Zombie> nextWave = new ArrayList<Zombie>();
    	for(int i = 0; i < amountOfZombies; i++) {
    		double strength = 1 - ((Math.rint(new Random().nextDouble() * 10)) / 10);
    		double resistance = 0 + ((Math.rint(new Random().nextDouble() * 10)) / 10);
    		double speed = 1 - ((Math.rint(new Random().nextDouble() * 10)) / 10);
    		double health = 100 + ((Math.rint(new Random().nextDouble() * 500)) / 10);
    		Zombie z = new Zombie(strength, resistance, speed, health);
    		nextWave.add(z);
    	}
    	return nextWave;
    }
    
    private void startNextWave() {
    	amountOfZombies += 2;
    	for(Zombie zombie: createNextWave()) {
    		addObject(zombie, 0, 0);
    	}
    	createNextWave().clear();
    }
}
