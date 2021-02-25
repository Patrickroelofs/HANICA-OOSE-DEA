package nl.han.dea.http;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ConnectionHandler implements Runnable {


    private static final String CONTENT_LENGTH = "{{CONTENT_LENGTH}}";
    private static final String DATE = "{{DATE}}";
    private static final String HTTP_STATUS = "{{HTTP_STATUS}}";

    // Status codes
    private static final String HTTP_200 = "200 OK";
    private static final String HTTP_404 = "404 NOT FOUND";

    private static final String HTTP_HEADER = "HTTP/1.1 " + HTTP_STATUS + "\n" +
            "Date: " + DATE + "\n" +
            "HttpServer: Simple DEA Webserver\n" +
            "Content-Length: " + CONTENT_LENGTH +
            "Content-Type: text/html\n";

    private Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        handle();
    }

    public void handle() {
        try {
            var inputStreamReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII));
            var outputStreamWriter = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.US_ASCII));

            var start = parseRequest(inputStreamReader);

            if(start == null) {
                return;
            }

            var resource = start.split(" ")[1];
            writeResponse(outputStreamWriter, resource);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String parseRequest(BufferedReader inputStreamReader) throws IOException {
        var request = inputStreamReader.readLine();
        var start = request;

        while (request != null && !request.isEmpty()) {
            System.out.println(request);
            request = inputStreamReader.readLine();
        }

        return start;
    }

    private void writeResponse(BufferedWriter outputStreamWriter, String resource) {
        try {
            outputStreamWriter.write(header(resource));
            outputStreamWriter.newLine();
            outputStreamWriter.write(new HtmlPageReader().readFile(resource));
            outputStreamWriter.newLine();
            outputStreamWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String header(String resource) {
        var status = HTTP_200;
        var length = "0";

        try {
            length = new HtmlPageReader().getLength(resource);
        } catch (ResourceNotAvailableException e) {
            status = HTTP_404;
        }

        return HTTP_HEADER
                .replace(CONTENT_LENGTH, length)
                .replace(DATE, OffsetDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME))
                .replace(HTTP_STATUS, status);
    }

    @Override
    public void run() {
        handle();
    }
}
