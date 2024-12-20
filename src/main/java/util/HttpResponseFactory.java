package util;

import http.*;

public final class HttpResponseFactory {

    private HttpResponseFactory() {
    }

    public static HttpResponse getResponse(HttpRequest request) {
        return switch (request.getPath()) {
            case null -> new NoBodyResponse();
            case "/" -> new NoBodyResponse();
            case String p when p.startsWith("/echo/") || ("/user-agent".equals(p) && request.getHeaders().containsKey(HttpHeader.USER_AGENT)) ->
                    new PlainTextResponse(request);
            case String p when p.startsWith("/files/") ->
                    request.getMethod() == HttpMethod.POST ? new WriteFileResponse(request) : new ReadFileResponse(request);
            default -> new NotFoundResponse();
        };
    }

}
