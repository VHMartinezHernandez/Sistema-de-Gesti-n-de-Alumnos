package gm.universidad;

import com.formdev.flatlaf.FlatDarculaLaf;
import gm.universidad.gui.UniversidadForma;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class UniversidadSwing {
    public static void main(String[] args) {
        // Configurar modo oscuro
        FlatDarculaLaf.setup();
        // Instancia la aplicación Spring con configuración específica
        ConfigurableApplicationContext contextoSpring =
                new SpringApplicationBuilder(UniversidadSwing.class)
                        .headless(false) // Permite la creación de interfaces gráficas
                        .web(WebApplicationType.NONE) // Indica que no se trata de una aplicación web
                        .run(args);

        // Crear un objeto de Swing
        SwingUtilities.invokeLater(()-> {
            UniversidadForma universidadForma = contextoSpring.getBean(UniversidadForma.class);
            universidadForma.setVisible(true);
        });
    }
}
