package src_main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import javafx.scene.input.KeyCode;
import src_resources.ClamSpeech;
import src_resources.GameState;
import src_resources.Item;
import src_resources.ItemType;
import src_resources.Shark;
import java.io.Serializable;


/**
 * @author 
 * Adam Johnson
 * Bradley Altmiller 
 * Justin Hamilton
 * Quinlan Kauffman
 * Sam Zabaldo
 *
 * @serial 100% of model is Serialized
 * 
 */
public class Model implements Serializable {


	private boolean bInitFlag = true; 
	private boolean bModelForceCont = false;
	private boolean bStartingFlag;
	private boolean bPlayerLost;
	private boolean bGameRunning;
	private boolean bGame2Correct;
	private boolean bFeedbackAvailable = false;
	private boolean bInOilLock;	
	private boolean bRemovingTrash = true;
	private boolean showMove = true;
	private boolean tutorialDone = false;
	private boolean bMouseClickLock = false;
	private int iBoundWidth;
	private int iBoundHeight;
	private int iGameLength;
	private int iGameProgress;
	private int iAlgaeLevel;
	private int iAlgaeCollected;
	private int iMaxAlgae = 75;
	private int tutorial3State = 0;
	private int feedbackIndex; 
	private ArrayList<Item> lstItems;
	private Random rand = new Random();
	private Item netSquare;
	private Shark shark;
	private ClamSpeech clamspeech; 
	private ItemType eMostAbundantTrash;
	private ItemType eNormalTrash1, eNormalTrash2;
	private ItemType eGuess;
	private GameState eGameState;
	private GameState eLastGameState; 
	
	// constructors
	/**
	 * Sets the window width and height so that elements on screen can 
	 * interact with the screen boundaries. 
	 * @param iBoundWidth window width
	 * @param iBoundHeight window height
	 */
	/**
	 * @param iBoundWith Width of the game screen
	 * @param iBoundHeight Height of the game screen
	 * 
	 */
	public Model(int iBoundWidth, int iBoundHeight) {
		bStartingFlag = false;
		this.iBoundWidth = iBoundWidth;
		this.iBoundHeight = iBoundHeight;
		eGameState = GameState.STARTSCREEN;
		bPlayerLost = false;
		lstItems = new ArrayList<Item>();
		shark = new Shark(ItemType.SHARK, -73, (iBoundHeight / 3) - (ItemType.SHARK.getiHeight() / 2));
		bGameRunning = false;
	}
	// constructors

	// accessors
	/**
	 * Returns the attribute bModelForceCont. 
	 * @return bModelForceCont. 
	 * The model used bModelForceCont to indicate that the view should prepare to 
	 * move on to the next gamestate. 
	 */
	public boolean getbModelForceCont() {
		return bModelForceCont;
	}

	/**
	 * Returns the list of Items on screen. 
	 * @return lstItems 
	 */
	public ArrayList<Item> getlstItems() {
		return lstItems;
	}

	/**
	 * Returns the current gamestate of the game. 
	 * @return eGameState 
	 */
	public GameState geteGameState() {
		return eGameState;
	}

	/**
	 * Returns the number maximum number of ticks for the current game. 
	 * @return iGameLength 
	 */
	public int getiGameLength() {
		return iGameLength; 
	}

	/**
	 * @return Currently elapsed game time
	 */
	/**
	 * Returns the current tick of the current game
	 * @return iGameProgress
	 */
	public int getiGameProgress() {
		return iGameProgress; 
	}
	
	/**
	 * @return Shark
	 */
	public Shark getShark() {
		return shark;
	}

	/**
	 * @return Currently algae - likelyhood of spawning per tick
	 */
	public int getiAlgaeLevel() {
		return iAlgaeLevel;
	}

	/**
	 * @return Whether or not current game state has been initiailized
	 */
	public boolean getbInitFlag() {
		return bInitFlag;
	}

	/**
	 * @return Lets the view know if it should scroll the background
	 * @see View Background scrolling methods
	 */
	public boolean getbGameRunning() {
		return bGameRunning;
	}
	
	/**
	 * @param index Direction of shark move bool to set
	 * @param b Value to set the move bool too
	 * @see Shark.java
	 */
	public void setMoveBool(int index, boolean b) {
		shark.setMoveBool(index, b);
	}
	
	public boolean getShowMove() {
		return showMove;
	}
	
	public int getTutorial3State() {
		return tutorial3State;
	}
	
	public boolean getbRemovingTrash() {
		return bRemovingTrash;
	}
	
	public void setGame2Guess(ItemType g) {
		eGuess = g;
	}
	
	public ItemType geteMostAbundantTrash() {
		return eMostAbundantTrash;
	}
	
	public boolean getFeedbackAvailable() {
		return bFeedbackAvailable;
	}

	public boolean getGame2Correct() {
		return bGame2Correct;
	}
	// accessors
	
	// class methods
	/**
	 * The main method that controls Model
	 * Chooses to execute game based on eGameState
	 * @param bViewForceCont
	 * @param kcKeyPress What key is currently being pressed
	 * @param kcKeyRelease What key was just released
	 * @param dMouseX Position of mouse X
	 * @param dMouseY Position of mouse Y
	 * @param iWidth Width of screen
	 * @param iHeight Height of screen
	 */
	public void update(boolean bViewForceCont, KeyCode kcKeyPress, 
			KeyCode kcKeyRelease, double dMouseX,
			double dMouseY, int iWidth, int iHeight, boolean bClick) {
		setupGameState(iWidth, iHeight);
		switch (eGameState) {
		case STARTSCREEN:
			executeStartscreen(kcKeyPress);
			break;
		case CUTSCENE1:
			executeCutscene(bViewForceCont, kcKeyPress);
			break;
		case CUTSCENE2:
			executeCutscene(bViewForceCont, kcKeyPress);
			break;
		case CUTSCENE3:
			executeCutscene(bViewForceCont, kcKeyPress);
			break;
		case CUTSCENE4:
			executeCutscene(bViewForceCont, kcKeyPress);
			break;
		case TUTORIAL1:
			executeTutorial1(kcKeyPress, kcKeyRelease);
			break;
		case TUTORIAL3:
			executeTutorial3(dMouseX, dMouseY, bClick, kcKeyPress);
			break;
		case GAME1:
			executeGame1(kcKeyPress, kcKeyRelease);
			break;
		case GAME2:
			executeGame2(kcKeyPress);
			break;
		case GAME3:
			executeGame3(dMouseX, dMouseY, bClick, kcKeyPress);
			break;
		case GAMEOVER:
			executeGameOver(kcKeyPress);
			break;
		case ENDSCREEN:
			executeEndscreen(kcKeyPress);
			break;
		}
		cleanupGameState(bViewForceCont);
	}
	// class methods

