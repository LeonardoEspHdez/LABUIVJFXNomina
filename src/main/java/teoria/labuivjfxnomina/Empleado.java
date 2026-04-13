/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teoria.labuivjfxnomina;

/**
 *
 * @author Leonardo
 */
public class Empleado {

    // declaración de campos
    private String nombre;
    private String apellidos;
    private String puesto;
    private double horasTrabajadas;
    private double pagoXHoras;
    private double salarioNeto;
    private String matricula;

    public Empleado(String matricula, String nombre, String apellidos, String puesto, double horasTrabajadas, double pagoXHoras) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.puesto = puesto;
        this.horasTrabajadas = horasTrabajadas;
        this.pagoXHoras = pagoXHoras;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the puesto
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * @param puesto the puesto to set
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    /**
     * @return the horasTrabajadas
     */
    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    /**
     * @param horasTrabajadas the horasTrabajadas to set
     */
    public void setHorasTrabajadas(double horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    /**
     * @return the pagoXHoras
     */
    public double getPagoXHoras() {
        return pagoXHoras;
    }

    /**
     * @param pagoXHoras the pagoXHoras to set
     */
    public void setPagoXHoras(double pagoXHoras) {
        this.pagoXHoras = pagoXHoras;
    }

    /**
     * @return the salarioNeto
     */
    public double getSalarioNeto() {
        return calcularSalarioNeto();
    }

    private double calcularSalarioNeto() {
        double horasExtras = 0.00;
        double pagoPorHorasExtras = 0.00;

        // Si trabajó más de 40 horas se pagan esas horas extras
        // al 10% más de lo que se paga por hora
        if (horasTrabajadas > 40) {
            horasExtras = horasTrabajadas - 40;

            pagoPorHorasExtras = pagoXHoras * 1.10;
        }

        return horasTrabajadas * pagoXHoras + pagoPorHorasExtras;
    }

    /**
     * @return the matricula
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * @param matricula the matricula to set
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
}
