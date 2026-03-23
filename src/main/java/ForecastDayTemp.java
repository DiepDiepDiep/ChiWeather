import weather.Period;

public interface ForecastDayTemp {
    ForecastDay BuildOverview(Period dPeriod, Period nPeriod);
}
