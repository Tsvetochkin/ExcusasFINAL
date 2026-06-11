package ar.edu.davinci.excusas;

import ar.edu.davinci.excusas.model.controller.ExcusaController;
import ar.edu.davinci.excusas.model.builder.CadenaEncargadosBuilder;
import ar.edu.davinci.excusas.model.domain.*;
import ar.edu.davinci.excusas.model.domain.chain.impl.*;
import ar.edu.davinci.excusas.model.domain.excusas.*;
import ar.edu.davinci.excusas.model.domain.observer.*;
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
	public CommandLineRunner runLogic() {
		return args -> {
			System.out.println("\n=== [SPRING BOOT] Empresa Excusas S.A. System Startup ===");

			// 1. Создаем сотрудников
			Empleado juan = new Empleado("Juan Tarde", "juan@empresa.com", 501);

			// 2. Настраиваем наблюдателей
			AdministradorProntuarios admin = AdministradorProntuarios.getInstancia();
			admin.agregarObservador(new EquipoDireccion());
			
			CEO ceo = new CEO("Elon Musk", "elon@empresa.com", 1);
			admin.agregarObservador(ceo);

			// 3. Собираем цепочку
			ExcusaController controller = new CadenaEncargadosBuilder()
				.agregar(new Recepcionista("Ana", "ana@empresa.com", 101))
				.agregar(new Supervisora("Berta", "berta@empresa.com", 102))
				.agregar(ceo)
				.agregar(new EncargadoDefecto("Bot de Rechazo", "bot@empresa.com", 999))
				.build();

			// 4. Тестируем разные отговорки
			System.out.println("\n--- Caso 1: Excusa Trivial ---");
			controller.manejar(new ExcusaTrivial(juan));

			System.out.println("\n--- Caso 2: Excusa de Luz (Moderada) ---");
			controller.manejar(new ExcusaLuz(juan));

			System.out.println("\n--- Caso 3: Excusa Inverosímil (CEO + Observer + Singleton) ---");
			controller.manejar(new ExcusaInverosimil(juan));

			System.out.println("\n=== System Ready ===\n");
		};
	}
}
