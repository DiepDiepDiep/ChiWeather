import javafx.scene.control.TextArea;
import weather.Properties;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//Same constructor behavior as super class Scene1VBox
public class HourlyVBox extends Scene1VBox{
    public HourlyVBox(Properties hourlyForecast, int day) {
        super(hourlyForecast, day);
    }



    //Specific text set for HourlyVBox objects
    @Override
    void setText(TextArea tf) {
        LocalTime curr= LocalTime.now().plusHours(day);
        String currTime = curr.format(DateTimeFormatter.ofPattern("hh:mm a"));
        tf.setText(String.valueOf(""+currTime+"\n"+hourlyForecast.periods.get(day).temperature)+" °"+hourlyForecast.periods.get(day).temperatureUnit);
    }
}
