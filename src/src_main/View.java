package src_main;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import src_resources.GameState;
import src_resources.Item;
import src_resources.ItemType;
import src_resources.Shark;

public class View {
	
	private boolean bInitFlag = true;
	private boolean bViewForceCont= false; 	
	private boolean bScrollBackground;
	private Canvas canvas; 
	private Canvas decCanvas;
	private Canvas sharkCanvas;
	private Group root;
	private Scene scene; 
	private GraphicsContext gc; 
	private GraphicsContext dgc;
	private GraphicsContext sgc;
	private int iBoundWidth; 
	private int iBoundHeight;
	private Media media; 
	private MediaPlayer player; 
	private MediaView viewer; 
	private Image imgBackground1; 
	private Image imgBackground2; 
	private Image imgShark; 
	private Image imgFish;
	private Image imgRay;
	private Image imgPlastic;
	private Image imgAlgae;
	private Image imgStartSign, imgEndSign;
	private Image imgRock1, imgRock2, imgRock3;
	private Image imgSeaweed1, imgSeaweed2, imgSeaweed3;
	private Image imgGlass;
	private Image imgMetal;
	private Image imgBoat;
	private Image imgQuestion;
	private Image imgNetTypes;
	private Image imgNetSelect;
	private Image imgYouDied;
	private Image imgTutGood, imgTutBad, imgTutMove;
	private Image imgArrowDown, imgArrowUp;
	private Image imgArrow;
	private Image imgTitle;
	private Image imgPressEnter;
	private Image imgBubble;
	private Image imgLeftClick;
	private Image imgThumbsUp, imgHQ;
	private Image imgYouWin;
	private Image imgClamSpeech;
	private Image imgNet;
	private File fCutscene1;
	private File fCutscene2; 
	private File fCutscene3; 
	private File fCutscene4; 
	private Stage s;
	private int backgroundIndex;
	private Rotate sharkImgRotate;
	private boolean game2Guessed;
	private ItemType game2Guess;
	private Button choice1;
	private Button choice2;
	private Button choice3;
	private boolean game2Correct;
	private boolean game2ModelResponse;
	private Image imgCorrect, imgIncorrect;
	private ItemType game2CorrectAnswer;
	private Media currCutscene;
	private GameState eGameState;
	private double moveOpa = 1.0;
	private int tutorialFlashIdx;
	private boolean showTutorialText;
	private int tutorial3State;
	private double dMouseX, dMouseY;
	private boolean isOnNetSelection;
	
	// constructors 
	public View(Stage stage, int winWidth, int winHeight) {
		s = stage;
		s.setTitle("Estuary Adventure!");
		
		root = new Group(); 
		scene = new Scene (root); 
		s.setScene(scene);
		
		iBoundWidth = winWidth;
		iBoundHeight = winHeight;
		
		canvas = new Canvas(iBoundWidth, iBoundHeight); 
		root.getChildren().add(canvas); 
		gc = canvas.getGraphicsContext2D();
		setupdecCanvas();
		setupsharkCanvas();
		backgroundIndex = 0;
		s.setResizable(true);
		imgBackground1 = createImage("underwater.png"); 
		imgBackground2 = createImage("game2-background.png"); 
		imgShark = createImage("sharkyswim.gif");
		imgFish = createImage("Fish1.gif");
		imgRay = createImage("Ray2.gif");
		imgPlastic = createImage("plastic.png");
		imgAlgae = createImage("algae.png");
		imgStartSign = createImage("thisWayToHQSign.png");
		imgEndSign = createImage("estuaryHQSign.png");
		imgRock1 = createImage("rock1.png");
		imgRock2 = createImage("rock2.png");
		imgRock3 = createImage("rock3.png");
		imgSeaweed1 = createImage("seaweed1.png");
		imgSeaweed2 = createImage("seaweed2.png");
		imgSeaweed3 = createImage("seaweed2.png");
		imgBoat = createImage("boat.png");
		imgGlass = createImage("glass.png");
		imgMetal = createImage("metal.png");
		imgNetTypes = createImage("NetTypes.png");
		imgNetSelect = createImage("NetSelect.png");
		imgYouDied = createImage("GameOver.png");
		imgTitle = createImage("Title.png");
		imgPressEnter = createImage("PressEnter.png");
		imgBubble = createImage("Bubble.png");
		imgTutGood = createImage("tutorialCheck.png");
		imgTutBad = createImage("tutorialCross.png");
		imgTutMove = createImage("tutorialArrowKeys.png");
		imgArrow = createImage("bigRedArrow.png");
		imgLeftClick = createImage("leftClick.png");
		imgArrowDown = createImage("bigRedArrowDown.png");
		imgArrowUp = createImage("bigRedArrowUp.png");
		imgThumbsUp = createImage("thumbsUp.png");
		imgHQ = createImage("building.png");
		imgYouWin = createImage("youwin.png");
		imgClamSpeech = createImage("clamspeech.png"); 
		imgNet = createImage("net.png");
		bScrollBackground = false;
	
		fCutscene1 = new File("src/assets/cutscene1.mp4");
		fCutscene2 = new File("src/assets/cutscene2.mp4");
		fCutscene3 = new File("src/assets/cutscene3.mp4");
		fCutscene4 = new File("src/assets/cutscene4.mp4"); 
	}
	// constructors 
	
	// accessors
	public boolean getbViewForceCont() {
		return bViewForceCont; 
	}
	
	public Scene getScene() {
		return scene; 
	}

	public int getiBoundWidth() {
		return iBoundWidth;
	}

	public int getiBoundHeight() {
		return iBoundHeight;
	}
	
