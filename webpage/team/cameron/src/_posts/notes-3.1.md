# Chapter 3 Section 1

### Important Terms:

**logical communication** - communication as if two processes were directly connected

**segment** - transport-layer packets, created usually by breaking application messages into chunks and adding transport-layer headers

**UDP** - the User Datagram Protocol, providing unreliable, connectionless service

**TCP** - the Transmission Control Protocol, providing reliable, connection-oriented service

**IP** - the network-layer protocol for the internet, Internet Protocol, in which each host has an IP address

**best-effort delivery service** - a service that makes an effort to deliver segments, but makes no guarantees

**unreliable service** - a service without guaranteed delivery, order, or integrity

**transport-layer multiplexing and demultiplexing** - extending host-to-host delivery to process-to-process delivery

**reliable data transfer** - a transfer that guarantees data arrives in order and correct

**congestion control** - preventing any one TCP connection from swamping the network layer with too much traffic

### Big Ideas

There are two transport-layer protocols available on the internet - TCP and UDP.

TCP has the advantage that it provides additional services on top of the unreliable IP
protocol that guarantee order and correctness of data. UDP does not provide these
guarantees, but suffers less additional per-segment overhead as a result. Both 
protocols provide error-checking capability.

These transport-layer protocols are implemented in the network edge and not 
in the network core. Their job is to extend the network-layer communication 
into the application. We call a message at this level a 'segment', to be 
distinguished from the 'message' at the application level and the 'datagram'
at the network level.

