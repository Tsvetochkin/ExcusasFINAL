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

			// 1. Создаем сотрудников
			Empleado dima = new Empleado("Dima", "dima@empresa.com", 501);
			Empleado mikhail = new Empleado("Mikhail", "mikhail@empresa.com", 502);
			Empleado andrey = new Empleado("Andrey", "andrey@empresa.com", 503);

			// 2. Сохраняем их через SERVICE
			empleadoService.guardar(dima);
			empleadoService.guardar(mikhail);
			empleadoService.guardar(andrey);

			// 3. Собираем цепочку ответственных
			ExcusaController controller = new CadenaEncargadosBuilder()
				.conRecepcionista("Ana", "ana@empresa.com", 101)
				.conSupervisora("Berta", "berta@empresa.com", 102)
				.conCEO("Elon Musk", "elon@empresa.com", 1)
				.conEncargadoPorDefecto("Bot de Rechazo", "bot@empresa.com", 999)
				.build();

			// 4. Дима подает Тривиальное оправдание
			System.out.println("\n--- Caso 1: Оправдание для Димы ---");
			Excusa excusaDima = new ExcusaTrivial(dima);
			controller.manejar(excusaDima);
			excusaService.guardar(excusaDima); // СОХРАНЯЕМ В БАЗУ!

			// 5. Михаил подает Сложное оправдание (Инверосимиль)
			System.out.println("\n--- Caso 2: Оправдание для Михаила ---");
			Excusa excusaMikhail = new ExcusaInverosimil(mikhail);
			controller.manejar(excusaMikhail);
			excusaService.guardar(excusaMikhail); // СОХРАНЯЕМ В БАЗУ!

			System.out.println("\n=== Datos Guardados en la Base de Datos ===");
		};
	}
}