	//helper methods
	private void setupGameState(int iWidth, int iHeight) {
		if (bInitFlag) {
			switch (eGameState) {
			case STARTSCREEN:
				System.out.println(eGameState);
				System.out.println("press enter to continue");
				lstItems.clear(); 
				break;
			case CUTSCENE1:
				System.out.println(eGameState);
				System.out.println("press enter to skip cutscene");
				break;
			case CUTSCENE2:
				System.out.println(eGameState);
				System.out.println("press enter to skip cutscene");
				break;
			case CUTSCENE3:
				System.out.println(eGameState);
				System.out.println("press enter to skip cutscene");
				break;
			case CUTSCENE4:
				System.out.println(eGameState);
				System.out.println("press enter to skip cutscene");
				break;
			case TUTORIAL1:
				tutorialDone = false; 
				shark.setMoveBools(false, false, false, false);
				shark.setiLocY((iBoundHeight / 2) - (ItemType.SHARK.getiHeight() / 2));
				shark.setiLocX(50);
				shark.setiHealth(75);
				iGameLength = 100;
				iGameProgress = 0;
				lstItems.clear();
				tutorialDone = false;
				addItem(ItemType.PLASTIC, iBoundWidth-(iBoundWidth/4), iBoundHeight/4-(ItemType.PLASTIC.getiHeight()/2));
				addItem(ItemType.FISH, iBoundWidth-(iBoundWidth/4), iBoundHeight-(iBoundHeight/4)-(ItemType.FISH.getiHeight()/2));
				break;
			case TUTORIAL3:
				iGameLength = 100;
				iGameProgress = 0;
				lstItems.clear();
				tutorialDone = false;
				tutorial3State = 1;
				addItem(ItemType.NETTYPES, 0, iBoundHeight-ItemType.NETTYPES.getiHeight()-27);
				netSquare = new Item(ItemType.NETSELECT, 0, iBoundHeight-ItemType.NETTYPES.getiHeight()-27);
				lstItems.add(netSquare);
				addItem(ItemType.PLASTIC, iBoundWidth-(iBoundWidth/4), iBoundHeight/3-(ItemType.PLASTIC.getiHeight()/2));
				addItem(ItemType.METAL, iBoundWidth-(iBoundWidth/4), iBoundHeight/2-(ItemType.PLASTIC.getiHeight()/2));
				addItem(ItemType.GLASS, iBoundWidth-(iBoundWidth/4), iBoundHeight-(iBoundHeight/3)-(ItemType.PLASTIC.getiHeight()/2));
				
				for(int i = iBoundWidth/4; i<=iBoundWidth-(iBoundWidth/4); i+=10) {
					addItem(ItemType.ALGAE, i, 0);
					i+=rand.nextInt(20);
				}
				break;
			case GAME1:
				System.out.println(eGameState);
				System.out.println("use arrow keys to move");	
				shark.setMoveBools(false,  false,  false,  true);
				shark.setiLocX(-73);
				shark.setiLocY((iBoundHeight / 3) - (ItemType.SHARK.getiHeight() / 2)); 
				bStartingFlag = true; 
				iGameLength = 1700;
				iGameProgress = 0;
				iAlgaeLevel = 0;
				shark.setiHealth(shark.getiMaxHealth()); 
				lstItems.clear(); 
				switch (rand.nextInt(3)) {
				case 1:
					eMostAbundantTrash = ItemType.PLASTIC;
					eNormalTrash1 = ItemType.METAL;
					eNormalTrash2 = ItemType.GLASS;
					break;
				case 2:
					eMostAbundantTrash = ItemType.METAL;
					eNormalTrash1 = ItemType.PLASTIC;
					eNormalTrash2 = ItemType.GLASS;
					break;
				default:
					eMostAbundantTrash = ItemType.GLASS;
					eNormalTrash1 = ItemType.PLASTIC;
					eNormalTrash2 = ItemType.METAL;
					break;
				}
				break;
			case GAME2:
				System.out.println(eGameState);
				System.out.println("answer the question");
				System.out.println("\nwhich type of trash appeared the most?");
				System.out.println("left: PLASTIC\tup: METAL\tright: GLASS");
				System.out.println(eMostAbundantTrash);
				iGameProgress = 700; 
				iGameLength = 700; 
				bFeedbackAvailable = false; 
				break;
			case GAME3:
				System.out.println(eGameState);
				System.out.println("use left and right key to toggel between tools");
				System.out.println("use mouse click to remove harmful objects in front of the shark"); 
	
				bStartingFlag = true;
				break;
			case GAMEOVER:
				System.out.println(eGameState);
				//System.out.println("better luck next time");
				//System.out.println("play again? left: YES\tright: NO");
				break;
			case ENDSCREEN:
				System.out.println(eGameState);
				System.out.println("thanks for playing");
				System.out.println("left: PLAY AGAIN\tright: QUIT");
				break;
			}
			bInitFlag = false;
		}
	
		int oldWidth = iBoundWidth;
		iBoundWidth = iWidth;
		iBoundHeight = iHeight;
		if(eGameState == GameState.GAME1 || eGameState == GameState.GAME3) {
			for (int i = oldWidth; i < iBoundWidth; i++) {
				if (rand.nextInt(100 - iAlgaeLevel) <= 2) {
					lstItems.add(new Item(ItemType.ALGAE, i, -10));
					lstItems.add(new Item(ItemType.ALGAE, iBoundWidth + rand.nextInt(iBoundWidth), -10));
				}
	
				if (i % 30 == 0) {
					switch (rand.nextInt(10)) {
					case 1:
						lstItems.add(new Item(ItemType.SEAWEED1, i, iBoundHeight - 125));
						break;
					case 2:
						lstItems.add(new Item(ItemType.SEAWEED2, i, iBoundHeight - 125));
						break;
					case 3:
						lstItems.add(new Item(ItemType.SEAWEED3, i, iBoundHeight - 75));
						break;
					default:
						break;
					}
				}
				if (i % 40 == 0) {
					switch (rand.nextInt(20)) {
					case 1:
						lstItems.add(new Item(ItemType.SEAWEED1, i, iBoundHeight - 125));
						break;
					case 2:
						lstItems.add(new Item(ItemType.SEAWEED2, i, iBoundHeight - 125));
						break;
					case 3:
						lstItems.add(new Item(ItemType.SEAWEED3, i, iBoundHeight - 75));
						break;
					default:
						break;
					}
					switch (rand.nextInt(20)) {
					case 1:
						lstItems.add(new Item(ItemType.ROCK1, i, iBoundHeight - 75));
						break;
					case 2:
						lstItems.add(new Item(ItemType.ROCK2, i, iBoundHeight - 75));
						break;
					case 3:
						lstItems.add(new Item(ItemType.ROCK3, i, iBoundHeight - 75));
						break;
					default:
						break;
					}
				}
			}
		}
	}

