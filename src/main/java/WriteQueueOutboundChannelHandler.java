import io.netty.channel.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class WriteQueueOutboundChannelHandler extends ChannelDuplexHandler {
    private static final Logger logger = Logger.getLogger("queue");
    final Queue<Object> messageQueue = new ConcurrentLinkedQueue<>();
    ChannelHandlerContext ctx;
    int qSize;
    boolean isWriting;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        messageQueue.clear();
        Main.unregisterChannel(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Main.registerChannel(ctx.channel());
        this.ctx = ctx;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info("Channel exception caught: " + cause);
    }

    private final ChannelFutureListener sendListener = future -> {
        if (future.isSuccess()) {
            poll();
        } else {
            future.channel().close();
        }
    };

    private synchronized void poll() {
        if (!messageQueue.isEmpty()) {
            this.ctx.writeAndFlush(messageQueue.poll()).addListener(sendListener);
            qSize--;
            Main.messageSent();
        } else {
            isWriting = false;
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        int size = qSize++;
        if (size > 5000 && size % 100 == 0) { //mod just to avoid too many logs (we still get the idea)
            logger.warning("QueueSize: " + size);
        }
        messageQueue.offer(msg);
        if (!isWriting) {
            isWriting = true;
            poll();
        }
    }
}
