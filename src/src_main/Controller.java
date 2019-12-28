package src_main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import src_resources.GameState;
import java.io.*;

public class Controller extends Application {
	
	private Model model; 
	private View view; 
	private int windowWidth = 700;
	private int windowHeight = 700;
	private EventHandler<KeyEvent> evKeyPress;
	private KeyCode kcKeyPress = KeyCode.ESCAPE;
	private EventHandler<KeyEvent> evKeyRelease;
	private KeyCode kcKeyRelease = KeyCode.ESCAPE;
	private EventHandler<MouseEvent> evMouse, evMouseClickDown, evMouseClickUp; 
	private double dMouseX = 0;
	private double dMouseY = 0;
	private final int iSleepTimer = 25;
	private boolean click;
	private Model serial;
	private String filename = "SharkCereal";
	
	public static void main(String[] args) {
		launch(args); 
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		view = new View(stage, windowWidth, windowHeight); 
		model = new Model(view.getiBoundWidth(), view.getiBoundHeight()); 
		
		evKeyPress = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				kcKeyPress = event.getCode();
				switch(event.getCode()) {
				case UP:
					model.setMoveBool(1, true);
					break;
				case DOWN:
					model.setMoveBool(2, true);
					break;
				case LEFT:
					model.setMoveBool(3, true);
					break;
				case RIGHT:
					model.setMoveBool(4, true);
					break;
				default:
					break;
				}
			}
		};
		
		evKeyRelease = new EventHandler<KeyEvent>() {
			@Override 
			public void handle(KeyEvent event) {
				kcKeyRelease = event.getCode(); 
				switch(event.getCode()) {
				case UP:
					model.setMoveBool(1, false);
					break;
				case DOWN:
					model.setMoveBool(2, false);
					break;
				case LEFT:
					model.setMoveBool(3, false);
					break;
				case RIGHT:
					model.setMoveBool(4, false);
					break;
				case O:
					try
			        {    

			            //Saving of object in a file 
			            FileOutputStream file = new FileOutputStream(filename); 
			            ObjectOutputStream out = new ObjectOutputStream(file); 

			            // Method for serialization of object 
			            out.writeObject(model);
			              
			            out.close(); 
			            file.close(); 
			              
			            System.out.println("Save state saved!"); 
			        } 
					catch (IOException e) { // catch all IOExceptions not handled by previous catch blocks
					  System.out.println("General I/O exception: " + e.getMessage());
					  e.printStackTrace();
					}
					break;
				case P:
					// Deserialization 
			        try
			        {    
			            // Reading the object from a file 
			            FileInputStream file = new FileInputStream(filename); 
			            ObjectInputStream in = new ObjectInputStream(file); 
			              
			            // Method for deserialization of object 
			            model = (Model)in.readObject(); 
			            view.clearCanvas();

			            in.close(); 
			            file.close(); 
			              
			            System.out.println("Save State loaded!"); 

			            
			        } 
			          
			        catch(IOException ex) 
			        { 
			            System.out.println("IOException is caught"); 
			        } 
			        catch(ClassNotFoundException ex) 
			        { 
			            System.out.println("ClassNotFoundException is caught"); 
			        } 
				default:
					break;
				}
			}
		};
		
		evMouse = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dMouseX = event.getX();
				dMouseY = event.getY(); 
			}
		};
		
		evMouseClickDown = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				click = true;
			}
		};
		
		evMouseClickUp = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				click = false;
			}
		};
		
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				
				view.getScene().setOnMouseMoved(evMouse); // click to remove the trash
				view.getScene().setOnMouseDragged(evMouse);
				view.getScene().setOnMousePressed(evMouseClickDown);
				view.getScene().setOnMouseReleased(evMouseClickUp);
            	view.getScene().setOnKeyPressed(evKeyPress);
            	view.getScene().setOnKeyReleased(evKeyRelease);
				
            	if(model.geteGameState() == GameState.GAME2) {
            		if(model.getFeedbackAvailable()) {
            			view.setGame2ModelResponse(true);
            			view.setGame2Correct(model.getGame2Correct(), 
            					model.geteMostAbundantTrash());
            		} else {
	            		if(view.getbGuessed()) {
	            			model.setGame2Guess(view.getGame2Guess());
	            		}
            		}
            	}
            	
            	if(model.geteGameState() == GameState.GAME3 || model.geteGameState() == GameState.TUTORIAL3) {
            		view.setMouseCoords(dMouseX, dMouseY);
            		if(view.getIsOnNetSelection()) {
            			view.getScene().setCursor(Cursor.HAND);
            		} else {
            			view.getScene().setCursor(Cursor.DEFAULT);
            		}
            	} else {
        			view.getScene().setCursor(Cursor.DEFAULT);
            	}
            	
//            	if(model.geteGameState() == GameState.TUTORIAL1) {
//            		view.setShowMove(model.getShowMove());
//            	}
            	
            	if(model.geteGameState() == GameState.TUTORIAL3) {
            		view.setTutorial3State(model.getTutorial3State());
            	}
            	
            	model.update(view.getbViewForceCont(), kcKeyPress, kcKeyRelease, 
            			dMouseX, dMouseY, view.getiBoundWidth(), 
            			view.getiBoundHeight(), click); 
            	view.update(model.getbModelForceCont(), model.geteGameState(), 
            			model.getlstItems(), model.getShark(), model.getiAlgaeLevel(), 
            			model.getbGameRunning(), model.getiGameProgress(), 
            			model.getiGameLength(), model.getbRemovingTrash());
            	
            	kcKeyPress = KeyCode.ESCAPE;
            	kcKeyRelease = KeyCode.ESCAPE;
            	
            	try {
					Thread.sleep(iSleepTimer);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            	
			}
		}.start(); 
		
		stage.show(); 
		
	}
	
}
