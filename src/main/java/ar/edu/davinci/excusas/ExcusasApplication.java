package ar.edu.davinci.excusas;

import ar.edu.davinci.excusas.model.controller.ExcusaController;
import ar.edu.davinci.excusas.model.builder.CadenaEncargadosBuilder;
import ar.edu.davinci.excusas.model.domain.*;
import ar.edu.davinci.excusas.model.domain.excusas.*;
import ar.edu.davinci.excusas.model.domain.observer.*;
import ar.edu.davinci.excusas.model.service.EmpleadoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExcusasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcusasApplication.class, args);
	}

	/**
	 * Теперь мы внедряем EmpleadoService.
	 * Это соответствует принципу разделения ответственности из твоего урока.
	 */
	@Bean
	public CommandLineRunner runLogic(EmpleadoService service) {
		return args -> {
			System.out.println("\n=== [SERVICE LAYER] Empresa Excusas S.A. System Startup ===");

			// 1. Создаем сотрудников
			Empleado dima = new Empleado("Dima", "dima@empresa.com", 501);
			Empleado mikhail = new Empleado("Mikhail", "mikhail@empresa.com", 502);
			Empleado andrey = new Empleado("Andrey", "andrey@empresa.com", 503);

			// 2. Сохраняем их через SERVICE
			service.guardar(dima);
			service.guardar(mikhail);
			service.guardar(andrey);

			System.out.println(">>> Данные прошли через Service и сохранены в БД!");

			// 3. Настраиваем наблюдателей
			AdministradorProntuarios admin = AdministradorProntuarios.getInstancia();
			admin.agregarObservador(new EquipoDireccion());
			
			// 4. Собираем цепочку ответственных
			ExcusaController controller = new CadenaEncargadosBuilder()
				.conRecepcionista("Ana", "ana@empresa.com", 101)
				.conSupervisora("Berta", "berta@empresa.com", 102)
				.conCEO("Elon Musk", "elon@empresa.com", 1)
				.conEncargadoPorDefecto("Bot de Rechazo", "bot@empresa.com", 999)
				.build();

			// 5. Тестируем отговорку
			System.out.println("\n--- Caso: Проверка логики для Михаила ---");
			controller.manejar(new ExcusaTrivial(mikhail));

			System.out.println("\n=== Architecture: Application -> Service -> Repository -> DB ===\n");
		};
	}
}
