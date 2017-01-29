package org.usfirst.frc.team4499.robot.tools;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Tegra implements Runnable{
	public static int x = -1;
	public static int y = -1;
	int port;
	String fromClient = "";
    String toClient = "";
    ServerSocket serverSocket;
    boolean run = true;
    Thread tegra;
	public Tegra() throws IOException{
	        port = 5801;
	        
	        this.start();
	        
	    }

	    public static int[] parsePoint(String line) {
	    	//System.out.println("Checking Line" + line);
	        int point[] = {0,0};
	        int position = 0;
	        if(line.length() > 1){
	        	if(line.charAt(0) == '#'){
	        		System.out.println(line);
	        		point[0] = -2;
	        		point[1] = -2;
	        		return point;
	        	}
		        	
		        
		        while(line.charAt(position) != '('){
		            position++;
		        }
		        position++;
		        while (position < line.length() && line.charAt(position) != ',') {
		            point[0] = (point[0] * 10) + (int)line.charAt(position) - 48;
		           // System.out.println(point[0]);
		            position++;
		        }
		        position++;
		        while (position < line.length() && line.charAt(position) != ')') {
		            point[1] = (point[1] * 10) + (int)line.charAt(position) - 48;
		            position++;
		        }
	        }

	        return point;

	    }

		@Override
		public void run() {
			
			try{
				 serverSocket = new ServerSocket(port);
				 while (run) {
					 	System.out.println("roboRIO looking for Tegra");
					 	fromClient = "";
			            Socket socket = serverSocket.accept();
			            System.out.println("Got Connection from: " + socket.getLocalAddress());
			            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			            while (fromClient != null && run) {
			                fromClient = in.readLine();
			                if(fromClient == null){
			                	x = -1;
			                	y = -1;
			                	System.out.println("Losts comms with Tegra");
			                	break;
			                }
			                int point[] = parsePoint(fromClient);
			                // Scanner scanner = new Scanner(fromClient);
			                //int x = scanner.nextInt();
			                // int y = scanner.nextInt();
			               // System.out.println("received: "  +"(" +(point[0]) +","+ (point[1]) + ")"); 
			                x = point[0];
			                y = point[1];
			            }

			        }
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		public void start(){
			System.out.println("Creating Tegra thread");
			tegra = new Thread(this,"TegraData");
			System.out.println("Starting Tegra thread");
			tegra.start();
		}
		public static int getX(){
			return x;
		}
		public static int getY(){
			return y;
		}
	}