	private void cleanupGameState(boolean bViewForceCont) {
		if (bViewForceCont) {
			bModelForceCont = false;
			bInitFlag = true;
	
			switch (eGameState) {
			case STARTSCREEN:
				if (bPlayerLost) {
					System.exit(0); 
				} else {
					eGameState = GameState.CUTSCENE1;
				}
				break;
			case CUTSCENE1:
				eGameState = GameState.TUTORIAL1;
				break;
			case TUTORIAL1:
				eGameState = GameState.GAME1;
				break;
			case GAME1:
				if (bPlayerLost) {
					eLastGameState = eGameState; 
					eGameState = GameState.GAMEOVER;
				} else {
					eGameState = GameState.CUTSCENE2;
				}
				lstItems.clear();
				break;
			case CUTSCENE2:
				eGameState = GameState.GAME2;
				break;
			case GAME2:
				eGameState = GameState.CUTSCENE3;
				break;
			case CUTSCENE3:
				eGameState = GameState.TUTORIAL3;
				break;
			case TUTORIAL3:
				eGameState = GameState.GAME3;
				lstItems.clear();
				tutorial3State = 5;
				break;
			case GAME3:
				if (bPlayerLost) {
					eLastGameState = eGameState; 
					eGameState = GameState.GAMEOVER;
				} else {
					eGameState = GameState.CUTSCENE4;
				}
				break;
			case CUTSCENE4:
				eGameState = GameState.ENDSCREEN;
				break;
			case ENDSCREEN:
				eGameState = GameState.STARTSCREEN;
				break;
			case GAMEOVER:
				if (bPlayerLost) {
					eGameState = GameState.STARTSCREEN;
					bPlayerLost = false; 
				} else {
					eGameState = GameState.values()[eLastGameState.ordinal() - 1];  
					shark.setiHealth(shark.getiMaxHealth());
				}
				break;
				default: 
					break;
			}
		}
	}

	private void executeCutscene(boolean bViewForceCont, KeyCode kcKeyPress) {
		if (bViewForceCont || kcKeyPress == KeyCode.ENTER) {
			bModelForceCont = true;
		}
	}

	/**
	 * @param kcKeyPress Have they pressed Enter yet?
	 */
	private void executeStartscreen(KeyCode kcKeyPress) {
		// if Enter, start game; if Backspace, end game
		if (kcKeyPress == KeyCode.ENTER) {
			bModelForceCont = true;
		} else if (kcKeyPress == KeyCode.BACK_SPACE) {
			bModelForceCont = true; 
			bPlayerLost = true; 
		}

		// occasionally spawn bubbles and move them across screen 
		if(rand.nextInt(60)==0){
			lstItems.add(new Item(ItemType.BUBBLE, rand.nextInt(iBoundWidth), iBoundHeight));
		}
		for(Item i : lstItems){
			i.move(false);
		}
	}

	/**
	 * @param bViewForceCont Force skip cutscene?
	 * @param  kcKeyPress Did they press enter yet?
	 */
	private void executeTutorial1(KeyCode kcKeyPress, KeyCode kcKeyRelease) {
		
		// skip tutorial if player presses Enter
		if(kcKeyPress == KeyCode.ENTER) {
			bModelForceCont = true;
		}
		
		boolean fish = false; 
		boolean trash = false; 
		
		// very beginning of tutorial; waiting for player to move keys for first time
		if (iGameProgress == 0) {
			if(kcKeyPress == KeyCode.LEFT || kcKeyPress == KeyCode.UP || kcKeyPress == KeyCode.RIGHT || kcKeyPress == KeyCode.DOWN) {
				iGameProgress ++; 
			}
			
		// middle of tutorial; player moving around and checking for collision with items
		} else if (iGameProgress == 1) {
			
			// for each item, if it hasn't yet been eaten, set the location
			// or, if it has been eaten, set the flag to true
			for(Item i : lstItems) {
				if(i.geteItemType() == ItemType.FISH) {
					i.setiLocX(iBoundWidth-(iBoundWidth/4));
					i.setiLocY(iBoundHeight-(iBoundHeight/4));
					fish = true;
				} else if(i.geteItemType() == ItemType.PLASTIC) {
					i.setiLocX(iBoundWidth-(iBoundWidth/4));
					i.setiLocY(iBoundHeight/4);
					trash = true;
				}
			}
			
			// move on if both items have been eaten 
			if(!trash && !fish) {
				iGameProgress = 3; 
			}
			
			// allow user to move shark and check for collisions
			shark.move(iBoundWidth, iBoundHeight);
			lstItems = checkCollision(lstItems, shark);
			
		// after a short delay, move on to next gamestate
		} else if (iGameProgress >= 3) {
			if (iGameProgress < 28) {
				iGameProgress ++; 
			} else {
				iGameProgress = 0; 
				bModelForceCont = true; 
			}
		}

	}

