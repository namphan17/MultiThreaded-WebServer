# Chapter 3 Section 3

### Important Terms:

**end-end principle** - it may not be expensive to provide functions at a higher
level, even if they are implemented at a lower level

### Big Ideas:

UDP is connectionless, compared to TCP which has specific private entities per
client-server connection. This is one advantage that UDP may have over TCP for
certain kinds of applications. Other advantages include:

* Greater control over the transport layer from an application level
* Less delay on segment transmission
* No handshake delay (no handshake in general)
* Lower memory requirements (no state-per-connection)
* Smaller overhead
* No congestion control (not throttled as much, theoretically)

Therefore, UDP is used when some loss is tolerable or when performance under 
stress is important. It used to be the case that streaming media was mostly a 
UDP affair - but TCP is increasingly used instead.

UDP can be made reliable - but this requires a lot of work on the application
developer's part.

The segment structure of UDP is very simple. The header contains four fields,
each 16 bits. Those fields are the source port number, the destination port 
number, the length of the data field, and a computed checksum. The data follows.

The checksum is computed essentially by padding the segment to some multiple of 
16 bits, adding together all the 16 bit words, and taking their ones complement.
At the recieving end, all the 16 bit words, with zero taking the place of the
checksum, are added, along with the checksum.
Overflow is thrown away. If the result is not all ones,
the packet has been corrupted somehow and either a warning is emitted or the 
packet never reaches the application layer.


