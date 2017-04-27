public class Config {
    public static final int PORT = System.getProperty("test.port") != null ? Integer.getInteger("test.port") : 45310;
    public static final int MESSAGE_CHARACTER_LENGTH = System.getProperty("test.length") != null ? Integer.getInteger("test.length") : 500;
    public static final int WRITE_BATCH_SIZE = System.getProperty("test.batch") != null ? Integer.getInteger("test.batch") : 10;
    public static final int SLEEP_BETWEEN_BATCHES_MILLIS = System.getProperty("test.sleep") != null ? Integer.getInteger("test.sleep") : 1;
    public static final int MEMORY_LOG_INTERVAL_MILLIS = System.getProperty("test.log_time") != null ? Integer.getInteger("test.log_time") : 10*1000;
    public static final String MESSAGE_DELIMITER = "\n";
}
