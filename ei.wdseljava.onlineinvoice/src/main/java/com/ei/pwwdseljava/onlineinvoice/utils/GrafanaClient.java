package com.ei.pwwdseljava.onlineinvoice.utils;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

public class GrafanaClient {

    private static final String INFLUXDB_URL = "http://localhost:8086";
    private static final String DATABASE = "test_results";
    private static InfluxDB influxDB;

    static {
        influxDB = InfluxDBFactory.connect(INFLUXDB_URL);
        influxDB.setDatabase(DATABASE);
    }

    public static void sendTestResult(String testName, String status) {
        influxDB.write(Point.measurement("test_results")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("test_name", testName)
                .addField("status", status)
                .build());
    }
}
