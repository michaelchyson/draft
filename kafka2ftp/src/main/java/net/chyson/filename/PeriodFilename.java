package net.chyson.filename;


import net.chyson.config.Config;
import net.chyson.period.PeriodRunner;

import java.text.SimpleDateFormat;


public class PeriodFilename implements FileNameInterface, Runnable {
    private PeriodRunner runner;
    private String filename;
    private SimpleDateFormat dateFormat;


    private String prefix;
    private String suffix;
    private String path;
    private String label;
    private int period;


    public PeriodFilename(Config config) {
        this.prefix = config.getFtp().getCommon().getProperty("prefix");
        this.suffix = config.getFtp().getCommon().getProperty("suffix");
        this.path = config.getFtp().getCommon().getProperty("path");
        this.period = (int) config.getFtp().getCommon().get("period");
        this.label = config.getFtp().getSpecial()[config.getIndex()].getProperty("label");
        this.dateFormat = new SimpleDateFormat(config.getFtp().getCommon().getProperty("yyyyMMddHHmmss"));
        this.runner = new PeriodRunner();
        this.runner.scheduleAtFixedRate(this, period);
        run();
    }


    public String getFilename() {
        return filename;
    }

    @Override
    public void run() {
        String time = dateFormat.format(System.currentTimeMillis() / 1000 / 60 / period * 1000 * 60 * period);
        String day = time.substring(0, 8);
        String hour = time.substring(8, 10);
        String minute = time.substring(10, 12);
        String fullMinute = time.substring(0, 12);

        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("/");
        sb.append(prefix);
        sb.append("_");
        sb.append(fullMinute);
        sb.append("_");
        sb.append(label);
        sb.append(".");
        sb.append(suffix);
        sb.append(".tmp");

        this.filename = sb.toString();
    }


}
