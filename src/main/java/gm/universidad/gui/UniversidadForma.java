package gm.universidad.gui;

import gm.universidad.modelo.Alumno;
import gm.universidad.servicio.AlumnoServicio;
import gm.universidad.servicio.IAlumnoServicio;
import gm.universidad.modelo.Alumno;
import gm.universidad.servicio.AlumnoServicio;
import gm.universidad.servicio.IAlumnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class UniversidadForma extends JFrame {
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    private JPanel panelTabla;
    private JPanel panelCampos;
    private JPanel panelBotones;
    IAlumnoServicio alumnoServicio;
    private DefaultTableModel tablaModeloClientes;
    private Integer idCliente;


    @Autowired
    public UniversidadForma(AlumnoServicio alumnoServicio){
        this.alumnoServicio = alumnoServicio;
        iniciarForma();

        guardarButton.addActionListener(e -> guardarCliente());

        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
        eliminarButton.addActionListener(e -> eliminarCliente());
        limpiarButton.addActionListener(e -> limpiarFormulario());

    }

    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);//centra ventana
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        // TODO: place custom component creation code here
//        this.tablaModeloClientes = new DefaultTableModel(0, 4);
        // Evitamos la edicion de los valores de las celdas de la tabla
        this.tablaModeloClientes = new DefaultTableModel(0,4){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        String[] cabeceros = {"Id", "Nombre", "Apellido", "Membresia"};
        this.tablaModeloClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(tablaModeloClientes);
        // Restringimos la seleccion de la tabla a un solo registro
        this.clientesTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Cargar listado de clientes
        listarClientes();
    }

    private void listarClientes(){
        this.tablaModeloClientes.setRowCount(0);
        var clientes = this.alumnoServicio.listarAlumnos();
        clientes.forEach(cliente -> {
            Object[] renglonCliente = {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMatricula()
            };
            this.tablaModeloClientes.addRow(renglonCliente);
        });
    }

    private void guardarCliente() {
        if (nombreTexto.getText().equals("")) {
            mostrarMensaje("Proporciona un nombre");
            nombreTexto.requestFocusInWindow();
            return;
        }
        if (membresiaTexto.getText().equals("")) {
            mostrarMensaje("Proporciona una matricula");
            membresiaTexto.requestFocusInWindow();
            return;
        }

        // Verifica si el número de membresía ya está registrado
        Integer membresia = Integer.parseInt(membresiaTexto.getText());
        if (this.idCliente == null && alumnoServicio.existeMatricula(membresia)) {
            mostrarMensaje("El número de matricula ya está registrado.");
            membresiaTexto.requestFocusInWindow();
            return;
        }

        // Recupera los valores del formulario
        var nombre = nombreTexto.getText();
        var apellido = apellidoTexto.getText();
        var alumno = new Alumno(this.idCliente, nombre, apellido, membresia);

        // Guarda o actualiza el cliente
        this.alumnoServicio.guardarAlumno(alumno);
        if (this.idCliente == null) {
            mostrarMensaje("Se agregó el nuevo Alumno");
        } else {
            mostrarMensaje("Se actualizó el Alumno");
        }

        // Limpia el formulario y recarga la lista de clientes
        limpiarFormulario();
        listarClientes();
    }



    private void cargarClienteSeleccionado(){
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){ // -1 significa que no selecciono ningun registro
            var id = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = clientesTabla.getModel().getValueAt(renglon, 1).toString();
            this.nombreTexto.setText(nombre);
            var apellido = clientesTabla.getModel().getValueAt(renglon, 2).toString();
            this.apellidoTexto.setText(apellido);
            var membresia = clientesTabla.getModel().getValueAt(renglon, 3).toString();
            this.membresiaTexto.setText(membresia);
        }
    }

    private void eliminarCliente(){
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){
            var idClienteStr = clientesTabla.getModel().getValueAt(renglon,0).toString();
            this.idCliente = Integer.parseInt(idClienteStr);
            var cliente = new Alumno();
            cliente.setId(this.idCliente);
            alumnoServicio.eliminarAlumno(cliente);
            mostrarMensaje("Alumno con id " + this.idCliente + " eliminado");
            limpiarFormulario();
            listarClientes();
        }
        else
            mostrarMensaje("Debe seleccionar un Alumno a eliminar");

    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void limpiarFormulario(){
        nombreTexto.setText("");
        apellidoTexto.setText("");
        membresiaTexto.setText("");
        // Limpiamos el id cliente seleccionado
        this.idCliente = null;
        // Deseleccionamos el registro seleccionado de la tabla
        this.clientesTabla.getSelectionModel().clearSelection();
    }
}
