package com.github.binout.asciidoctor.extensions;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.OptionsBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class UriIncludeProcessorTest {

    private static HttpServer server;

    @BeforeClass
    public static void startServer() throws IOException {
        InetSocketAddress addr = new InetSocketAddress(9999);
        server = HttpServer.create(addr, 0);

        server.createContext("/hello", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                Headers responseHeaders = httpExchange.getResponseHeaders();
                responseHeaders.set("Content-Type", "text/plain");
                httpExchange.sendResponseHeaders(200, 0);

                OutputStream responseBody = httpExchange.getResponseBody();
                responseBody.write("hello".getBytes());
                responseBody.close();
            }
        });
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
    }

    @AfterClass
    public static void stopServer(){
        server.stop(0);
    }

    @Test
    public void should_get_content_from_server() {
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        asciidoctor.javaExtensionRegistry().includeProcessor(UriIncludeProcessor.class);
        Options options = OptionsBuilder.options().backend("html5").get();

        String rendered = asciidoctor.convert("include::http://127.0.0.1:9999/hello[]", options);

        assertThat(rendered).isEqualTo(
                "<div class=\"paragraph\">\n" +
                        "<p>hello</p>\n" +
                        "</div>");
    }

}