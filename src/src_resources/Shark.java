package src_resources;

import java.io.Serializable;


public class Shark extends Item implements Serializable {
	
	private int iMaxHealth = 100; 
	private int iHealth; 
	private boolean bMoveUp; 
	private boolean bMoveDown; 
	private boolean bMoveLeft; 
	private boolean bMoveRight;
	private boolean bDirection = true;
	private int rotInd;
	private int dirTimeoutInd;
	private boolean flashing;
	private int flashingIndex;
	private boolean flashState;
	private int[] sinusoidArr;
	private final int sinusoidLength = 200;
	private boolean flashIndefinitely;
	
	// constructors
	public Shark(ItemType eItemType, int iLocX, int iLocY) {
		super(eItemType, iLocX, iLocY);
		eItemType = ItemType.SHARK; 
		iHealth = iMaxHealth; 
		flashing = false;
		flashingIndex = 0;
		flashState = false;
		eItemType.setiIncrX(7);
		eItemType.setiIncrY(7);
	}
	// constructors
	
	// accessors
	public int getiMaxHealth() {
		return iMaxHealth; 
	}
	
	public int getiHealth() {
		return iHealth;
	}
	
	public boolean getbFlashing() {
		return flashing;
	}
	
	public int getRotInd() {
		return rotInd;
	}
	
	public boolean getbDirection() {
		return bDirection;
	}
	
	public void setMoveBools(boolean up, boolean down, boolean left, boolean right) {
		bMoveUp = up;
		bMoveDown = down;
		bMoveRight = right;
		bMoveLeft = left;
	}
	
	public void setMoveBool(int index, boolean b) {
		switch(index) {
		case 1:
			bMoveUp = b;
			break;
		case 2:
			bMoveDown = b;
			
			break;
		case 3:
			bMoveLeft = b;
			break;
		case 4:
			bMoveRight = b;
			break;
		default:
			break;
		}
		
		boolean oldDir = bDirection;
		if(bMoveLeft && !bMoveRight) {
			bDirection = false;
			if(bDirection != oldDir) {
				if(rotInd != 0) {
					rotInd*=-1;
				}
			}
		} else if(!bMoveLeft && bMoveRight) {
			bDirection = true;
			if(bDirection != oldDir) {
				if(rotInd != 0) {
					rotInd*=-1;
				}
			}
		} else if(bMoveLeft && bMoveRight) {
			bDirection = true;
			if(bDirection != oldDir) {
				if(rotInd != 0) {
					rotInd*=-1;
				}
			}
		}
	}

	public void setiHealth(int iHealth) {
		if(iHealth >= iMaxHealth) {
			this.iHealth = iMaxHealth;
		} else if(iHealth <= 0) {
			this.iHealth = 0;
		} else {
			this.iHealth = iHealth;
		}
	}
	
	public boolean getFlashState() {
		return flashState;
	}

	public void setiIncrX(int incr) {
		eItemType.setiIncrX(incr);
	}

	public void setSinusoidalMovement(int amplitude, int offset) {
		// https://www.mathsisfun.com/algebra/amplitude-period-frequency-phase-shift.html
		// y = A sin(B(x + C)) + D
		double period = 2*Math.PI/sinusoidLength;
		sinusoidArr = new int[sinusoidLength];
		for (int i = 0; i < sinusoidLength; i++) {
			sinusoidArr[i] = (int) (amplitude*Math.sin(period*i) + offset);
		}
	}
	// accessors

	// class methods
	public void startFlashing(boolean inDefinite) {
		flashingIndex = 0;
		flashing = true;
		flashIndefinitely = inDefinite;
	}
	
	public boolean incFlashIndex() {
		flashingIndex++;
		if(flashingIndex >= 20) {
			if(flashState) {
				flashState = false;
			}
			flashing = false;
		} else if(flashingIndex%2 == 0) {
			flashState = !flashState;
			if(flashIndefinitely) {
				flashingIndex = 0;
			}
		}
		
		return flashState;
	}
	
