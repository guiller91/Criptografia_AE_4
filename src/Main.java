import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

public class Main {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {

			// variables para control de la aplicacion
			String texto = "";
			boolean continuar = true;
			int num;

			// 0 = fail 1=Simetrico 2=Asimetrico
			int num2 = 0, num3 = 0;

			Coche coche, coche2;

			// Variables para transformación en bytes
			byte[] bytesTexto = null;
			byte[] bytesTextoCifrado = null;

			// generador Simetrico
			KeyGenerator kGeneSime = KeyGenerator.getInstance("AES");
			SecretKey sk = kGeneSime.generateKey();
			Cipher cifraSimetrico = Cipher.getInstance("AES");
			cifraSimetrico.init(Cipher.ENCRYPT_MODE, sk);
			Cipher descifraSimetrico = Cipher.getInstance("AES");
			descifraSimetrico.init(Cipher.DECRYPT_MODE, sk);

			// generador Asimetrico
			KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance("RSA");
			KeyPair claves = kpGenerator.generateKeyPair();
			Cipher cifraAsimetrico = Cipher.getInstance("RSA");
			cifraAsimetrico.init(Cipher.ENCRYPT_MODE, claves.getPublic());
			Cipher descifraAsimetrico = Cipher.getInstance("RSA");
			descifraAsimetrico.init(Cipher.DECRYPT_MODE, claves.getPrivate());

			// Objeto que nos cifrara un objeto entero
			SealedObject so = null;

			do {
				System.out.println("\nElige un número del menú:\n" + "1. Encriptar frase\n"
						+ "2. Mostrar frase encriptada\n" + "3. Desencriptar frase\n" + "4. Encriptar coche\n"
						+ "5. Desencriptar coche\n" + "6. Salir del programa");
				num = Integer.parseInt(sc.nextLine());

				switch (num) {

				case 1:
					System.out.println("Introduzca la frase a encriptar");
					texto = sc.nextLine();
					bytesTexto = texto.getBytes();
					System.out.println("Para encriptación simetrica pulse 1\n" + "Para encriptación asimetrica pulse 2");
					num2 = Integer.parseInt(sc.nextLine());
					if (num2 == 1) {
						bytesTextoCifrado = cifraSimetrico.doFinal(bytesTexto);
						System.out.println("Mensaje cifrado correctamente");
					} else if (num2 == 2) {
						bytesTextoCifrado = cifraAsimetrico.doFinal(bytesTexto);
						System.out.println("Mensaje cifrado correctamente");
					} else {
						System.out.println("No has elegido una opción disponible, volveras al menu principal");
						texto = "";
						num2 = 0;
					}

					break;
				case 2:
					if (num2 == 0) {
						System.out.println("Introduce una frase antes de seleccionar esta opción");
					}
					if (num2 == 1) {
						System.out.println("La frase que has cifrado es: " + texto);
						System.out.println("Cifrado Simétrico : " + new String(bytesTextoCifrado));
					}
					if (num2 == 2) {
						System.out.println("La frase que has cifrado es: " + texto);
						System.out.println("Cifrado Asimétrico : " + new String(bytesTextoCifrado));
					}
					break;
				case 3:
					if (num2 == 0) {
						System.out.println("Introduce una frase antes de seleccionar esta opción");
					}
					if (num2 == 1) {
						System.out.println("Cifrado Simetrico (en bytes): " + bytesTextoCifrado);
						bytesTexto = descifraSimetrico.doFinal(bytesTextoCifrado);
						System.out.println("Frase Descifrada frase: " + new String(bytesTexto));
					}
					if (num2 == 2) {
						System.out.println("Cifrado Asimetrico (en bytes): " + bytesTextoCifrado);
						bytesTexto = descifraAsimetrico.doFinal(bytesTextoCifrado);
						System.out.println("Frase Descifrada frase: " + new String(bytesTexto));
					}
					break;
				case 4:
					coche = new Coche();
					System.out.println("Introduzca Marca");
					coche.setMarca(sc.nextLine());
					System.out.println("Introduzca Modelo");
					coche.setModelo(sc.nextLine());
					System.out.println("Introduzca Matricula");
					coche.setMatricula(sc.nextLine());
					System.out.println("Introduzca Precio");
					coche.setPrecio(Integer.parseInt(sc.nextLine()));
					System.out.println("Para encriptación simetrica pulse 1\n" + "Para encriptación asimetrica pulse 2");
					num3 = Integer.parseInt(sc.nextLine());
					if (num3 == 1) {
						so = new SealedObject(coche, cifraSimetrico);
						System.out.println("Coche cifrado correctamente");
					} else if (num3 == 2) {
						so = new SealedObject(coche, cifraAsimetrico);
						System.out.println("Coche cifrado correctamente");
					} else {
						System.out.println("No has elegido una opción disponible, volveras al menu principal");
						num3 = 0;
					}

					break;
				case 5:
					if (num3 == 0) {
						System.out.println("Encripta un Coche antes de seleccionar esta opción");
					}
					if (num3 == 1) {
						System.out.println("Coche Cifrado de forma Simetrica: " + so.toString());
						coche2 = (Coche) so.getObject(descifraSimetrico);
						System.out.println("Datos del " + coche2.toString());
					}
					if (num3 == 2) {
						System.out.println("Coche Cifrado de forma Asimetrica: " + so.toString());
						coche2 = (Coche) so.getObject(descifraAsimetrico);
						System.out.println("Datos del " + coche2.toString());
					}

					break;
				case 6:
					System.out.println("Cerrando aplicación");
					continuar = false;
					break;
				}

			} while (continuar);

		} catch (Exception e) {
			System.err.println("Algo ha fallado: Error -> " + e);
			e.printStackTrace();
		}
		
		System.out.println("Aplicación cerrada");

	}

}