	public boolean getbGuessed() {
		return game2Guessed;
	}
	
	public ItemType getGame2Guess() {
		return game2Guess;
	}

	public void setGame2ModelResponse(boolean r) {
		game2ModelResponse = r;
	}

	public void setGame2Correct(boolean c, ItemType a) {
		game2Correct = c;
		game2CorrectAnswer = a;
	}

	public void setTutorial3State(int s) {
		tutorial3State = s;
	}
	// accessors

	// class methods
	public void update(boolean bModelForceCont, GameState eGameState, 
			ArrayList<Item> lstItems, Shark shark, int iAlgaeLevel, 
			boolean scrollBackground, int iGameProgress, int iGameLength, 
			boolean removingTrash) {
		setupGameState(eGameState, scrollBackground); 
		this.eGameState = eGameState;
		switch (eGameState) {
		case STARTSCREEN:
			displayStartscreen(lstItems, iGameProgress);
			break;
		case CUTSCENE1:
			displayCutscene(bModelForceCont);
			break;
		case CUTSCENE2:
			displayCutscene(bModelForceCont);
			break;
		case CUTSCENE3:
			displayCutscene(bModelForceCont);
			break;
		case CUTSCENE4:
			displayCutscene(bModelForceCont);
			break;
		case TUTORIAL1:
			displayTutorial1(lstItems, shark, iGameProgress);
			break;
		case TUTORIAL3:
			displayTutorial3(lstItems, removingTrash, iGameProgress, iAlgaeLevel);
			break;
		case GAME1:
			displayGame1(lstItems, shark, iAlgaeLevel, iGameProgress);
			break;
		case GAME2:
			displayGame2(iGameLength, iGameProgress, shark);
			break;
		case GAME3:
			displayGame3(lstItems, shark, iAlgaeLevel * 3, removingTrash);
			break;
		case GAMEOVER: 
			displayGameOver(); 
			break;
		case ENDSCREEN:
			displayEndscreen();
			break;
		}
		cleanupGameState(bModelForceCont); 
	}
	// class methods
	
	// helper methods
	// TODO this shouldnt exist? 
	public Image[] getClamImages() {
		Image[] imgs = {imgHQ, imgArrow, imgThumbsUp};
		return imgs;
	}

