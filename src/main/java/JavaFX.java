import javafx.application.Application;

import javafx.scene.Scene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;

public class JavaFX extends Application {
	TextField temperature,weather;


	//Scene 1 attributes
	Scene s1;
	VBox s1vbMain, s1vbHour1, s1vbHour2, s1vbHour3, s1vbHour4, s1vbHour5, s1vbHour6, s1vbHour7;
	HBox s1hbTemp, s1hbWeather, s1hbHourlyTemp, s1hbButton;
	Button s1bThreeDayForecast;
	BorderPane s1bp;

	//Scene 2 attributes
	Scene s2;
	VBox s2vbMain;
	HBox s2hbTitle, s2hbDay1, s2hbDay2, s2hbDay3;

	VBox s2vbD1Temp, s2vbD1Forcast, s2vbD1Speed, s2vbD1Direction;
	TextField D1Day, D1DTemp, D1NTemp, D1Speed;
	ImageView D1DForcast, D1NForcast, D1Arrow, D1Compass;
	StackPane D1Direction;

	VBox s2vbD2Temp, s2vbD2Forcast, s2vbD2Speed, s2vbD2Direction;
	TextField D2Day, D2DTemp, D2NTemp, D2Speed;
	ImageView D2DForcast, D2NForcast, D2Arrow, D2Compass;
	StackPane D2Direction;

	VBox s2vbD3Temp, s2vbD3Forcast, s2vbD3Speed, s2vbD3Direction;
	TextField D3Day, D3DTemp, D3NTemp, D3Speed;
	ImageView D3DForcast, D3NForcast, D3Arrow, D3Compass;
	StackPane D3Direction;



	public static void main(String[] args) {
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("I'm a professional Weather App!");
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}
		temperature = new TextField();
		weather = new TextField();
		temperature.setText("Today's weather is: "+String.valueOf(forecast.get(0).temperature));
		weather.setText(forecast.get(0).shortForecast);
		
		s1hbTemp = new HBox(20, temperature);
		s1hbWeather = new HBox(20, weather);

		//Will add more components to main's vbox. Just used to display for now
		s1vbMain = new VBox(20,s1hbTemp, s1hbWeather);
		s1bp = new BorderPane();
		s1bp.setPadding(new Insets(20));
		s1bp.setCenter(s1vbMain);
		s1 = new Scene(s1bp, 700, 700);
		//End of Scene1



		//Everything below is start of scene2


		//Default scene shown
		primaryStage.setScene(s1);
		primaryStage.show();
	}

}
