
public class Zombie extends SmoothMover {
	
	public int strength;
	public double resistance; // should be from 0-1, example: 0.7 stands for 70% less damage to get
	public int speed;
	public double health;
	
	public Zombie() {
		this(10, 0.1, 10, 100);		
	}
	
	public Zombie(int strength, double resistance, int speed, double health) {
		//TODO: image is temporarily
		setImage("./ppl2.png");
		this.strength = strength;
		this.resistance = resistance;
		this.speed = speed;
		this.health = health;
	} 
	
	public void act() {
		
	}
	
	public void dropCurrency(Zombie zombie) {
		if (zombie.health <= 0) {
			//TODO: Player gets money/gold + creating class money/gold
			getWorld().removeObject(zombie);
		}
	}
	
	public void attackGate() {		
		//if (getOneIntersectingObject(Gate.class)) {
			//TODO: create class Gate, decrease the Gate´s health
		//}
	}
}
