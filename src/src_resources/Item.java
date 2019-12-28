package src_resources; 

import java.io.Serializable;

public class Item implements Serializable{
	
	// TODO make these unprotected? 
	protected int iLocX; 
	protected int iLocY; 
	protected ItemType eItemType; 
	private int curStep;
	
	// constructors 
	public Item(ItemType eItemType, int iLocX, int iLocY) {
		this.iLocX = iLocX; 
		this.iLocY = iLocY; 
		this.eItemType = eItemType;
		curStep = 0;
	}
	// constructors 
	
	// accessors
	public int getiLocX() {
		return iLocX;
	}

	public void setiLocX(int iLocX) {
		this.iLocX = iLocX;
	}

	public int getiLocY() {
		return iLocY;
	}

	public void setiLocY(int iLocY) {
		this.iLocY = iLocY;
	}

	public int getiHealthChange() {
		return eItemType.getiHealthChange();
	} 
	
	public ItemType geteItemType() {
		return eItemType;
	}
	
	public boolean getbIsDecoration() {
		return eItemType.getbIsDecoration();
	}
	
	public int getiWidth() {
		return eItemType.getiWidth();
	}
	
	public int getiHeight() {
		return eItemType.getiHeight();
	}
	
	public void setiIncrX(int incr) {
		eItemType.setiIncrX(incr);
	}
	
	public void setiIncrY(int incr) {
		eItemType.setiIncrY(incr);
	}
	// accessors
	
	// class methods
	public void move(boolean bSlowMovement) {
		curStep++;
		if (!bSlowMovement) {
			iLocX -= eItemType.getiIncrX(); 
			iLocY -= eItemType.getiIncrY(); 
		} else {
			iLocX -= (eItemType.getiIncrX()) / 2; 
			iLocY -= (eItemType.getiIncrY()) / 2; 
		}
		if(eItemType == ItemType.BUBBLE){
			iLocX += (int)(Math.sin((double)curStep/25)*3);
			iLocY -= (eItemType.getiIncrY()-8);
		}
	}
	
	public void updateiLocY(int windowHeight) {
		switch(eItemType) {
		case ROCK1:
			iLocY = windowHeight-75;
			break;
		case ROCK2:
			iLocY = windowHeight-75;
			break;
		case ROCK3:
			iLocY = windowHeight-75;
			break;
		case SEAWEED1:
			iLocY = windowHeight-125;
			break;
		case SEAWEED2:
			iLocY = windowHeight-125;
			break;
		case SEAWEED3:
			iLocY = windowHeight-75;
			break;
		case STARTSIGN:
			iLocY = windowHeight-200;
			break;
		case ENDSIGN:
			iLocY = windowHeight-200;
			break;
		default:
			break;
		}
	}
	// class methods
	
	// helper methods
	@Override
	public String toString() {
		return eItemType.toString();
	}
	// helper methods
}
