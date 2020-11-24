public class Zombie extends SmoothMover {
	
	public int strength;
	public int resistance;
	public int speed;
	
	public Zombie() {
		//TODO: image is temporarily
		setImage("./ppl2.png");
	}
	
	public Zombie(int strength, int resistance, int speed) {
		this.strength = strength;
		this.resistance = resistance;
		this.speed = speed;
	} 
}
