import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TCPServer50 {
    private String message;
    int nrcli = 0;
    public static final int SERVERPORT = 4444;
    private OnMessageReceived messageListener = null;
    private boolean running = false;
    TCPServerThread50[] sendclis = new TCPServerThread50[10];
    PrintWriter mOut;
    BufferedReader in;
    ServerSocket serverSocket;
    //el constructor pide una interface OnMessageReceived
    public TCPServer50(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }
    public OnMessageReceived getMessageListener(){
        return this.messageListener;
    }
    public void sendConsumingMessageTCPServer(String message, ArrayList<Integer> consumers, int a){
        if(a == -1) {
            for (int i = 0; i < consumers.size(); i++) {
                sendclis[consumers.get(i)].sendMessage(message);
                System.out.println("ENVIANDO A CONSUMING " + (i + 1));
            }
        }else{
            sendclis[a].sendMessage(message);
            System.out.println("ENVIANDO A CONSUMING " + (a + 1));
        }
    }
    public void sendProducerConsuming(int index) {
        sendclis[index].sendMessage("Is Producer or Consuming?");
        System.out.println("ENVIANDO A CLIENTE " + (index));
    }

    public void sendProducerMessageTCPServer(String message, int indexProducer){
        sendclis[indexProducer].sendMessage(message);
        System.out.println("ENVIANDO A PRODUCER " + (indexProducer));
    }

    public int IDClient(){
        return nrcli;
    }
    
    public void run(){
        running = true;
        try{
            System.out.println("TCP Server"+"S : Connecting...");
            serverSocket = new ServerSocket(SERVERPORT);
            
            while(running){
                Socket client = serverSocket.accept();
                System.out.println("TCP Server"+"S: Receiving...");
                nrcli++;
                System.out.println("Engendrado " + nrcli);
                sendclis[nrcli] = new TCPServerThread50(client,this,nrcli,sendclis);
                Thread t = new Thread(sendclis[nrcli]);
                t.start();
                System.out.println("Nuevo conectado:"+ nrcli+" jugadores conectados");
                TimeUnit.SECONDS.sleep(5);
                sendProducerConsuming(nrcli);
            }
            
        }catch( Exception e){
            System.out.println("Error"+e.getMessage());
        }finally{

        }
    }
    public  TCPServerThread50[] getClients(){
        return sendclis;
    } 

    public interface OnMessageReceived {
        public void messageReceived(String message) throws InterruptedException;
    }
}