	private void setupGameState(GameState eGameState, boolean scroll) {
		bScrollBackground = scroll;
		iBoundWidth = (int) s.getWidth();
		iBoundHeight = (int) s.getHeight();
		canvas.setWidth(iBoundWidth);
		canvas.setHeight(iBoundHeight);
		if(decCanvas != null){
			decCanvas.setWidth(iBoundWidth);
			decCanvas.setHeight(iBoundHeight);
		}
	
		
		if(sharkCanvas != null) {
			sharkCanvas.setWidth(iBoundWidth);
			sharkCanvas.setHeight(iBoundHeight);
		}
		
		if (bInitFlag) {
			switch (eGameState) {
	
			case STARTSCREEN:
				setupdecCanvas();
				setupsharkCanvas();
				break;
			case CUTSCENE1: 
				if(decCanvas != null) {
					root.getChildren().remove(decCanvas);
				}
				playMedia(fCutscene1); 
				break; 
			case CUTSCENE2: 
				player.stop();
				if(decCanvas != null) {
					root.getChildren().remove(decCanvas);
					root.getChildren().remove(sharkCanvas);
				}
				playMedia(fCutscene2); 
				break; 
			case CUTSCENE3: 
				if(decCanvas != null) {
					root.getChildren().remove(choice1);
					root.getChildren().remove(choice2);
					root.getChildren().remove(choice3);
					root.getChildren().remove(decCanvas);
				}
				playMedia(fCutscene3); 
				break; 
			case CUTSCENE4: 
				if(decCanvas != null) {
					root.getChildren().remove(decCanvas);
					root.getChildren().remove(sharkCanvas);
				}
				playMedia(fCutscene4); 
				break; 
			case TUTORIAL1:
				setupsharkCanvas();
				setupdecCanvas();
				tutorialFlashIdx = 0;
				showTutorialText = true;
				break;
			case TUTORIAL3:
				setupdecCanvas();
				tutorialFlashIdx = 0;
				showTutorialText = true;
				break;
			case GAME1: 
				setupsharkCanvas();
				setupdecCanvas();
				
				break;
			case GAME2:
				setupdecCanvas();
				game2Guessed = false;
				game2Guess = null;
				imgQuestion = createImage("game2question.png");
				imgCorrect = createImage("correct.png");
				imgIncorrect = createImage("incorrect.png");
				choice1 = new Button("Plastic");
				choice2 = new Button("Metal");
				choice3 = new Button("Glass");
				choice1.setMinWidth(75);
				choice2.setMinWidth(75);
				choice3.setMinWidth(75);
				choice1.setLayoutY(iBoundHeight/2+100);
				choice2.setLayoutY(iBoundHeight/2+100);
				choice3.setLayoutY(iBoundHeight/2+100);
				choice1.setLayoutX(iBoundWidth/4-(choice1.getMinWidth()/2));
				choice2.setLayoutX(iBoundWidth/2-(choice2.getMinWidth()/2));
				choice3.setLayoutX((iBoundWidth-iBoundWidth/4)-(choice3.getMinWidth()/2));
				choice1.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						game2Guess = ItemType.PLASTIC;
						game2Guessed = true;
					}
				});
				choice2.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						game2Guess = ItemType.METAL;
						game2Guessed = true;
					}
				});
				choice3.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						game2Guess = ItemType.GLASS;
						game2Guessed = true;
					}
				});
				
				root.getChildren().add(choice1);
				root.getChildren().add(choice2);
				root.getChildren().add(choice3);
				break;
			case GAME3:
				setMouseCoords(0,0);
				isOnNetSelection = false;
				setupsharkCanvas();
				setupdecCanvas();
				break;
			default:
				break;
			}
			bViewForceCont = false;
			bInitFlag = false;
			if (decCanvas != null) {
				dgc.clearRect(0, 0, iBoundWidth, iBoundHeight);
			}
		}
	}

	private void cleanupGameState(boolean bModelForceCont) {
		if (bModelForceCont) {
			bViewForceCont = true;
			bInitFlag = true;
			if (sgc != null) {
				sgc.clearRect(0, 0, iBoundWidth, iBoundHeight);
			}
			if(dgc != null) {
				dgc.clearRect(0, 0, iBoundWidth, iBoundHeight);
			}
		}
	}

	private void displayCutscene(boolean bModelForceCont) {
		gc.clearRect(0, 0, iBoundHeight, iBoundHeight);
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, iBoundWidth, iBoundHeight);
		
		int playerHeight = iBoundHeight;
		int playerWidth = (playerHeight*currCutscene.getWidth())/((currCutscene.getHeight()==0)?1:currCutscene.getHeight());
		int playerY = 0;
		int playerX = iBoundWidth/2-playerWidth/2;
		
		if(iBoundWidth < playerWidth) {
			playerWidth = iBoundWidth;
			playerHeight = (playerWidth*currCutscene.getHeight())/((currCutscene.getWidth()==0)?1:currCutscene.getWidth());
			playerX = 0;
			playerY = iBoundHeight/2-playerHeight/2;
		}
		
		viewer.setX(playerX);
		viewer.setY(playerY);
		viewer.setFitWidth(playerWidth);
		viewer.setFitHeight(playerHeight);
		
		if (bModelForceCont || player.getStatus() == MediaPlayer.Status.DISPOSED) {
			gc.clearRect(0, 0, iBoundWidth, iBoundHeight);
			gc.drawImage(imgBackground1, 0,  0, iBoundWidth, iBoundHeight);
			player.dispose(); 
			root.getChildren().remove(viewer); 
			bViewForceCont = true; 
			bInitFlag = true;
		}
	}

	private void displayStartscreen(ArrayList<Item> lstItems, int iGameProgress) {
		
		gc.clearRect(0, 0, iBoundWidth, iBoundHeight);
		dgc.clearRect(0, 0, iBoundWidth, iBoundHeight);
		gc.drawImage(imgBackground1, 0,0,iBoundWidth,iBoundHeight);
		drawAllItems(lstItems, new Shark(ItemType.SHARK, -100, -100), iGameProgress); 
		int scaler = 250;
		int width = (int)((double)scaler*2.25);
		int height = scaler;
		dgc.drawImage(imgTitle, iBoundWidth/2-width/2, iBoundHeight/2-height, width, height);
		dgc.drawImage(imgPressEnter, iBoundWidth/2-330/1.3/2, iBoundHeight*3/4-96/1.3, 330/1.3, 96/1.3);
	}
	
	private void displayTutorial1(ArrayList<Item> lstItems, Shark shark, int iGameProgress) {
		sgc.clearRect(0, 0, iBoundWidth, iBoundHeight);
		drawBackgroundCrop(imgBackground1);
		drawAllItems(lstItems, shark, iGameProgress);
		if(!shark.getFlashState()) {
		drawProgressBar(25, 25, shark.getiMaxHealth(), shark.getiHealth(), Color.GREEN, Color.RED, "Health"); 
		}
	}
	
	private void displayGame1(ArrayList<Item> lstItems, Shark shark, int iAlgaeLevel, int iGameProgress) {	
		drawBackgroundScroll(imgBackground1);
		drawAllItems(lstItems, shark, iGameProgress); 
		drawAlgaeShadow(iAlgaeLevel); 
		if(!shark.getFlashState()) {
			drawProgressBar(25, 25, shark.getiMaxHealth(), shark.getiHealth(), Color.GREEN, Color.RED, "Health"); 
		}
	}
	
	private void displayGame2(int iGameLength, int iGameProgress, Shark shark) {
		drawBackgroundCrop(imgBackground2); 
		
		choice1.setLayoutY(iBoundHeight/2+100);
		choice2.setLayoutY(iBoundHeight/2+100);
		choice3.setLayoutY(iBoundHeight/2+100);
		choice1.setLayoutX(iBoundWidth/4-(choice1.getMinWidth()/2));
		choice2.setLayoutX(iBoundWidth/2-(choice2.getMinWidth()/2));
		choice3.setLayoutX((iBoundWidth-iBoundWidth/4)-(choice3.getMinWidth()/2));
		
		drawProgressBar(25, 75, iGameLength, iGameProgress, Color.BLUE, Color.WHITE, "Time"); 
		drawProgressBar(25, 25, shark.getiMaxHealth(), shark.getiHealth(), Color.GREEN, Color.RED, "Health");

		
		int questionWidth = iBoundWidth/3;
		int questionHeight = (int)Math.round((questionWidth*imgQuestion.getHeight())/imgQuestion.getWidth());
		
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);
		gc.setGlobalAlpha(0.5);
		gc.fillRoundRect((iBoundWidth/2)-(questionWidth/2)-10,(iBoundHeight/2)-(questionHeight)-10, questionWidth+20, questionHeight+20, 10,10);
		gc.setGlobalAlpha(1);
		gc.strokeRoundRect((iBoundWidth/2)-(questionWidth/2)-10,(iBoundHeight/2)-(questionHeight)-10, questionWidth+20, questionHeight+20, 10,10);
		
		if(game2ModelResponse) {
			if(game2Correct) {
				int correctHeight = (int)Math.round((questionWidth*imgCorrect.getHeight())/imgCorrect.getWidth());
				gc.drawImage(imgCorrect, (iBoundWidth/2)-(questionWidth/2), (iBoundHeight/2)-(questionHeight)+(questionHeight/10), questionWidth, correctHeight);
			} else {
				int incorrectHeight = (int)Math.round((questionWidth*imgIncorrect.getHeight())/imgIncorrect.getWidth());
				gc.setFont(Font.font("Comic Sans MS", 15));
				gc.setFill(Color.BLACK);
				gc.setTextAlign(TextAlignment.CENTER);
				gc.fillText(("The correct answer was "+game2CorrectAnswer.toString().toLowerCase()+"."), (iBoundWidth/2), (iBoundHeight/2)-(questionHeight)+incorrectHeight+10);
				/*tGame2AnswerText.setY((iBoundHeight/2)-(questionHeight)+incorrectHeight+10);
				tGame2AnswerText.setText("The correct answer was "+game2CorrectAnswer.toString().toLowerCase()+".");
				tGame2AnswerText.setX((iBoundWidth/2)-(tGame2AnswerText.getLayoutBounds().getWidth()/2));*/
				gc.drawImage(imgIncorrect, (iBoundWidth/2)-(questionWidth/2), (iBoundHeight/2)-(questionHeight), questionWidth, incorrectHeight);
			}
		} else {
			gc.drawImage(imgQuestion, (iBoundWidth/2)-(questionWidth/2), (iBoundHeight/2)-(questionHeight), questionWidth, questionHeight);
		}
	}
	
	private void displayTutorial3(ArrayList<Item> lstItems, boolean removingTrash, int iGameProgress, int iAlgaeLevel) {
		drawBackgroundCrop(imgBackground1);
		drawAllItemsGame3(lstItems, null, removingTrash);
	}
			
	private void displayGame3(ArrayList<Item> lstItems, Shark shark, int iAlgaeLevel, boolean removingTrash) {
		dgc.clearRect(0,0, iBoundWidth, iBoundHeight);
		drawBackgroundScroll(imgBackground1);
		drawAllItemsGame3(lstItems, shark, removingTrash); 
		drawAlgaeShadow(iAlgaeLevel); 
		drawProgressBar(25, 25, shark.getiMaxHealth(), shark.getiHealth(), Color.GREEN, Color.RED, "Health");
	}
	
	private void displayGameOver() {
		gc.clearRect(0,0,iBoundWidth,iBoundHeight);
		dgc.clearRect(0,0,iBoundWidth, iBoundHeight);
		sgc.clearRect(0,0,iBoundWidth, iBoundHeight);
		gc.drawImage(imgYouDied, 0, 0, iBoundWidth, iBoundHeight);
	}
	
	private void displayEndscreen() {
		gc.clearRect(0,0,iBoundWidth,iBoundHeight);
		dgc.clearRect(0,0,iBoundWidth, iBoundHeight);
		gc.drawImage(imgYouWin, 0, 0, iBoundWidth, iBoundHeight);
	}
	
	private void playMedia(File fCutscene) {
		try {
			media = new Media(fCutscene.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		currCutscene = media;
		
		player = new MediaPlayer(media);
		viewer = new MediaView(player); 
		
		player.setOnEndOfMedia(() -> {
			player.dispose();  
		});
		
		root.getChildren().add(viewer);
		
		player.play(); 
	}
	
	private void setupdecCanvas() {
		decCanvas = new Canvas(iBoundWidth, iBoundHeight);
		root.getChildren().add(decCanvas);
		dgc = decCanvas.getGraphicsContext2D();
	}
	
	private void setupsharkCanvas() {
		sharkCanvas = new Canvas(iBoundWidth, iBoundHeight);
		root.getChildren().add(sharkCanvas);
		sgc = sharkCanvas.getGraphicsContext2D();
		sharkImgRotate = new Rotate();
		sharkCanvas.getTransforms().add(sharkImgRotate);
	}
	
	private void drawBackgroundScroll(Image imgBackground) {
		decCanvas.setWidth(iBoundWidth);
		decCanvas.setHeight(iBoundHeight);
		gc.setGlobalAlpha(1.0);
		gc.clearRect(0, 0, iBoundWidth, iBoundHeight);
		dgc.clearRect(0, 0, iBoundWidth, iBoundHeight);
		sgc.clearRect(0, 0, iBoundWidth, iBoundHeight);
		
		if(bScrollBackground) {
			backgroundIndex--;
		}
		int numBackgrounds = ((int) Math.ceil((double) iBoundWidth/(double) iBoundHeight))+1;
		for(int i = 0; i < numBackgrounds; i++) {
			gc.drawImage(imgBackground, backgroundIndex+(iBoundHeight*i),  0, iBoundHeight, iBoundHeight);
		}
		
		if(backgroundIndex+iBoundHeight <= 0) {
			backgroundIndex = 0;
		}
	}
	
	private void drawBackgroundCrop(Image imgBackground) {
		decCanvas.setWidth(iBoundWidth);
		decCanvas.setHeight(iBoundHeight);
		gc.setGlobalAlpha(1.0);
		int backgroundHeight = iBoundHeight;
		int backgroundWidth = (int)Math.round((backgroundHeight*imgBackground.getWidth())/imgBackground.getHeight());
		int backgroundY = 0;
		int backgroundX = (iBoundWidth/2)-(backgroundWidth/2);
		if(iBoundWidth > backgroundWidth) {
			backgroundWidth = iBoundWidth;
			backgroundHeight = (int)Math.round((backgroundWidth*imgBackground.getHeight())/imgBackground.getWidth());
			backgroundX = 0;
			backgroundY = (iBoundHeight/2)-(backgroundHeight/2);
		}
		
		gc.clearRect(0, 0, iBoundWidth, iBoundHeight);
		dgc.clearRect(0, 0, iBoundWidth, iBoundHeight);
		
		gc.drawImage(imgBackground, backgroundX, backgroundY, backgroundWidth, backgroundHeight);
	}
	
	private void drawAllItems(ArrayList<Item> lstItems, Shark shark, int iGameProgress) {
		if(sharkCanvas != null) {
			sharkCanvas.setWidth(iBoundWidth);
			sharkCanvas.setHeight(iBoundHeight);
		}
		for (Item i : lstItems) {
			Image img;
			int wid = i.getiWidth();
			int hei = i.getiHeight();
    		switch(i.geteItemType()) {
	    		case FISH:
	    			img = imgFish;
	    			break;
	    		case RAY:
	    			img = imgRay;
	    			break;
	    		case PLASTIC:
	    			img = imgPlastic;
	    			break;
	    		case ALGAE:
	    			img = imgAlgae;
	    			break;
	    		case STARTSIGN:
	    			img = imgStartSign;
	    			break;
	    		case ENDSIGN:
	    			img = imgEndSign;
	    			break;
	    		case NETTYPES:
	    			img = imgNetTypes;
	    			break;
	    		case NETSELECT:
	    			img = imgNetSelect;
	    			break;
	    		case ROCK1:
					img=imgRock1;
	    			break;
	    		case ROCK2:
					img=imgRock2;
	    			break;
	    		case ROCK3:
					img=imgRock3;
	    			break;
	    		case SEAWEED1:
	    			img = imgSeaweed1;
	    			break;
	    		case SEAWEED2:
	    			img = imgSeaweed2;
	    			break;
	    		case SEAWEED3:
	    			img = imgSeaweed3;
	    			break;
	    		case BOAT:
	    			img = imgBoat;
	    			break;
	    		case GLASS:
	    			img = imgGlass;
	    			break;
	    		case METAL:
	    			img = imgMetal;
	    			break;
	    		case BUBBLE:
	    			img = imgBubble;
	    			break;
	    		case CLAMSPEECH: 
	    			img = imgClamSpeech; 
	    			break;
	    		default:
	    			img = imgFish;
    				break;
    		}
    		
    		if(i.getbIsDecoration()) {
    			dgc.drawImage(img, i.getiLocX(), i.getiLocY(), wid, hei);
    			if(i.geteItemType() == ItemType.CLAMSPEECH) {
    				switch (iGameProgress) {
    				case 2: 
    					dgc.drawImage(imgHQ, i.getiLocX()+(i.getiWidth()/4),i.getiLocY()+10,i.getiWidth()/2,i.getiWidth()/2);
    					break;
    				case 3: 
    					dgc.drawImage(imgArrow, i.getiLocX()+(i.getiWidth()/4),i.getiLocY()+10,i.getiWidth()/2,i.getiWidth()/2);
    					break;
    				case 4: 
    					dgc.drawImage(imgThumbsUp, i.getiLocX()+(i.getiWidth()/4),i.getiLocY()+10,i.getiWidth()/2,i.getiWidth()/2);
    					break;
    				}
    			}
    		} else {
    			if(eGameState == GameState.TUTORIAL1) {
    				switch(i.geteItemType()) {
    				case FISH:
    					dgc.drawImage(imgTutGood, i.getiLocX(), i.getiLocY()+i.getiHeight(), i.getiWidth(), i.getiWidth());
    					if(iGameProgress == 1) {
    						gc.setGlobalAlpha(1.0-moveOpa);
    						gc.drawImage(imgArrow, i.getiLocX()-((330*i.getiHeight())/150)-10, i.getiLocY(), (330*i.getiHeight())/150,i.getiHeight());
    						gc.setGlobalAlpha(1.0);
    					}
    					break;
    				case PLASTIC:
    					dgc.drawImage(imgTutBad, i.getiLocX(), i.getiLocY()+i.getiHeight(), i.getiWidth(), i.getiWidth());
						if(iGameProgress == 1) { 
    						gc.setGlobalAlpha(1.0-moveOpa);
    						gc.drawImage(imgArrow, i.getiLocX()-((330*i.getiHeight())/150)-10, i.getiLocY(), (330*i.getiHeight())/150,i.getiHeight());
    						gc.setGlobalAlpha(1.0);
						}
    					break;
					default:
						break;
    				}
    			}
    			gc.drawImage(img, i.getiLocX(), i.getiLocY(), wid, hei);
    		}
		}
		
		if(shark != null) {
			sharkImgRotate.setPivotX(shark.getiLocX()+(shark.getiWidth()/2));
			sharkImgRotate.setPivotY(shark.getiLocY()+(shark.getiHeight()/2));
			sharkImgRotate.setAngle(shark.getRotInd());
			
			if(iGameProgress == 0) {
				if(eGameState == GameState.TUTORIAL1) {
					dgc.drawImage(imgTutMove, shark.getiLocX(), shark.getiLocY()+shark.getiHeight());
				}
			} else {
				if(eGameState == GameState.TUTORIAL1) { 
					if(moveOpa >= 0) {
						dgc.setGlobalAlpha(moveOpa);
						dgc.drawImage(imgTutMove, shark.getiLocX(), shark.getiLocY()+shark.getiHeight());
						moveOpa-=0.15;
						dgc.setGlobalAlpha(1.0);
					}
				}
			}
			
			if(shark.getbFlashing()) {
				if(!shark.incFlashIndex()) {
					if(shark.getbDirection()) {
						sgc.drawImage(imgShark, shark.getiLocX(), shark.getiLocY(), shark.getiWidth(), shark.getiHeight());
					} else {
						sgc.drawImage(imgShark, shark.getiLocX()+shark.getiWidth(), shark.getiLocY(), -shark.getiWidth(), shark.getiHeight());
					}
				}
			} else {
				if(shark.getbDirection()) {
					sgc.drawImage(imgShark, shark.getiLocX(), shark.getiLocY(), shark.getiWidth(), shark.getiHeight());
				} else {
					sgc.drawImage(imgShark, shark.getiLocX()+shark.getiWidth(), shark.getiLocY(), -shark.getiWidth(), shark.getiHeight());
				}
			}
		}
		
		tutorialFlashIdx++;
		if(tutorialFlashIdx >= 25) {
			showTutorialText = !showTutorialText;
			tutorialFlashIdx = 0;
		}
		if(showTutorialText) {
			if(eGameState == GameState.TUTORIAL1 || eGameState == GameState.TUTORIAL3) {
				dgc.setFill(Color.RED);
		    	dgc.setFont(Font.font("Comic Sans MS", 30));
		    	dgc.fillText("Tutorial", 25, 85);
		    	dgc.setFont(Font.font("Comic Sans MS", 20));
		    	dgc.fillText("(Press Enter to Skip)", 25, 115);
			}
		}
	}
	
	private Image createImage(String filepath) {
		return new Image("assets/" + filepath);
	}
	
	private void drawMouseCursor(double mouseX, double mouseY) {
		dgc.drawImage(imgNet, (int)mouseX-25, (int)mouseY-5, 75, 50);
	}

	private void drawAlgaeShadow(int iAlgaeLevel) {
		dgc.setFill(Color.BLACK);

		dgc.setGlobalAlpha((0.55*iAlgaeLevel)/75);

    	dgc.fillRect(0, 0, iBoundWidth, iBoundHeight);
    	dgc.setGlobalAlpha(1.0);
	}

	private void drawProgressBar(int x, int y, int iCapacity, int iProgress, Paint foreColor, Paint backColor, String barTitle) {		
		dgc.setStroke(null);
    	dgc.setFill(backColor);
    	dgc.fillRect(x, y, 25 + (iBoundWidth / 2), 25);
    	
    	dgc.setFill(foreColor);
    	dgc.fillRect(x, y, ((double)iProgress/(double)iCapacity) * (25 + (iBoundWidth / 2)), 25);
    	
    	dgc.setStroke(Color.BLACK);
    	dgc.setLineWidth(3.0);
    	dgc.setFill(null);
    	dgc.strokeRect(x, y, 25 + (iBoundWidth / 2), 25);
    	
    	dgc.setFill(Color.BLACK);
    	dgc.setFont(Font.font("Comic Sans MS", 25));
    	dgc.fillText(barTitle, x+25+(iBoundWidth/2)+10, y+22);
	}
	
	public boolean getIsOnNetSelection() {
		return isOnNetSelection;
	}
	
	private void drawAllItemsGame3(ArrayList<Item> lstItems, Shark shark, boolean removingTrash) {
		if(shark != null) {
			sharkCanvas.setWidth(iBoundWidth);
			sharkCanvas.setHeight(iBoundHeight);
		}
		
		int canvasFlip = 1;
		
		if(eGameState == GameState.GAME3) {
			canvasFlip = -1;
		}
		
		boolean algaeSelectLock = false;
		boolean trashSelectLock = false;
		Item algaeSelected = new Item(ItemType.ALGAE, 0,0);
		
		Item nsq = new Item(ItemType.NETSELECT,0,0);
		Item nt = new Item(ItemType.NETTYPES,0,0);
		boolean nsqFound = false;
		boolean ntFound = false;
		
		for (Item i : lstItems) {
			Image img;
			int wid = i.getiWidth();
			int hei = i.getiHeight();

    		switch(i.geteItemType()) {
	    		case FISH:
	    			img = imgFish;
	    			break;
	    		case NETTYPES:
	    			img = imgNetTypes;
	    			break;
	    		case RAY:
	    			img = imgRay;
	    			break;
	    		case PLASTIC:
	    			img = imgPlastic;
	    			break;
	    		case ALGAE:
	    			img = imgAlgae;
	    			break;
	    		case STARTSIGN:
	    			img = imgStartSign;
	    			break;
	    		case ENDSIGN:
	    			img = imgEndSign;
	    			break;
	    		case ROCK1:
					img = imgRock1;
	    			break;
	    		case ROCK2:
					img = imgRock2;
	    			break;
	    		case ROCK3:
					img = imgRock3;
	    			break;
	    		case SEAWEED1:
	    			img = imgSeaweed1;
	    			break;
	    		case SEAWEED2:
	    			img = imgSeaweed2;
	    			break;
	    		case SEAWEED3:
	    			img = imgSeaweed3;
	    			break;
	    		case BOAT:
	    			img = imgBoat;
	    			break;
	    		case GLASS:
	    			img = imgGlass;
	    			break;
	    		case METAL:
	    			img = imgMetal;
	    			break;
	    		case NETSELECT:
	    			img = imgNetSelect;
	    			break;
	    		default:
	    			img = imgFish;
    				break;
    		}
    		
    		if(eGameState == GameState.TUTORIAL3) {
	    		switch(tutorial3State) {
	    		case 1:
	    			gc.drawImage(imgArrowDown, ItemType.NETTYPES.getiWidth()/2, iBoundHeight-ItemType.NETTYPES.getiHeight()-30-(((ItemType.NETTYPES.getiWidth()/2)*330)/150), ItemType.NETTYPES.getiWidth()/2, ((ItemType.NETTYPES.getiWidth()/2)*330)/150);
	    			gc.drawImage(imgLeftClick, ItemType.NETTYPES.getiWidth()/2, iBoundHeight-ItemType.NETTYPES.getiHeight()-30-(((ItemType.NETTYPES.getiWidth()/2)*330)/150)-ItemType.NETTYPES.getiWidth()/2-10, ItemType.NETTYPES.getiWidth()/2, ItemType.NETTYPES.getiWidth()/2);
	    			break;
	    		case 2:
    				gc.drawImage(imgLeftClick, iBoundWidth/4, ItemType.ALGAE.getiHeight()+10, ItemType.ALGAE.getiWidth(), ItemType.ALGAE.getiWidth());
    				gc.drawImage(imgArrow, (iBoundWidth/4)+ItemType.ALGAE.getiWidth()+10, ItemType.ALGAE.getiHeight()+10, (iBoundWidth/2)-ItemType.ALGAE.getiWidth()-10, ItemType.ALGAE.getiWidth());
	    			break;
	    		case 3:
	    			gc.drawImage(imgArrowDown, 0, iBoundHeight-ItemType.NETTYPES.getiHeight()-30-(((ItemType.NETTYPES.getiWidth()/2)*330)/150), ItemType.NETTYPES.getiWidth()/2, ((ItemType.NETTYPES.getiWidth()/2)*330)/150);
	    			gc.drawImage(imgLeftClick, 0, iBoundHeight-ItemType.NETTYPES.getiHeight()-30-(((ItemType.NETTYPES.getiWidth()/2)*330)/150)-ItemType.NETTYPES.getiWidth()/2-10, ItemType.NETTYPES.getiWidth()/2, ItemType.NETTYPES.getiWidth()/2);
	    			break;
	    		case 4:
	    			if(i.geteItemType() == ItemType.PLASTIC || i.geteItemType() == ItemType.METAL || i.geteItemType() == ItemType.GLASS) {
	    				gc.drawImage(imgArrow, i.getiLocX()-((i.getiWidth()*330)/150)-10, i.getiLocY(), (i.getiWidth()*330)/150, i.getiHeight());
	    				gc.drawImage(imgLeftClick, i.getiLocX()-((i.getiWidth()*330)/150)-10-i.getiWidth()-10, i.getiLocY(), i.getiWidth(), i.getiWidth());
	    			}
	    			
	    			break;
	    		default:
	    			break;
	    		}
    		}
    		
    		if(i.getbIsDecoration()) {
    			if(i.geteItemType() == ItemType.NETSELECT) {
    				nsq = i;
    				nsqFound = true;
    			}
    			
    			if(i.geteItemType() == ItemType.NETTYPES) {
    				nt = i;
    				ntFound = true;
    			}
    			// dgc.drawImage(img, i.getiLocX(), i.getiLocY(), wid, hei);
    			if(i.geteItemType() == ItemType.NETSELECT || i.geteItemType() == ItemType.NETTYPES) {
    				
    			} else {
    				if(canvasFlip == -1) {
	    				if(i.geteItemType() == ItemType.ALGAE && !removingTrash && !algaeSelectLock) {
	    					if(dMouseX > iBoundWidth-i.getiLocX() && dMouseX < iBoundWidth-i.getiLocX()+i.getiWidth()) {
			    				if(dMouseY > i.getiLocY() && dMouseY < i.getiLocY()+i.getiHeight()) {
			    					algaeSelectLock = true;
			    					algaeSelected = i;
			    				}
			    			}
	    				}
	    				dgc.drawImage(img, iBoundWidth - i.getiLocX(), i.getiLocY(), wid, hei);
    				} else {
    					if(i.geteItemType() == ItemType.ALGAE && !removingTrash && !algaeSelectLock) {
	    					if(dMouseX > i.getiLocX() && dMouseX < i.getiLocX()+i.getiWidth()) {
			    				if(dMouseY > i.getiLocY() && dMouseY < i.getiLocY()+i.getiHeight()) {
			    					algaeSelectLock = true;
			    					algaeSelected = i;
			    				}
			    			}
	    				}
	    				dgc.drawImage(img, i.getiLocX(), i.getiLocY(), wid, hei);
    				}
    			}
    		} else {
    			// gc.drawImage(img, i.getiLocX(), i.getiLocY(), wid, hei);
    			if(canvasFlip == -1) {
	    			if(removingTrash) {
		    			if((i.geteItemType() == ItemType.PLASTIC || i.geteItemType() == ItemType.METAL || i.geteItemType() == ItemType.GLASS) && !trashSelectLock) {
			    			if(dMouseX > iBoundWidth-i.getiLocX()-i.getiWidth() && dMouseX < iBoundWidth-i.getiLocX()) {
			    				if(dMouseY > i.getiLocY() && dMouseY < i.getiLocY()+i.getiHeight()) {
			    					gc.setStroke(Color.RED);
			    					gc.setLineWidth(3);
			    					gc.strokeRect(iBoundWidth-i.getiLocX()-i.getiWidth(), i.getiLocY(), i.getiWidth(), i.getiHeight());
			    					gc.drawImage(imgLeftClick, iBoundWidth-i.getiLocX()-i.getiWidth(), i.getiLocY()+i.getiHeight()+10, 25,25);
			    					trashSelectLock = true;
			    				}
			    			}
		    			}
	    			}
	    			gc.drawImage(img, iBoundWidth - i.getiLocX(), i.getiLocY(), canvasFlip*wid, hei);
    			} else {
    				if(removingTrash) {
		    			if((i.geteItemType() == ItemType.PLASTIC || i.geteItemType() == ItemType.METAL || i.geteItemType() == ItemType.GLASS) && !trashSelectLock) {
			    			if(dMouseX < i.getiLocX()+i.getiWidth() && dMouseX > i.getiLocX()) {
			    				if(dMouseY > i.getiLocY() && dMouseY < i.getiLocY()+i.getiHeight()) {
			    					gc.setStroke(Color.RED);
			    					gc.setLineWidth(3);
			    					gc.strokeRect(i.getiLocX(), i.getiLocY(), i.getiWidth(), i.getiHeight());
			    					gc.drawImage(imgLeftClick, i.getiLocX(), i.getiLocY()+i.getiHeight()+10, 25,25);
			    					trashSelectLock = true;
			    				}
			    			}
		    			}
	    			}
	    			gc.drawImage(img, i.getiLocX(), i.getiLocY(), canvasFlip*wid, hei);
    			}
    		}
		}

		if(algaeSelectLock) {
			if(canvasFlip == -1) {
				dgc.setStroke(Color.RED);
				dgc.setLineWidth(3);
				dgc.strokeRect(iBoundWidth-algaeSelected.getiLocX(), algaeSelected.getiLocY(), algaeSelected.getiWidth(), algaeSelected.getiHeight());
				dgc.drawImage(imgLeftClick, iBoundWidth-algaeSelected.getiLocX(), algaeSelected.getiLocY()+algaeSelected.getiHeight()+10, 25,25);
			} else {
				dgc.setStroke(Color.RED);
				dgc.setLineWidth(3);
				dgc.strokeRect(algaeSelected.getiLocX(), algaeSelected.getiLocY(), algaeSelected.getiWidth(), algaeSelected.getiHeight());
				dgc.drawImage(imgLeftClick, algaeSelected.getiLocX(), algaeSelected.getiLocY()+algaeSelected.getiHeight()+10, 25,25);
			}
		}
		
		if(shark != null) {
			sharkImgRotate.setPivotX( iBoundWidth - (shark.getiLocX()+(shark.getiWidth()/2)));
			sharkImgRotate.setPivotY(shark.getiLocY()+(shark.getiHeight()/2));
			sharkImgRotate.setAngle(shark.getRotInd());
			if(shark.getbFlashing()) {
				if(!shark.incFlashIndex()) {
					sgc.drawImage(imgShark, iBoundWidth - shark.getiLocX(), shark.getiLocY(), canvasFlip*shark.getiWidth(), shark.getiHeight());
				}
			} else {
				sgc.drawImage(imgShark, iBoundWidth - shark.getiLocX(), shark.getiLocY(), canvasFlip*shark.getiWidth(), shark.getiHeight());
			}
		}
		
		Item[] selContainer = {nt, nsq};
		
		if(ntFound && nsqFound) {
			for(Item i : selContainer) {
				if(canvasFlip == -1) {
					dgc.drawImage((i.geteItemType()==ItemType.NETTYPES)?imgNetTypes:imgNetSelect, iBoundWidth - i.getiLocX(), i.getiLocY(), canvasFlip*i.getiWidth(), i.getiHeight());
					isOnNetSelection = false;
					if(dMouseX > 0 && dMouseX < ItemType.NETTYPES.getiWidth()) {
						if(dMouseY < iBoundHeight && dMouseY > iBoundHeight-ItemType.NETTYPES.getiHeight()-27) {
							isOnNetSelection = true;
						}
					}
				} else {
					dgc.drawImage((i.geteItemType()==ItemType.NETTYPES)?imgNetTypes:imgNetSelect, i.getiLocX(), i.getiLocY(), canvasFlip*i.getiWidth(), i.getiHeight());
					isOnNetSelection = false;
					if(dMouseX > 0 && dMouseX < ItemType.NETTYPES.getiWidth()) {
						if(dMouseY < iBoundHeight && dMouseY > iBoundHeight-ItemType.NETTYPES.getiHeight()-27) {
							isOnNetSelection = true;
						}
					}
				}
			}
		}
		
		tutorialFlashIdx++;
		if(tutorialFlashIdx >= 25) {
			showTutorialText = !showTutorialText;
			tutorialFlashIdx = 0;
		}
		if(showTutorialText) {
			if(eGameState == GameState.TUTORIAL1 || eGameState == GameState.TUTORIAL3) {
				dgc.setFill(Color.RED);
		    	dgc.setFont(Font.font("Comic Sans MS", 30));
		    	dgc.fillText("Tutorial", 25, 85);
		    	dgc.setFont(Font.font("Comic Sans MS", 20));
		    	dgc.fillText("(Press Enter to Skip)", 25, 115);
			}
		}
	}
	
	public void setMouseCoords(double x, double y) {
        dMouseX = x;
        dMouseY = y;
    }
	
	public void clearCanvas(){
		setupsharkCanvas();
		setupdecCanvas();
		gc.clearRect(0,0,iBoundWidth,iBoundHeight);
		dgc.clearRect(0,0,iBoundWidth, iBoundHeight);
		sgc.clearRect(0,0,iBoundWidth, iBoundHeight);
		if(player != null){ player.dispose();}  
	}
	// helper methods
}
