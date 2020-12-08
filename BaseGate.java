import greenfoot.Actor;

public class BaseGate extends Actor {

	private static final String GATE_IMAGE_NAME = "Gate.png";

	private double durability = 300;


	public BaseGate() {
		setImage(GATE_IMAGE_NAME);
	}

	public double getDurability() {
		return durability;
	}


	public void setDurability(double durability) {
		this.durability = durability;
	}
}