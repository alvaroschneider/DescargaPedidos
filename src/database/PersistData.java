/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import entities.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alvaro
 */
public class PersistData {

    public void insertarCabecera(int cliente, int sucursal, int oc) throws Exception {
        // Define la conexion
        Connection laConexion = ConnectionManager.getConnection();

        // Arma la sentencia de insercion y la ejecuta
        String laInsercion = "INSERT INTO cabecera (cliente_p, sucursal_p, oc_p) VALUES ('" + String.valueOf(cliente) + "', '" + String.valueOf(sucursal) + "', '" + String.valueOf(oc) + "')";
        Statement stmtInsercion = laConexion.createStatement();
        stmtInsercion.execute(laInsercion);

        // Cierra el Statement y la Connection
        stmtInsercion.close();
        laConexion.close();

        // Informa que la insercion ha sido realizada con exito
        System.out.println("La insercion ha sido realizada con exito...");
    }

    public void insertarCabeceraConFecha(int cliente, int sucursal, int oc, String fecha, String hora) throws Exception {
        // Define la conexion
        Connection laConexion = ConnectionManager.getConnection();

        // Arma la sentencia de insercion y la ejecuta
        String laInsercion = "INSERT INTO cabecera (cliente_p, sucursal_p, oc_p, fecha_p, hora_p) VALUES ('" + String.valueOf(cliente) + "', '" + String.valueOf(sucursal) + "', '" + String.valueOf(oc) + "', '" + fecha + "', '" + hora + "')";
        Statement stmtInsercion = laConexion.createStatement();
        stmtInsercion.execute(laInsercion);

        // Cierra el Statement y la Connection
        stmtInsercion.close();
        laConexion.close();

        // Informa que la insercion ha sido realizada con exito
        System.out.println("La insercion ha sido realizada con exito...");
    }

    public int getIdPedido() throws Exception {
        Connection laConexion = ConnectionManager.getConnection();

        // Arma la sentencia de insercion y la ejecuta
        String consulta = "SELECT * FROM cabecera";
        Statement stmt = laConexion.createStatement();
        ResultSet rs = stmt.executeQuery(consulta);

        rs.last();

        int nro = rs.getInt("nro_p");

        // Cierra el Statement y la Connection
        stmt.close();
        laConexion.close();
        return nro;
    }

    public DatosCliente getCabeceraPedido(int nro) throws Exception {

        DatosCliente dc = new DatosCliente();
        Connection laConexion = ConnectionManager.getConnection();

        // Arma la sentencia de insercion y la ejecuta
        String consulta = "SELECT * FROM cabecera WHERE( nro_p = '" + String.valueOf(nro) + "')";
        Statement stmt = laConexion.createStatement();
        ResultSet rs = stmt.executeQuery(consulta);

        String cliente = rs.getString("cliente_p");
        String sucursal = rs.getString("sucursal_p");
        String oc = rs.getString("oc_p");
        String fecha = rs.getString("fecha_p");
        String hora = rs.getString("hora_p");

        dc.setCliente(Integer.parseInt(cliente));
        dc.setSucursal(Integer.parseInt(sucursal));
        dc.setOc(Integer.parseInt(oc));
        dc.setFecha(fecha);
        dc.setHora(hora);
        //rs.last();

        //int nro = rs.getInt("nro_p");

        // Cierra el Statement y la Connection
        stmt.close();
        laConexion.close();
        return dc;
    }

    public void insertarCuerpo(int nro, int cod, int cantFact) throws Exception {

        Connection laConexion = ConnectionManager.getConnection();

        Statement stmtInsercion = null;
        stmtInsercion = laConexion.createStatement();

        String laInsercion = null;


        laInsercion = "INSERT INTO cuerpo (nro_p, cod_p, cantFact_p) VALUES ('" + String.valueOf(nro) + "', '" + String.valueOf(cod) + "', '" + String.valueOf(cantFact) + "')";
        stmtInsercion.execute(laInsercion);
        System.out.println(laInsercion);
        stmtInsercion.close();
        laConexion.close();

        System.out.println(nro + " - " + cod + " - " + cantFact);
    }

    public Vector getItems() throws Exception {
        Vector items = new Vector();
        Connection laConexion = ConnectionManager.getConnection();

        // Arma la sentencia de insercion y la ejecuta
        String consulta = "SELECT * FROM cuerpo";
        Statement stmt = laConexion.createStatement();
        ResultSet rs = stmt.executeQuery(consulta);

        while (rs.next()) {
            if (rs.getInt("marca") == 0) {
                Item item = new Item();
                item.setNro(rs.getInt("nro_p"));
                item.setCod(rs.getInt("cod_p"));
                item.setCantFact(rs.getInt("cantFact_p"));
                item.setBoca(1);
                items.add(item);
            }

        }

        stmt.close();
        laConexion.close();
        return items;
    }

