public class Writer implements Runnable{
    private static final String MSG = new String(new char[Config.MESSAGE_CHARACTER_LENGTH]).replace("\0", "A")+Config.MESSAGE_DELIMITER;
    @Override
    public void run() {
        try {
            long x = 0;
            while (true) {
                Main.write(MSG);
                if (x % Config.WRITE_BATCH_SIZE == 0) {
                    Thread.sleep(Config.SLEEP_BETWEEN_BATCHES_MILLIS);
                }
                x++;
            }
        }
        catch (InterruptedException e) {

        }
    }
}
