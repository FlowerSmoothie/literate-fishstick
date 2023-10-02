package Client;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;


public class ClientPartTwo {
    private static final InetAddress HOST;
    private static final int PORT = 8001;

    static {
        try {
            HOST = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runClient() throws SocketException, IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            while (true) {
                System.out.println("Enter 1 to continue, other to exit: ");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.next();
                if(line.equals("1"))
                {
                    byte[] buf = new byte[128];
                    double x, y, z;
                    while(true)
                    {
                        System.out.println("Enter X:");
                        if (scanner.hasNextDouble())
                        {
                            x = scanner.nextDouble();
                            break;
                        }
                        System.out.println("Wrong input!");
                    }
                    while(true)
                    {
                        System.out.println("Enter Y:");
                        if (scanner.hasNextDouble())
                        {
                            y = scanner.nextDouble();
                            break;
                        }
                        System.out.println("Wrong input!");
                    }
                    while(true)
                    {
                        System.out.println("Enter Z:");
                        if (scanner.hasNextDouble())
                        {
                            z = scanner.nextDouble();
                            break;
                        }
                        System.out.println("Wrong input!");
                    }
                    line = new String("" + x + ' ' + y + ' ' + z);
                    byte[] params = line.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket packet = new DatagramPacket(params, params.length, HOST, PORT);
                    socket.send(packet);
                    packet = new DatagramPacket(buf, buf.length, HOST, PORT);
                    socket.receive(packet);
                    String res = new String(packet.getData()).trim();
                    System.out.println("Your answer is: " + res);
                    try (FileWriter file = new FileWriter("operationsLog.txt"))
                    {
                        file.append("X = " + x+ "\nY = " + y + "\nZ = " + z + "\nResulted = " + res + "\n\n");
                    }
                }
                else return;
            }
        }
    }

    public static void main(String[] args)
    {
        try
        {
            ClientPartTwo client = new ClientPartTwo();
            client.runClient();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