    public Vector getItemsNoDescargados() throws Exception {
        Vector items = new Vector();
        Connection laConexion = ConnectionManager.getConnection();

        // Arma la sentencia de insercion y la ejecuta
        String consulta = "SELECT ca.marca, ca.nro_p, ca.cliente_p, ca.sucursal_p, ca.oc_p, cu.cod_p, cu.cantFact_p\n"
                + "FROM cuerpo cu, cabecera ca\n"
                + "WHERE ca.nro_p = cu.nro_p AND ca.marca = 0\n"
                + "ORDER BY ca.cliente_p, ca.sucursal_p, ca.oc_p, ca.nro_p";
        Statement stmt = laConexion.createStatement();
        ResultSet rs = stmt.executeQuery(consulta);

        while (rs.next()) {
            if (rs.getInt("marca") == 0) {
                Item item = new Item();
                item.setNro(rs.getInt("nro_p"));
                item.setCliente(rs.getInt("cliente_p"));
                item.setSucursal(rs.getInt("sucursal_p"));
                item.setOc(rs.getInt("oc_p"));
                item.setCod(rs.getInt("cod_p"));
                item.setCantFact(rs.getInt("cantFact_p"));
                item.setBoca(1);
                items.add(item);
            }

        }

        stmt.close();
        laConexion.close();
        return items;
    }

    public Vector getAllItems() throws Exception {
        Vector items = new Vector();
        Connection laConexion = ConnectionManager.getConnection();

        // Arma la sentencia de insercion y la ejecuta
        String consulta = "SELECT * FROM cuerpo WHERE marca = 1";
        Statement stmt = laConexion.createStatement();
        ResultSet rs = stmt.executeQuery(consulta);

        while (rs.next()) {
            Item item = new Item();
            item.setNro(rs.getInt("nro_p"));
            item.setCod(rs.getInt("cod_p"));
            item.setCantFact(rs.getInt("cantFact_p"));
            item.setBoca(1);
            items.add(item);


        }

        stmt.close();
        laConexion.close();
        return items;
    }

    public void modificaMarca() throws Exception {
        Connection laConexion = ConnectionManager.getConnection();

        // Arma la sentencia de insercion y la ejecuta
        String consulta = "UPDATE cabecera LEFT JOIN cuerpo ON cabecera.nro_p = cuerpo.nro_p SET cabecera.marca = 1 "
                + "WHERE (((cuerpo.nro_p) Is Not Null))";
        Statement stmt = laConexion.createStatement();
        stmt.execute(consulta);

        consulta = "UPDATE cuerpo SET marca = 1";
        stmt = laConexion.createStatement();
        stmt.execute(consulta);
    }

