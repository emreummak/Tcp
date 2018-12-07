package Tcp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
public class TcpL implements Runnable {
	Socket client;
	public TcpL(Socket client) {
		this.client = client;
	}
	public static void main(String[] args) {
		int port = 12002;
		ServerSocket server;
		try {
			server = new ServerSocket(port);
			while (true) {
				Socket clientSocket = server.accept();
				TcpL processor = new TcpL(clientSocket);
				Thread t = new Thread(processor);
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			disconnect();
		} catch (IOException e) {
			System.err.println("Error in client processor: " + e);
		} catch (Exception e) {

		}
	}

	private void disconnect() throws IOException {
		this.client.close();
	}

}