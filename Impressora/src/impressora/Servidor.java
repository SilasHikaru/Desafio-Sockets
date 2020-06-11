/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impressora;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Servidor implements Runnable{
    
    private ServerSocket serverSocket;
    private Socket cliente;
    private int porta = 2525;
    private boolean ativado;
    private boolean executando;
    private Thread thread;
    
    private List<MsgCliente> clientes;
   
     public Servidor() throws IOException{
        clientes = new ArrayList<MsgCliente>();
        ativado = false;
        executando = false;
        
        open();
     }
     private void open() throws IOException{
         serverSocket = new ServerSocket(porta);
         ativado = true;
     }
     private void close()throws IOException{
         
         for(MsgCliente cliente : clientes){
             try{
                 cliente.stop();
             }catch(Exception e){
                 System.out.print(e);
             }
         }
         
         serverSocket.close();
         
         serverSocket = null;
         
         ativado = false;
         executando = false;    
         
         thread = null;
     }
     
     public void start(){
         if(!ativado || executando){ // se foi ativado e não estiver executando continua
             return;
         }
         executando = true;
         thread = new Thread(this);
         thread.start();
     }
     
     public void stop()throws Exception{
        
        executando = false;
        thread.join();
        
     }
     
     
    @Override
    public void run() {
        
        System.out.println("Aguardando conexão");
        
       while(executando) {
           try{
             //  serverSocket.setSoTimeout(2500);
               Socket socket = serverSocket.accept();
               
               System.out.println("Conexão estabelecida");
               
               MsgCliente msgCliente = new MsgCliente(socket);
               msgCliente.start();
               clientes.add(msgCliente);
               System.out.println(msgCliente);
               Thread.sleep(100);
           }catch(SocketTimeoutException e){
               System.out.println("");
               break;
           }catch(Exception e){
               
           }
       }   
       try{
           close();
       }catch(IOException e){
           
       }     
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando servidor");
        
        Servidor servidor = new Servidor();
        servidor.start();
              
        System.out.println("Pressione Enter para encerrar o servidor");
        new Scanner(System.in).nextLine();
        
        System.out.println("Encerrando servidor");
        servidor.stop();
    }
    
    
}
