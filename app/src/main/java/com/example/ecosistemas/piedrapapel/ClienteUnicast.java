package com.example.ecosistemas.piedrapapel;

import android.widget.Button;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClienteUnicast extends Thread {

	static DatagramSocket socket;
	static int PUERTO;
	static InetAddress DIRECCION;
	static int ID;



	@Override
	public void run() {
		ID = -1;
		Inicializar();
		while (true) {
			recibir();
		}
	}

	public void Inicializar() {
		try {
			PUERTO = 5000;
			DIRECCION = InetAddress.getByName("192.168.115.2");
			socket = new DatagramSocket();


		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void enviar(final String mensaje) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				DatagramPacket datagramPacket = new DatagramPacket(mensaje.getBytes(), mensaje.length(), DIRECCION,
						PUERTO);

				DIRECCION = datagramPacket.getAddress();
				PUERTO = datagramPacket.getPort();
				
				System.out.println("Direccion: " + DIRECCION + "   Puerto: " + PUERTO);

				
				try {
					socket.send(datagramPacket);
					System.out.println("Dato enviado");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	public void recibir() {


		try {
			byte[] informacion = new byte[100];
			DatagramPacket datagramPacket = new DatagramPacket(informacion, informacion.length);

			System.out.println("Esperando datos");
			socket.receive(datagramPacket);

			String mensaje = new String(datagramPacket.getData()).trim();

			DIRECCION = datagramPacket.getAddress();
			PUERTO = datagramPacket.getPort();

			mensajesRecibidos(mensaje);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void mensajesRecibidos(String mensaje) {

		if(mensaje.contains("tu id es")){
			String[] separar = mensaje.split(":");
			int temp = Integer.parseInt(separar[1]);
			if (ID == -1){
				ID = temp;
			}
		}

	}

	public long getId(){
		return this.ID;
	}




}