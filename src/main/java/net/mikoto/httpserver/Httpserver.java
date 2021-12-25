package net.mikoto.httpserver;

import com.sun.net.httpserver.HttpServer;
import net.mikoto.httpserver.controller.ControllerEntry;

import java.io.IOException;
import java.net.InetSocketAddress;

import static net.mikoto.httpserver.util.HttpUtil.formData2Dic;

/**
 * @author mikoto
 * @date 2021/12/4 9:37
 */
public class Httpserver {
    /**
     * 初始化单例
     */
    private static Httpserver INSTANCE = null;

    private static HttpServer server;

    /**
     * 创建服务器
     */
    private Httpserver(int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            System.err.println("ERROR::Server can't create at once.");
            e.printStackTrace();
        }
    }

    public synchronized static Httpserver setPort(int port) {
        if (INSTANCE == null) {
            INSTANCE = new Httpserver(port);
        }
        return INSTANCE;
    }

    public static Httpserver getInstance() {
        return INSTANCE;
    }

    /**
     * 创建http上下文
     *
     * @param context         上下文
     * @param controllerEntry controller入口
     * @return 当前实例
     */
    public Httpserver createContext(String context, ControllerEntry controllerEntry) {
        server.createContext(context, httpExchange -> controllerEntry.entry(httpExchange, formData2Dic(httpExchange.getRequestURI().getQuery())));
        return this;
    }

    /**
     * 启动服务器
     */
    public void start() {
        server.start();
    }

    /**
     * 停止服务器
     */
    public void stop() {
        server.stop(0);
    }
}
