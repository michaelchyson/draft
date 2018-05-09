package net.chyson.filename;


import net.chyson.config.Config;
import net.chyson.period.PeriodRunner;

import java.text.SimpleDateFormat;


public class PeriodFilename implements FileNameInterface, Runnable {
    /**
     * generate filename at the fix rate
     */
    private PeriodRunner runner;
    /**
     * hadoop filename
     */
    private String filename;
    /**
     * the time format contained in the hadoop filepath and filename
     */
    private SimpleDateFormat dateFormat;


    //filename: path/subdir/prefix_time_label.suffix
    private String prefix;
    private String suffix;
    private String path;
    private String label;
    /**
     * how long to generate a new filename
     */
    private int period;
    private String subdir;


    public PeriodFilename(Config config) {
        this.prefix = config.getHdfs().getCommon().getProperty("prefix");
        this.suffix = config.getHdfs().getCommon().getProperty("suffix");
        this.path = config.getHdfs().getCommon().getProperty("path");
        this.subdir = config.getHdfs().getCommon().getProperty("subdir");
        this.period = (int) config.getHdfs().getCommon().get("period");
        this.label = config.getHdfs().getSpecial()[config.getLabel()].getProperty("label");
        this.dateFormat = new SimpleDateFormat(config.getHdfs().getCommon().getProperty("yyyyMMddHHmmss"));
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
        if ("no".equals(subdir)) {

        } else if ("day".equals(subdir)) {
            sb.append("/");
            sb.append(day);
        } else if ("hour".equals(subdir)) {
            sb.append("/");
            sb.append(day);
            sb.append("/");
            sb.append(hour);
        } else {
            sb.append("/");
            sb.append(day);
            sb.append("/");
            sb.append(hour);
            sb.append("/");
            sb.append(minute);
        }

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
