import java.util.logging.Logger;

public class LogUsage implements Runnable{
    private static final Logger logger = Logger.getLogger("memory");
    int lastMessageReceivedCount, lastMessageSentCount;
    long lastLogTimeMillis;

    @Override
    public void run() {
        while (true){
            try {
                logger.info(logMemoryUsage());
                lastLogTimeMillis = System.currentTimeMillis();
                lastMessageReceivedCount = Main.getMessagesReceived();
                lastMessageSentCount = Main.getMessagesSent();
                Thread.sleep(Config.MEMORY_LOG_INTERVAL_MILLIS);
            } catch (Exception ex) {

            }
        }
    }

    //memory usage in mb
    private String logMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long total = runtime.totalMemory()/1048576, max = runtime.maxMemory()/1048576, free = runtime.freeMemory()/1048576;
        long used = total - free;
        float dtSec = (System.currentTimeMillis() - lastLogTimeMillis)/1000;
        int messageReceivedAvg = (int)((Main.getMessagesReceived() - lastMessageReceivedCount)/dtSec);
        int messageSentAvg = (int)((Main.getMessagesSent() - lastMessageSentCount)/dtSec);

        return String.format("%d,%d,%d,%d,%d,%d,%d",System.currentTimeMillis(),total,max,free,used,messageReceivedAvg,messageSentAvg);
    }
}

