# 8 Review Questions

1. Which kind of switching is this unfunny joke about?
   > A man walks into a bar, and orders a beer. The bartender calls the 
   > wholesaler. The wholesaler calls the brewery. The brewery makes the beer.
   > The brewery sells the beer to the wholesaler. The wholesaler sells the beer
   > to the bar. The bartender serves the man, and the man starts drinking
   > the beer.
   > 
   > In the meantime, a second person walks into the bar. The bartender yells, 
   > "Can't you see I'm busy!!"

   Answer: circuit switching

2. What kind of loss, suffered by a packet switching network, could be 
   alleviated if infinite output buffer sizes were possible?

   Packet loss.

3. What is 'distributed' about a DDoS attack?
   
   The attack comes from many computers that are geographically distinct, making
   it more difficult to figure out which computers are legitimate requests.

4. Why don't we use two layers of the ISO OSI model?
   
   We do use them, but we leave the choices there to application developers. 
   Most applications do make presentation and session choices.

5. Fill in the network protocol in this joke:  
   > I would tell you a joke about ___, but you might not get it.

   Answer: UDP

6. Which network protocol does this joke reflect?
   > Hello, would you like to hear a joke?
   >
   > Yes, I'd like to hear a joke.
   >
   > OK, I'll tell you a joke.
   >
   > OK, I'll hear a joke.
   >
   > Are you ready to hear a joke?
   >
   > Yes, I am ready to hear a joke.
   >
   > OK, I'm about to send the joke. It will last 10 seconds, it has two 
   > characters, it does not have a setting, it ends with a punchline.
   >
   > OK, I'm ready to hear the joke that will last 10 seconds, has two 
   > characters, does not have a setting and will end with a punchline.
   >
   > I'm sorry, your connection has timed out...Hello, would you like to hear a 
   > joke?

   Answer: TCP 
   
7. Name two protocols that are involved in the sending and retrieval of e-mail.
   
   Pick two of: SMTP, POP3, IMAP, HTTP

8. Why is the opening of a TCP connection called a "three-way handshake"?
   
   The opening part of a client's TCP connection goes to a "welcoming socket", 
   which is not unique to the client. Then, a unique socket for the connection 
   is created.

# Influential person: Bob Taylor

This account was taken from the book, *Where Wizards Stay Up Late*, by Katie 
Hafner and Matthew Lyon.

Bob Taylor was the leader of the ARPANET project at ARPA, where he lead the 
development of the Interface Message Processor units that were initially 
installed at each node of the fledgling network.

ARPANET was one very early incarnation of the Internet that connected four nodes
across the country at top-level universities. This network was established by
December of 1969, and shortly thereafter Bob left the project to pursue other 
opportunities.

He's not as well known as Cerf or Baran as he was the manager of the project
through an office at the Pentagon known as the IPTO, instead of a direct 
engineering contributor.

In 1994, when many of the original ARPANET contributors re-assembled to 
celebrate the 25th anniversary of their achievement, the press had already created the
myth that ARPANET had been created partially to provide communication in the 
event of a nuclear attack on the US. Bob Taylor had attempted to remedy this 
false belief to no avail.

ARPANET had been created as a scientific enterprise, to link universities 
together so that they could share computing resources. Mainframe computers at 
that time were very expensive, and it would be a time and expense savings if 
researchers from one university could avoid getting their own computer.

One anecdote from his career I found interesting - the initial pitch for ARPANET
was for one million dollars, and Taylor got this funding without a laid-out plan
and in a twenty minute conversation with his boss, the leader of ARPA, Charlie
Herzfeld.

# Tutorial on ifdown and ifup, and a little ifconfig

If you ever, on Linux, need to change your network configuration, you'll need
to know the network interface name.

You can get this from `ifconfig` - the command without arguments prints all
the interfaces installed on the computer. An ifconfig listing will have the name
of the interface in the first line of each block, each block corresponding to
one interface. Example for an interface named `eno1`.

```
eno1      Link encap:Ethernet  HWaddr 90:b1:1c:88:bc:1d  
          inet addr:10.101.6.28  Bcast:10.101.6.255  Mask:255.255.255.0
          inet6 addr: fe80::8c3d:d00f:c95:8a5a/64 Scope:Link
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:73651 errors:0 dropped:0 overruns:0 frame:0
          TX packets:61284 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:60730681 (60.7 MB)  TX bytes:10256534 (10.2 MB)
          Interrupt:20 Memory:f7c00000-f7c20000 
```

Say that you wanted to change your `eno1` to a static IP address - on Ubuntu
you would make the necessary changes to `/etc/network/interfaces`. Then, you 
would run the following to apply those changes:

``` bash
ifdown eno1
ifup eno1
```