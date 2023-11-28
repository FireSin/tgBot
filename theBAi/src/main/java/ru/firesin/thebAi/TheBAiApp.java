package ru.firesin.thebAi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@SpringBootApplication(scanBasePackages = "ru.firesin")
public class TheBAiApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(TheBAiApp.class, args);
    }
}
