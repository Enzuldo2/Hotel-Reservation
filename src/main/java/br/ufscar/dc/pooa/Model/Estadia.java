package br.ufscar.dc.pooa.Model;

import br.ufscar.dc.pooa.Model.users.Person;
import br.ufscar.dc.pooa.Model.rooms.Room;

import java.util.Date;

public class Estadia {
    private int id;
    private Person cliente;
    private Room quarto;
    private Date dataEntrada;
    private Date dataSaida;
    private int pessoas;

    public Estadia(int id, Person cliente, Room quarto, Date dataEntrada, Date dataSaida,int pessoas) {
        this.id = id;
        this.cliente = cliente;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.pessoas = pessoas;
    }

    public Estadia() {

    }

    // getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getCliente() {
        return cliente;
    }

    public int getPessoas() {
        return pessoas;
    }

    public void setPessoas(int pessoas) {
        this.pessoas = pessoas;
    }

    public void setCliente(Person cliente) {
        this.cliente = cliente;
    }

    public Room getQuarto() {
        return quarto;
    }

    public void setQuarto(Room quarto) {
        this.quarto = quarto;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public float getPreco() {
        return quarto.getBridgeroom().getRoomPrice();
    }

    public float getPrecoTotal() {
        return getPreco() * (dataSaida.getTime() - dataEntrada.getTime());
    }

    public boolean isReservado() {
        return quarto.getReservation();
    }
}
