# Actividad 4 - Criptografía

## Miembros del grupo:

- [Iván Gaitán Muñoz](https://github.com/IGaitanM)
- [Luz Maria Lozano Asimbaya](https://github.com/luzlozas)
- [Miguel Pérez Larren](https://github.com/miguelperezlarren)
- [Guillermo Pérez Arias](https://github.com/guiller91)

## Video pruebas

[https://www.youtube.com/watch?v=43HCHZDi7NY](https://www.youtube.com/watch?v=43HCHZDi7NY)



## Objetivos:

Crear aplicación que nos ofrezca lo siguiente:

- Encriptar frases y objetos
    - De forma simétrica
    - De forma Asimétrica
- Mostrar frase u objeto encriptado
- Desencriptar frase u objeto

## Explicación puntos clave

---

Con el siguiente código vamos a generar las herramientas para hacerlo de forma simétrica. 

```java
			KeyGenerator kGeneSime = KeyGenerator.getInstance("AES");
			SecretKey sk = kGeneSime.generateKey();
			Cipher cifraSimetrico = Cipher.getInstance("AES");
			cifraSimetrico.init(Cipher.ENCRYPT_MODE, sk);
			Cipher descifraSimetrico = Cipher.getInstance("AES");
			descifraSimetrico.init(Cipher.DECRYPT_MODE, sk);
```

Con el siguiente código vamos a generar las herramientas para hacerlo de forma asimétrica. 

```java
			KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance("RSA");
			KeyPair claves = kpGenerator.generateKeyPair();
			Cipher cifraAsimetrico = Cipher.getInstance("RSA");
			cifraAsimetrico.init(Cipher.ENCRYPT_MODE, claves.getPublic());
			Cipher descifraAsimetrico = Cipher.getInstance("RSA");
			descifraAsimetrico.init(Cipher.DECRYPT_MODE, claves.getPrivate());
```

Con los códigos anteriores podemos cifrar tanto las frases como los objetos, lo único que nos haría falta para cifrar el objeto completo seria llamar a la clase `SealedObject so = null;` . Lo inicializamos en `null` dado que le daremos valor según las exigencias del usuario.

Nuestra aplicación mostrara el siguiente menú:

```java
Elige un número del menú:

1. Encriptar frase
2. Mostrar frase encriptada
3. Desencriptar frase
4. Encriptar coche
5. Desencriptar coche
6. Salir del programa
```

### Para el caso 1:

Vamos a recoger la frase que nos meta el usuario en una variable que después la convertiremos en `Byte[].` Según quiera el usuario cifraremos de forma simétrica o asimétrica utilizando el cifrador `cifraSimetrico` o `cifraAsimetrico` con el método `.doFinal`  pasándole el valor del texto ya convertido en `Byte[]`

```java
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
```

### Para el caso 2:

Mostraremos el cifrado que ha elegido con una cadena de texto del cifrado.

```java
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
```

### Para el caso 3:

Descifraremos el mensaje utilizando el cifrador `descifraSimetrico` o `descifraAsimetrico` según haya elegido previamente el usuario, usando `.doFinal()` y pasándole la variable con los bytes cifrados.

```java
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
```

### Para el caso 4:

Creamos el objeto `Coche` con los datos que nos pase el usuario y lo encriptamos con la clase `SealedObject` pasándole el objeto y el cifrador que elija el usuario, ya sea asimétrico o simétrico.

```java
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
```

### Para el caso 5:

Desencriptaremos el objeto con `SealedObject` y el método `getObject()` diciéndole la clase del objeto y pasándole el `descrifraSimetrico` o `descifraAsimetrico` según haya escogido anteriormente el usuario.

```java
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
```

### Para el caso 6:

Para cerrar la aplicación pasamos `False` al boolean `continuar` para salir del bucle `Do-While` .

```java
case 6:
					System.out.println("Cerrando aplicación");
					continuar = false;
					break;
```