import java.util.*;
import java.io.*;
public class  Listas{
public static void main(String args[]) throws IOException {
ArrayList<String> obj = new ArrayList<String>();
int tamanoLista;
Scanner in = new Scanner(System.in);
int c, ch;
int i, j;
String str, str1;
do {
System.out.println("MANIPULACION DE CADENAS");
System.out.println("************************");
System.out.println("1. Adicionar al final");
System.out.println("2. Insertar en una posicion particular");
System.out.println("3. Buscar");
System.out.println("4. Listar las cadenas que comienzan con la letra");
System.out.println("5. Tamaño");
System.out.println("6. Remover");
System.out.println("7. Ordenar");
System.out.println("8. Mostrar");
System.out.println("Ingrese la opción");
c = Integer.parseInt(in.next());
switch (c) {
case 1: {
System.out.println("Digite la cadena ");
str = in.next();
obj.add(str);
break;
}
case 2: {
System.out.println("Digite la cadena a insertar: ");
str = in.next();
tamanoLista = obj.size();
System.out.println("Digite el índice (0 a " + tamanoLista + "): ");
int idx = Integer.parseInt(in.next());
if (idx >= 0 && idx <= tamanoLista) {
	obj.add(idx, str);
	System.out.println("Cadena insertada correctamente.");
} else {
	System.out.println("Índice fuera de rango.");
}
break;
}
case 3: {
System.out.println("Digite la cadena a buscar: ");
str = in.next();
if (obj.contains(str)) {
	System.out.println("La cadena '" + str + "' SÍ está en la lista.");
} else {
	System.out.println("La cadena '" + str + "' NO está en la lista.");
}
break;
}
case 4: {
System.out.println("Digite la letra inicial: ");
str = in.next();
char letra = str.charAt(0);
System.out.println("Cadenas que comienzan con '" + letra + "':");
for (String s : obj) {
	if (s.length() > 0 && s.startsWith(String.valueOf(letra))) {
		System.out.println(s);
	}
}
break;
}
case 5: {
tamanoLista = obj.size();
System.out.println("Tamaño de la lista " + tamanoLista);
break;
}
case 6: {
System.out.println("Digite la cadena a remover: ");
str = in.next();
if (obj.remove(str)) {
	System.out.println("Cadena removida correctamente.");
} else {
	System.out.println("La cadena no se encontró en la lista.");
}
break;
}
case 7: {
Collections.sort(obj);
System.out.println("Lista ordenada correctamente.");
break;
}
case 8: {
System.out.println("Elementos en la lista:");
for (String s : obj) {
	System.out.println(s);
}
break;
}
}
System.out.println("Por favor ingrese, 0 para salir, 1 para continuar");
ch = Integer.parseInt(in.next());
} while (ch == 1);
}
}