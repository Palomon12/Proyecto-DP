import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ejemplo1 {

	public static void main(String[] args) {
		List<Integer> listaNumeros = new ArrayList<>(List.of(7,3,6,4,5,5,9));

		System.out.print("Lista inicial: \t\t" );
		listaNumeros.forEach(n -> System.out.print(n + " "));
		
		Collections.sort(listaNumeros);
		System.out.print("\nLista Ordenado: \t" );
		listaNumeros.forEach(n -> System.out.print(n + " "));
		
		Collections.reverse(listaNumeros);
		System.out.print("\nLista Invers: \t\t" );
		listaNumeros.forEach(n -> System.out.print(n + " "));
		
		Collections.shuffle(listaNumeros);
		System.out.print("\nLista Desordenado: \t" );
		listaNumeros.forEach(n -> System.out.print(n + " "));
		
		System.out.println("\nCantidad de 5: \t\t" + Collections.frequency(listaNumeros, 5));
		System.out.println("Número Mayor: \t\t" + Collections.max(listaNumeros));
		System.out.println("Número Menor: \t\t" + Collections.min(listaNumeros));
		
		Collections.sort(listaNumeros);
		System.out.println("Posición del 6: \t" + Collections.binarySearch(listaNumeros, 6));
		int indice = Collections.binarySearch(listaNumeros, 8);
		System.out.println("Posición del 8: \t" + indice);
		listaNumeros.add(-indice-1, 8);
		System.out.print("Nueva Lista Ordenada: \t" );
		listaNumeros.forEach(n -> System.out.print(n + " "));		
		
		System.out.println();
		List<Integer> otraLista1 = List.of(0,1,2);
		if (Collections.disjoint(listaNumeros, otraLista1)) 
            System.out.print("No tiene elementos en común con: ");
        else
        	System.out.print("\nSí tiene elementos en común con: ");
		otraLista1.forEach(n -> System.out.print(n + " "));
		
        List<Integer> otraLista2 = List.of(1,2,3,4);
        if (Collections.disjoint(listaNumeros, otraLista2)) 
            System.out.print(" - No tiene elementos en común con: ");
        else
        	System.out.print("\nSí tiene elementos en común con: ");
		otraLista2.forEach(n -> System.out.print(n + " "));
        
	}
}
