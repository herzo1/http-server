package httpserver.core.protocol;

/**
 * The class HttpConstants defines constants of the HTTP protocol.
 */
public class HttpConstants {

	// General constants
	public static final String PROTOCOL_VERSION = "HTTP/1.0";
	public static final String AUTHENTICATION_SCHEME = "Basic";

	// Method names
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_DELETE = "DELETE";

	// Header names
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_CONTENT_LENGTH = "Content-Length";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_COOKIE = "Cookie";
	public static final String HEADER_LOCATION = "Location";
	public static final String HEADER_SET_COOKIE = "Set-Cookie";
	public static final String HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";

	// MIME types
	public static final String MIME_TYPE_TEXT_PLAIN = "text/plain";
	public static final String MIME_TYPE_TEXT_HTML = "text/html";
	public static final String MIME_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";

	// Response status
	public static final String STATUS_OK = "200 Ok";
	public static final String STATUS_MOVED_PERMANENTLY = "301 Moved Permanently";
	public static final String STATUS_FOUND = "302 Found";
	public static final String STATUS_UNAUTHORIZED = "401 Unauthorized";
	public static final String STATUS_FORBIDDEN = "403 Forbidden";
	public static final String STATUS_NOT_FOUND = "404 Not Found";
	public static final String STATUS_METHOD_NOT_ALLOWED = "405 Method Not Allowed";
	public static final String STATUS_INTERNAL_SERVER_ERROR = "500 Internal Server Error";
}
