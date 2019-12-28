package test_main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.*;
import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import src_main.Model;
import src_resources.GameState;
import src_resources.Item;
import src_resources.ItemType;

public class TestModel {

	private Model testModel;

	@Before
	public void setUp() throws Exception {
		testModel = new Model(500,500);
		assertEquals(testModel.geteGameState(), GameState.STARTSCREEN);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		try {
			testModel.update(true, KeyCode.ESCAPE, KeyCode.ESCAPE, 0, 0, 500, 500, false);
			testModel.update(true, KeyCode.ESCAPE, KeyCode.ESCAPE, 0, 0, 500, 500, false);
			testModel.update(true, KeyCode.ESCAPE, KeyCode.ESCAPE, 0, 0, 500, 500, false);
			
			//test game 1
			Method initGame1 = testModel.getClass().getDeclaredMethod("initializeGame1", KeyCode.class, KeyCode.class);
			initGame1.setAccessible(true);
			
			//skip clam speech
			while(testModel.getiGameProgress() <= 6) {
				initGame1.invoke(testModel, KeyCode.SPACE, KeyCode.ESCAPE);
			}

			/*Field lstItems = testModel.getClass().getDeclaredField("lstItems");
			lstItems.setAccessible(true);*/
			Field iGameProgress = testModel.getClass().getDeclaredField("iGameProgress");
			iGameProgress.setAccessible(true);
			Method spawnFish = testModel.getClass().getDeclaredMethod("spawnFish");
			spawnFish.setAccessible(true);
			Method spawnTrash = testModel.getClass().getDeclaredMethod("spawnTrash");
			spawnTrash.setAccessible(true);
			
			for(int i = 0; i < 1500; i++) {
				spawnFish.invoke(testModel);
				spawnTrash.invoke(testModel);
			}
			
			
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
