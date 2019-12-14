
package socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;


public class UDPServerPLus {

    
 
	// list to store accounts
	public static ArrayList<Account> accountList;
	// set fixed port server
	public static int serverPort = 9152;

	public static void main(String[] args) throws IOException {
          
		// fill account list
		accountList = new ArrayList<>();
		accountList.add(new Account(15324, "Abdullah Ali"));
		accountList.add(new Account(90781, "Manal Abdullah"));
		accountList.add(new Account(88125, "Henry Markos"));
		accountList.add(new Account(62044, "Hisham Mansoor"));
		accountList.add(new Account(71825, "Asma Awal"));
		accountList.add(new Account(12818, "Osama Ahmed"));
		accountList.add(new Account(29502, "Alice Tarkood"));
		accountList.add(new Account(19012, "Mohammed Khalid"));

		System.out.println("### Server ###");
		// create socket and open port
		DatagramSocket socket = new DatagramSocket(serverPort);
		System.out.println("Running.........\n");
		// define sender and reciverer messages
		String SM, RM;
                 if(Packet.getNumber() ==0){
		// Receive inital packet from client
		byte[] b0 = new byte[1024];
		DatagramPacket receiveInitalPkt = new DatagramPacket(b0, b0.length);
		socket.receive(receiveInitalPkt);
		RM = new String(receiveInitalPkt.getData(), 0, receiveInitalPkt.getLength());
		System.out.println("From client: " + RM);
		// Send acknowledgment to client to start send packet
		SM = "Hi client...you can send to me 2 packet";
		DatagramPacket sendIntialAck = new DatagramPacket(SM.getBytes(), SM.length(), receiveInitalPkt.getAddress(),
				receiveInitalPkt.getPort());
		socket.send(sendIntialAck);
                 Packet.changeNumber(1);
                }
                
           if(Packet.getNumber() ==1){
		// Receive first packet from client
		byte[] b = new byte[1024];
		DatagramPacket receivePkt1 = new DatagramPacket(b, b.length);
		socket.receive(receivePkt1);
		RM = new String(receivePkt1.getData(), 0, receivePkt1.getLength());
		System.out.println("From client, Account name: " + RM);
		// get account number by name
		int accountNumber = getAccountNumber_byName(RM);
		String number = "";
		// if account not found send 0000
		if (accountNumber == 0)
			number = "0000";
		else
			number = String.valueOf(accountNumber);
		// send first acknowledgment to client
		SM = "Account number: " + number;
		DatagramPacket sendAck1 = new DatagramPacket(SM.getBytes(), SM.length(), receivePkt1.getAddress(),
				receivePkt1.getPort());
		socket.send(sendAck1);
                 Packet.changeNumber(2);
           }

              if(Packet.getNumber() ==2){
		// Receive first packet from client
		byte[] b = new byte[1024];
		// Receive second packet from client
		b = new byte[1024];
		DatagramPacket receivePkt2 = new DatagramPacket(b, b.length);
		socket.receive(receivePkt2);
		RM = new String(receivePkt2.getData(), 0, receivePkt2.getLength());
		System.out.println("From client: " + RM);
		// get client text and decryption text
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(RM);
		RM = stringBuilder.reverse().toString();
		System.out.println("Text after decryption: " + RM);
		// if not Send Acknowledgment1 to Sender
		SM = " decryption text successfully";
		DatagramPacket sendAck2 = new DatagramPacket(SM.getBytes(), SM.length(), receivePkt2.getAddress(),
				receivePkt2.getPort());
		socket.send(sendAck2);
                 Packet.changeNumber(2);
              }

		// Close server
                Packet.changeNumber(0);
		socket.close();
		System.out.println("Server socket Closed");

	}

	// search in list to find number of account by name
	public static int getAccountNumber_byName(String name) {

		for (int i = 0; i < accountList.size(); i++) {
			Account account = accountList.get(i);
			if (account.getName().equalsIgnoreCase(name)) {
				return account.getId();
			}
		}
		// 0s means not found
		return 0000;
	}

}
