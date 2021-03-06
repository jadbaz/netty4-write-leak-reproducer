public class Config {
    public static final int PORT = Integer.getInteger("test.port",45310);
    public static final int MESSAGE_CHARACTER_LENGTH = Integer.getInteger("test.length",500);
    public static final int WRITE_BATCH_SIZE = Integer.getInteger("test.batch",10);
    public static final int SLEEP_BETWEEN_BATCHES_MILLIS = Integer.getInteger("test.sleep",1);
    public static final int MEMORY_LOG_INTERVAL_MILLIS = Integer.getInteger("test.log_time",10*1000);
    public static final String MESSAGE_DELIMITER = "\n";
    public static final boolean GENERATE_RANDOM_STRING = Boolean.getBoolean("test.random");
}
