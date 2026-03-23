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
	Button s2ButtSwitch;

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

		LocalTime curr= LocalTime.now();
		String currTime = curr.format(DateTimeFormatter.ofPattern("hh:mm a"));

		temperature.setText(""+currTime+"\nToday's weather is: "+String.valueOf(forecast.get(0).temperature)+" °" + forecast.get(0).temperatureUnit);
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
		s2ButtSwitch = new Button();
		s2ButtSwitch.setText("Back");
		s2ButtSwitch.setOnAction(e ->
				primaryStage.setScene(s1));
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		s2bpTitle = new BorderPane();
		s2bpTitle.setLeft(s2ButtSwitch);
		s2bpTitle.setCenter(s2tfTitle);


		WeatherDayForecast factory = new WeatherDayForecast();

		ForecastDay Day1 = factory.BuildOverview(forecast.get(PerShift), forecast.get(PerShift + 1));
		ForecastDay Day2 = factory.BuildOverview(forecast.get(PerShift + 2), forecast.get(PerShift + 3));
		ForecastDay Day3 = factory.BuildOverview(forecast.get(PerShift + 4), forecast.get(PerShift + 5));

		s2vbMain = new VBox(s2bpTitle, Day1.Overview, Day2.Overview, Day3.Overview);

		s2 = new Scene(s2vbMain);
//--------------------------------------------------------------------------------------------------------------------------------------------

		//Default scene shown
		primaryStage.setScene(s2);
		primaryStage.show();
	}

}
