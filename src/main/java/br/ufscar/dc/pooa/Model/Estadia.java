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


}
