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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;
import java.util.ArrayList;

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
	BorderPane s2bpTitle;
	TextField s2tfTitle;
	Button s2BackSwitch, s2ConvertSwitch;

	//Scene 3 attributes
	Scene s3;
	VBox s3vbMain;
	BorderPane s3bpTitle;
	TextField s3tfTitle;
	Button s3BackSwitch, s3ConvertSwitch;


	private class ButtonExitHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent actionEvent) {
			Platform.exit();
		}
	}



	public static void main(String[] args) {
		launch(args);
	}


	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("ChiWeather");
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

		//TextArea that displays the current time and temperature in degrees Fahrenheit
		temperature = new TextArea();
		temperature.setPrefWidth(700);
		temperature.setEditable(false);
		temperature.setPrefHeight(120);
		temperature.setStyle("-fx-font-size: 25px; -fx-text-fill: #263CEB; -fx-background-color: white; -fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #263CEB; -fx-border-width: 2;");
		//Takes the current time and parses into a common format (hours:minutes (am/pm))
		LocalTime curr= LocalTime.now();
		String currTime = curr.format(DateTimeFormatter.ofPattern("hh:mm a"));

		//Adds the current time and current temperature to be displayed within the textArea
		temperature.setText(""+currTime+"\nChicago, IL\n"+String.valueOf(forecast.get(0).temperature)+" °" + forecast.get(0).temperatureUnit);

		//TextArea that displays a detailed description/forecast of the current weather conditions
		weather = new TextArea();
		weather.setWrapText(true);
		weather.setPrefWidth(700);
		weather.setPrefHeight(150);
		weather.setEditable(false);
		weather.setStyle("-fx-font-size: 21px; -fx-text-fill: #263CEB; -fx-background-color: white; -fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #263CEB; -fx-border-width: 2;");
		weather.setText("Conditions for "+LocalDate.now() +"\n"+ forecast.get(0).detailedForecast);
		//Adds the both the temperature and weather descriptions into HBoxes
		s1hbTemp = new HBox(20, temperature);
		s1hbWeather = new HBox(20, weather);

		//Displays temp in VBox 1 hours from the current time
		s1vbHour1 = new HourlyVBox(hourlyForecast, 1).buildBox();

		//Displays temp in VBox 2 hours from the current time
		s1vbHour2 = new HourlyVBox(hourlyForecast, 2).buildBox();

		//Displays temp in VBox 3 hours from the current time
		s1vbHour3 = new HourlyVBox(hourlyForecast, 3).buildBox();

		//Displays temp in VBox 4 hours from the current time
		s1vbHour4 = new HourlyVBox(hourlyForecast, 4).buildBox();

		//Displays temp in VBox 5 hours from the current time
		s1vbHour5 = new HourlyVBox(hourlyForecast, 5).buildBox();

		//Displays temp in VBox 6 hours from the current time
		s1vbHour6 = new HourlyVBox(hourlyForecast, 6).buildBox();

		//Displays temp in VBox 7 hours from the current time
		s1vbHour7 = new HourlyVBox(hourlyForecast, 7).buildBox();

		//HBox that holds the hourly temperature VBoxes
		s1hbHourlyTemp = new HBox(20, s1vbHour1, s1vbHour2, s1vbHour3, s1vbHour4, s1vbHour5, s1vbHour6, s1vbHour7);

		//Button that, upon user interaction, switches to a scene that displays a 3-day weather forecast (scene2)
		s1bThreeDayForecast = new Button("View 3 Day Forecast");
		s1bThreeDayForecast.setPrefWidth(700);
		s1bThreeDayForecast.setPrefHeight(100);
		s1bThreeDayForecast.setStyle("-fx-font-size: 14px; -fx-background-color: #263CEB; -fx-text-fill: white; -fx-background-radius: 20; -fx-cursor: hand;");		s1bThreeDayForecast.setOnAction(e-> {
			primaryStage.setScene(s2);
		});

		//VBox which holds all the other previously created boxes
		s1vbMain = new VBox(20,s1hbTemp,s1hbWeather,s1hbHourlyTemp,s1bThreeDayForecast);

		//The BorderPane which sets the spacing for the main VBox, and is displayed on the scene
		s1bp = new BorderPane();
		s1bp.setPadding(new Insets(20));
		s1bp.setCenter(s1vbMain);

		//Creates scene1 with the BorderPane
		s1 = new Scene(s1bp,700,700);

