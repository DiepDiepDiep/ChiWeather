import javafx.scene.control.TextField;
import weather.Properties;

public class HourlyVBox extends Scene1VBox{
    public HourlyVBox(Properties hourlyForecast, int day) {
        super(hourlyForecast, day);
    }

    @Override
    void setText(TextField tf) {
        tf.setText(String.valueOf(hourlyForecast.periods.get(day).temperature));
    }
}