    public DefaultTableModel getModelo() throws Exception {
        Connection laConexion = ConnectionManager.getConnection();

        //El siguiente paso es realizar la consulta y obtener el ResultSet. El código es el siguiente
        Statement s = laConexion.createStatement();
        ResultSet rs = s.executeQuery("SELECT u.username, c.nro_p, c.cliente_p, c.sucursal_p, c.oc_p, c.fecha_p, c.hora_p, c.fechae_p, ROUND(sum(cu.cantFact_p/p.pallet),1) as pallets "
                + "FROM usuarios u, cabecera c, cuerpo cu, productos p "
                + "WHERE c.usuario_id=u.usuario_id and c.nro_p=cu.nro_p and cu.cod_p=p.producto_id and c.marca=0 "
                + "GROUP BY u.username, c.nro_p, c.cliente_p, c.sucursal_p, c.oc_p, c.fecha_p, c.hora_p, c.fechae_p "
                + "ORDER BY c.nro_p DESC");

//Para meter los datos en el JTable, usaremos la clase DefaultTableModel. Para ello basta con instanciar el JTable como se muestra en el codigo
        DefaultTableModel modelo = new DefaultTableModel();

//Ahora sólo hay que rellenar el DefaultTableModel con los datos del ResultSet.

// Creamos las columnas.
        modelo.addColumn("user");
        modelo.addColumn("nro_p");
        modelo.addColumn("cliente_p");
        modelo.addColumn("sucursal_p");
        modelo.addColumn("oc_p");
        modelo.addColumn("fecha_p");
        modelo.addColumn("hora_p");
        modelo.addColumn("fechae_p");
        modelo.addColumn("pallets");

// Recorremos los registros con un ciclo while
        while (rs.next()) {
            // Se crea un array que será una de las filas de la tabla.
            Object[] fila = new Object[9]; // Hay tres columnas en la tabla

            // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
            for (int i = 0; i < 9; i++) {
                fila[i] = rs.getObject(i + 1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
            }                                          //No es como en PHP o otros lenguajes donde los indices inician con 0

            // Se añade al modelo la fila completa.
            modelo.addRow(fila);
        }
        return modelo;

    }

    public DefaultTableModel getModeloTodosLosPedidos() throws Exception {
        Connection laConexion = ConnectionManager.getConnection();

        //El siguiente paso es realizar la consulta y obtener el ResultSet. El código es el siguiente
        Statement s = laConexion.createStatement();
        /*
        ResultSet rs = s.executeQuery("SELECT u.username, c.nro_p, c.cliente_p, c.sucursal_p, c.oc_p, c.fecha_p, c.hora_p, c.fechae_p\n"
                + "FROM usuarios u, cabecera c\n"
                + "WHERE c.usuario_id=u.usuario_id\n"
                + "ORDER BY c.nro_p DESC;");
                */ 
        ResultSet rs = s.executeQuery("SELECT u.username, c.nro_p, c.cliente_p, c.sucursal_p, c.oc_p, c.fecha_p, c.hora_p, c.fechae_p, ROUND(sum(cu.cantFact_p/p.pallet),1) as pallets "
                + "FROM usuarios u, cabecera c, cuerpo cu, productos p "
                + "WHERE c.usuario_id=u.usuario_id and c.nro_p=cu.nro_p and cu.cod_p=p.producto_id and c.marca=1 "
                + "GROUP BY u.username, c.nro_p, c.cliente_p, c.sucursal_p, c.oc_p, c.fecha_p, c.hora_p, c.fechae_p "
                + "ORDER BY c.nro_p DESC");

//Para meter los datos en el JTable, usaremos la clase DefaultTableModel. Para ello basta con instanciar el JTable como se muestra en el codigo
        DefaultTableModel modelo = new DefaultTableModel();
        //JTable tabla = new JTable(modelo);

        //JCheckBox jcb = new JCheckBox();
        //jcb.setSelected(true);

//Ahora sólo hay que rellenar el DefaultTableModel con los datos del ResultSet.

// Creamos las columnas.
        modelo.addColumn("user");
        modelo.addColumn("nro_p");
        modelo.addColumn("cliente_p");
        modelo.addColumn("sucursal_p");
        modelo.addColumn("oc_p");
        modelo.addColumn("fecha_p");
        modelo.addColumn("hora_p");
        modelo.addColumn("fechae_p");
        modelo.addColumn("pallets");

// Recorremos los registros con un ciclo while
        while (rs.next()) {
            // Se crea un array que será una de las filas de la tabla.
            Object[] fila = new Object[9]; // Hay tres columnas en la tabla

            // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
            for (int i = 0; i < 9; i++) {
                fila[i] = rs.getObject(i + 1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
            }                                          //No es como en PHP o otros lenguajes donde los indices inician con 0

            // Se añade al modelo la fila completa.
            modelo.addRow(fila);
        }

        return modelo;

    }

    public Cabecera getCabecera(int nroP) throws Exception {
        Cabecera c = new Cabecera();
        Connection laConexion = ConnectionManager.getConnection();

        // Arma la sentencia de insercion y la ejecuta
        String consulta = "SELECT * FROM cabecera WHERE nro_p =" + nroP;
        Statement stmt = laConexion.createStatement();
        ResultSet rs = stmt.executeQuery(consulta);

        //rs.next();
        // Arma el objeto

        c.setNro(rs.getInt("nro_p"));
        c.setCliente(rs.getInt("cliente_p"));
        c.setSucursal(rs.getInt("sucursal_p"));
        c.setOc(rs.getInt("oc_p"));
        c.setFecha(rs.getString("fecha_p"));
        c.setHora(rs.getString("hora_p"));

        stmt.close();
        laConexion.close();

        return c;
    }

    public Vector getItemsPorNroPedido(Vector nros) throws Exception {
        int nro;
        Vector items = new Vector();
        Connection laConexion = ConnectionManager.getConnection();

        for (int i = 0; nros.size() > i; i++) {
            nro = (Integer) nros.get(i);
            System.out.println("nro pd: " + nro);
            // Arma la sentencia de insercion y la ejecuta
            String consulta = "SELECT * FROM cuerpo WHERE nro_p = " + nro;
            Statement stmt = laConexion.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);

            while (rs.next()) {
                Item item = new Item();
                item.setNro(rs.getInt("nro_p"));
                item.setCod(rs.getInt("cod_p"));
                item.setCantFact(rs.getInt("cantFact_p"));
                item.setBoca(1);
                items.add(item);
            }
            stmt.close();
        }

        laConexion.close();
        return items;
    }
}
