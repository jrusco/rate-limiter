# Common algorithms and techniques every developer should know

## Load Management / Rate Limiting

- **Token Bucket Algorithm** - allows burst traffic, enforces average rate.
- **Leaky Bucket Algorithm** - smooths bursts, enforces fixed rate.
- **Fixed Window Counter** - simple time bucket count.
- **Sliding Window Log** - high accuracy, memory-heavy.
- **Cost-Based Rate Limiting** - assigns weights to requests (used in APIs with varying load costs).

## Load Balancing Algorithms

- **Round Robin** - rotate through servers.
- **Least Connections** - send traffic to the least busy server.
- **IP Hashing** - sticky sessions by IP.
- **Weighted Round Robin** - servers with more capacity get more requests.

## Caching Algorithms

- **LRU (Least Recently Used)** - evict least recently accessed item.
- **LFU (Least Frequently Used)** - evict least used item over time.
- **TTL (Time-to-Live)** - auto-expiry based on time.
- **Write-Through / Write-Behind** - cache vs DB sync strategies.

## Networking & Distributed Systems

- **Consistent Hashing** - scalable partitioning (used in Redis Cluster, Cassandra).
- **Gossip Protocol** - peer-to-peer state sharing.
- **Paxos / Raft** - consensus algorithms for distributed state (used in etcd, Consul).
- **Vector Clocks** - ordering in distributed systems.

## Queueing & Scheduling

- **Priority Queues** - task ordering.
- **Time-Partitioned Queues** - for scheduled jobs (e.g., cron tweets).
- **Exponential Backoff** - retry strategy for failed tasks.
- **Circuit Breaker Algorithm** - halt retries when service is down.

## Security / Auth Algorithms

- **HMAC (Hash-based Message Authentication Code)** - API key verification.
- **OAuth 2.0 Flow** - authorization delegation.
- **JWT (JSON Web Tokens)** - stateless session validation.
- **Captcha Challenge Algorithms** - bot prevention.

## Data Sync & Consistency

- **Eventual Consistency** - used in NoSQL.
- **Read Repair / Hinted Handoff** - used in Cassandra, Dynamo.
- **CRDT’s (Conflict-free Replicated Data Types)** - real-time collaboration apps.
- **Merkle Trees** - sync diffs across replicas (used in Git, Cassandra).

## Storage & Compression

- **Delta Encoding** - storing differences.
- **Run-Length Encoding / Huffman Coding** - basic compression.
- **Zstandard / Brotli** - modern compression algorithms.
- **Parquet / Avro / Protobuf** - efficient data serialization.

## Monitoring / Metrics

- **Histogram Binning** - used in Prometheus.
- **Percentile Estimation (t-digest)** - latency SLO tracking.
- **EWMA (Exponential Weighted Moving Average)** - smooth trend lines.

## Build / Compilation / Infra

- **Topological Sorting** - dependency resolution (used in build tools like Maven, Gradle).
- **Dependency Graph Traversal** - resolving npm or Maven dependencies.
- **Blue-Green / Canary Deployments** - controlled releases.
- **Rolling Update Algorithms** - gradual deployment without downtime.
