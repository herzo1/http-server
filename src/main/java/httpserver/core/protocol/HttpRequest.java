package httpserver.core.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import static httpserver.core.protocol.HttpConstants.HEADER_CONTENT_LENGTH;
import static httpserver.core.protocol.HttpConstants.HEADER_CONTENT_TYPE;
import static httpserver.core.protocol.HttpConstants.METHOD_GET;
import static httpserver.core.protocol.HttpConstants.METHOD_POST;
import static httpserver.core.protocol.HttpConstants.MIME_TYPE_FORM_URLENCODED;

/**
 * The class HttpRequest is responsible for the parsing of HTTP requests.
 */
public class HttpRequest {

	private static final Logger logger = Logger.getLogger(HttpRequest.class.getName());

	private final InputStream inputStream;
	private String requestLine;
	private String method;
	private String uri;
	private String path;
	private String query;
	private String protocol;
	private final Map<String, String> parameters = new HashMap<>();
	private final Map<String, String> headers = new HashMap<>();

	public HttpRequest(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void parse() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		requestLine = reader.readLine();
		logger.fine("Request line: " + requestLine);
		if (requestLine != null) {
			parseRequestLine();
			parseParameters(query);
			parseRequestHeaders(reader);
			parseFormParameters(reader);
		}
	}

	public String getMethod() {
		return method;
	}

	public boolean isGet() {
		return method.equals(METHOD_GET);
	}

	public boolean isPost() {
		return method.equals(METHOD_POST);
	}

	public String getUri() {
		return uri;
	}

	public String getPath() {
		return path;
	}

	public String getQuery() {
		return query;
	}

	public String getProtocol() {
		return protocol;
	}

	public Set<String> getParameterNames() {
		return parameters.keySet();
	}

	public String getParameter(String name) {
		return parameters.get(name);
	}

	public Set<String> getHeaderNames() {
		return headers.keySet();
	}

	public String getHeader(String name) {
		return headers.get(name);
	}

	private void parseRequestLine() {
		String[] parts = requestLine.split(" ");
		method = parts[0];
		uri = parts[1];
		String[] tokens = uri.split("\\?");
		path = tokens.length > 0 ? tokens[0] : uri;
		query = tokens.length > 1 ? tokens[1] : null;
		protocol = parts[2];
	}

	private void parseParameters(String paramString) {
		if (paramString == null) return;
		String[] params = paramString.split("&");
		for (String param : params) {
			logger.fine("Parameter: " + param);
			String[] tokens = param.split("=");
			try {
				String name = URLDecoder.decode(tokens[0], StandardCharsets.UTF_8.name());
				String value = tokens.length > 1 ? URLDecoder.decode(tokens[1], StandardCharsets.UTF_8.name()) : "";
				parameters.put(name, value);
			} catch (UnsupportedEncodingException ex) {
				logger.severe(ex.toString());
			}
		}
	}

	private void parseRequestHeaders(BufferedReader reader) throws IOException {
		while (true) {
			String header = reader.readLine();
			if (header == null || header.isEmpty()) break;
			logger.fine("Header: " + header);
			String[] tokens = header.split(": ");
			headers.put(tokens[0], tokens[1]);
		}
	}

	private void parseFormParameters(BufferedReader reader) throws IOException {
		if (Objects.equals(headers.get(HEADER_CONTENT_TYPE), MIME_TYPE_FORM_URLENCODED)) {
			int length = Integer.parseInt(headers.get(HEADER_CONTENT_LENGTH));
			char[] body = new char[length];
			int num = reader.read(body);
			parseParameters(new String(body, 0, num));
		}
	}

	@Override
	public String toString() {
		return requestLine;
	}
}