	/**
	 * Game 1 and all of its logic
	 * <p>
	 * Starts with a startsign that gives the player instruction
	 * Controls spawning of algae, decorations, enemies, and food
	 * Ends with an end sign
	 * @param kcKeyPress Which key pressed
	 * @param kcKeyRelease Which key released
	 */
	private void executeGame1(KeyCode kcKeyPress, KeyCode kcKeyRelease) {
		
		// initialize game 1
		if (iGameProgress <= 6) {
			
			initializeGame1(kcKeyPress, kcKeyRelease);
			
		} else if (iGameProgress < iGameLength) {
			// execute game 1
			updateAlgaeLevel(); 
			
			// spawn trash, fish, and decorations
			if (iGameProgress <= iGameLength - 100) {
				spawnFish();
				spawnTrash();
				spawnAlgae(); 
				spawnSeaweed();
				spawnRocks(); 
				spawnBoats(); 
			}

			// spawn sign at end of game
			if (iGameProgress == iGameLength - 50) {
				addItem(ItemType.ENDSIGN, iBoundWidth, iBoundHeight - 200);
			}
			
			// update item positions
			updateItemPosAndMove();
			
			// update shark and game attributes
			updateSharkAndGame();
			
		} else {
			// execute end of game1
			endGame1();

		}

	}
	
	/**
	 * Updates position of all items
	 */
	/**
	 * Sharky Quiz Show
	 * @param kcKeyPress Can choose most abundant trash based on either keypress or click
	 */
	private void executeGame2(KeyCode kcKeyPress) {
		if (iGameProgress > 0) {
			if(!bFeedbackAvailable) {
				if(!bFeedbackAvailable) {
					iGameProgress--;
				}
				switch (kcKeyPress) {
				case LEFT:
					eGuess = ItemType.PLASTIC;
					break;
				case UP:
					eGuess = ItemType.METAL;
					break;
				case RIGHT:
					eGuess = ItemType.GLASS;
					break;
				default:
					break;
				}
				if (eGuess != null) {
					if (eGuess.equals(eMostAbundantTrash)) {
						System.out.println("correct");
						shark.setiHealth(shark.getiHealth() + 20);
						bGame2Correct = true;
						feedbackIndex = 0;
					} else {
						System.out.println("incorrect; the answer is " + eMostAbundantTrash);
						bGame2Correct = false;
						feedbackIndex = 0;
					}
					bFeedbackAvailable = true;
				}
			} else {
				if(feedbackIndex++ > 50) {
					bModelForceCont = true;
				}
			}
		} else {
			System.out.println("time up");
			System.out.println("the correct answer is " + eMostAbundantTrash);
			bModelForceCont = true;
			iGameProgress = iGameLength; 
		}
	}

	private void executeTutorial3(double dMouseX, double dMouseY, boolean click, KeyCode kcKeyPress) {
		if(!tutorialDone) {
			iGameProgress++;
			if(iGameProgress >= 50) {
				iGameProgress = 0;
			}
			
			if(kcKeyPress == KeyCode.ENTER) {
				bModelForceCont = true;
			}
			
			switch(tutorial3State) {
			case 1:
				if(click) {
					if(dMouseY > iBoundHeight - ItemType.NETTYPES.getiHeight()-27 && dMouseY < iBoundHeight){
						if(dMouseX < ItemType.NETTYPES.getiWidth() && dMouseX > ItemType.NETTYPES.getiWidth()/2){
							bRemovingTrash = false;
							netSquare.setiLocX(netSquare.getiWidth());
							netSquare.setiLocY(iBoundHeight-netSquare.getiHeight()-27);
							tutorial3State++;
						}
					}
				}
				break;
			case 2:
				boolean hasAlgae = false;
				for(Item i : lstItems) {
					if(i.geteItemType() == ItemType.ALGAE) {
						hasAlgae = true;
					}
				}
				if(!hasAlgae) {
					tutorial3State++;
				}
				break;
			case 3:
				if(click) {
					if(dMouseY > iBoundHeight - ItemType.NETTYPES.getiHeight()-27 && dMouseY < iBoundHeight){
						//If clicking on left half of box
						if(dMouseX < ItemType.NETTYPES.getiWidth()/2 && dMouseX > 0){
							bRemovingTrash = true;
							netSquare.setiLocX(0);
							netSquare.setiLocY(iBoundHeight-netSquare.getiHeight()-27);
							tutorial3State++;
						}
					}
				}
				break;
			case 4:
				boolean hasTrash = false;
				for(Item i : lstItems) {
					if(i.geteItemType() == ItemType.PLASTIC || i.geteItemType() == ItemType.METAL || i.geteItemType() == ItemType.GLASS) {
						hasTrash = true;
					}
				}
				if(!hasTrash) {
					tutorial3State++;
				}
				break;
			case 5:
				tutorialDone = true;
				break;
			default:
				break;
			}
			if(kcKeyPress == KeyCode.ENTER) {
				bModelForceCont = true;
			}
			
			Iterator<Item> iter = lstItems.iterator();
			Item tmpItem;
			boolean canRemove = false;
			while(iter.hasNext()) {
				tmpItem = iter.next();
				canRemove = false;
				
				if((tmpItem.getiLocX() + tmpItem.getiWidth()) < 0) {
					canRemove = true; // checking if off the screen
				}
				
				else {
					// item is not off the screen
					if(!tmpItem.getbIsDecoration()) {
						
						if(click && !bMouseClickLock) {
							// checking that clicking to remove object
							if( dMouseX >= tmpItem.getiLocX() && dMouseX <= (tmpItem.getiLocX() + tmpItem.getiWidth()) &&
									dMouseY >= tmpItem.getiLocY() && dMouseY <= (tmpItem.getiLocY() + tmpItem.getiHeight()) ) {
								if (bRemovingTrash == false && tmpItem.geteItemType().equals(ItemType.ALGAE) ) {
									canRemove = true; // remove the item if not harmful
								}
								else if (bRemovingTrash == true && tmpItem.geteItemType().getbIsTrash()) {
									canRemove = true; // remove the item if not harmful
								}
								bMouseClickLock = true;
							}
						}
						
						// check collision with shark
						if(tmpItem.getiLocX() < shark.getiLocX()+shark.getiWidth() && tmpItem.getiLocX()+tmpItem.getiWidth() > shark.getiLocX()) {
							if(tmpItem.getiLocY() < shark.getiLocY()+shark.getiHeight() && tmpItem.getiLocY()+tmpItem.getiHeight() > shark.getiLocY()) {
								if(!tmpItem.geteItemType().equals(ItemType.BOAT)) {
									System.out.println("Collision with " + tmpItem);
									shark.setiHealth(shark.getiHealth() + tmpItem.getiHealthChange());
									if(tmpItem.getiHealthChange() < 0) {
										shark.startFlashing(false);
									}
									canRemove = true;
								}
							}
						}
					} else if (tmpItem.geteItemType().equals(ItemType.ALGAE)) {
						if (!bRemovingTrash) {
							if(click) {
								// scrub algae
								if((int)dMouseX > tmpItem.getiLocX() && (int)dMouseX < tmpItem.getiLocX()+tmpItem.getiWidth()) {
									if((int)dMouseY > 0 && (int)dMouseY < tmpItem.getiHeight()) {
										iAlgaeCollected++;
										setiAlgaeLevel(iAlgaeCollected);
										canRemove = true;
										bMouseClickLock = true;
									}
								}
							}
						}
					}
				}

				if(canRemove) {
					iter.remove(); // remove item if necessary
				}
			}
			if(bMouseClickLock) {
				if(!click) {
					bMouseClickLock = false;
				}
			}
		} else {
			iGameProgress++;
			if(iGameProgress >= 25) {
				bModelForceCont = true;
			}
		}
	}
	
