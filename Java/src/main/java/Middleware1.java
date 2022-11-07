import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Middleware1 {
   TCPServer50 mTcpServer;
   Scanner sc;
   BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(5);
   boolean isEmpty;
   public static void main(String[] args) throws InterruptedException {
       Middleware1 objser = new Middleware1();
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
            //ServidorEnvia(salir);
       }
       System.out.println("Servidor bandera 02"); 
   
   }
   void ServidorRecibe(String llego) throws InterruptedException {
       if (!llego.equals("listo") && blockingQueue.remainingCapacity()!=0)
           blockingQueue.put(llego);
       else if (blockingQueue.remainingCapacity()==0) {
           System.out.println("COLA LLENA ESPERA POR FAVOR");
           String cola = "COLA LLENA ESPERA POR FAVOR";
           mTcpServer.sendColaMessageTCPServer(cola);
       }
       System.out.println("SERVIDOR40 El mensaje:" + llego);
       if (llego.equals("listo")) {
           System.out.println(blockingQueue.remainingCapacity());
           ServidorEnvia("s");
       }
   }
   void ServidorEnvia(String sus) throws InterruptedException {
       String envia = blockingQueue.take();
       if (mTcpServer != null) {
           mTcpServer.sendMessageTCPServer(envia);
       }
   }
}
