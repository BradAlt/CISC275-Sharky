package src_resources;
import java.io.Serializable;

public enum ItemType implements Serializable {
	
	FISH(10, false, 75, 75), 
	RAY(35, 20, false, 100, 75), 
	GLASS(-25, 8, false, 60, 75, true), 
	PLASTIC(-10, false, 75, 75, true), 
	METAL(-15, 5, false, 50, 40, true),
	SEAWEED1(0, 5, true, 200, 200),
	SEAWEED2(0, 5, true, 300, 200),
	SEAWEED3(0, 5, true, 100, 100),
	ROCK1(0, 5, true, 200, 100),
	ROCK2(0, 5, true, 200, 100),
	ROCK3(0, 5, true, 200, 100),
	BOAT(-1, 5, false, 300, 300),
	SHARK(0, false, 150,100),
	ALGAE(0, 5, true, 50, 50),
	STARTSIGN(0, 5, true, 150, 225),
	ENDSIGN(0, 5, true, 150, 225),
	CLAMSPEECH(0, true, 175, 275),
	BUBBLE(100, 0, 5, false, 80, 80),
	NETSELECT(0, 0, true, 110, 110),
	NETTYPES(0, 0, true, 220, 110),
	MISSINGITEM(); // 2:1 ratio
	
	private int iIncrX = 10; 
	private int iIncrY = 0; 
	private int iHeight = 50;
	private int iWidth = 50;
	private int iHealthChange; 
	private boolean bIsDecoration;
	private boolean bIsTrash;
	
	// constructors
	ItemType() {
		
	}
	
	ItemType(int iHealthChange, boolean isDec, int width, int height, boolean isTrash) {
		// overloaded constructor to specify is ItemType is trash
		this.iHealthChange = iHealthChange; 
		this.bIsDecoration = isDec;
		this.iWidth = width;
		this.iHeight = height;
		this.bIsTrash = isTrash;
	}
	
	ItemType(int iHealthChange, int iIncrX, boolean isDec, int width, int height, boolean isTrash) {
		// overloaded constructor to specify is ItemType is trash
		this.iHealthChange = iHealthChange; 
		this.bIsDecoration = isDec;
		this.iWidth = width;
		this.iHeight = height;
		this.bIsTrash = isTrash;
		this.iIncrX = iIncrX; 
	}
	
	ItemType(int iHealthChange, int xInc, boolean isDec, int width, int height) {
		this.iHealthChange = iHealthChange; 
		this.iIncrX = xInc;
		this.bIsDecoration = isDec;
		this.iWidth = width;
		this.iHeight = height;
		this.bIsTrash = false;
	}

	ItemType(int iHealthChange, int xInc, int yInc, boolean isDec, int width, int height) {
		this.iHealthChange = iHealthChange; 
		this.iIncrX = xInc;
		this.iIncrY = yInc;
		this.bIsDecoration = isDec;
		this.iWidth = width;
		this.iHeight = height;
		this.bIsTrash = false;
	}
	
	ItemType(int iHealthChange, boolean isDec, int width, int height) {
		this.iHealthChange = iHealthChange; 
		this.bIsDecoration = isDec;
		this.iWidth = width;
		this.iHeight = height;
		this.bIsTrash = false;
	}
	
	ItemType(int iHealthChange, boolean isDec) {
		this.iHealthChange = iHealthChange; 
		this.bIsDecoration = isDec;
		this.bIsTrash = false;
	}
	// constructors
	
	// accessors
	public int getiWidth() {
		return iWidth;
	}
	
	public int getiHeight() {
		return iHeight;
	}

	public int getiIncrX() {
		return iIncrX;
	}
	
	public int getiIncrY() {
		return iIncrY;
	}
	
	public int getiHealthChange() {
		return iHealthChange; 
	}
	
	public boolean getbIsDecoration() {
		return bIsDecoration;
	}
	
	public void setiIncrX(int incr) {
		this.iIncrX = incr;
	}
	
	public void setiIncrY(int incr) {
		this.iIncrY = incr;
	}
	
	public boolean getbIsTrash() {
		return bIsTrash;
	}
	// accessors
}