	/**
	 * Reverse of Game 1, now clicking trash and auto-Sharky
	 * <p>
	 * As algae increases, less food
	 * Remove algae in one mode
	 * Remove trash in another
	 * @param dMouseX Mouse location X
	 * @param dMouseY Mouse location Y
	 * @param kcKeyPress Key pressed
	 */
	private void executeGame3(double dMouseX, double dMouseY, boolean click, KeyCode kcKeyPress) {
		/*
		 * need to flip the dMouseX if flip all images
		 * 
		 */
		dMouseX = iBoundWidth - dMouseX; // only flip the x-axis
		
		if (bStartingFlag) {
			iMaxAlgae = 15; 
			bRemovingTrash = true;
			iAlgaeCollected = 0;
			setiAlgaeLevel(iAlgaeCollected);
			iGameProgress = 0;
			iGameLength = getiGameLength(45); // want the game to be x seconds long
			lstItems = getBaseItems(); // add a few items to the screen
			lstItems.addAll(getInitialAlgae(iAlgaeLevel)); // adding
			lstItems.add(new Item(ItemType.NETTYPES, iBoundWidth - ItemType.NETTYPES.getiWidth(), iBoundHeight - ItemType.NETTYPES.getiHeight()));
			netSquare = new Item(ItemType.NETSELECT, iBoundWidth - ItemType.NETTYPES.getiWidth(), iBoundHeight - ItemType.NETTYPES.getiHeight());
			lstItems.add(netSquare);
			shark.setSinusoidalMovement(iBoundHeight/4, iBoundHeight/2); // set the sinusoidal movement
			bStartingFlag = false; 
		}
		
		if(click && !bMouseClickLock) {
			//If clicking in the Y area of the box
			if(dMouseY > iBoundHeight - ItemType.NETTYPES.getiHeight()-27 && dMouseY < iBoundHeight){
				//If clicking on left half of box
				if(dMouseX < iBoundWidth && dMouseX > iBoundWidth - ItemType.NETTYPES.getiWidth()/2){
					bRemovingTrash = false;
					bMouseClickLock = true;
				}
				//If clicking on right half of box
				else if(dMouseX < iBoundWidth - ItemType.NETTYPES.getiWidth()/2 && dMouseX > iBoundWidth - ItemType.NETTYPES.getiWidth()){
					bRemovingTrash = true;
					bMouseClickLock = true;
				}
			}
		}

		if(bRemovingTrash == true){
			netSquare.setiLocX(iBoundWidth - ItemType.NETTYPES.getiWidth());
			netSquare.setiLocY(iBoundHeight - ItemType.NETTYPES.getiHeight()-27); 
		}
		else{
			netSquare.setiLocX(iBoundWidth - ItemType.NETTYPES.getiWidth() + ItemType.NETSELECT.getiWidth());
			netSquare.setiLocY(iBoundHeight - ItemType.NETTYPES.getiHeight()-27); 
			
		}
		
		
		iGameProgress++; // keeping track of the game's duration
		
		if (iGameProgress % (35) == 0) {
			shark.setiHealth(shark.getiHealth() - 2);
		}
		
		setiAlgaeLevel(iAlgaeCollected); // iAlageLevel is dependent on the trash collected 
		if(iAlgaeCollected > 0 && iGameProgress%20 == 0) {
			iAlgaeCollected--;
		}
		
//		if (iGameProgress >= iGameLength || shark.getiHealth() <= 0) {
//			System.out.println(eGameState + " is over");
//			bModelForceCont = true; // go to the next game state
//			return; // exit the function
//		}
		
		if (iGameProgress >= iGameLength && shark.getiHealth() > 0) {
			System.out.println("you won game3"); 
			bModelForceCont = true;
			bPlayerLost = false; 
		} else if (shark.getiHealth() <= 0 ){
			System.out.println("you lost game3"); 
			bModelForceCont = true;
			bPlayerLost = true;
		}
		
		Iterator<Item> iter = lstItems.iterator();
		Item tmpItem;
		boolean canRemove = false;
		while(iter.hasNext()) {
			tmpItem = iter.next();
			canRemove = false;
			
			if((tmpItem.getiLocX() + tmpItem.getiWidth()) < 0) {
				canRemove = true; // checking if off the screen
			}
			
			else {
				// item is not off the screen
				if(!tmpItem.getbIsDecoration()) {
					
					if(click && !bMouseClickLock) {
						// checking that clicking to remove object
						if( dMouseX >= tmpItem.getiLocX() && dMouseX <= (tmpItem.getiLocX() + tmpItem.getiWidth()) &&
								dMouseY >= tmpItem.getiLocY() && dMouseY <= (tmpItem.getiLocY() + tmpItem.getiHeight()) ) {
							if (bRemovingTrash == false && tmpItem.geteItemType().equals(ItemType.ALGAE) ) {
								canRemove = true; // remove the item if not harmful
							}
							else if (bRemovingTrash == true && tmpItem.geteItemType().getbIsTrash()) {
								canRemove = true; // remove the item if not harmful
							}
							bMouseClickLock = true;
						}
					}
					
					// check collision with shark
					if(tmpItem.getiLocX() < shark.getiLocX()+shark.getiWidth() && tmpItem.getiLocX()+tmpItem.getiWidth() > shark.getiLocX()) {
						if(tmpItem.getiLocY() < shark.getiLocY()+shark.getiHeight() && tmpItem.getiLocY()+tmpItem.getiHeight() > shark.getiLocY()) {
							if(!tmpItem.geteItemType().equals(ItemType.BOAT)) {
								System.out.println("Collision with " + tmpItem);
								shark.setiHealth(shark.getiHealth() + tmpItem.getiHealthChange());
								if(tmpItem.getiHealthChange() < 0) {
									shark.startFlashing(false);
								}
								canRemove = true;
							}
						}
					}
				}
				else if (tmpItem.geteItemType().equals(ItemType.ALGAE)) {
					if (!bRemovingTrash) {
						if(click) {
							// scrub algae
							if( dMouseX >= tmpItem.getiLocX()-tmpItem.getiWidth() && dMouseX <= (tmpItem.getiLocX()) &&
									dMouseY >= tmpItem.getiLocY() && dMouseY <= (tmpItem.getiLocY() + tmpItem.getiHeight()) ) {
								iAlgaeCollected++;
								setiAlgaeLevel(iAlgaeCollected);
								canRemove = true;
								bMouseClickLock = true;
							}
						}
					}
				}
			}
			if(tmpItem.geteItemType() == ItemType.NETTYPES){
				//Checkpoint1
				tmpItem.setiLocX(iBoundWidth - ItemType.NETTYPES.getiWidth());
				tmpItem.setiLocY(iBoundHeight - ItemType.NETTYPES.getiHeight() - 27);
			}

			tmpItem.move(true); // update the items location
			if(canRemove) {
				iter.remove(); // remove item if necessary
			}
		}
		
		if(bMouseClickLock) {
			if(!click) {
				bMouseClickLock = false;
			}
		}
		
		if(iGameProgress % 25 == 0) {
			// lstItems.add(generateRandomItem());
			lstItems.addAll(generateRandomAlgaeOrFood(iAlgaeLevel));
		}

		if(iGameProgress % 75 == 0) {
			ItemType trashType = generateRandomTrashType();
			lstItems.add(new Item(trashType, iBoundWidth, rand.nextInt(iBoundHeight-80)+ trashType.getiHeight()));
		}
		
	//	spawnDecoration(false);
		
		shark.moveGame3(iGameProgress, iBoundHeight); // update the shark's location
		
	}
	
