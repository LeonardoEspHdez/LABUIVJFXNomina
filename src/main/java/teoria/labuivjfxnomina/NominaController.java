package teoria.labuivjfxnomina;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class NominaController implements Initializable {

    @FXML
    private TextField txtNombre, txtApellidos, txtHorasTrabajadas, txtMatricula, txtBuscar;

    @FXML
    private TableView<Empleado> tblListaDeEmpleados;

    @FXML
    private TableColumn<Empleado, String> colMatricula;

    @FXML
    private TableColumn<Empleado, String> colNombre;

    @FXML
    private TableColumn<Empleado, String> colApellidos;

    @FXML
    private TableColumn<Empleado, String> colPuesto;

    @FXML
    private TableColumn<Empleado, String> colHorasTrabajadas;

    @FXML
    private TableColumn<Empleado, String> colPagoXHora;

    @FXML
    private TableColumn<Empleado, String> colSalarioNeto;

    /**
     * Lista observable que almacenará los empleados. El TableView mostrará
     * automáticamente lo que se agregue aquí
     */
    private ObservableList<Empleado> listaEmpleados;

    /**
     * Lista auxiliar para mostrar resultados filtrados
     */
    private ObservableList<Empleado> listaFiltrada = FXCollections.observableArrayList();

    @FXML
    private ComboBox cboPuesto;

    /**
     * Este método se ejecuta automáticamente cuando se carga el archivo FXML
     * Carga los items del combobox
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Elementos o ítems que se agregarán al combobox
        cboPuesto.getItems().addAll("Administrador", "Gerente", "Empleado");

        // establece el valor predeterminado
        cboPuesto.getSelectionModel().select("Administrador");

        //Relacionar cada columna con el atributo correspondiente de la clase Empleado
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        colHorasTrabajadas.setCellValueFactory(new PropertyValueFactory<>("horasTrabajadas"));
        colPagoXHora.setCellValueFactory(new PropertyValueFactory<>("pagoXHoras"));
        colSalarioNeto.setCellValueFactory(new PropertyValueFactory<>("salarioNeto"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        // Crear la lista observable
        listaEmpleados = FXCollections.observableArrayList();

        // Asignar la lista al TableView
        tblListaDeEmpleados.setItems(listaEmpleados);
    }

    @FXML
    protected void onAgregarEmpleadoButtonClick() {
        // obtenemos los datos capturados
        String matricula = txtMatricula.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String puesto = (String) cboPuesto.getValue();
        double horasTrabajadas = Double.parseDouble(txtHorasTrabajadas.getText());
        double pagoXHora = 800.0;

        // Creamos el objeto Empleado
        Empleado empleado = new Empleado(matricula, nombre, apellidos, puesto, horasTrabajadas, pagoXHora);

        // cuando se le paga por hora.
        listaEmpleados.add(empleado);

        limpiarFormulario();
    }

    @FXML
    protected void limpiarFormulario() {
        txtMatricula.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");

        // Establece el valor predeterminado de la lista desplegable
        cboPuesto.getSelectionModel().select("Administrador");

        txtHorasTrabajadas.setText("");

        txtMatricula.requestFocus();
    }

    /**
     * Elimina un empleado seleccionado
     */
    @FXML
    protected void onEliminarEmpleadoButtonClick() {

        // Obtenemos el empleado actualmente seleccionado en el TableView
        Empleado empleadoSeleccionado = tblListaDeEmpleados.getSelectionModel().getSelectedItem();

        // Validamos que realmente se seleccionó algo
        if (empleadoSeleccionado == null) {
            // importa el paquete correspondiente para que no te marque error
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sin seleccion");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar un empleado para poderlo eliminar");
            alert.showAndWait();
            return;
        }

        // Mostrar confirmación antes de eliminar
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("Deseas eliminar el empleado: "
                + empleadoSeleccionado.getNombre() + " "
                + empleadoSeleccionado.getApellidos());

        // importa los paquetes correspondientes
        Optional<ButtonType> resultado = confirmacion.showAndWait();

        // Si el usuario confirma, se elimina de la lista observable
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            listaEmpleados.remove(empleadoSeleccionado);

            Alert informacion = new Alert(Alert.AlertType.INFORMATION);
            informacion.setTitle("Eliminación exitosa");
            informacion.setHeaderText(null);
            informacion.setContentText("El empleado fue eliminado correctamente.");
            informacion.showAndWait();
        }
    } // fin del método eliminar  

    /**
     * Permite buscar un empleado por matricula o por nombre
     */
    @FXML
    protected void onBuscarEmpleadoButtonClick() {

        // obtener el texto escrito por el usuario
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();

        // Si el campo esta vacio, mostrar todos los empleados
        if (textoBusqueda.isEmpty()) {
            tblListaDeEmpleados.setItems(listaEmpleados);
            return;
        }

        // Limpiar resultados anteriores
        listaFiltrada.clear();

        // Recorrer la lista maestra y buscar coincidencias
        for (Empleado empleado : listaEmpleados) {
            boolean coincideMatricula = empleado.getMatricula().toLowerCase().contains(textoBusqueda);
            boolean coincideNombre = empleado.getNombre().toLowerCase().contains(textoBusqueda);

            if (coincideMatricula || coincideNombre) {
                listaFiltrada.add(empleado);
            }
        }

        // actualizar setItems(listaFiltrada)
        tblListaDeEmpleados.setItems(listaFiltrada);
    }
    
    /**
     * Muestra la lista de todos los empleados
     */
    @FXML
    protected void onMostrarTodosButtonClick() {
        txtBuscar.clear();
        tblListaDeEmpleados.setItems(listaEmpleados);
    }
}
