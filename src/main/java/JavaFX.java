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

import java.util.Date;

import javax.swing.text.html.ImageView;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.time.Instant;

import java.time.format.TextStyle;
import java.util.Locale;

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

	VBox s2vbD1Temp, s2vbD1forecast, s2vbD1Speed, s2vbD1Direction;
	TextField s2D1Day, s2D1DTemp, s2D1NTemp, s2D1Speed;
	ImageView s2D1Dforecast, s2D1Nforecast, s2D1Arrow, s2D1Compass;
	StackPane s2D1Direction;

	VBox s2vbD2Temp, s2vbD2forecast, s2vbD2Speed, s2vbD2Direction;
	TextField s2D2Day, s2D2DTemp, s2D2NTemp, s2D2Speed;
	ImageView s2D2Dforecast, s2D2Nforecast, s2D2Arrow, s2D2Compass;
	StackPane s2D2Direction;

	VBox s2vbD3Temp, s2vbD3forecast, s2vbD3Speed, s2vbD3Direction;
	TextField s2D3Day, s2D3DTemp, s2D3NTemp, s2D3Speed;
	ImageView s2D3Dforecast, s2D3Nforecast, s2D3Arrow, s2D3Compass;
	StackPane s2D3Direction;

	//--------------------------------------------------------------------------------------------------------------------------------------------
	//Scene1 Functions

	//--------------------------------------------------------------------------------------------------------------------------------------------
	//Scene2 Functions
	public class Locations {
		int Long;
		int Lat;
		String City;

		public void getLocation(String Loc) {
			String City = Loc;

		}
	}

	public static int getAvgDTemp(ArrayList<Period> Hourly, LocalDate Date) {
		int avg = 0, count = 0;
		for (Period p : Hourly) {
		}
		return 0;
	}

	//--------------------------------------------------------------------------------------------------------------------------------------------

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

		//Get the city/location of current location.


	//--------------------------------------------------------------------------------------------------------------------------------------------
		//Start of Scene 1
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

//--------------------------------------------------------------------------------------------------------------------------------------------
		//Start of Scene2
		ArrayList<Period> forecastTemp = WeatherAPI.getForecast("LOT",77,70);
		if (forecastTemp == null) {
			throw new RuntimeException("Forecast did not load");
		}
		//Set up the elements for the smaller VBox's

		//-------------------------
		 s2D1Day = new TextField();

		 //Get the current Date from the API
		 LocalDate localDate = forecastTemp.get(0).startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 String dayName = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

		 //Set text for s2D1Day to day
		 s2D1Day.setText(dayName);

		 //-------------------------
		 s2D1DTemp = new TextField();

		 //Get the current day temperature
		 s2D1DTemp.setText("Day: " + forecastTemp.get(0).temperature + forecastTemp.get(0).temperatureUnit);

		 //-------------------------
		 s2D1NTemp = new TextField();


		 s2 = new Scene(s2D1DTemp, 700, 700);

		//End of Scene2

//--------------------------------------------------------------------------------------------------------------------------------------------

		//Default scene shown
		primaryStage.setScene(s2);
		primaryStage.show();
	}

}