	/**
	 * @param kcKeyPress Left to restart at checkpoint, Right to Main menu
	 */
	private void executeGameOver(KeyCode kcKeyPress) {
		if (kcKeyPress == KeyCode.LEFT) {
			bPlayerLost = false;
			bModelForceCont = true;
		} else if (kcKeyPress == KeyCode.RIGHT) {
			bModelForceCont = true;
		}
	}
	
	/**
	 * @param kcKeyPress Left to Restart, Right to Exit
	 */
	private void executeEndscreen(KeyCode kcKeyPress) {
		if (kcKeyPress == KeyCode.LEFT) {
			bPlayerLost = false;
			bModelForceCont = true;
		} else if (kcKeyPress == KeyCode.RIGHT) {
			System.exit(0);
		}
	}

	/**
	 * Checks if shark has collided with any of the game items
	 * @param lstItems List of all items.
	 * @param shark Sharky. Includes location importantly
	 * @return Updated list of items
	 */
	private ArrayList<Item> checkCollision(ArrayList<Item> lstItems, Shark shark) {

		Iterator<Item> it = lstItems.iterator();
		Item tmpItem;
		boolean isInOil = false;
		while (it.hasNext()) {
			tmpItem = it.next();
			if (tmpItem.getiLocX() + tmpItem.getiWidth() <= 0) {
				it.remove();
			}

			if (!tmpItem.getbIsDecoration()) {
				if (tmpItem.getiLocX() < shark.getiLocX() + shark.getiWidth()
						&& tmpItem.getiLocX() + tmpItem.getiWidth() > shark.getiLocX()) {
					if (tmpItem.getiLocY() < (shark.getiLocY()-shark.getRotInd()/2) + shark.getiHeight()+shark.getRotInd()
							&& tmpItem.getiLocY() + tmpItem.getiHeight() > shark.getiLocY()-(shark.getRotInd()/2)) {
						if (tmpItem.geteItemType() != ItemType.BOAT) {
							it.remove();
						}
						
						if(tmpItem.geteItemType() == ItemType.BOAT) {
							isInOil = true;
						}
						
						
						if(!bInOilLock) {
							if (tmpItem.getiHealthChange() < 0) {
								shark.startFlashing(isInOil);
								if(isInOil) {
									bInOilLock = true;
								}
							}
						}
						
						shark.setiHealth(shark.getiHealth() + tmpItem.getiHealthChange());
					}
				}
			}
		}
		
		if(!isInOil && bInOilLock) {
			bInOilLock = false;
			shark.startFlashing(false);
		}

		return lstItems;
	}

