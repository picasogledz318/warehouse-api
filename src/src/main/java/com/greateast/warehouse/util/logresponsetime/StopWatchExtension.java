package com.greateast.warehouse.util.logresponsetime;

import org.springframework.util.StopWatch;

import java.text.NumberFormat;

public class StopWatchExtension extends StopWatch {


    public String shortSummary(StopWatch stopWatch) {
       return "StopWatch '" + stopWatch.getId() + "': running time = " + stopWatch.getTotalTimeNanos()/1000000 + " ms";
    }

    public String prettyPrintInMilliseconds(StopWatch stopWatch) {
        StringBuilder sb = new StringBuilder(shortSummary(stopWatch));
        sb.append('\n');

            sb.append("---------------------------------------------\n");
            sb.append("ms         %     Task name\n");
            sb.append("---------------------------------------------\n");
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumIntegerDigits(9);
            nf.setGroupingUsed(false);
            NumberFormat pf = NumberFormat.getPercentInstance();
            pf.setMinimumIntegerDigits(3);
            pf.setGroupingUsed(false);
            TaskInfo[] var4 = stopWatch.getTaskInfo();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                TaskInfo task = var4[var6];
                long timeMillis = task.getTimeNanos()/1000000;
                long totalTimeMillis = stopWatch.getTotalTimeNanos()/1000000;
                sb.append(nf.format(timeMillis)).append("  ");
                sb.append(pf.format((double)timeMillis / (double)totalTimeMillis)).append("  ");
                sb.append(task.getTaskName()).append("\n");
            }

        return sb.toString();
    }
}
