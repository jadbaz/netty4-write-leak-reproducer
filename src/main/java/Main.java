import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("main");
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static int messagesReceived = 0, messagesSent = 0;

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format","%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s %5$s%6$s%n");

        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(
                                new StringEncoder(),
                                new StringDecoder(),
                                new WriteQueueOutboundChannelHandler()
                        );
                    }
                });

        b.bind(Config.PORT);
        logger.info("Binding on port " + Config.PORT);
        (new Thread(new Writer())).start();
        (new Thread(new LogUsage())).start();
    }

    public static void registerChannel(Channel channel) {
        channels.add(channel);
        logger.info("New channel registered ");
    }

    public static void unregisterChannel(Channel channel) {
        logger.info("Channel removed");
        channels.remove(channel);
    }

    public static void write(String msg) {
        msg += "\n";
        messageReceived();
        if (!channels.isEmpty()) {
            channels.write(msg);
        }
    }

    public static void messageReceived() {
        messagesReceived++;
    }
    public static void messageSent() {
        messagesSent++;
    }

    public static int getMessagesReceived() {
        return messagesReceived;
    }
    public static int getMessagesSent() {
        return messagesSent;
    }
}