	/**
	 * Transitions between game states
	 * <p>
	 * Depends on game state and whether the playerh as lost
	 * @param bViewForceCont Whether to cleanup
	 */
	/**
	 * @return List of items to 
	 */
	private ArrayList<Item> getBaseItems() {
		ArrayList<Item> tmpItems = new ArrayList<>();
		tmpItems.add(new Item(ItemType.ALGAE, 10, 10));
		tmpItems.add(generateRandomItem());
		tmpItems.add(generateRandomItem());
		tmpItems.add(generateRandomItem());
		tmpItems.add(generateRandomItem());
		return tmpItems;
	}

	private void endGame1() {
		
		// if shark is off screen, go to next cutscene
		if (shark.getiLocX() >= iBoundWidth) {
			bModelForceCont = true;
			iGameProgress = 0;
		} else {
			// refuse player input and move shark off screen
			bGameRunning = false;
			shark.setiIncrX(15);
			shark.setMoveBools(false, false, false, true);
			shark.move(false);
			
			// not needed, since background stops scrolling
			/*for (Item i : lstItems) {
				if (i.getbIsDecoration() || i.geteItemType() == ItemType.BOAT) {
					i.updateiLocY(iBoundHeight);
				} else {
					i.move(false);
				}
			}*/
		}
	}
	
	private ItemType generateRandomTrashType() {
		int r = rand.nextInt(3);
		ItemType itemTypeToAdd = ItemType.PLASTIC;
		if (r == 1) {
			itemTypeToAdd = ItemType.GLASS;
		}
		else if (r == 2) {
			itemTypeToAdd = ItemType.METAL;
		}
		return itemTypeToAdd;
	}
	
	/**
	 * Governs what random item is spawned each tick
	 * @return Item to spawn
	 */
	private Item generateRandomItem() {
		int r = rand.nextInt(5);
		ItemType itemTypeToAdd = ItemType.FISH; // default to add a fish
		if (r == 1) {
			itemTypeToAdd = ItemType.RAY;
		}
		else if (r > 1){
			itemTypeToAdd = generateRandomTrashType();
		}
		return new Item(itemTypeToAdd, iBoundWidth, rand.nextInt(iBoundHeight-80)+itemTypeToAdd.getiHeight());
	}	
	
	/**
	 * Converts seconds to game ticks
	 * @param desiredTimeInSeconds Length of game in seconds
	 * @return length of game in ticks
	 */
	private int getiGameLength(int desiredTimeInSeconds) {
		/* normalizing iGameLength to be in relative terms of seconds
		 * desiredTime / x ticks = sleepTime / 1 tick
		 * just a proportion
		 */
		return (int) desiredTimeInSeconds*1000/25; // the number of ticks depdendent on how long the controller sleeps
	}

	/**
	 * Updates algae level and collected algae
	 * @param algaeCollected How much algae has been collected. Caps at Max Algae
	 */
	private void setiAlgaeLevel(int algaeCollected) {
		if (algaeCollected > iMaxAlgae) {
			algaeCollected = iMaxAlgae;
		}
		// iAlgaeLevel = iMaxAlgae*(iMax - algaeCollected)/maxTrash;
		iAlgaeLevel = iMaxAlgae - algaeCollected;
	}

	/**
	 * @param algaeLevel Pretty much ignored
	 * @return Populates an item list with algae 
	 */
	private ArrayList<Item> getInitialAlgae(int algaeLevel) {
		ArrayList<Item> tmpItems = new ArrayList<>();
		for (int i = 0; i < 25; i++) {
			tmpItems.add(new Item(ItemType.ALGAE, iBoundWidth + rand.nextInt(iBoundWidth), -10));
		}
		return tmpItems;
	}
	
	private void updateAlgaeLevel() {
		iAlgaeLevel = (75 * iGameProgress) / (iGameLength);
	}

	private void addItem(ItemType t, int x, int y) {
		lstItems.add(new Item(t, x, y));
	}

	private void spawnFish() {
		
		int r = rand.nextInt(1200);
		
		// as iAlgaeLevel approaches iMaxAlgae, decrease the chance of spawning a fish
		if (r <= (iMaxAlgae - iAlgaeLevel)) {
			
			// there is a 1/5 chance that a spawned fish will be a ray
			if (r % 5 == 0) {
				addItem(ItemType.RAY, iBoundWidth + rand.nextInt(iBoundWidth),
						rand.nextInt(iBoundHeight - 140) + 40);
			} else {
				addItem(ItemType.FISH, iBoundWidth + rand.nextInt(iBoundWidth),
						rand.nextInt(iBoundHeight - 140) + 40);
			}
		}
	
		
	}
	
	private void spawnTrash() {
		
		int r = rand.nextInt(75);
		
		// the chance of spawning trash is constant, 1 out of a constant number
		if (r == 1) {
			
			// there is a 3/5 chance that the trash type will be the same type as the most abundant type (used for game2) 
			// there is a 2/y chance that the trash type will be either of the others 
			switch (rand.nextInt(5)) {
			case 1:
				addItem(eNormalTrash1, iBoundWidth + rand.nextInt(iBoundWidth),
						rand.nextInt(iBoundHeight - 140) + 40);
				break;
			case 2:
				addItem(eNormalTrash2, iBoundWidth + rand.nextInt(iBoundWidth),
						rand.nextInt(iBoundHeight - 140) + 40);
				break;
			default:
				addItem(eMostAbundantTrash, iBoundWidth + rand.nextInt(iBoundWidth),
						rand.nextInt(iBoundHeight - 140) + 40);
				break;
			}
		}
	}

