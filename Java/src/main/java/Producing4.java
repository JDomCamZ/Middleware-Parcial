import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Producing4 {
    TCPClient50 mTcpClient;
    Scanner sc;
    public static void main(String[] args)  {
        Producing4 objcli = new Producing4();
        objcli.iniciar();
    }

    void iniciar(){
        new Thread(
                new Runnable() {

                    @Override
                    public void run() {
                        mTcpClient = new TCPClient50("192.168.0.18",
                                new TCPClient50.OnMessageReceived(){
                                    @Override
                                    public void messageReceived(String message){
                                        ClienteRecibe(message);
                                    }
                                }
                        );
                        mTcpClient.run();
                    }
                }
        ).start();
        //---------------------------

        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Cliente bandera 01");
        while( !salir.equals("s")){
            salir = sc.nextLine();
            ClienteEnvia(salir);
        }
        System.out.println("Cliente bandera 02");

    }
    void ClienteRecibe(String llego){
        if (llego.equals("Is Producer or Consuming?")) {
            System.out.println("CLINTE50 El mensaje::" + llego);
            ClienteEnvia("Producer");
            ClienteEnvia("cttlisto");
        }
        else{
            System.out.println("CLINTE50 El mensaje::" + llego);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ClienteEnvia("cttlisto");
        }


    }
    void ClienteEnvia(String envia){
        if (mTcpClient != null) {
            mTcpClient.sendMessage(envia);
        }
    }

}
