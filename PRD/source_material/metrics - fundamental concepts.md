# Fundamental Performance Metrics Concepts

---

1. **Latency** - The time taken to complete a single request or operation.

2. **Throughput** - Number of operations completed per second (e.g., requests/sec, transactions/sec).

3. **P95 / P99** - Percentile metrics that show the worst-case latency experienced by 5% or 1% of users.

4. **Tail Latency** - The slowest requests (e.g., 99th+ percentile) - critical for real-world UX.

5. **Cold Start** - Initial delay when a system (e.g., Lambda) starts from scratch.

6. **Warm Start** - Reusing a previously-initialized service to avoid startup latency.

7. **TTFB (Time to First Byte)** - Time between sending a request and receiving the first byte of the response.

8. **RPS (Requests Per Second)** - A common measure of server/API throughput.

9. **QPS (Queries Per Second)** - Similar to RPS, but often used for databases and search systems.

10. **Error Rate** - Percentage of requests that result in an error (e.g., 5XX or 4XX codes).

11. **Apdex Score** - A user satisfaction score based on how many requests are fast, tolerable, or frustrating.

12. **SLA (Service Level Agreement)** - The uptime or performance a provider promises to deliver.

13. **SLO (Service Level Objective)** - The internal performance target you aim to meet.

14. **SLI (Service Level Indicator)** - The actual measured value (e.g., “99.95% of requests under 500ms”).

15. **Resource Utilization** - Measures CPU, memory, disk, and network usage under load.

16. **GC Pause Time** - Time spent stopping your app for garbage collection (important in JVM, Go, etc.).

17. **Throughput vs Latency Tradeoff** - Pushing more requests may lower latency... until it doesn’t.

18. **Jank** - Lag or visual stuttering in frontend rendering - often caused by long tasks or reflows.

19. **CPU Throttling** - CPU-intensive apps may be slowed by limits in containers or cloud settings.

20. **I/O Wait Time** - Time the CPU spends waiting for disk or network I/O operations to complete.

21. **TTI (Time to Interactive)** - How long it takes for a web page to become usable.

22. **CLS (Cumulative Layout Shift)** - Measures how much elements move unexpectedly during load (UX killer).

23. **FPS (Frames Per Second)** - Key visual performance metric for games and frontend animation.

24. **Memory Footprint** - Total memory used by your process/system under typical load.

25. **Throttling & Backpressure** - Mechanisms to slow down clients or systems to maintain stability under