	public void move(int width, int height) {
		if (bMoveUp) {
			iLocY -= eItemType.getiIncrY();
		}
		if (bMoveDown) {
			iLocY += eItemType.getiIncrY();
		}
		if (bMoveLeft) {
			iLocX -= eItemType.getiIncrX();
		}
		if (bMoveRight) {
			iLocX += eItemType.getiIncrX();
		}
		
		if(!bMoveLeft && !bMoveRight && !bMoveUp && !bMoveDown) {
			if(!bDirection) {
				if(dirTimeoutInd < 30) {
					dirTimeoutInd++;
				} else {
					bDirection = true;
					dirTimeoutInd = 0;
				}
			}
		} else {
			dirTimeoutInd = 0;
		}
		
		if(bMoveUp && !bMoveDown) {
			if(bDirection) {
				if(rotInd >= -25) {
					rotInd-=6;
				}
			} else {
				if(rotInd <= 25) {
					rotInd+=6;
				}
			}
		} else if(bMoveDown && !bMoveUp) {
			if(bDirection) {
				if(rotInd <= 25) {
					rotInd+=6;
				} 
			} else {
				if(rotInd >= -25) {
					rotInd-=6;
				}
			}
		} else if(!bMoveUp && !bMoveDown) {
			if(rotInd != 0) {
				if(rotInd < 0) {
					rotInd+=6;
					if(rotInd > 0) {
						rotInd = 0;
					}
				} else {
					rotInd-=6;
					if(rotInd < 0) {
						rotInd = 0;
					}
				}
			}
		} else if(bMoveUp && bMoveDown) {
			if(rotInd != 0) {
				if(rotInd < 0) {
					rotInd+=6;
					if(rotInd > 0) {
						rotInd = 0;
					}
				} else {
					rotInd-=6;
					if(rotInd < 0) {
						rotInd = 0;
					}
				}
			}
		}
		
		if(iLocY <= 40) {
			iLocY = 40;
		} else if(iLocY >= height-super.getiHeight()-80) {
			iLocY = height-super.getiHeight()-80;
		}
		
		if(iLocX <= 0) {
			iLocX = 0;
		} else if(iLocX >= width-super.getiWidth()) {
			iLocX = width-super.getiWidth();
		}
	}
	
	@Override
	public void move(boolean bSlowMovement) {
		if (bMoveUp) {
			iLocY -= eItemType.getiIncrY();
		}
		if (bMoveDown) {
			iLocY += eItemType.getiIncrY();
		}
		if (bMoveLeft) {
			iLocX -= eItemType.getiIncrX();
		}
		if (bMoveRight) {
			iLocX += eItemType.getiIncrX();
		}
		
		if(!bMoveLeft && !bMoveRight && !bMoveUp && !bMoveDown) {
			if(!bDirection) {
				if(dirTimeoutInd < 30) {
					dirTimeoutInd++;
				} else {
					bDirection = true;
					dirTimeoutInd = 0;
				}
			}
		} else {
			dirTimeoutInd = 0;
		}
		
		if(rotInd != 0) {
			if(rotInd < 0) {
				rotInd+=6;
				if(rotInd > 0) {
					rotInd = 0;
				}
			} else {
				rotInd-=6;
				if(rotInd < 0) {
					rotInd = 0;
				}
			}
		}
	}

	public void moveGame3(int gameProgress, int windowHeight) {
		double x = (double)gameProgress/11;
		double scaler = (double)windowHeight;
		iLocX = 40 + (int) (Math.sin(x/2.5)*30); // shark does not move in the horizontal direction for Game3
		iLocY = (int)((scaler/2.3) + (scaler/2.2 * (Math.cos(x/4)/1.8 + Math.sin(x)/5)));
		rotInd = (int)(-60*(0.2 * (Math.cos(x) - 0.694444 * Math.sin(x/4))));
	}
	// class methods
}
