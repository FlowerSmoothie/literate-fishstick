package Client;

import java.io.*;
import java.net.*;

public class Client
{
    public static void main(String[] arg)
    {
        try
        {
            Socket clientSocket = new Socket("127.0.0.1",2525);
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream  cois = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Enter any string to send to server (write EXIT to exit)\n");
            String clientMessage = stdin.readLine();
            while(!clientMessage.equals("EXIT"))
            {
                coos.writeObject(clientMessage);
                System.out.println("~server~: "+cois.readObject());
                System.out.println("---------------------------");
                clientMessage = stdin.readLine();
            }
            coos.writeObject("EXIT");
            coos.close();
            cois.close();
            clientSocket.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
