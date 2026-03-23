import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import weather.Properties;

public abstract class Scene1VBox {
    protected Properties hourlyForecast;
    protected int day;


    //Parameterized Constructor that sets the values for the protected variables in an object
    public Scene1VBox(Properties hourlyForecast, int day) {
        this.hourlyForecast = hourlyForecast;
        this.day = day;
    }

    //Default definition for building a VBox
    public VBox buildBox() {
        TextArea tf = new TextArea();
        tf.setPrefHeight(120);
        tf.setEditable(false);
        setText(tf);
        tf.setStyle("-fx-font-size: 14; -fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 2; ");
        VBox vb = new VBox(20, tf);
        vb.setPrefWidth(130);
        vb.setPrefHeight(160);

        return vb;
    }

    //Abstract method for setting the text within a TextArea
    abstract void setText(TextArea tf);
}
