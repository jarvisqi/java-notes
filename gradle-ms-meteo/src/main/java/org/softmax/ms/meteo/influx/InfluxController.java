package org.softmax.ms.meteo.influx;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/influx", name = "InfluxController")
public class InfluxController {

    private Logger logger = LoggerFactory.getLogger(InfluxController.class);

    @Autowired
    private InfluxDB influxDB;

    @RequestMapping(value = "save")
    public void insert() {

        influxDB.createDatabase("host_cpu");

        String measurement = "host_cpu_usage_total";
        Map<String, String> tags = new HashMap<>();
        tags.put("host_name", "host2");
        tags.put("cpu_core", "core0");

        Map<String, Object> fields = new HashMap<>();
        fields.put("cpu_usage", 0.22);
        fields.put("cpu_idle", 0.56);

        Point.Builder builder = Point.measurement(measurement);
        builder.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        builder.tag(tags);
        builder.fields(fields);

        influxDB.write(builder.build());

        logger.info("finished");

    }

    @RequestMapping(value = "query")
    public void query() {

        String command = "select * from host_cpu_usage_total";

        QueryResult query = influxDB.query(new Query(command));

        List<QueryResult.Result> results = query.getResults();
        logger.info("results");

    }

}
