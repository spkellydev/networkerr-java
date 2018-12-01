package com.networkerr.app;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class ServerTest {
    private static Thread thread;
    private CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();

    @BeforeClass
    public static void testBed() throws InterruptedException {
        thread = new Thread(() -> {
            try {
                Server.main(new String[] {"dev"});
            } catch (Exception e) {
                System.out.println("Shutting down, server failed");
            }
        });
        thread.start();
        // give server time to bootstrap
        Thread.sleep(5000);
    }
    @Test
    public void serverShouldBoot() {
        try {
            httpclient.start();
            HttpGet request = new HttpGet("http://localhost:8080/uptime");
            Future<HttpResponse> future = httpclient.execute(request, null);
            HttpResponse response = future.get();
            System.out.println("Response: " + response.getStatusLine());
            InputStream in = response.getEntity().getContent();
            Scanner s = new Scanner(in);
            String result = s.hasNext() ? s.next() : "";
            System.out.println(String.format("%s should equal %s", result, "up"));
            assertEquals("up", result);
            System.out.println("Shutting down");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}