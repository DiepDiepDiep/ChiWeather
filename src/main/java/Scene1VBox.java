import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import weather.Properties;

public abstract class Scene1VBox {
    protected Properties hourlyForecast;
    protected int day;

    public Scene1VBox(Properties hourlyForecast, int day) {
        this.hourlyForecast = hourlyForecast;
        this.day = day;
    }

    // Template method
    public VBox buildBox() {
        TextField tf = new TextField();
        tf.setEditable(false);
        setText(tf);
        VBox vb = new VBox(20, tf);
        vb.setPrefWidth(75);
        return vb;
    }

    abstract void setText(TextField tf);
}
