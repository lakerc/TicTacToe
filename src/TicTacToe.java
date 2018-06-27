import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TicTacToe extends Application{
	public static void main(String[] args){
		Application.launch(args);
	}
	public void start(Stage primaryStage){
		//single value array so it can be accessed in the event handler. cheeky
		int[] numClicks = {0};

		//ArrayLists to hold the cells that either X or O are occupying
		ArrayList<Integer> xCellsUsed = new ArrayList<>();
		ArrayList<Integer> oCellsUsed = new ArrayList<>();

		//600x650 window
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 600,650);

		//Classic tic tac toe lines
		Line line1 = new Line(200,600,200,0);
		Line line2 = new Line(400,600,400,0);
		Line line3 = new Line(0,200,600,200);
		Line line4 = new Line(0,400,600,400);

		//Win/Lose display text
		String style = "-fx-font: 15 arial";
		Text oWinsLbl = new Text(5,610, "O Wins: ");
		oWinsLbl.setStyle(style);

		Text oLossesLbl = new Text(5,625,"O Losses: ");
		oLossesLbl.setStyle(style);

		Text oRatioLbl = new Text(5,640, "Win/Loss Ratio: ");
		oRatioLbl.setStyle(style);

		Text xWinsLbl = new Text(465,610, "X Wins: ");
		xWinsLbl.setStyle(style);

		Text xLossesLbl = new Text(465,625, "X Losses: ");
		xLossesLbl.setStyle(style);

		Text xRatioLbl = new Text(465, 640, "Win/Loss Ratio: ");
		xRatioLbl.setStyle(style);

		Text oWins = new Text(60, 610, "0");
		oWins.setStyle(style);

		Text oLosses = new Text(75,625,"0");
		oLosses.setStyle(style);

		Text oRatio = new Text(115, 640, "0.0");
		oRatio.setStyle(style);

		Text xWins = new Text(520, 610, "0");
		xWins.setStyle(style);

		Text xLosses = new Text(535, 625, "0");
		xLosses.setStyle(style);

		Text xRatio = new Text(575, 640, "0.0");
		xRatio.setStyle(style);

		//Handle mouse clicks
		pane.setOnMouseClicked(event -> {
            //create our message box that will display win status or errors
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);

            //get coordinates of the mouse click
            double xPoss = event.getX();
            double yPoss = event.getY();

            //if user clicks outside of initial bounds, show an error window and do nothing
            if (xPoss < 0 || xPoss > 600 || yPoss < 0 || yPoss > 650){
                alert.setContentText("The place you clicked is invalid. Try again.");
                alert.showAndWait();
                return;
            }

            //refine those coordinates for placing the shapes
            int xPos = Helpers.pickPos(xPoss);
            int yPos = Helpers.pickPos(yPoss);

            //create an X
            Line xLine1 = new Line(xPos+10, yPos+10, xPos+180, yPos+180);
            Line xLine2 = new Line(xPos+180, yPos+10, xPos+10, yPos+180);

            //create an O
            Circle circ = new Circle(xPos+100, yPos+100, 90, Color.WHITE);
            circ.setStroke(Color.BLACK);

            //if the clicked cell isn't in use, handle X and O placement
            if (!Helpers.cellInUse(Helpers.pickPos(xPos), Helpers.pickPos(yPos), xCellsUsed, oCellsUsed)){
                //even numbered clicks are X
                if (numClicks[0]%2 == 0) {
                    //mark this cell as being used by X and add the lines
                    xCellsUsed.add(Helpers.getCell(Helpers.pickPos(xPos), Helpers.pickPos(yPos)));
                    pane.getChildren().addAll(xLine1, xLine2);
                }
                //odd numbered clicks are O
                else{
                    //mark this cell as being used by O and add the circle
                    oCellsUsed.add(Helpers.getCell(Helpers.pickPos(xPos), Helpers.pickPos(yPos)));
                    pane.getChildren().add(circ);
                }
                //increment our numclicks for handling X and O placement
                numClicks[0]++;
            }
            //Check to see if X wins
            if (xCellsUsed.size() >= 3){
                if (Helpers.checkWin(xCellsUsed)){
                    //update our win labels and stuff
                    updateCount(xWins, xLosses, xRatio, oLosses, oWins, oRatio);
                    //set our window content and display it
                    alert.setContentText("X Wins!!");
                    alert.showAndWait();
                    //reset the UI
                    reset(xCellsUsed, oCellsUsed, numClicks, pane);
                    //re-add everything to the pane
                    pane.getChildren().addAll(line1,line2,line3,line4,oLossesLbl,oWinsLbl,oRatioLbl,xWinsLbl,xLossesLbl,xRatioLbl,oWins
                            ,oLosses, oRatio, xWins, xLosses, xRatio);
                }
            }
            //Check to see if O wins
            if (oCellsUsed.size() >= 3){
                if (Helpers.checkWin(oCellsUsed)){
                    //update our win labels and ratios
                    updateCount(oWins,oLosses,oRatio,xLosses, xWins, xRatio);
                    //set our window content and display it
                    alert.setContentText("O Wins!!");
                    alert.showAndWait();
                    //reset the UI
                    reset(xCellsUsed, oCellsUsed, numClicks, pane);
                    //re-add everything to the pane
                    pane.getChildren().addAll(line1,line2,line3,line4,oLossesLbl,oWinsLbl,oRatioLbl,xWinsLbl,xLossesLbl,xRatioLbl,oWins
                            ,oLosses, oRatio, xWins, xLosses, xRatio);
                }
            }
            //Check for a tie
            if (xCellsUsed.size() + oCellsUsed.size() == 9){
                alert.setContentText("It's a tie!");
                alert.showAndWait();
                //reset the UI
                reset(xCellsUsed, oCellsUsed, numClicks, pane);
                //re-add everything to the pane
                pane.getChildren().addAll(line1,line2,line3,line4,oLossesLbl,oWinsLbl,oRatioLbl,xWinsLbl,xLossesLbl,xRatioLbl,oWins
                        ,oLosses, oRatio, xWins, xLosses, xRatio);
            }

        });
		//Add everything to the pane and display the window
		pane.getChildren().addAll(line1,line2,line3,line4,oLossesLbl,oWinsLbl,oRatioLbl,xWinsLbl,xLossesLbl,xRatioLbl,oWins
				,oLosses, oRatio, xWins, xLosses, xRatio);
		primaryStage.setScene(scene);
		//set window image
		primaryStage.getIcons().add(new Image("file:icon.png"));
		primaryStage.setTitle("Tic Tac Toe");
		primaryStage.show();
	}

	//clear the cells used, reset numClicks, and clear everything off the board
	public static void reset(ArrayList<Integer> xCells, ArrayList<Integer> oCells, int numClicks[], Pane pane){
		xCells.clear();
		oCells.clear();
		numClicks[0] = 0;
		//easier to clear the pane and re-add lines and ratios afterward
		pane.getChildren().clear();
	}

	//update the win/loss count
	public static void updateCount(Text aWins, Text aLosses, Text aRatio, Text bLosses, Text bWins, Text bRatio){
		int aWin = Integer.parseInt(aWins.getText());
		aWin++;
		aWins.setText(aWin + "");
		int bWin = Integer.parseInt(bWins.getText());
		int bLoss = Integer.parseInt(bLosses.getText());
		bLoss++;
		bLosses.setText(bLoss + "");
		updateRatio(aWin, aLosses, aRatio);
		updateRatio(bWin, bLosses, bRatio);
	}

	//update the win/loss ratio
	public static void updateRatio(int aWin, Text aLosses, Text aRatio){
		int aLoss = Integer.parseInt(aLosses.getText());
		if (Double.parseDouble(aLosses.getText()) == 0.0)
			aRatio.setText((aWin/1.0) + "");
		else{
			double ratio = (int)(100*(aWin/(aLoss+0.0)))/100.0;
			aRatio.setText(ratio + "");
		}
	}
}