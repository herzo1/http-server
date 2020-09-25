package httpserver.core.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static httpserver.core.protocol.HttpConstants.HEADER_CONTENT_TYPE;

/**
 * The class HttpRequest is responsible for the writing of HTTP responses.
 */
public class HttpResponse {

	private final OutputStream outputStream;
	private final ByteArrayOutputStream byteStream;
	private String status;
	private final Map<String, Object> parameters = new HashMap<>();
	private final Map<String, String> headers = new HashMap<>();

	public HttpResponse(OutputStream outputStream) {
		this.outputStream = outputStream;
		this.byteStream = new ByteArrayOutputStream();
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<String> getParameterNames() {
		return parameters.keySet();
	}

	public Object getParameter(String name) {
		return parameters.get(name);
	}

	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}

	public void addHeader(String name, String value) {
		headers.put(name, value);
	}

	public void writeBody(String line) throws IOException {
		byteStream.write(line.getBytes());
	}

	public void writeBody(byte[] data) throws IOException {
		byteStream.write(data);
	}

	public void send() throws IOException {
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8.name()));
		writer.println(HttpConstants.PROTOCOL_VERSION + " " + status);
		for (String key : headers.keySet()) {
			writer.println(key + ": " + headers.get(key));
		}
		if (byteStream.size() > 0) {
			writer.println(HEADER_CONTENT_TYPE + ": " + byteStream.size());
		}
		writer.println();
		writer.flush();
		outputStream.write(byteStream.toByteArray());
	}
}
