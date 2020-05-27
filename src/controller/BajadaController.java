/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.*;
import entities.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFileChooser;

/**
 *
 * @author alvaro
 */
public class BajadaController {

    private Vector items = new Vector();
    Cabecera c = new Cabecera();
    private ArrayList cabeceras = new ArrayList();
    private PersistData pd = new PersistData();
    private Vector pedidos = new Vector();
    private Vector pedidosPick = new Vector();
    private int maxNroBoca = 29;
    int j = -1;
    JFileChooser file = null;

    public BajadaController() {
    }

    public void bajaPedidosSeleccionados(Vector nros) throws Exception {
        
        for(int i=0;nros.size()>i;i++){
            System.out.println("nro vector: "+nros.get(i));
        }
        
        PersistData pd = new PersistData();
        items = pd.getItemsPorNroPedido(nros);

        cabeceras = (ArrayList) Cabecera.obtenerCabeceras(ConnectionManager.getConnection());
        
        reorganizaItems();
        modificaBocas();
        separaPick();
        escribeArchivo();
    }

    public void bajaPedidosNoDescargados() throws Exception {
        items = (Vector) pd.getItemsNoDescargados();
        cabeceras = (ArrayList) Cabecera.obtenerCabecerasNoDescargadas(ConnectionManager.getConnection());
        pd.modificaMarca();
        reorganizaItems();
        //Modifica nro de boca de los pedidos que suman mas de 24 pallets dentro del vector pedidos
        modificaBocas();
        separaPick();
        escribeArchivo();


    }

    public void bajaTodosLosPedidos() throws Exception {
        items = (Vector) pd.getAllItems();
        cabeceras = (ArrayList) Cabecera.obtenerCabeceras(ConnectionManager.getConnection());

        reorganizaItems();
        modificaBocas();
        separaPick();
        escribeArchivo();
    }

    public void escribeArchivo() throws Exception {

        //Escribe pedidos en arhivo .csv
        String entrada = null;
        file = new JFileChooser();
        int retVal = file.showSaveDialog(file);
        if (retVal == file.APPROVE_OPTION) {

            entrada = file.getSelectedFile().getAbsolutePath() + ".csv";
            File archivoDeSalida = new File(entrada);
            FileWriter out = null;

            out = new FileWriter(archivoDeSalida);

            for (int i = 0; i < pedidos.size(); i++) {
                Pedido p = (Pedido) pedidos.get(i);

                String info = String.valueOf(p.getCliente()) + ";" + String.valueOf(p.getSucursal()) + ";" + String.valueOf(p.getOc()) + ";" + p.getFecha() + ";" + String.valueOf(p.getItem().getCod()) + ";" + String.valueOf(p.getItem().getCantFact()) + ";0;0;" + String.valueOf(p.getItem().getBoca()) + ";" + String.valueOf(p.getFechae()) + ";;" + String.valueOf(p.getItem().getNro()) + "\n";

                out.write(info);
            }
            out.close();
        }
    }

    public void reorganizaItems() throws Exception {
        System.out.println("reorganizaItems");
        //Recorre vector item y evalua si tiene mas de 24 pallets y lo divide en pedidos de 24 pallets
        for (int i = 0; i < items.size(); i++) {
            Item item = new Item();
            item = (Item) items.get(i);
            
            System.out.println("item cliente "+item.getCliente());
            //en p se guarda los datos logisticos del sku
            Producto p = Producto.obtenerPorId(ConnectionManager.getConnection(), item.getCod());
            //calcula pallets pedidos en el item
            float palletsPedidos = (float) item.getCantFact() / (float) p.getPallet();
            if (palletsPedidos > 12) {
                int pedidosEnteros = (int) (palletsPedidos / 12);
                float pedidosResto = palletsPedidos / 12 - pedidosEnteros;

                for (int j = 0; j < pedidosEnteros; j++) {
                    Item item2 = new Item();
                    item2.setNro(item.getNro());
                    item2.setCod(item.getCod());

                    //genera la cantidad para el nuevo item con 24 plts
                    int cant = p.getPallet() * 12;

                    maxNroBoca++;
                    item2.setCantFact(cant);
                    item2.setBoca(maxNroBoca);

                    items.insertElementAt(item2, i);
                    i++;
                }
                maxNroBoca++;
                Item item2 = new Item();
                float cant = pedidosResto * 12 * p.getPallet();

                item2.setNro(item.getNro());
                item2.setCod(item.getCod());
                item2.setCantFact((int) cant);
                item2.setBoca(maxNroBoca);

                items.insertElementAt(item2, i);
                i++;
            }
        }

        //Elimina items mayores a 24 pallets y prepara vector pedidos
        for (int i = 0; i < items.size(); i++) {
            Item item = new Item();
            item = (Item) items.get(i);
            Producto p = Producto.obtenerPorId(ConnectionManager.getConnection(), item.getCod());
            float palletsPedidos = (float) item.getCantFact() / (float) p.getPallet();
            if (palletsPedidos > 12) {
                items.remove(i);
                i = i - 1;
            } else {
                //agrega pedidos a vector pedidos
                for (int j = 0; j < cabeceras.size(); j++) {
                    Cabecera c = new Cabecera();
                    c = (Cabecera) cabeceras.get(j);
                    if (item.getNro() == c.getNro()) {
                        Pedido ped = new Pedido();
                        ped.setCliente(c.getCliente());
                        ped.setSucursal(c.getSucursal());
                        ped.setOc(c.getOc());
                        ped.setFecha(c.getFecha());
                        ped.setItem(item);
                        ped.setNro(c.getNro());
                        ped.setFechae(c.getFechae());
                        pedidos.add(ped);
                    }
                }
            }
        }
    }

