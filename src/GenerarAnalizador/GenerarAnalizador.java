/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenerarAnalizador;

/**
 *
 * @author hboiton
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class GenerarAnalizador {
    
    public static void main(String[] args) {
                try{
		Runtime runtime = Runtime.getRuntime();
		String[] consola = new String[2];
		consola[0] = "javacc";
                consola[1]="src/Analizador/analizadorlienzos.jj";
                
                Process proc = runtime.exec(consola);
		InputStream is = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
                }catch(IOException e){
                    System.out.print(e.toString());
                }
    }
    
    
}
