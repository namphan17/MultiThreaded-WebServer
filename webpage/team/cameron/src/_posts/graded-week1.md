# Graded Exercise 1

## 10 Questions and Answers

1. Why is transport-level multiplexing and demultiplexing necessary?

   If it didn't exist, there could only ever be one process running on a host!
   
   This answer needs some context: at the bare minimum, a transport layer 
   takes host-to-host messages and turns them into process-to-process messages.
   This process is known as transport-level multiplexing and demultiplexing.
   It's necessary because some of the segments arriving at the transport
   layer at a particular host are destined for unique processes on that host - 
   some segments are for a web server, while others may be for a secure shell
   connection, for example. The transport layer has only one 'pipe' in and one 
   'pipe' out - the network layer - and so it must somehow distinguish between
   messages bound for different processes.

2. Why does UDP use a two-tuple for multiplexing and TCP use a four-tuple?

   The critical difference is the privacy of the connection. UDP is said to be
   'connectionless' - this means that all the UDP segments destined for a 
   certain port and address arrive at the same place, and it's up to the 
   application layer to do any additional demultiplexing. TCP has a notion of 
   a private connection - this means that every process-to-process connection
   has a specific private entity that receives and sends data. Sometimes,
   this is useful for an application.
   
   More technically, UDP identifies one connection per port and address, while
   TCP takes into account the port and address of the sender as well.

3. Given that TCP provides so many more features to an application, why would
   a developer choose UDP?

   Sometimes, the additional overhead of those features is a steep cost. If
   your application has specific tight time demands, and can suffer some 
   packet loss, it makes a great deal more sense to use UDP. This is especially 
   noticeable when your data consists of many small messages, where the
   transmission overhead of TCP can very quickly add up. One arena where this
   conversation happens over and over again is in networked game development -
   the TCP overhead can make a latency difference on the order of several video frames
   on some kinds of connections. Streaming media is another arena where this
   discussion happens frequently.
   
4. What are the four key mechanics that allow reliable data transfer over an unreliable
   network, and what does each do?

   The four key mechanics: acknowledgements, timers, retransmissions, and
   sequence numbers. 
   
   Acknowledgements are segments sent from receiver to sender, letting the sender 
   know that a particular segment was received properly. They rely on the lower-level
   details of checksums to confirm that a segment was not corrupted on the network.
   
   Timers control when a segment should be considered lost - if an acknowledgement 
   is not received in time, the sender begins a retransmission. This saves a great deal 
   of time that would otherwise be spent waiting for acknowledgements.
   
   Retransmission is the process of resending data for which acknowledgements were
   not received, or not received in time. This is how loss and corruption are corrected.
   
   Sequence numbers are used to indicate the order in which packets should be re-assembled
   to provide the complete message. This allows a receiver to discover if a packet has been
   lost, as well as re-order packets that are recieved out of order.

5. How does a checksum work?

   It's easiest to begin this thought with how we check a checksum, and then
   move to how one is computed.
   
   In the most straightforward implementation, a checksum allows all the words
   (usually 16-bit words) in a segment to be summed with overflow to some 
   constant number. In practice, a segment is padded to be a multiple of 16-bits
   and then summed, and then the result is checked to see if it is all 1s. This
   means that almost certainly, there was no corruption of the segment on it's
   way across the network. If the sum is anything else, the packet should be
   marked as having an error and discarded.
   
   Creating a checksum is not much harder. We fill the checksum field with zeros
   and then compute the number that would have to fit in that field to create
   an all ones sum at the receiving end.

6. What are the key points of the alternating-bit protocol?

   In actual TCP packets, there is a relatively large sequence and acknowledgement
   number. For the introductory discussion of sequence and acknowledgement
   numbers in the text, for the alternating-bit protocol, this is simplified
   to a 1-bit number. 
   
   This has some important consequences:
   
   The protocol becomes a stop-and-wait protocol, because it sends one packet
   and then must wait for a sufficient acknowledgement before continuing. It
   is very slow as a result - there is no pipelining of packets. We explore
   pipelining in the next question.
   
   As a positive, it is very easy to detect when a duplicate packet has been
   sent - are we expecting packet 0 or packet 1? Also, our ACK messages can
   be very compact and simple.
   
   Aside from the speed limitation, the alternating-bit protocol is already 
   a reliable data transfer, and because it stops and waits, it does not need
   to worry about accepting more than one packet at a time, and does not need
   to worry about out-of-order packets.


