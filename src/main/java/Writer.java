import java.util.Random;

public class Writer implements Runnable{
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static final Random rnd = new Random();

    static String randomString(int len){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    private static final String STATIC_MSG = new String(new char[Config.MESSAGE_CHARACTER_LENGTH]).replace("\0", "A")+Config.MESSAGE_DELIMITER;
    private String msg;
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            long x = 0;
            while (true) {
                msg = Config.GENERATE_RANDOM_STRING ? randomString(Config.MESSAGE_CHARACTER_LENGTH)+Config.MESSAGE_DELIMITER : STATIC_MSG;
                Main.write(msg);
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
