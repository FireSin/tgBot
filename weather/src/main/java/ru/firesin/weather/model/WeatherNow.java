package ru.firesin.weather.model;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author:    firesin
 * Date:      28.10.2023
 */

@Data
public class WeatherNow {
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private Sys sys;
    private String name;

    private String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(timestamp * 1000));
    }

    @Override
    public String toString() {
        return "Погода в городе " + name +
                "\n\nОблачность: " + weather.get(0).getDescription() +
                "\nТемпература: " + main.getTemp() + "°C " + getTemperatureEmoji(main.getTemp()) +
                "\nОщущается как: " + main.getFeelsLike() + "°C" +
                "\nСкорость ветра: " + wind.getSpeed() + "м/с 🌬️" +
                "\nДавление: " + main.getPressure() + "мм рт.ст." +
                "\nВлажность: " + main.getHumidity() + "%" +
                "\n🌅 Восход: " + formatTime(sys.getSunrise()) +
                "\n🌇 Закат: " + formatTime(sys.getSunset());
    }

    private String getTemperatureEmoji(double temperature) {
        if (temperature < -10) {
            return "❄️";
        } else if (temperature < 0) {
            return "🥶";
        } else if (temperature < 10) {
            return "😬";
        } else if (temperature < 20) {
            return "😊";
        } else {
            return "☀️";
        }
    }
}
