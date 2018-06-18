package org.art.micro.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * A special controller which is used to test a remote
 * asynchronous non-blocking servlet.
 * Simulates data sending by chunks with specified delay.
 */
@Controller
public class AsyncNonblockingServletTestController {

    @RequestMapping("/sendData")
    public String testRemoteService() throws IOException {
        System.out.println("Sending POST request to the remote server");
        int serverPort = 8080;
        String address = "127.0.0.1";
        InetAddress ipAddress = InetAddress.getByName(address);
        String headers =
                "POST /web/nonblock.call HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Cache-Control: no-cache\r\n" +
                        "Accept: text/html,application/json\r\n" +
                        "Accept-Language: en-US,en;q=0.9\r\n" +
                        "\r\n";
        String message = "Hello from remote server\r\n";
        Socket socket = new Socket(ipAddress, serverPort);
        OutputStream out = socket.getOutputStream();
        out.write(headers.getBytes());
        out.write(message.getBytes());
        out.write(-1);
        out.flush();
        InputStream in = socket.getInputStream();
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = in.read()) != -1) {
            sb.append((char) b);
        }
        return sb.toString();
    }
}