    public void modificaBocas() throws Exception {
        int nroPedido = 0;
        int nroCli = 0;
        int nroSuc = 0;
        int nroBoca = 1;
        int nroOC = 0;
        float acumPallets = 0;
        float pallets = 0;
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = new Pedido();
            p = (Pedido) pedidos.get(i);
            if (p.getCliente() == nroCli && p.getSucursal() == nroSuc && p.getOc() == nroOC) {
                Producto prod = Producto.obtenerPorId(ConnectionManager.getConnection(), p.getItem().getCod());
                pallets = (float) p.getItem().getCantFact() / (float) prod.getPallet();
                if (pallets == 12) {
                    acumPallets = 0;
                } else {
                    acumPallets = acumPallets + pallets;
                    //System.out.println("acumPallets: " + acumPallets);
                    if (acumPallets > 12) {
                        Item item = new Item();
                        item = p.getItem();
                        nroBoca++;
                        item.setBoca(nroBoca);
                        p.setItem(item);
                        pedidos.setElementAt(p, i);
                        acumPallets = pallets;
                    } else {
                        Item item = new Item();
                        item = p.getItem();
                        item.setBoca(nroBoca);
                        p.setItem(item);
                        pedidos.setElementAt(p, i);
                    }
                }
            } else {
                Producto producto = Producto.obtenerPorId(ConnectionManager.getConnection(), p.getItem().getCod());
                pallets = (float) p.getItem().getCantFact() / (float) producto.getPallet();
                acumPallets = pallets;
                //nroPedido = p.getNro();

                if (p.getCliente() == nroCli && p.getSucursal() == nroSuc) {
                    nroBoca++;
                    Item item = new Item();
                    item = p.getItem();
                    item.setBoca(nroBoca);
                    p.setItem(item);
                    pedidos.setElementAt(p, i);
                } else {
                    nroBoca = 1;
                }

                nroCli = p.getCliente();
                nroSuc = p.getSucursal();
                nroOC = p.getOc();
                //nroBoca=1;
            }
        }
    }

    public void separaPick() throws Exception {
        int pallet;
        float pick;

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = (Pedido) pedidos.get(i);
            Item item = new Item();
            item = p.getItem();
            Producto producto = Producto.obtenerPorId(ConnectionManager.getConnection(), item.getCod());

            pallet = item.getCantFact() / producto.getPallet();
            pick = ((float) item.getCantFact() / (float) producto.getPallet()) - pallet;

            item.setCantFact(pallet * producto.getPallet());

            //System.out.println("cant: " + (float) item.getCantFact() / producto.getPallet());

            p.setItem(item);
            pedidos.set(i, p);

            if (pick > 0) {
                Item item2 = new Item();

                item2.setBoca(item.getBoca() + 80);
                item2.setCliente(item.getCliente());
                item2.setCod(item.getCod());
                item2.setMarca(item.getMarca());
                item2.setNro(item.getNro());
                item2.setOc(item.getOc());
                item2.setSucursal(item.getSucursal());
                item2.setCantFact((int) (producto.getPallet() * pick));
                System.out.println("cliitem: " + item.getCliente() + "cliitem2: " + item2.getCliente() + "pick: " + item2.getCantFact());

                Pedido p1 = new Pedido();

                p1.setCliente(p.getCliente());
                p1.setFecha(p.getFecha());
                p1.setFechae(p.getFechae());
                p1.setNro(p.getNro());
                p1.setOc(p.getOc());
                p1.setSucursal(p.getSucursal());
                p1.setItem(item2);
                pedidosPick.add(p1);
            }

        }

        for (int i = 0; i < pedidosPick.size(); i++) {
            Pedido p = (Pedido) pedidosPick.get(i);
            pedidos.add(p);
        }
    }
}
