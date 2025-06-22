/*
 * Producto.java
 *
 */

package entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Sebastian S. Sanga
 */
public class Producto {
    
    // Constantes
    public static final int NINGUNO = 0;
    public static final int VDS = 1;
    public static final int VVI = 2;
    public static final int SER = 3;
    public static final int OTRO = 4;
    
    // Atributos
    private int id;
    private String nombre;
    private String detalle;
    private int tipo;
    private int pallet;
    private int litros;
    
    /** Creates a new instance of Producto */
    public Producto() {
    }
    
    public static Producto obtenerPorId(Connection conn, int id) throws SQLException
    {
        // Define un producto
        Producto p = new Producto();

        // Construye la query
        String laConsulta = "SELECT * FROM productos WHERE producto_id = " + id;
        
        // Ejecuta la query
        Statement stmtConsulta = conn.createStatement();
        ResultSet rs = stmtConsulta.executeQuery(laConsulta);
        
        // Obtiene los datos
        if( rs.next() )
        {
            // Arma el objeto
            p.setId( rs.getInt("producto_id") );
            p.setNombre( rs.getString("nombre") );
            p.setDetalle( rs.getString("detalle") );
            p.setTipo( rs.getInt("tipo") );
            p.setPallet( rs.getInt("pallet") );
            p.setLitros( rs.getInt("litros") );
        }
        
        // Cierra el Statement
        stmtConsulta.close();
        
        // Retorna el item
        return p;
    }
    
    public static ArrayList obtener(Connection conn, int tipo) throws SQLException
    {
        // Define una coleccion
        ArrayList productos = new ArrayList();

        // Construye la query
        String laConsulta = "SELECT * FROM productos";
                
        // Si hay un tipo de producto...
        if( tipo != Producto.NINGUNO )
            laConsulta += " WHERE tipo = " + tipo;
        
        // Ejecuta la query
        Statement stmtConsulta = conn.createStatement();
        ResultSet rs = stmtConsulta.executeQuery(laConsulta);
        
        // Obtiene los datos
        while( rs.next() )
        {
            // Arma el objeto
            Producto p = new Producto();
            p.setId( rs.getInt("producto_id") );
            p.setNombre( rs.getString("nombre") );
            p.setDetalle( rs.getString("detalle") );
            p.setTipo( rs.getInt("tipo") );
            p.setPallet( rs.getInt("pallet") );
            p.setLitros( rs.getInt("litros") );
            productos.add(p);
       }
        
        // Cierra el Statement
        stmtConsulta.close();
        
        // Retorna la coleccion
        return productos;
    }
    
    public String getTipoAsString()
    {
        switch(tipo)
        {
            case VVI: return "Villavicencio";
            case VDS: return "Villa del Sur";
            case SER: return "Ser";
            case OTRO: return "Otros";
        }
        
        return "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getPallet() {
        return pallet;
    }

    public void setPallet(int pallet) {
        this.pallet = pallet;
    }

    public int getLitros() {
        return litros;
    }

    public void setLitros(int litros) {
        this.litros = litros;
    }
    
}
