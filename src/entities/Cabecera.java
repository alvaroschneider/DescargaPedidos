/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author alvaro
 */
public class Cabecera {

    private int nro;
    private int cliente;
    private int sucursal;
    private int oc;
    private String fecha;
    private String hora;
    private String fechae;

    public String getFechae() {
        return fechae;
    }

    public void setFechae(String fechae) {
        this.fechae = fechae;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getOc() {
        return oc;
    }

    public void setOc(int oc) {
        this.oc = oc;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public static ArrayList obtenerCabeceras(Connection conn) throws SQLException {
        // Define una coleccion
        ArrayList cabeceras = new ArrayList();

        // Construye la query
        String laConsulta = "SELECT c.* "
                + "FROM cabecera c, cuerpo cu "
                + "WHERE c.nro_p=cu.nro_p and cu.nro_p Is Not Null "
                + "GROUP BY c.nro_p, c.cliente_p, c.sucursal_p, c.oc_p, c.fecha_p, c.hora_p, c.fechae_p, c.marca, c.usuario_id";


        // Ejecuta la query
        Statement stmtConsulta = conn.createStatement();
        ResultSet rs = stmtConsulta.executeQuery(laConsulta);

        // Obtiene los datos
        while (rs.next()) {
            // Arma el objeto
            Cabecera c = new Cabecera();
            c.setNro(rs.getInt("nro_p"));
            c.setCliente(rs.getInt("cliente_p"));
            c.setSucursal(rs.getInt("sucursal_p"));
            c.setOc(rs.getInt("oc_p"));
            c.setFecha(rs.getString("fecha_p"));
            c.setHora(rs.getString("hora_p"));
            c.setFechae(rs.getString("fechae_p"));
            cabeceras.add(c);
        }

        // Cierra el Statement
        stmtConsulta.close();

        // Retorna la coleccion
        return cabeceras;
    }

    public static ArrayList obtenerCabecerasNoDescargadas(Connection conn) throws SQLException {
        // Define una coleccion
        ArrayList cabeceras = new ArrayList();

        // Construye la query
        String laConsulta = "SELECT c.* "
                + "FROM cabecera c, cuerpo cu "
                + "WHERE c.marca=0 and c.nro_p=cu.nro_p and cu.nro_p Is Not Null "
                + "GROUP BY c.nro_p, c.cliente_p, c.sucursal_p, c.oc_p, c.fecha_p, c.hora_p, c.fechae_p, c.marca, c.usuario_id";

        // Si hay un tipo de producto...
        //if (tipo != Producto.NINGUNO) {
        //    laConsulta += " WHERE tipo = " + tipo;
        //}

        // Ejecuta la query
        Statement stmtConsulta = conn.createStatement();
        ResultSet rs = stmtConsulta.executeQuery(laConsulta);

        // Obtiene los datos
        while (rs.next()) {
            // Arma el objeto
            Cabecera c = new Cabecera();
            c.setNro(rs.getInt("nro_p"));
            c.setCliente(rs.getInt("cliente_p"));
            c.setSucursal(rs.getInt("sucursal_p"));
            c.setOc(rs.getInt("oc_p"));
            c.setFecha(rs.getString("fecha_p"));
            c.setHora(rs.getString("hora_p"));
            c.setFechae(rs.getString("fechae_p"));
            System.out.println("fechae: "+ rs.getString("fechae_p"));
            cabeceras.add(c);
        }

        // Cierra el Statement
        stmtConsulta.close();

        // Retorna la coleccion
        return cabeceras;
    }

}
