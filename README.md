# UDPplus
I. Overview
UDP is a transport layer protocol that provides no reliability. This project aims to show how to
implement extra features for the protocol in the application layer. The project will develop a new
version of UDP protocol called UDPplus which provides reliable connection oriented between a
client and a UDPplus sever. The UDPplus is implemented at application layer using UDP protocol
only.


II. Basic Scenario
The UPDplus is a simple bank inquiry application where the client sends full name and receive the
account name that is associated with this name.

Client Process: the process reads the name from a user then sends it to the server which returns
the account number. The process prints the account number to the user.
Server Process: the process remains ready to receive inquiries, once an inquiry is received it looks
for the account associated with the name as it is showed in the table above. If the account is found
the process returns the account number or returns “0000” as an error.


III. Application Core Features
The basic scenario should be extended to implement the following features:
1. Connection oriented feature: the UPDplus should implement two way handshaking. The
client should send a notification to the server in order to establish a new connection. If the
server is available it will replay with the maximum number of messages the server can
handle. If the server is not available (i.e. busy), it will replay with -1.
a. You should implement the server to randomly response with a chance 20% not
available.
b. The client cannot send datagrams until a positive response received from the server.
c. The port number for the server side is 9152.
d. The port number for the client side should be random.

2. Reliability feature: the reliability in UDPplus is achieved by
a. Every message sent by the client, the server should send an acknowledgement
b. Implement a timeout event in the client side, the event happens when an
acknowledgment is not received within 30 millisecond, the client should resend the
message. Each message has its own timeout and acknowledgement.
c. Implement a 10% random timeout event i.e. 10% of client’s messages will not be
acknowledged .

3. Security feature: the UDPplus should implement encryption and decryption features for
each messages exchanged between the client and the server processes. Control information
messages are sent plainly.
a. The encryption method is to reverse the message before sending it. For example, if
the client process wants to inquire about “Osama Ahmed” it sends it as “demhA
amasO”
b. The decryption method is to reverse the message before processing it. 
