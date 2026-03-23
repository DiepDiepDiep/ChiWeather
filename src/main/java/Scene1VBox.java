import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import weather.Properties;

public abstract class Scene1VBox {
    protected Properties hourlyForecast;
    protected int day;

    public Scene1VBox(Properties hourlyForecast, int day) {
        this.hourlyForecast = hourlyForecast;
        this.day = day;
    }

    // Template method design pattern
    public VBox buildBox() {
        TextArea tf = new TextArea();
        tf.setPrefHeight(75);
        tf.setEditable(false);
        setText(tf);
        tf.setStyle("-fx-font-size: 14; -fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 2; ");
        VBox vb = new VBox(20, tf);
        vb.setPrefWidth(125);
        vb.setPrefHeight(125);
        return vb;
    }

    abstract void setText(TextArea tf);
}
