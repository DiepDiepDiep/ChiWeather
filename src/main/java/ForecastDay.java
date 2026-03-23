import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import weather.Period;

public class ForecastDay {
    public Period time;

    public VBox VBoxTemp, VBForecast, VBSpeed, VBDirection;
    public TextField CurrDay, DayTemp, NightTemp, WindSpeed;
    public ImageView DayForecast, NightForecast, WindArrow, WindCompass;
    public StackPane WindDirection;
    public HBox Overview;
}
