# HTTP Concepts Every Developer Should Know

1. **HTTP Methods** - Define the action: GET (fetch), POST (create), PUT (replace), PATCH (update), DELETE (remove).

2. **Status Codes** - Tell what happened: 2xx (success), 4xx (client error), 5xx (server error).

3. **Headers** - Key-value pairs that carry meta info (like Content-Type, Authorization, Cache-Control).

4. **Request Body** - The actual data sent in POST/PUT/PATCH requests (often JSON or form data).

5. **Query Parameters** - Key-value pairs in the URL after ? used to filter, sort, or search.

6. **Path Parameters** - Dynamic parts of a URL (e.g., /user/:id) used to identify resources.

7. **Idempotency** - Repeating a request (like PUT or DELETE) has the same effect every time.

8. **Safe Methods** - Methods like GET and HEAD that shouldn't modify server state.

9. **HTTP/1.1 vs HTTP/2 vs HTTP/3** - Newer versions improve speed via multiplexing, binary frames, and QUIC protocol.

10. **Keep-Alive** - Reuses TCP connections for multiple requests to reduce latency.

11. **Content-Type** - Tells the server/client what format the body is in (e.g., application/json).

12. **Accept** - Tells the server what response formats the client can handle.

13. **Authorization** - Carries credentials (tokens, basic auth) to authenticate requests.

14. **Caching** - Uses headers like Cache-Control, ETag, and Last-Modified to reduce load and latency.

15. **ETag** - A unique hash for a resource version; helps with conditional requests and caching.

16. **Redirects (3xx)** - Instructs clients to try a different URL (e.g., 301, 302, 307).

17. **CORS (Cross-Origin Resource Sharing)** - Controls which origins can access your APIs from browsers.

18. **Preflight Requests** - An OPTIONS request browsers send before certain CORS requests to check permissions.

19. **HTTP Methods Tunneling** - Some proxies only allow GET/POST, so PUT/DELETE is tunneled through POST + _method.

20. **User-Agent** - Header sent by browsers to identify themselves (often spoofed or altered).

21. **Referrer** - Header that shows the URL of the previous page (used in analytics and security).

22. **Host Header** - Indicates the domain the request is targeting (critical in virtual hosting).

23. **Connection Header** - Controls connection behavior (e.g., close, keep-alive).

24. **HEAD Method** - Like GET but returns headers only (no body) — useful for checking resources.
