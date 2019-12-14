package socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class UDPClientPlus {
    

            
    	// generate port number
	public static int get_randomPort() {
		int port = (int) (Math.random() * 9000) + 1000;
		while (true) {
			if (is_portAvailable(port)) {
				return port;
			} else {
				port = ThreadLocalRandom.current().nextInt(0, 1000);
			}
		}
	}
	// check if port available
	private static boolean is_portAvailable(int port) {
		try {
			new ServerSocket(port).close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	public static void main(String[] args) throws IOException {

                Packet.changeNumber(0);
		System.out.println("### Client ###");

		// create socket with random port
		DatagramSocket socket = new DatagramSocket(get_randomPort());

		// Create IP address local host
		InetAddress ip = InetAddress.getByName("localhost");
		// set timer 30 millisecond
		int timer = 30;
		// inital value server not available
		boolean isServerAvailable = false;
		try {
			// time out to check server response
			socket.setSoTimeout(timer);
			// inital message to server
			String initalCon = "server you are available ?";
			byte[] initalConByte = new byte[1024];
			initalConByte = initalCon.getBytes();
			// send packet with inital message
			DatagramPacket initalPkt = new DatagramPacket(initalConByte, initalConByte.length, ip,
					UDPServerPLus.serverPort);
			socket.send(initalPkt);
			// get rsponse from server
			byte[] b0 = new byte[1024];
			DatagramPacket initalAck = new DatagramPacket(b0, b0.length);
			socket.receive(initalAck);
			String response = new String(initalAck.getData(), 0, initalAck.getLength());
			System.out.println("From server response: " + response);
			isServerAvailable = true;
		} catch (SocketTimeoutException e) {
			// time out
			System.out.println("Service not available..cannot establis connection");
		}

                Packet.changeNumber(1);
		// check if server available start send packet
		if (isServerAvailable == true) {

			// First packet
			// get account name from user to get acount number from server
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter account name : ");
			String packet_content1 = scanner.nextLine();
			byte[] packet_data1 = new byte[1024];
			// send packet
			packet_data1 = packet_content1.getBytes();
			DatagramPacket pkt1 = new DatagramPacket(packet_data1, packet_data1.length, ip, UDPServerPLus.serverPort);
			socket.send(pkt1);
			// Set timer (3) millisecond for acknowledgment
			socket.setSoTimeout(timer);
			String RM1 = null;
                        int check =0;
			while (RM1 == null) {
				byte[] b1 = new byte[1024];
				DatagramPacket Ack1 = new DatagramPacket(b1, b1.length);
				try {
					// recive acknowledgment
					socket.receive(Ack1);
					RM1 = new String(Ack1.getData(), 0, Ack1.getLength());
					System.out.println("From server acknowledgement 1: " + RM1);
				} catch (SocketTimeoutException e) {
                                       
					// timeout .
                                        if(check==0){
					System.out.println("Timeout packet 1....");
                                         System.out.println("Resending packet 1......:");
                                        }
                                        check=1;
				       
                                            socket.send(pkt1);
                                        

				}
			}

                        Packet.changeNumber(2);
			// Second packet
			// Get text from usrr to encryption to server
			System.out.print("(Secutity).. Enter text : ");
			String packet_content2 = scanner.nextLine();
			// reserve text with stringBuilder
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(packet_content2);
			packet_content2 = stringBuilder.reverse().toString();
			System.out.println("Encryption text.......");
			byte[] packet_data2 = new byte[1024];
			packet_data2 = packet_content2.getBytes();
			DatagramPacket pkt2 = new DatagramPacket(packet_data2, packet_data2.length, ip, UDPServerPLus.serverPort);
			socket.send(pkt2);
			// Set timer for acknowledgment
			socket.setSoTimeout(timer);
			String RM = null;
                         int check2 =0;
			while (RM == null) {
				// recive acknowledgment
				byte[] b = new byte[1024];
				DatagramPacket Ack2 = new DatagramPacket(b, b.length);
				try {
	                              socket.receive(Ack2);
					RM = new String(Ack2.getData(), 0, Ack2.getLength());
					System.out.println("From server acknowledgement 2: " + RM);
				} catch (SocketTimeoutException e) {
					// timeout
                                        if(check2==0){
					System.out.println("Timeout packet 2....");
                                         System.out.println("Resending packet 2......:");
                                        }
                                        check2=1;
				       
                                            socket.send(pkt2);
			}
                        }

			// close connection
                        Packet.changeNumber(0);
			socket.close();
			System.out.println("client Socket Closed");

		}
	}


}