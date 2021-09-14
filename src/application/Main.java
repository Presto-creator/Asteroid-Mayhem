package application;
/**
 * Asteroid Mayhem was created by Steven Kronmiller and Preston Leigh
 * All images and music are open-source and copyright free
 * Version 4-26-2021
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.util.Scanner;

public class Main extends Application {

	/* The commands needed to play the game
	 * --module-path "C:\Program Files\javafx-sdk-16\lib" --add-modules=javafx.controls 
	 * --module-path "C:\Program Files\javafx-sdk-16\lib" --add-modules=javafx.media
	 */
	
	//general variables
	private Font textFont;
	private Font textItalic;
	private AudioClip backgroundMusic;
	private Scanner in;
	
	// title variables
	private FlowPane titlePane;
	private Scene titleScene;
	private Text titleText;
	private Button playGame;
	private Button howToPlay;
	private int highScore;
	private int currScore;
	private Image backgroundGif;
	private Image backgroundGifSlow;
	private ImageView backgroundGIFBeg;
	private Group titleGroup;
	
	//tutorial variables
	private Text tutorialText;
	private Button returnMainMenu;
	private Scene tutorialScene;
	private ImageView backgroundGIFTut;
	private Group tutorialGroup;

	// game variables
	private ImageView backgroundGIFGame;
	private Scene gameScene;
	private Group gameGroup;
	private Text currentScore;
	private Text dashes1;
	private Text dashes2;

	// end variables
	private Scene endScene;
	private Button returnButton;
	private Text gameOver;
	private Text PrevHighScoreText;
	private Text PrevScoreInt;
	private Text HighScoreInt;
	private FlowPane endPane;
	private Group endGroup;
	private ImageView backgroundGIFEnd;
	
	//Sprites + Animation
	private long lastNanoTime;
	private Sprite shipSprite;
	private Sprite asteroidSprite;
	private Sprite asteroidSprite2;
	private Canvas canvas;
	private GraphicsContext gc;

	/**
	 * Starts the program and creates the scenes
	 * @param primaryStage is the Stage in which the game is set
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			
			in = new Scanner(new File("Highscore.txt")); // allows file to be opened
			
			while(in.hasNext()) {
				highScore = in.nextInt(); // reads in high score from text file
			}
			in.close();
			
			createSprites(); // create the spaceship + asteroids sprite
		
			currScore = 0;
			
			createTitleScene(primaryStage);
			backgroundMusic = new AudioClip(new File("BackgroundMusic.wav").toURI().toString());

			titleScene = new Scene(titleGroup, 450, 750);

			
			createTutorialScene(primaryStage);
			
			tutorialScene = new Scene(tutorialGroup, 450, 750);
			
			createEndScene(primaryStage);
			
			endScene = new Scene(endGroup, 450, 750, Color.BLACK);
			
			primaryStage.setResizable(false); // cannot resize the window
			primaryStage.setTitle("Asteroid Mayhem");
			primaryStage.setScene(titleScene);
			primaryStage.show();
			backgroundMusic.setVolume(0.2);
			backgroundMusic.play();
			backgroundMusic.setCycleCount(10); // song plays 10 times, no looping function sadly

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launches the game
	 * @param args are the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	
	/**
	 * Updates the current score and high score
	 */
	public void keepScore() {
		
		PrevScoreInt.setText(currScore + "\n");		
    	currentScore.setText("SCORE: " + currScore);
    	HighScoreInt.setText(highScore + "\n");
	}

	/**
	 * What happens when a key is pressed
	 * @param event is the info about the key pressed
	 */
	public void ProcessKeyPress(KeyEvent event){
		switch(event.getCode()){
			case UP:
				shipSprite.setVelocity(0, -200);
				break;
			case DOWN:
				shipSprite.setVelocity(0, 200);
				break;
			default:
				break;
		}
	}
	
	/**
	 * When the key is released
	 * @param event is the info about the key released
	 */
	public void ProcessKeyRelease(KeyEvent event) {
		shipSprite.setVelocity(0, 0);
	}
	
	/**
	 * Creates all the sprites in the game
	 */
	public void createSprites()
	{
		shipSprite = new Sprite();
		shipSprite.setImage("RedSpaceship.png");
		shipSprite.setPosition(30, 375);
		
		asteroidSprite = new Sprite();
		asteroidSprite.setImage("Asteroid.PNG");
		double y = (447) * Math.random();
		asteroidSprite.setPosition(450, y+90);
		asteroidSprite.setVelocity(-300, 0);
		
		asteroidSprite2 = new Sprite();
		asteroidSprite2.setImage("Asteroid.PNG");
		y = (447) * Math.random();
		asteroidSprite2.setPosition(450, y+90);
		asteroidSprite2.setVelocity(-300, 0);
	}
	
	/**
	 * function that sets the title scene up for creation
	 * @param primaryStage is the Stage in which the game is set
	 */
	public void createTitleScene(Stage primaryStage) {

		backgroundGif = new Image((new File("giphy.gif")).toURI().toString()); // allows us to use gif as background
		backgroundGifSlow = new Image((new File("slowbackground.gif")).toURI().toString());
		backgroundGIFBeg = new ImageView(backgroundGifSlow);
		
		backgroundGIFBeg.setRotate(-90);
		backgroundGIFBeg.setScaleX(2.25);
		backgroundGIFBeg.setScaleY(1.67);
		backgroundGIFBeg.setTranslateY(100);
		backgroundGIFBeg.setTranslateX(-20);

		textFont = new Font("Impact", 40);
		textItalic = Font.font("Impact", FontPosture.ITALIC, 50);
		
		titleText = new Text("ASTEROID\nMAYHEM");
		titleText.setFill(Color.GOLD);
		titleText.setFont(textItalic);
		titleText.setStrokeWidth(2);
		titleText.setTranslateX(125);
		titleText.setTranslateY(200);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setLineSpacing(10);
		titleText.setStroke(Color.BLACK);

		playGame = new Button("START");
		playGame.setOnAction(e -> createGameScene(primaryStage));
		playGame.setScaleX(2.5);
		playGame.setScaleY(2.5);
		playGame.setTranslateX(190);
		playGame.setTranslateY(400);
		playGame.setTextAlignment(TextAlignment.CENTER);
		playGame.setStyle("-fx-background-color: goldenrod; -fx-font-weight: bold");
	
		howToPlay = new Button("HOW TO\nPLAY");
		howToPlay.setOnAction(e -> primaryStage.setScene(tutorialScene));
		howToPlay.setScaleX(2.5);
		howToPlay.setScaleY(2.5);
		howToPlay.setTranslateX(185);
		howToPlay.setTranslateY(500);
		howToPlay.setAlignment(Pos.CENTER);
		howToPlay.setTextAlignment(TextAlignment.CENTER);
		howToPlay.setStyle("-fx-background-color: goldenrod; -fx-font-weight: bold");

		titlePane = new FlowPane(titleText);
		titlePane.setAlignment(Pos.TOP_CENTER);
		titlePane.setPadding(new Insets(175, 10, 10, 10));

		titleGroup = new Group(backgroundGIFBeg, titleText, howToPlay, playGame);
	}

	/**
	 * function that sets the tutorial scene up for creation
	 * @param primaryStage is the Stage in which the game is set
	 */
	public void createTutorialScene(Stage primaryStage) {
		backgroundGIFTut = new ImageView(backgroundGifSlow);
		
		backgroundGIFTut.setRotate(-90);
		backgroundGIFTut.setScaleX(2.25);
		backgroundGIFTut.setScaleY(1.67);
		backgroundGIFTut.setTranslateY(100);
		backgroundGIFTut.setTranslateX(-20);
		
		tutorialText = new Text("How To Play:\n\nUp Arrow Key to go up\n\nDown Arrow Key to go down");
		tutorialText.setFont(textFont);
		tutorialText.setFill(Color.GOLDENROD);
		tutorialText.setTextAlignment(TextAlignment.CENTER);
		tutorialText.setTranslateY(200);
		
		returnMainMenu = new Button("Return to\nMain Menu");
		returnMainMenu.setStyle("-fx-background-color: goldenrod");
		returnMainMenu.setFont(textFont);
		returnMainMenu.setTextAlignment(TextAlignment.CENTER);
		returnMainMenu.setTranslateX(110);
		returnMainMenu.setTranslateY(500);
		returnMainMenu.setOnAction(e -> primaryStage.setScene(titleScene));
		
		tutorialGroup = new Group(backgroundGIFTut, tutorialText, returnMainMenu);
		
	}
	
	/**
	 * function that sets the game scene up for creation and also houses the animation function
	 * @param primaryStage is the Stage in which the game is set
	 */
	public void createGameScene(Stage primaryStage) {
		
		canvas = new Canvas(450, 750);
		gc = canvas.getGraphicsContext2D();
		
		backgroundGIFGame = new ImageView(backgroundGif);
		backgroundGIFGame.setRotate(-90);
		backgroundGIFGame.setScaleX(2.25);
		backgroundGIFGame.setScaleY(1.67);
		backgroundGIFGame.setTranslateY(100);
		backgroundGIFGame.setTranslateX(-20);

		dashes1 = new Text("- - - - - - - - - - - - - - - - - - - - - - - -");
		dashes1.setFont(new Font("Arial", 40));
		dashes1.setFill(Color.RED);
		dashes1.setTranslateX(-48);
		dashes1.setTranslateY(100);

		dashes2 = new Text("- - - - - - - - - - - - - - - - - - - - - - - -");
		dashes2.setFont(new Font("Arial", 40));
		dashes2.setFill(Color.RED);
		dashes2.setTranslateX(-48);
		dashes2.setTranslateY(650);

		currentScore = new Text("SCORE:  " + currScore);
		currentScore.setFont(textFont);
		currentScore.setFill(Color.GOLD);
		currentScore.setTranslateX(150);
		currentScore.setTranslateY(70);
				
		lastNanoTime = System.nanoTime(); //initialize starting time
		
		createSprites();
		
		gameGroup = new Group(backgroundGIFGame, currentScore, dashes1, dashes2, canvas);
		gameScene = new Scene(gameGroup, 450, 750);
		gameScene.setOnKeyPressed(this::ProcessKeyPress);
		gameScene.setOnKeyReleased(this::ProcessKeyRelease);
		
		primaryStage.setScene(gameScene);
		
		/**
		 * allows us to move the objects on screen
		 */
		new AnimationTimer()
		{
			//must override this method that is called 60 times per second (frame rate)
			/**
			 * The animation
			 * @param currentNanoTime is the time for the animation to run
			 */
			public void handle(long currentNanoTime)
			{
				//calculates time since last frame
				double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;
                
                //check for collision
                if(shipSprite.intersects(asteroidSprite) || shipSprite.intersects(asteroidSprite2))
                {
                	this.stop();
                	
                	primaryStage.setScene(endScene);
                	
                	if (currScore > highScore) {
        				highScore = currScore;
        				keepScore();
        				try {
        					new PrintWriter("Highscore.txt").close(); // erase the contents of the file
        					PrintWriter writer = new PrintWriter("Highscore.txt");
        					writer.print(highScore);
        					writer.close();
        				} catch (FileNotFoundException e1) {
        					e1.printStackTrace();
        				}
        			}
                	keepScore();
                	currScore = 0;
                }
                else if(asteroidSprite.getXPosition() < -150 && asteroidSprite2.getXPosition() < -150)
                {
                	double y = (447) * Math.random();
                	asteroidSprite.setPosition(450, y + 90);
                	y = (447) * Math.random();
                	asteroidSprite2.setPosition(450, y + 90);
                	currScore++;
                	currentScore.setText("SCORE: " + currScore);
                	
                	if (currScore >5) { //these three if statements make the game harder as you progress
                		asteroidSprite.setVelocity(-350, 0);
                		asteroidSprite2.setVelocity(-370, 0);
                	}
                	else if (currScore >10) {
                		asteroidSprite.setVelocity(-450, 0);
                		asteroidSprite2.setVelocity(-500, 0);
                	}
                	if (currScore > 15 ) {
                		asteroidSprite.setVelocity(-550, 0);
                		asteroidSprite2.setVelocity(-300, 0);
                	}
                	if (currScore > 20 ) {
                		asteroidSprite.setVelocity(-600, 0);
                		asteroidSprite2.setVelocity(-500, 0);
                	}
                }
                
                //skybox that ship can't pass
                if(shipSprite.getYPosition() <= 90)
                {
                	shipSprite.setPosition(30, 91);
                	shipSprite.setVelocity(0, 0);
                }
                else if(shipSprite.getYPosition() >= 620)
                {
                	shipSprite.setPosition(30, 619);
                	shipSprite.setVelocity(0, 0);
                }
                
                //update sprites
                asteroidSprite.update(elapsedTime);
                asteroidSprite2.update(elapsedTime);
                shipSprite.update(elapsedTime);
                
              //clear the graphics and render the new scene
                gc.clearRect(0, 0, 450, 750);
                asteroidSprite.render(gc);
                asteroidSprite2.render(gc);
                shipSprite.render(gc);
			}
		}.start();
	}
	
	/**
	 * function that sets up the end scene for creation
	 * @param primaryStage is the Stage in which the game is set
	 */
	public void createEndScene(Stage primaryStage) {
		backgroundGIFEnd = new ImageView(backgroundGif);
		backgroundGIFEnd.setRotate(-90);
		backgroundGIFEnd.setScaleX(2.25);
		backgroundGIFEnd.setScaleY(1.67);
		backgroundGIFEnd.setTranslateY(100);
		backgroundGIFEnd.setTranslateX(-20);

		gameOver = new Text("GAME\nOVER");
		gameOver.setFont(textItalic);
		gameOver.setFill(Color.GOLD);
		gameOver.setTranslateX(175);
		gameOver.setTranslateY(5);
		gameOver.setTextAlignment(TextAlignment.CENTER);

		PrevHighScoreText = new Text("PREVIOUS\nSCORE:\n\nHIGH\nSCORE:");
		PrevHighScoreText.setFont(textFont);
		PrevHighScoreText.setFill(Color.GOLDENROD);
		PrevHighScoreText.setTranslateY(250);
		PrevHighScoreText.setTranslateX(45);
		PrevHighScoreText.setTextAlignment(TextAlignment.CENTER);
		PrevHighScoreText.setLineSpacing(10);

		PrevScoreInt = new Text(currScore + "\n"); // need \n to allow it to easily display the int
		PrevScoreInt.setFont(textItalic);
		PrevScoreInt.setFill(Color.GOLD);
		PrevScoreInt.setTranslateX(-50);
		PrevScoreInt.setTranslateY(280);
		PrevScoreInt.setTextAlignment(TextAlignment.CENTER);

		HighScoreInt = new Text(highScore + "\n"); // same issue as PrevScoreInt
		HighScoreInt.setFont(textItalic);
		HighScoreInt.setFill(Color.GOLD);
		HighScoreInt.setTranslateX(-88);
		HighScoreInt.setTranslateY(460);
		HighScoreInt.setTextAlignment(TextAlignment.CENTER);

		returnButton = new Button("MAIN MENU");

		returnButton.addEventHandler(ActionEvent.ACTION, (e)->{
			primaryStage.setScene(titleScene);
			
			currScore = 0;
			});
			
		returnButton.setTextAlignment(TextAlignment.CENTER);
		returnButton.setFont(textFont);
		returnButton.setStyle("-fx-background-color: gold");
		returnButton.setTranslateX(115);
		returnButton.setTranslateY(350);

		endPane = new FlowPane(gameOver, PrevHighScoreText, PrevScoreInt, HighScoreInt, returnButton);

		endGroup = new Group(backgroundGIFEnd, endPane);
	}
}

//FIN