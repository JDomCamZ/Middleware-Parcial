import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Servidor50 {
   TCPServer50 mTcpServer;
   Scanner sc;
   static BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(5);
   public static void main(String[] args) throws InterruptedException {
       Servidor50 objser = new Servidor50();
       objser.iniciar();
   }
   void iniciar() throws InterruptedException {
       new Thread(
            new Runnable() {

                @Override
                public void run() {
                      mTcpServer = new TCPServer50(
                        new TCPServer50.OnMessageReceived(){
                            @Override
                            public void messageReceived(String message) throws InterruptedException {
                                ServidorRecibe(message);
                            }
                        }
                    );
                    mTcpServer.run();                   
                }
            }
        ).start();
        //-----------------
        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Servidor bandera 01");
        while( !salir.equals("s")){
            salir = sc.nextLine();
            ServidorEnvia(salir);
       }
       System.out.println("Servidor bandera 02"); 
   
   }
   void ServidorRecibe(String llego) throws InterruptedException {
       blockingQueue.put(llego);
       System.out.println("SERVIDOR40 El mensaje:" + llego);
       ServidorEnvia("s");
   }
   void ServidorEnvia(String sus) throws InterruptedException {
       String envia = blockingQueue.take();
       if (mTcpServer != null) {
           mTcpServer.sendMessageTCPServer(envia);
       }
   }
}
