import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import weather.Period;
import weather.Properties;
import weather.WeatherAPI;
import java.awt.*;
import java.util.Date;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.time.Instant;

import java.time.format.TextStyle;
import java.util.Locale;


public class JavaFX extends Application {

	TextArea temperature,weather;
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
	Button s2ButtSwitch;

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

	private class ButtonExitHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent actionEvent) {
			Platform.exit();
		}
	}


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

	public static double getDirection(String direction) {
		double angle = 0;

		if(direction.equals("N")) {
			angle = 0;
		}
		else if(direction.equals("NNE")) {
			angle = 22.5;
		}
		else if(direction.equals("NE")) {
			angle = 45;
		}
		else if(direction.equals("ENE")) {
			angle = 67.5;
		}
		else if(direction.equals("E")) {
			angle = 90;
		}
		else if(direction.equals("ESE")) {
			angle = 112.5;
		}
		else if(direction.equals("SE")) {
			angle = 135;
		}
		else if(direction.equals("SSE")) {
			angle = 157.5;
		}
		else if(direction.equals("S")) {
			angle = 180;
		}
		else if(direction.equals("SSW")) {
			angle = 202.5;
		}
		else if(direction.equals("SW")) {
			angle = 225;
		}
		else if(direction.equals("WSW")) {
			angle = 247.5;
		}
		else if(direction.equals("W")) {
			angle = 270;
		}
		else if(direction.equals("WNW")) {
			angle = 292.5;
		}
		else if(direction.equals("NW")) {
			angle = 315;
		}
		else if(direction.equals("NNW")) {
			angle = 337.5;
		}

		return angle;
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

		Properties hourlyForecast = WeatherAPI.getHourlyForecast("LOT",77,70);
		if (hourlyForecast == null){
			throw new RuntimeException("Hourly forecast did not load");
		}

		//Get the city/location of current location.


	//--------------------------------------------------------------------------------------------------------------------------------------------
		//Scene 1 Start

		//TOP HBOX
		temperature = new TextArea();
		temperature.setPrefWidth(700);
		temperature.setEditable(false);
		temperature.setMaxHeight(7);

		weather = new TextArea();
		weather.setWrapText(true);
		weather.setPrefWidth(700);
		weather.setPrefHeight(70);
		weather.setEditable(false);

		temperature.setText("Today's weather is: "+String.valueOf(forecast.get(0).temperature)+" °" + forecast.get(0).temperatureUnit);
		weather.setText("Today's conditions: "+forecast.get(0).detailedForecast);
		s1hbTemp = new HBox(20, temperature);
		s1hbWeather = new HBox(20, weather);

		//HBOX with hourly times
		s1vbHour1 = new HourlyVBox(hourlyForecast, 1).buildBox();
		s1vbHour2 = new HourlyVBox(hourlyForecast, 2).buildBox();
		s1vbHour3 = new HourlyVBox(hourlyForecast, 3).buildBox();
		s1vbHour4 = new HourlyVBox(hourlyForecast, 4).buildBox();
		s1vbHour5 = new HourlyVBox(hourlyForecast, 5).buildBox();
		s1vbHour6 = new HourlyVBox(hourlyForecast, 6).buildBox();
		s1vbHour7 = new HourlyVBox(hourlyForecast, 7).buildBox();
		s1hbHourlyTemp = new HBox(20, s1vbHour1, s1vbHour2, s1vbHour3, s1vbHour4, s1vbHour5, s1vbHour6, s1vbHour7);

		s1bThreeDayForecast = new Button("View 3 Day Forecast");
		s1bThreeDayForecast.setOnAction(e-> {
			primaryStage.setScene(s2);
		});


		s1vbMain = new VBox(20,s1hbTemp,s1hbWeather,s1hbHourlyTemp,s1bThreeDayForecast);
		s1bp = new BorderPane();
		s1bp.setPadding(new Insets(20));
		s1bp.setCenter(s1vbMain);

		s1 = new Scene(s1bp,700,700);

//--------------------------------------------------------------------------------------------------------------------------------------------
		//Start of Scene2
		ArrayList<Period> forecastTemp = WeatherAPI.getForecast("LOT",77,70);
		if (forecastTemp == null) {
			throw new RuntimeException("Forecast did not load");
		}
		//Set up the elements for the smaller VBox's

		int PerShift = 0; //Variable that determines if tomorrow is 1 period away or 2 period away

		if(!forecastTemp.get(0).isDaytime) {
			PerShift = 1;
		}
		else {
			PerShift = 2;
		}

		//s2D1Temp (Day, Day Temp, Night Temp)
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		 s2D1Day = new TextField();

		 //Get the current Date from the API
		 LocalDate localDate = forecastTemp.get(PerShift).startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 String dayName = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

		 //Set text for s2D1Day to day
		 s2D1Day.setText(dayName);

		 //Set settings for textField
		 s2D1Day.setEditable(false);
		 s2D1Day.setPrefHeight(35);

		//Set the border
		 s2D1Day.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
				 			"-fx-font-size: 30px; -fx-font-family: Arial; -fx-alignment: center-left");

		 //-------------------------
		 s2D1DTemp = new TextField();

		 //Get the current day temperature
		 s2D1DTemp.setText("Day: " + forecastTemp.get(PerShift).temperature + forecastTemp.get(PerShift).temperatureUnit);

		//Set settings for textField
		s2D1DTemp.setEditable(false);
		s2D1DTemp.setPrefHeight(45);
		//s2D1NTemp.setPrefWidth(165);

		 s2D1DTemp.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;" +
				 			"-fx-font-size: 20px; -fx-font-family: Arial; -fx-alignment: center-left");
		 //-------------------------
		 s2D1NTemp = new TextField();

		 s2D1NTemp.setText("Night: " + forecastTemp.get(PerShift + 1).temperature + forecastTemp.get(PerShift + 1).temperatureUnit);

		//Set settings for textField
		s2D1NTemp.setEditable(false);
		s2D1NTemp.setPrefHeight(35);

		 s2D1NTemp.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;" +
				 			"-fx-font-size: 20px; -fx-font-family: Arial; -fx-alignment: center-left");
	    //-------------------------
		s2vbD1Temp = new VBox(1, s2D1Day, s2D1DTemp,s2D1NTemp);
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		//s2D1Forecast (Icons)
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		Image Dforecast = new Image(forecastTemp.get(PerShift).icon);

		s2D1Dforecast = new ImageView(Dforecast);
		//-------------------------
		Image Nforecast = new Image(forecastTemp.get(PerShift).icon);

		s2D1Nforecast = new ImageView(Nforecast);
		//-------------------------
		s2vbD1forecast = new VBox(10, s2D1Dforecast, s2D1Nforecast);
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		//s2D1Speed (Wind Speed)
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		s2D1Speed = new TextField();

		//Get the current wind speed.
		s2D1Speed.setText(forecastTemp.get(PerShift).windSpeed);

		s2D1Speed.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

		s2D1Speed.setAlignment(Pos.CENTER);
		//-------------------------
		s2vbD1Speed = new VBox(10, s2D1Speed);
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		//s2D1Direction (Wind Direction)
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		Image compass = new Image(getClass().getResourceAsStream("/compass.png"));
		if(compass == null) {
			throw new RuntimeException("Compass Image did not load");
		}

		s2D1Compass = new ImageView(compass);

		//Scale image
		s2D1Compass.setPreserveRatio(true);
		s2D1Compass.setSmooth(true);
		s2D1Compass.setFitHeight(200);
		s2D1Compass.setFitWidth(200);
		//-------------------------
		Image arrow = new Image(getClass().getResourceAsStream("/compass_arrow_north.png"));
		if(arrow == null) {
			throw new RuntimeException("Arrow Image did not load");
		}

		s2D1Arrow = new ImageView(arrow);

		//Find the direction the arrow faces
		Double direction = getDirection(forecastTemp.get(PerShift).windDirection);

		//Rotate Image
		s2D1Arrow.setRotate(direction);

		//Scale image
		s2D1Arrow.setPreserveRatio(true);
		s2D1Arrow.setSmooth(true);
		s2D1Arrow.setFitHeight(100);
		s2D1Arrow.setFitWidth(100);
		//-------------------------
		s2D1Direction = new StackPane(s2D1Compass, s2D1Arrow);
		//-------------------------
		s2vbD1Direction = new VBox(10, s2D1Direction);
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		s2hbDay1 = new HBox(10,s2vbD1Temp,s2vbD1forecast,s2vbD1Speed,s2vbD1Direction);

		s2hbDay1.setStyle("-fx-border-color: black; -fx-border-width: 2;");

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		s2ButtSwitch = new Button();
		s2ButtSwitch.setText("Back");
		s2ButtSwitch.setOnAction(e->{
			primaryStage.setScene(s1);
		});
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		s2vbMain = new VBox(s2ButtSwitch, s2hbDay1);

		s2 = new Scene(s2vbMain, 700, 700);

		//End of Scene2

//--------------------------------------------------------------------------------------------------------------------------------------------

		//Default scene shown
		primaryStage.setScene(s1);
		primaryStage.show();
	}

}