	private void initializeGame1(KeyCode kcKeyPress, KeyCode kcKeyRelease) {

		// move shark onto screen and create clam
		if (iGameProgress <= 0) { 
			
			if (shark.getiLocX() < 50) {
				shark.move(false);
			} else {
				clamspeech = new ClamSpeech(50, iBoundHeight);
				lstItems.add(clamspeech);
				shark.setMoveBools(false, false, false, false);
				iGameProgress ++;
			}		
			
		// move clam onto screen
		} else if (iGameProgress == 1) { 
			
			if (clamspeech.getiLocY() > (iBoundHeight) / 2) {
				clamspeech.transitionUp(); 
			} else {
				iGameProgress ++;
			}
			
		// clam speaks
		} else if (iGameProgress <= (1 + clamspeech.getiNumInstructions())) { 			
			
			iGameProgress = clamspeech.speak(kcKeyPress, iGameProgress);
		
		// move clams off of screen
		} else if (iGameProgress == (2 + clamspeech.getiNumInstructions())) {	
			
			if (clamspeech.getiLocY() < iBoundHeight) {
				clamspeech.transitionDown(); 
			} else {
				iGameProgress ++;
			}
			
		// get ready to start game
		} else if (iGameProgress == (3 + clamspeech.getiNumInstructions())) {
			
			lstItems.remove(clamspeech); 
			shark.setiIncrX(7);
			bGameRunning = true; 
			addItem(ItemType.STARTSIGN, iBoundWidth, iBoundHeight-200);
			iGameProgress ++; 
			
		}
		
	}
	
	private void updateSharkAndGame() {
		
		// move shark and check collision with items
		shark.move(iBoundWidth, iBoundHeight);
		lstItems = checkCollision(lstItems, shark);
		
		iGameProgress++;
	
		// slowly decrement shark health
		if (iGameProgress % 25 == 0) {
			shark.setiHealth(shark.getiHealth() - 2);
		}
	
		// if health is zero, end game
		if (shark.getiHealth() <= 0) {
			iGameProgress = iGameLength;
			bPlayerLost = true;
			bModelForceCont = true;
		}
	}

	private void updateItemPosAndMove() {
		
		// update item positions
		for (Item i : lstItems) {
			if (i.getbIsDecoration()) {
				i.updateiLocY(iBoundHeight);
			}
			i.move(false);
		}
	}

	private ArrayList<Item> generateRandomAlgaeOrFood(int algaeLevel) {
		System.out.println("Algae level game 3: " + iAlgaeLevel);
		ArrayList<Item> tmpItems = new ArrayList<>();
		int r = rand.nextInt(iMaxAlgae);
		if (r < algaeLevel) {
			tmpItems.add(new Item(ItemType.ALGAE, iBoundWidth + rand.nextInt(iBoundWidth), -10));
		}
		if (r > (algaeLevel - (iMaxAlgae / 5))) {
			if (r % 2 == 0) {
				tmpItems.add(new Item(ItemType.FISH, iBoundWidth, rand.nextInt(iBoundHeight-80) + ItemType.FISH.getiHeight()));
			}
			else {
				tmpItems.add(new Item(ItemType.RAY, iBoundWidth, rand.nextInt(iBoundHeight-80)+ ItemType.RAY.getiHeight()));
			}
		}
		return tmpItems;
	}
	
	private void spawnAlgae() {
		// upper bound of random number is proportional to iMaxAlgae
		int x = 1000 - (iAlgaeLevel * (1000 / iMaxAlgae));
		if (x < 0) {
			x = 0;
		}
		
		// generate random number
		int r = rand.nextInt(x);
		
		// spawn algae according to random number
		if (r <= iAlgaeLevel) {
			addItem(ItemType.ALGAE, iBoundWidth + rand.nextInt(iBoundWidth), -10);
		}
	}
	
	private void spawnSeaweed() {
		
		// periodically spawn seaweed
		if (iGameProgress % 30 == 0 && iGameProgress >= 50) {
			
			// 3/5 chance to spawn seaweed, pick a random type of seaweed if spawning
			switch (rand.nextInt(5)) {
			case 1:
				if (eGameState == GameState.GAME1) {
					addItem(ItemType.SEAWEED1, iBoundWidth, iBoundHeight - 125);
				} else {
					addItem(ItemType.SEAWEED1, iBoundWidth + ItemType.SEAWEED1.getiWidth(), iBoundHeight - 125);
				}
				break;
			case 2:
				if (eGameState == GameState.GAME1) {
					addItem(ItemType.SEAWEED2, iBoundWidth, iBoundHeight - 125);
				} else {
					addItem(ItemType.SEAWEED2, iBoundWidth + ItemType.SEAWEED1.getiWidth(), iBoundHeight - 125);
				}				
				break;
			case 3:
				if (eGameState == GameState.GAME1) {
					addItem(ItemType.SEAWEED3, iBoundWidth, iBoundHeight - 125);
				} else {
					addItem(ItemType.SEAWEED3, iBoundWidth + ItemType.SEAWEED1.getiWidth(), iBoundHeight - 125);
				}
				break;
			default:
				break;
			}
		}
	}
	
	private void spawnRocks() {
		
		// periodically spawn rocks
		if(iGameProgress%40 == 0 && iGameProgress >= 50) {
			
			// 3/5 chance to spawn rock, pick random type if spawning
			switch(rand.nextInt(5)) {
			case 1:
				if (eGameState == GameState.GAME1) {
					addItem(ItemType.ROCK1, iBoundWidth, iBoundHeight - 75);
				} else {
					addItem(ItemType.ROCK1, iBoundWidth + ItemType.ROCK1.getiWidth(), iBoundHeight - 75);
				}
				break;
			case 2:
				if (eGameState == GameState.GAME1) {
					addItem(ItemType.ROCK2, iBoundWidth, iBoundHeight - 75);
				} else {
					addItem(ItemType.ROCK2, iBoundWidth + ItemType.ROCK2.getiWidth(), iBoundHeight - 75);
				}
				break;
			case 3:
				if (eGameState == GameState.GAME1) {
					addItem(ItemType.ROCK3, iBoundWidth, iBoundHeight - 75);
				} else {
					addItem(ItemType.ROCK3, iBoundWidth + ItemType.ROCK3.getiWidth(), iBoundHeight - 75);
				}
				break;
			default:
				break;
			}
		}
	}
	
	private void spawnBoats() {
		
		// rarely spawn a boat
		if(iGameProgress % 200 == 0) {
			if(rand.nextInt(7) <= 3) {
				addItem(ItemType.BOAT, iBoundWidth, 0);
			}
		}
	}
	// helper methods

}