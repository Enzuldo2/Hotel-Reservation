package br.ufscar.dc.pooa.Model.domain.Reserva;

import br.ufscar.dc.pooa.Model.domain.users.Person;
import br.ufscar.dc.pooa.Model.interfaces.Bridge_Room;

import java.util.Date;

public class Reserva {
        private int id;
        private Person cliente;
        private Bridge_Room categoria;
        private Date dataReserva;
        private Date dataEntrada;
        private Date dataSaida;
        private Boolean reserved;

        public Reserva(Person cliente, Bridge_Room categoria, Date dataReserva, Date dataEntrada, Date dataSaida, Boolean reserved) {
            this.cliente = cliente;
            this.categoria = categoria;
            this.dataReserva = dataReserva;
            this.dataEntrada = dataEntrada;
            this.dataSaida = dataSaida;
        }

    public Reserva() {
    }


    //getters and setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Boolean getReserved() {
            return reserved != null ? reserved : false;
        }

        public void setReserved(Boolean reserved) {
            this.reserved = reserved;
        }

        public Person getCliente() {
            return cliente;
        }

        public void setCliente(Person cliente) {
            this.cliente = cliente;
        }

        public Bridge_Room getCategoria() {
            return categoria;
        }

        public void setCategoria(Bridge_Room categoria) {
            this.categoria = categoria;
        }

        public Date getDataReserva() {
            return dataReserva;
        }

        public void setDataReserva(Date dataReserva) {
            this.dataReserva = dataReserva;
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


        //methods
        public float getPreco() {
            return categoria.getRoomPrice();
        }

        public String getTipoQuarto() {
            return categoria.getRoomType();
        }

        public float getPrecoTotal() {
            return getPreco() * (dataSaida.getTime() - dataEntrada.getTime());
        }


}
