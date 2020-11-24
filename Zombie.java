import greenfoot.*;

public class Zombie extends SmoothMover {
	
	public int strength;
	public int resistance;
	public int speed;
	
	public Zombie() {
		this(10, 10, 10);		
	}
	
	public Zombie(int strength, int resistance, int speed) {
		//TODO: image is temporarily
		setImage("./ppl2.png");
		this.strength = strength;
		this.resistance = resistance;
		this.speed = speed;
		dropCurrency(this);
		attackGate();
	} 
	
	public void dropCurrency(Zombie zombie) {
		if (zombie.resistance <= 0) {
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
