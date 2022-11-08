import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Middleware3 {
    TCPServer50 mTcpServer;
    Scanner sc;
    HashMap<Integer, BlockingQueue<String>> colas = new HashMap<>();
    int llegocont = 0;
    ArrayList<Integer> producer = new ArrayList<>();
    ArrayList<Integer> consumer = new ArrayList<>();

    public Middleware3() {
    }

    public static void main(String[] args) throws InterruptedException {
        Middleware3 objser = new Middleware3();
        objser.iniciar();
    }

    void iniciar() throws InterruptedException {
        (new Thread(new Runnable() {
            public void run() {
                colas.put(0, new LinkedBlockingDeque<>(5));
                mTcpServer = new TCPServer50(new TCPServer50.OnMessageReceived() {
                    public void messageReceived(String message) throws InterruptedException {
                        ServidorRecibe(message);
                    }
                });
                mTcpServer.run();
            }
        })).start();
        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Servidor bandera 01");

        while(!salir.equals("s")) {
            salir = sc.nextLine();
        }

        System.out.println("Servidor bandera 02");
    }

    void ServidorRecibe(String llego) throws InterruptedException {
        if (llego.equals("Producer")) {
            AddProducer(mTcpServer.IDClient());
        } else if (llego.equals("Consumer")) {
            AddConsuming(mTcpServer.IDClient());
            if(consumer.size()>0)
                colas.put(consumer.size(), new LinkedBlockingDeque<>(5));
        } else if (!llego.equals("listo") && (colas.get(consumer.size())).remainingCapacity() != 0) {
            colas.forEach((k,v) -> {
                try {
                    v.put(llego);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        if ((colas.get(consumer.size())).remainingCapacity() == 0) {
            String cola = "COLA LLENA ESPERA POR FAVOR";
            System.out.println(cola);
            mTcpServer.sendProducerMessageTCPServer(cola, producer.get(0));
        }

        if (llego.equals("listo")) {
            ++llegocont;
        }

        System.out.println("SERVIDOR40 El mensaje:" + llego);
        if (llegocont == mTcpServer.nrcli - 1 && consumer.size() > 0 && producer.size() > 0 && !(colas.get(consumer.size())).isEmpty()) {
            llegocont = 0;
            ServidorEnvia("s");
        }

    }

    void ServidorEnvia(String sus) throws InterruptedException {

        for(int i=0; i<consumer.size();i++){
            String envia = colas.get(consumer.indexOf(consumer.get(i))).take();
            envia = " se guardo en la cola " + i +
                    "\ny se mando al consumer: " + consumer.indexOf(consumer.get(i)) +
                    "\nel mensaje es: " + envia;
            if (mTcpServer != null) {
                mTcpServer.sendConsumingMessageTCPServer(envia, consumer, consumer.get(i));
            }
        }

    }

    void AddProducer(int ID) {
        producer.add(ID);
    }

    void AddConsuming(int ID) {
        consumer.add(ID);
    }
}