7. What are the key points of the go-back-n and selective-repeat protocols?

   These are both pipelined protocols, meaning many packets can be 'in the air'
   at once. As such, they use larger sequence and acknowledgement numbers, and
   are much faster. Go-back-n and selective repeat are two basic approaches to
   recovering from errors that may occur in a pipelined protocol.
   
   In go-back-n protocols, the sender can send many packets without waiting
   for an acknowledgement, but can't have more than some specified number
   of unacknowledged packets at a time. Essentially, when that maximum is
   reached, it must wait for some acknowledgements before continuing.
   
   This brings forth the notion of a sliding-window-protocol - there is some 
   allowable window of packets that can be sent but haven't yet been acknowledged.
   This window moves forward over the range of sequence numbers as time progresses.
   
   Another speedup in go-back-n is called cumulative acknowledgement, meaning 
   that a reciever can send a single acknowledgement for multiple packets.
   
   When packets are lost or delayed in go-back-n, the sender retransmits all
   packets that have been sent but not acknowledged. This is where go-back-n
   gets its name.
   
   One problem with GBN protocols is that we may have to retransmit many packets
   when a single one is lost - which is a problem that is addressed by selective
   repeat protocols.
   
   Selective repeat improves on GBN by only retransmitting packets for which no
   ACK has been recieved - this does away with cumulative acknowledgements,
   and returns to one ACK per packet, but this is worth it in channels where 
   packet loss is relatively high. We again have the notion of a window,
   but the window may not be synchronized. This has the important effect that
   a window cannot be more than half the range of sequence numbers, as sequence
   numbers are usually finite in length.

8. Which is actually used by TCP, go-back-n, or selective repeat?

   This is a trick question - TCP has features of both protocols, with some 
   significant differences. For example, TCP uses sequence and ack numbers 
   that refer to the bytes expected in the message, not the packet numbers.
   We say that TCP is a hybrid.

9. What's the practical reason why TCP congestion control is additive when boosting
   transmission speed, and multiplicative when reducing it?

   It's not actually discussed in too much detail in the book - but the essential
   answer is that we want to rapidly cut throughput to avoid congestion, and 
   slowly probe back up to soak up available bandwidth. If TCP was multiplicative
   both ways, we'd see very unstable performance under load.

10. What's the problem with fairness and UDP? Fairness and TCP?

    UDP applications do not throttle their bandwidth usage when faced with
    congestion - they simply lose packets. Recalling that a lost packet 
    uses all the bandwidth up to the point of loss, a lost UDP packet may 
    crowd out other TCP connections. This is an intense area of research.
    
    Something like this problem also happens with TCP - applications that
    use multiple TCP connections in parallel may obtain an unequal share of
    bandwidth, when bandwidth is allocated equally per connection.
    
## Two back-of-chapter problems

### Chapter 3, Problem P1

This problem has six parts, dealing mostly with port numbers and your
understanding of transport level multiplexing.

There are many possible answers to this problem, but there's a pattern that they
must follow. Recall that a server process S is running on a single port. Recall
also that Telnet is a TCP service, so the port numbers will be consistent for
a single connection.

With that in mind, parts *a* through *d* will share a single port number for
Server S. The port numbers for A and B should remain consistent - the source 
port for A and the destination port for A will be the same. Here's a sample
solution for *a* through *d* that follows these constraints:

*a*: source 5678, destination 23

*b*: source 5689, destination 23

*c*: source 23, destination 5678

*d*: source 23, destination 5689

Parts *e* and *f* deserve some additional context. Recall that a port number is
unique to a host - when a process reserves a single TCP port number on a host,
no other process on that host can use that port number. Thus, we answer *e* and
*f* like this:

*e*: Yes, it is possible that A to S and B to S can have the same exact source
and destination port numbers. This is because a reservation on A does not affect
the reservations on B.

*f*: No, this is not possible if they are on the same host. Different port
numbers will have to be used by Clients A and B.

### Chapter 3, Problem P3

This problem delves into a simple implementation of a checksum. A useful 
definition is *1s complement*, which simply means:

> Substitute each 1 for a 0, and each 0 for a 1.

This substitution is done simultaneously - otherwise you would end up with all 
1s!

Notice also that the problem leaves it to you to decide that summing bytes
together may produce an overflow that you need to discard.

So, for the first part of the exercise, you have the following arithmetic:

```
   01010011
 + 01100110 sum of the first two bytes
   --------
   10111001
   01110100
 + -------- sum of running total and third byte  
   00101101
   
   11010010 1s complement (inversion)
```

UDP takes the 1s complement of the sum, because it means that the sum of 
all the words plus the checksum will equal all 1s in binary - this is a simple
method of error detection that requires only basic programming knowledge to
understand. It can be implemented simply as the check of the sum of all the 
words in the segment, discarding the overflow.

The reciever detects those errors by making sure the sum equals 
`1111111111111111` in binary.

When we look at what kinds of errors can be detected this way, we see that a one
bit error cannot go undetected - there will always be a carried effect from that
bit.

A 2-bit error, however, cannot always be detected. The bits might be flipped in
such a way that the first error is 'corrected' by the second, and the checksum
still equals the same number. This could happen if the flipped bits occupy the 
same place in the words in two different words.

## Wireshark Lab

[Wireshark-TCP.pdf](Wireshark-TCP.pdf)
