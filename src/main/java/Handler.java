import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.log4j.Logger;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class Handler extends ChannelInboundHandlerAdapter {

    private static Logger logger =  Logger.getLogger(Handler.class);

    public static ChannelGroup users =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //http://crysislinux.github.io/smart_websocket_client/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) {
        String json="";
        logger.info("===================进入=======================");
        if (o instanceof FullHttpRequest) {
            logger.info("http");
           json= handleHttpRequest(ctx, (FullHttpRequest) o);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(json.getBytes(StandardCharsets.UTF_8)));
            response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN,"*");
            response.headers().set(ACCESS_CONTROL_ALLOW_HEADERS,"Origin, X-Requested-With, Content-Type, Accept");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
            ctx.writeAndFlush(response);
        } else if (o instanceof WebSocketFrame) {
            logger.info("websocket");
            // json=handleWebSocketFrame(ctx,(TextWebSocketFrame) o);
           // judgement_action(((TextWebSocketFrame) o).text(),ctx.channel());
        }
         logger.info("返回数据"+json);
    }
/**
 *  处理业务流程 http://localhost:6789/index?id=33
 */
    private String handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest fuHr) {
        String url = fuHr.uri();
        System.out.println("method:"+fuHr.method());
        if (fuHr.method().toString().equals("GET")){
            if (url.contains("?")){
                url=url.split("\\?")[0];
            }
        }
        logger.info("URL: "+url);
        ByteBuf byteBuf=fuHr.content();
        String data=byteBuf.toString(Charset.forName("utf-8"));
        logger.info("data "+data);
        String json="123";
        switch (url){
            case "/search":


               break;
        }
        return json;
    }
    private String  handleWebSocketFrame(ChannelHandlerContext ctx,TextWebSocketFrame ws){
        System.out.println(ws.text());
        return "hello";
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("==================channelInactive"+ctx.channel().id()+"========================");
        users.remove(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.info("==================userEventTriggered"+ctx.channel().id()+"========================");
        users.add(ctx.channel());
    }

    public static void main(String[] args) {

    }

}