//--------------------------------------------------------------------------------------------------------------------------------------------
		//Start of Scene2
		int PerShift = 0;

		if(!forecast.get(0).isDaytime) {
			PerShift = 1;
		}
		else {
			PerShift = 2;
		}

		//TextField for Location
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		s2tfTitle = new TextField();
		s2tfTitle.setText("Chicago");
		s2tfTitle.setEditable(false);
		s2tfTitle.setStyle("-fx-alignment: center; -fx-background-color: transparent; -fx-border-color: transparent;");
		s2tfTitle.setAlignment(Pos.CENTER);
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		//Button to go back to Scene1
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		s2BackSwitch = new Button();
		s2BackSwitch.setText("Back");
		s2BackSwitch.setOnAction(e ->
				primaryStage.setScene(s1));
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		//Button to go to Scene3
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		s2ConvertSwitch = new Button();
		s2ConvertSwitch.setText("Celsius");
		s2ConvertSwitch.setOnAction(e ->
				primaryStage.setScene(s3));
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		s2bpTitle = new BorderPane();
		s2bpTitle.setLeft(s2BackSwitch);
		s2bpTitle.setCenter(s2tfTitle);
		s2bpTitle.setRight(s2ConvertSwitch);


		WeatherDayForecast factoryF = new WeatherDayForecast();

		ForecastDay Day1F = factoryF.BuildOverview(forecast.get(PerShift), forecast.get(PerShift + 1));
		ForecastDay Day2F = factoryF.BuildOverview(forecast.get(PerShift + 2), forecast.get(PerShift + 3));
		ForecastDay Day3F = factoryF.BuildOverview(forecast.get(PerShift + 4), forecast.get(PerShift + 5));

		s2vbMain = new VBox(s2bpTitle, Day1F.Overview, Day2F.Overview, Day3F.Overview);

		s2 = new Scene(s2vbMain);
//--------------------------------------------------------------------------------------------------------------------------------------------
		//Start of Scene3
		//TextField for Location
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		s3tfTitle = new TextField();
		s3tfTitle.setText("Chicago");
		s3tfTitle.setEditable(false);
		s3tfTitle.setStyle("-fx-alignment: center; -fx-background-color: transparent; -fx-border-color: transparent;");
		s3tfTitle.setAlignment(Pos.CENTER);
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		//Button to go back to Scene1
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		s3BackSwitch = new Button();
		s3BackSwitch.setText("Back");
		s3BackSwitch.setOnAction(e ->
				primaryStage.setScene(s1));
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		//Button to go back to Scene2
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		s3ConvertSwitch = new Button();
		s3ConvertSwitch.setText("Fahrenheit");
		s3ConvertSwitch.setOnAction(e ->
				primaryStage.setScene(s2));
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		s3bpTitle = new BorderPane();
		s3bpTitle.setLeft(s3BackSwitch);
		s3bpTitle.setCenter(s3tfTitle);
		s3bpTitle.setRight(s3ConvertSwitch);


		WeatherDayForecastC factoryC = new WeatherDayForecastC();

		ForecastDay Day1C = factoryC.BuildOverview(forecast.get(PerShift), forecast.get(PerShift + 1));
		ForecastDay Day2C = factoryC.BuildOverview(forecast.get(PerShift + 2), forecast.get(PerShift + 3));
		ForecastDay Day3C = factoryC.BuildOverview(forecast.get(PerShift + 4), forecast.get(PerShift + 5));

		s3vbMain = new VBox(s3bpTitle, Day1C.Overview, Day2C.Overview, Day3C.Overview);

		s3 = new Scene(s3vbMain);
//--------------------------------------------------------------------------------------------------------------------------------------------


		//Default scene shown
		primaryStage.setScene(s1);
		primaryStage.show();
	}

}
