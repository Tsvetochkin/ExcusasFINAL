package ar.edu.davinci.excusas;

import ar.edu.davinci.excusas.model.controller.ExcusaController;
import ar.edu.davinci.excusas.model.builder.CadenaEncargadosBuilder;
import ar.edu.davinci.excusas.model.domain.*;
import ar.edu.davinci.excusas.model.domain.excusas.*;
import ar.edu.davinci.excusas.model.domain.observer.*;
import ar.edu.davinci.excusas.model.service.EmpleadoService;
import ar.edu.davinci.excusas.model.service.ExcusaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExcusasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcusasApplication.class, args);
	}

	@Bean
	public CommandLineRunner runLogic(EmpleadoService empleadoService, ExcusaService excusaService) {
		return args -> {
			System.out.println("\n=== [COMPLETE PERSISTENCE] Empresa Excusas S.A. System Startup ===");

			Empleado dima = new Empleado("Dima", "dima@empresa.com", 501);
			Empleado mikhail = new Empleado("Mikhail", "mikhail@empresa.com", 502);
			Empleado andrey = new Empleado("Andrey", "andrey@empresa.com", 503);

			empleadoService.guardar(dima);
			empleadoService.guardar(mikhail);
			empleadoService.guardar(andrey);

			ExcusaController controller = new CadenaEncargadosBuilder()
				.conRecepcionista("Ana", "ana@empresa.com", 101)
				.conSupervisora("Berta", "berta@empresa.com", 102)
				.conCEO("Elon Musk", "elon@empresa.com", 1)
				.conEncargadoPorDefecto("Bot de Rechazo", "bot@empresa.com", 999)
				.build();

			System.out.println("\n--- Caso 1: Dima submits a trivial excuse ---");
			Excusa excusaDima = new ExcusaTrivial(dima);
			controller.manejar(excusaDima);
			excusaService.guardar(excusaDima);

			System.out.println("\n--- Caso 2: Mikhail submits an unbelievable excuse ---");
			Excusa excusaMikhail = new ExcusaInverosimil(mikhail);
			controller.manejar(excusaMikhail);
			excusaService.guardar(excusaMikhail);

			System.out.println("\n=== Datos Guardados en la Base de Datos ===");
		};
	}
}
