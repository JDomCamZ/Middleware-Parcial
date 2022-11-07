import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Middleware1 {
   TCPServer50 mTcpServer;
   Scanner sc;
   BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(5);
   ArrayList<Integer> producer = new ArrayList<Integer>();
   ArrayList<Integer> consumer = new ArrayList<Integer>();
   boolean ready = false;
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
       if (llego.equals("Producer")) {
           AddProducer(mTcpServer.IDClient());
       }
       else if (llego.equals("Consumer"))
           AddConsuming(mTcpServer.IDClient());
       else if (!llego.equals("listo") && blockingQueue.remainingCapacity()!=0)
           blockingQueue.put(llego);
       if (blockingQueue.remainingCapacity()==0) {
           String cola = "COLA LLENA ESPERA POR FAVOR";
           System.out.println(cola);
           mTcpServer.sendProducerMessageTCPServer(cola, producer.get(0));
       }
       if (llego.equals("listo"))
           ready = true;
       System.out.println("SERVIDOR40 El mensaje:" + llego);
       if ((ready==true && consumer.size()>0 && producer.size()>0) && !blockingQueue.isEmpty()) {
           ready = false;
           System.out.println(blockingQueue.remainingCapacity());
           ServidorEnvia("s");
       }
   }
   void ServidorEnvia(String sus) throws InterruptedException {
       String envia = blockingQueue.take();
       if (mTcpServer != null) {
           mTcpServer.sendConsumingMessageTCPServer(envia, consumer);
       }
   }
    void AddProducer (int ID) {
        producer.add(ID);
    }

    void AddConsuming(int ID) {
        consumer.add(ID);
    }
}
