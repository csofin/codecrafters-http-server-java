package http;

import config.Environment;
import util.Strings;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public class FileResponse extends HttpResponse {

    private String contents;

    public FileResponse(HttpRequest request) {
        super(request);

        String directory = Objects.requireNonNullElse(Environment.getInstance().getDirectory(), ".");
        String filename = request.getPath().startsWith("/files/") ? Strings.after(request.getPath(), "/files/") : "";

        Path filepath = Path.of(directory, filename);
        try {
            this.contents = Files.readString(filepath);
        } catch (IOException e) {
            // noop
        }
    }

    @Override
    protected HttpStatus getResponseStatus() {
        return Objects.isNull(contents) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
    }

    @Override
    protected Map<HttpHeader, String> getResponseHeaders() {
        return Objects.isNull(contents) ? Map.of() : Map.of(
                HttpHeader.CONTENT_TYPE, "application/octet-stream",
                HttpHeader.CONTENT_LENGTH, String.valueOf(contents.length())
        );
    }

    @Override
    public byte[] body() {
        return Objects.isNull(contents) ? null : contents.getBytes(StandardCharsets.UTF_8);
    }

}
