import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Reto {

	public static void main(String[] args) {
		//Inicialización
		String[] listadoEquipos = { "Equipo 1", "Equipo 2", "Equipo 3", "Equipo 4", "Equipo 5", "Equipo 6" };

		Set<String> emparejamientos = new HashSet<>();
		List <String> listado1 = new ArrayList<>(Arrays.asList(listadoEquipos));
		List <String> listado2 = new ArrayList<>(Arrays.asList(listadoEquipos));
		
		//Proceso
		Collections.shuffle(listado1);
		Collections.shuffle(listado2);
		
		for (String equipo1 : listado1) {
			for (String equipo2 : listado2) {
				if (!equipo1.equals(equipo2)) {
					ArrayList <String> partido = new ArrayList<>(Arrays.asList(equipo1,equipo2));
					Collections.sort(partido);
					String encuentro = partido.get(0) + " - " + partido.get(1);
					emparejamientos.add(encuentro);
				}
			}
		}
		//Salida
		System.out.println(Arrays.toString(listadoEquipos));
		emparejamientos.forEach(partido -> System.out.println(partido));

	}
}
