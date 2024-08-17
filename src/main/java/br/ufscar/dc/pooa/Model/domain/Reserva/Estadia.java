package br.ufscar.dc.pooa.Model.domain.Reserva;

import br.ufscar.dc.pooa.Model.domain.reservation.Not_Reserved;
import br.ufscar.dc.pooa.Model.domain.users.Person;
import br.ufscar.dc.pooa.Model.interfaces.Room;

import java.util.Date;

public class Estadia {
    private int id;
    private Person cliente;
    private Room quarto;
    private Date dataEntrada;
    private Date dataSaida;

    public Estadia(int id, Person cliente, Room quarto, Date dataEntrada, Date dataSaida) {
        this.id = id;
        this.cliente = cliente;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        setDataSaida(dataSaida);
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
        if(quarto.getReservation_State() instanceof Not_Reserved) {
            return false;
        }
        return true;
    }
}
