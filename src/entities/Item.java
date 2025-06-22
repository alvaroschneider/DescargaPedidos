/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author alvaro
 */
public class Item implements Comparable {

    
    private int nro;
    private int cliente;
    private int sucursal;
    private int oc;
    private int cod;
    private int cantFact;
    private int boca;
    private int marca;

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public int getOc() {
        return oc;
    }

    public void setOc(int oc) {
        this.oc = oc;
    }
    
    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    
    public int getBoca() {
        return boca;
    }

    public void setBoca(int boca) {
        this.boca = boca;
    }

    public int getCantFact() {
        return cantFact;
    }

    public void setCantFact(int cantFact) {
        this.cantFact = cantFact;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public int compareTo(Object arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
