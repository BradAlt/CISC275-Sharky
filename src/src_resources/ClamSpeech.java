package src_resources;

import javafx.scene.input.KeyCode;

public class ClamSpeech extends Item {

	private int iNumInstructions; 
	
	// constructors
	public ClamSpeech(int iLocX, int iLocY) {
		super(ItemType.CLAMSPEECH, iLocX, iLocY);
		iNumInstructions = 3; 
	}
	// constructors
	
	// accessors
	public int getiNumInstructions() {
		return iNumInstructions; 
	}
	// accessors
	
	// class methods
	public void transitionUp() {
		super.setiLocY(super.getiLocY() - 15); 
	}
	
	public void transitionDown() {
		super.setiLocY(super.getiLocY() + 15); 
	}
	
	public int speak(KeyCode kcKeyPress, int iGameProgress) {
		if(kcKeyPress == KeyCode.SPACE || kcKeyPress == KeyCode.ENTER) {
			iGameProgress ++; 
		}
		return iGameProgress; 
	}
	
	
//	public void move(KeyCode kcKeyPress, KeyCode kcKeyRelease, int winHeight) {
//		if(!transitionDone) {
//			if(bDismissed) {
//				super.setiLocY(super.getiLocY()+15);
//				if(super.getiLocY() >= winHeight) {
//					transitionDone = true;
//				}
//			} else {
//				super.setiLocY(super.getiLocY()-35);
//				iCurrTextInd = 0;
//				iCurrImage = iImages[iCurrTextInd];
//				if(super.getiLocY() <= winHeight-300) {
//					transitionDone = true;
//				}
//			}
//		} else {
//			if(kcKeyPress == KeyCode.SPACE || kcKeyPress == KeyCode.ENTER) {
//				if(iCurrTextInd == iImages.length) {
//					bDismissed = true;
//					transitionDone = false;
//				} else {
//					iCurrImage = iImages[iCurrTextInd];
//					iCurrTextInd++;
//				}
//			}
//		}
//	}
	// class methods
}
