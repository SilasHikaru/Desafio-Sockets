/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impressora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author cliente
 */
public class MsgCliente implements Runnable{
    
    private Socket socket;
    private InputStream imprime;
    private PrintStream out;
    
    private boolean ativado;
    private boolean executando;
    
    private Thread thread;
    
    public MsgCliente(Socket socket) throws IOException {
        
        this.socket = socket;
        this.ativado = false;
        this.executando = false;
        open();
    }
    
    private void open()throws IOException {
        try{
     
            imprime = socket.getInputStream();
            out = new PrintStream(socket.getOutputStream());
            ativado = true;
        }catch(Exception e){
            close();
            throw e;
        }
    }
    private void close(){
        if(imprime != null){
            try{
            imprime.close();
            }catch(Exception e){
                System.out.print(e);
            }
        }
        
        if(out != null){
            try{
            out.close();
            }catch(Exception e){
                System.out.print(e);
            }
        }
        
        try{
            socket.close();
        }catch(Exception e){
                System.out.print(e);
        }
        
        imprime = null;
        out = null;
        socket = null;
        
        ativado = false;
        executando = false;
        thread = null;
    }
    
    public void start(){
        if(!ativado||executando){
            return;
        }
        executando = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public void stop() throws Exception{
        executando = false;
        thread.join();
    }
    
    @Override
    public void run(){
        while(executando){
            try{
                              
                System.out.println("Mensagem recebida: " + imprime.read() );
                Thread.sleep(100);
            }catch(SocketTimeoutException e){
                
            }catch(Exception e){
                System.out.print(e);
                break;
            }
            
        }
        System.out.println("Conexão sendo encerrada");
        close();
    }
}
