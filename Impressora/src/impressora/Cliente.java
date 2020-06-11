/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impressora;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;

/**
 *
 * @author cliente
 */
public class Cliente {
    
    public static void main(String[] args) throws IOException {
  
  System.out.println("Inciando Cliente.");
  
  System.out.println("Inciando Conexão com Servidor.");
  
  Socket socket = new Socket("localhost",2525);
 
  System.out.println("Conexão estabelecida.");
  
  InputStream entrada = socket.getInputStream();
  
  OutputStream output = socket.getOutputStream();
  
 // BufferedReader in = new BufferedReader(new InputStreamReader(entrada));
  
  PrintStream out = new PrintStream(output);
  
  
  Scanner scanner = new Scanner(System.in);
  
  while (true) {
      
    if(entrada.available() != 0){
        
        byte[] mensagem = new byte[entrada.available()];
        entrada.read(mensagem);
        String dadosLidos = new String(mensagem);
        System.out.println(mensagem);
        out.println(dadosLidos);
        
         if ("FIM".equals(mensagem)) {
             break;
         }
    }

    /*mensagem = in.readLine();

    System.out.println(
      "Mensagem recebida do servidor: " +
      mensagem);
      */
   }

   System.out.println("Encerrando conexão.");

   entrada.close();

   out.close();

   socket.close();
  
  }
}
