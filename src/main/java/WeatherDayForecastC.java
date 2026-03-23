import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import weather.Period;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

public class WeatherDayForecastC implements ForecastDayTemp {
    private static double getDirection(String direction) {
        double angle = 0;

        switch(direction) {
            case "N": angle = 0; break;
            case "NNE": angle = 22.5; break;
            case "NE": angle = 45; break;
            case "ENE": angle = 67.5; break;
            case "E": angle = 90; break;
            case "ESE": angle = 112.5; break;
            case "SE": angle = 135; break;
            case "SSE": angle = 157.5; break;
            case "S": angle = 180; break;
            case "SSW": angle = 202.5; break;
            case "SW": angle = 225; break;
            case "WSW": angle = 247.5; break;
            case "W": angle = 270; break;
            case "WNW": angle = 292.5; break;
            case "NW": angle = 315; break;
            case "NNW": angle = 337.5; break;
        }

        return angle;
    }

    private static double convertToC(int Degrees){
        return (Degrees - 32) * (5.0/9.0);
    }

    private static String cleanStringforNum(String line) {
        String num = "0123456789";
        String clean = "";
        if(line.contains("to")) {
            int toIndex = line.indexOf('t');
            String begin = "";
            String end = "";

            //Get the first number
            for(char x : line.toCharArray()) {
                if(x == 't') {
                    break;
                }
                else if(num.indexOf(x) != -1) {
                    begin += x;
                }
            }

            //Get the second number
            for(char x : line.substring((toIndex + 1)).toCharArray()) {
                if(num.indexOf(x) != -1) {
                    end += x;
                }
            }

            //Make the string
            double first = Double.parseDouble(begin) * 1.60934;
            double last = Double.parseDouble(end) * 1.60934;

            clean = (int)first + " to " + (int)last;
        }
        else {
            for(char x : line.toCharArray()) {
                if(num.indexOf(x) != -1) {
                    clean += x;
                }
            }

            double kmh = Double.parseDouble(clean) * 1.60934;

            clean = String.valueOf((int)kmh);
        }

        return clean;
    }

    //Function to build the VBox that shows curr day, day temp, and night temp.
    @Override
    public void BuildTempVBox(Period dPeriod, Period nPeriod, ForecastDay Day) {
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.CurrDay = new TextField();

        //Get the current Date from the API
        LocalDate localDate = dPeriod.startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String dayName = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

        //Set text for CurrDay to day
        Day.CurrDay.setText(dayName);

        //Set settings for textField
        Day.CurrDay.setEditable(false);
        Day.CurrDay.setPrefHeight(35);

        //Set the border
        Day.CurrDay.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;" +
                "-fx-font-size: 30px; -fx-font-family: Arial; -fx-alignment: center-left");
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.DayTemp = new TextField();

        //Get the current day temperature and convert it to Celsius
        Day.DayTemp.setText("Day: " + (int)convertToC(dPeriod.temperature) + " °C");

        //Set settings for textField
        Day.DayTemp.setEditable(false);
        Day.DayTemp.setPrefHeight(45);

        //Set the border
        Day.DayTemp.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;" +
                "-fx-font-size: 20px; -fx-font-family: Arial; -fx-alignment: center-left");
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.NightTemp = new TextField();

        //Get the night temperature and convert it to Celsius

        Day.NightTemp.setText("Night: " + (int)convertToC(nPeriod.temperature) + " °C");

        //Set settings for textField
        Day.NightTemp.setEditable(false);
        Day.NightTemp.setPrefHeight(35);

        //Set the border
        Day.NightTemp.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;" +
                "-fx-font-size: 20px; -fx-font-family: Arial; -fx-alignment: center-left");
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.VBoxTemp = new VBox(1, Day.CurrDay, Day.DayTemp, Day.NightTemp);
    }

    //Function to build the VBox that shows the icons that show that the forecast for that day is
    @Override
    public void BuildForecastVBox(Period dPeriod, Period nPeriod, ForecastDay Day) {
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Image Dforecast = new Image(dPeriod.icon);

        Day.DayForecast = new ImageView(Dforecast);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Image Nforecast = new Image(nPeriod.icon);

        Day.NightForecast = new ImageView(Nforecast);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.VBForecast = new VBox(10, Day.DayForecast, Day.NightForecast);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }

    //Function to build the VBox that shows the wind speed for that day
    @Override
    public void BuildSpeedVBox(Period dPeriod, ForecastDay Day) {
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.WindSpeed = new TextField();

        //Get the current wind speed.
        Day.WindSpeed.setText(cleanStringforNum(dPeriod.windSpeed) + " kmh");

        Day.WindSpeed.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        Day.WindSpeed.setAlignment(Pos.CENTER);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.VBSpeed = new VBox(10, Day.WindSpeed);
    }

    //Fucntion to build the VBox that shows the wind direction
    @Override
    public void BuildDirectionVBox(Period dPeriod, ForecastDay Day) {
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Image compass = new Image(getClass().getResourceAsStream("/compass.png"));
        if(compass == null) {
            throw new RuntimeException("Compass Image did not load");
        }

        Day.WindCompass = new ImageView(compass);

        //Scale image
        Day.WindCompass.setPreserveRatio(true);
        Day.WindCompass.setSmooth(true);
        Day.WindCompass.setFitHeight(200);
        Day.WindCompass.setFitWidth(200);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Image arrow = new Image(getClass().getResourceAsStream("/compass_arrow_north.png"));
        if(arrow == null) {
            throw new RuntimeException("Arrow Image did not load");
        }

        Day.WindArrow = new ImageView(arrow);

        //Find the direction the arrow faces
        double direction = getDirection(dPeriod.windDirection);

        //Rotate Image
        Day.WindArrow.setRotate(direction);

        //Scale image
        Day.WindArrow.setPreserveRatio(true);
        Day.WindArrow.setSmooth(true);
        Day.WindArrow.setFitHeight(100);
        Day.WindArrow.setFitWidth(100);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.WindDirection = new StackPane(Day.WindCompass, Day.WindArrow);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.VBDirection = new VBox(10, Day.WindDirection);
    }

    @Override
    public ForecastDay BuildOverview(Period dPeriod, Period nPeriod) {
        ForecastDay Day = new ForecastDay();

        //VBox for the Current Day, Day temp, and Night temp
        BuildTempVBox(dPeriod, nPeriod, Day);

        //VBox for the Forecast (Icons)
        BuildForecastVBox(dPeriod, nPeriod, Day);

        //VBox for the wind speed
        BuildSpeedVBox(dPeriod, Day);

        //s2D1Direction (Wind Direction)
        BuildDirectionVBox(dPeriod, Day);

        Day.Overview = new HBox(10, Day.VBoxTemp, Day.VBForecast, Day.VBSpeed, Day.VBDirection);

        Day.Overview.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        return Day;
    }
}
