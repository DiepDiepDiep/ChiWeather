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

public class WeatherDayForecast implements ForecastDayTemp {
    private static double getDirection(String direction) {
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

    @Override
    public ForecastDay BuildOverview(Period dPeriod, Period nPeriod) {
        ForecastDay Day = new ForecastDay();
        
        //VBox for the Current Day, Day temp, and Night temp
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
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.DayTemp = new TextField();

        //Get the current day temperature
        Day.DayTemp.setText("Day: " + dPeriod.temperature + dPeriod.temperatureUnit);

        //Set settings for textField
        Day.DayTemp.setEditable(false);
        Day.DayTemp.setPrefHeight(45);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.NightTemp = new TextField();

        Day.NightTemp.setText("Night: " + nPeriod.temperature + nPeriod.temperatureUnit);

        //Set settings for textField
        Day.NightTemp.setEditable(false);
        Day.NightTemp.setPrefHeight(35);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.VBoxTemp = new VBox(1, Day.CurrDay, Day.DayTemp, Day.NightTemp);

        //VBox for the Forecast (Icons)
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Image Dforecast = new Image(dPeriod.icon);

        Day.DayForecast = new ImageView(Dforecast);
        //-------------------------
        Image Nforecast = new Image(dPeriod.icon);

        Day.NightForecast = new ImageView(Nforecast);
        //-------------------------
        Day.VBForecast = new VBox(10, Day.DayForecast, Day.NightForecast);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        //VBox for the wind speed
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.WindSpeed = new TextField();

        //Get the current wind speed.
        Day.WindSpeed.setText(dPeriod.windSpeed);

        Day.WindSpeed.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        Day.WindSpeed.setAlignment(Pos.CENTER);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Day.VBForecast = new VBox(10, Day.WindSpeed);

        //s2D1Direction (Wind Direction)
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
        Double direction = getDirection(dPeriod.windDirection);

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

        Day.Overview = new HBox(10, Day.VBoxTemp, Day.VBForecast, Day.VBSpeed, Day.VBDirection);

        return Day;
    }
}
