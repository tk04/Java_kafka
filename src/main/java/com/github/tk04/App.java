package com.github.tk04;

import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Properties props = new Properties(); //producer properties
        props.setProperty("bootstrap.servers", "localhost:9092");
    }
}
