# Chapter 3 Section 2

### Important Terms:

**socket** - an entity through which data passes between network and process

**demultiplexing**  - in a transport context, delivery of the data in a segment 
to the correct process

**multiplexing** - in a transport context, gathering data chunks from different 
sockets encapsulating those chunks, and passing them to the network

**source/destination port number field** - fields in a segment that indicate the socket to 
which data is to be delivered. a 16-bit integer.

**well-known port numbers** - the port numbers from 0 to 1023 that are reserved
for use by well-known application protocols

### Big Ideas

UDP Multiplexing is simpler than TCP multiplexing - a UDP segment contains two 
2-tuples identifying the source and destination. Each tuple contains an IP
address and a port number. Two UDP segments with the same destination tuple 
will be routed to the same socket. A UDP program may use the source tuple to 
know where to send a reply.

TCP Multiplexing is more complex. Each TCP socket is identified by a four-tuple,
containing the same information as the source and delivered tuples from UDP,
but simply concatenated together. Essentially, each socket is a private connection
between two hosts. The host uses the entire four-tuple to figure out where to
send an incoming TCP segment.

