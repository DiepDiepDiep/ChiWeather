import weather.Period;

public interface ForecastDayTemp {
    //Function to build the VBox that shows curr day, day temp, and night temp.
    public void BuildTempVBox(Period dPeriod, Period nPeriod, ForecastDay Day);

    //Function to build the VBox that shows the icons that show that the forecast for that day is
    public void BuildForecastVBox(Period dPeriod, Period nPeriod, ForecastDay Day);

    //Function to build the VBox that shows the wind speed for that day
    public void BuildSpeedVBox(Period dPeriod, ForecastDay Day);

    //Fucntion to build the VBox that shows the wind direction
    public void BuildDirectionVBox(Period dPeriod, ForecastDay Day);

    //Fucntion to build the Day Overview HBox
    ForecastDay BuildOverview(Period dPeriod, Period nPeriod);
}
