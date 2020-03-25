package xyz.zzyitj.netty.framework.http;


import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/24 10:12 下午
 * @email zzy.main@gmail.com
 */
public class HttpResponse {
    private ChannelHandlerContext ctx;
    private FullHttpResponse response;

    public HttpResponse(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        this.response = new DefaultFullHttpResponse(HTTP_1_1, OK);
    }

    public HttpResponse setHttpVersion(HttpVersion version) {
        response.setProtocolVersion(version);
        return this;
    }

    public HttpResponse setStatus(HttpResponseStatus status) {
        response.setStatus(status);
        return this;
    }

    public HttpResponse setHeader(CharSequence name, Object value) {
        response.headers().set(name, value);
        return this;
    }

    public HttpResponse setHeader(HttpHeaders headers) {
        response.headers().set(headers);
        return this;
    }

    public HttpResponse write(CharSequence sequence) {
        write(sequence, CharsetUtil.UTF_8);
        return this;
    }

    public HttpResponse write(CharSequence sequence, Charset charset) {
        response.content().writeCharSequence(sequence, charset);
        return this;
    }

    public HttpResponse write(Number n) {
        if (n instanceof Short) {
            response.content().writeShort((Short) n);
        } else if (n instanceof Integer) {
            response.content().writeInt((Integer) n);
        } else if (n instanceof Long) {
            response.content().writeLong((Long) n);
        } else if (n instanceof Double) {
            response.content().writeDouble((Double) n);
        } else if (n instanceof Float) {
            response.content().writeFloat((Float) n);
        }
        return this;
    }

    public HttpResponse write(byte[] src) {
        response.content().writeBytes(src);
        return this;
    }

    public HttpResponse write(Boolean b) {
        response.content().writeBoolean(b);
        return this;
    }

    public HttpResponse write(int c) {
        response.content().writeChar(c);
        return this;
    }

    public void write(Object o) {
        if (o instanceof String) {
            write((String) o);
        } else if (o instanceof Number) {
            write((Number) o);
        } else if (o instanceof Boolean) {
            write((Boolean) o);
        } else {
            write(JSON.toJSONString(o));
        }
    }

    public void writeAndFlush() {
        ctx.write(response);
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    public void writeAndFlush(Object o) {
        write(o);
        writeAndFlush();
    }
}
