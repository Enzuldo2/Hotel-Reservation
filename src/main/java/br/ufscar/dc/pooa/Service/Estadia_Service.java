package br.ufscar.dc.pooa.Service;

import br.ufscar.dc.pooa.Model.domain.Reserva.Estadia;
import br.ufscar.dc.pooa.Model.domain.Reserva.Reserva;
import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.dao.EstadioDAO;
import br.ufscar.dc.pooa.dao.QuartoDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Estadia_Service {
    private static Estadia_Service instance = null;
    List<Estadia> estadias;

    private Estadia_Service() {
        estadias = new ArrayList<>();
    }

    public static Estadia_Service getInstance() {
        if (instance == null) {
            return new Estadia_Service();
        }
        return instance;
    }

    public boolean makeEstadia_ComReserva(int clientId, int reservaid, int pessoas) throws SQLException, ClassNotFoundException {
        boolean created = false;
        var client = Client_Service.getInstance().getClient(clientId);
        var reserva = Reserva_Service.getInstance().getReservas(clientId);
        if (reserva != null) {
            for (Reserva r : reserva) {
                if (r.getId() == reservaid) {
                    DefaultRoom quarto = Quarto_Service.getInstance().getRoom(r.getCategoria().getRoomType());
                    if(quarto != null) {
                        createEstadia(client, quarto, r.getDataEntrada(), r.getDataSaida(), pessoas);
                        Reserva_Service.getInstance().removeReserva(r); //remove a reserva e exclue a lista de espera
                        created = true;
                        break;
                    }
                }
            }
        }
        return created;
    }

    public boolean makeEstadia_MudandoData(int clientId , int reservaid , int pessoas ,  Date Data_Saida) throws SQLException, ClassNotFoundException {
        boolean created = false;
        var client = Client_Service.getInstance().getClient(clientId);
        var reserva = Reserva_Service.getInstance().getReservas(clientId);
        Reserva rerserva_temp;
        if (reserva != null) {
            for (Reserva r : reserva) {
                if (r.getId() == reservaid) {
                    DefaultRoom quarto = Quarto_Service.getInstance().getRoom(r.getCategoria().getRoomType());
                    if(quarto != null) {
                        rerserva_temp = r;
                        Reserva_Service.getInstance().removeReserva(r); //remove a reserva e exclue a lista de espera
                        var bool = Reserva_Service.getInstance().verifica_Reserva(rerserva_temp.getDataEntrada(), rerserva_temp.getDataSaida(), rerserva_temp.getCategoria().getRoomType());
                        if(bool){
                            //verifica se é possivel aumentar a data da reserva
                            createEstadia(client, quarto, rerserva_temp.getDataEntrada(), Data_Saida, pessoas);
                            created = true;
                            break;
                        }
                        else {
                            System.out.println("Não a quartos disponíveis para a data adicional");
                        }
                    }

                }
                else{
                    System.out.println("não existe reserva");
                }
            }
        }
        return created;
    }

    public boolean makeEstadia_SemReserva(int clientId, Date dataEntrada, Date dataSaida, String tipo_quarto,int pessoas) throws SQLException, ClassNotFoundException {
        boolean created = false;
        var client = Client_Service.getInstance().getClient(clientId);
        DefaultRoom quarto = Quarto_Service.getInstance().getRoom(tipo_quarto);
        var tem_espaco = Reserva_Service.getInstance().verifica_Reserva(dataEntrada, dataSaida, tipo_quarto);
        if(quarto != null && tem_espaco){ //verifica mesmo assim as reservas, pois cliente pode errar.
            createEstadia(client, quarto, dataEntrada, dataSaida, pessoas);
            created = true;
        }
        else {
            System.out.println("Não a quartos disponíveis");
        }
        return created;
    }



    private void createEstadia(Client cliente, DefaultRoom quarto, Date dataEntrada, Date dataSaida, int pessoas) throws SQLException, ClassNotFoundException {
        EstadioDAO.createEstadia(cliente, quarto, dataEntrada, dataSaida,pessoas);
        quarto.setReserved(true);
        QuartoDAO.update(quarto.getId(), quarto.getRoomType(), quarto.getDescription(), quarto.getCapacity(), quarto.getHeight(), quarto.getLength(), quarto.getWidth(), true);
        estadias = EstadioDAO.readEstadias();
    }

    public List<Estadia> getEstadias() throws SQLException, ClassNotFoundException {
        estadias = EstadioDAO.readEstadias();
        return estadias;
    }

    public void DeleteEstadia(int id) {
        try {
            for(Estadia e : estadias){
                if(e.getId() == id){
                    var quarto_service = Quarto_Service.getInstance();
                    var quarto = quarto_service.getRoom(e.getQuarto().getRoomId());
                    quarto.setReserved(false);
                    quarto_service.updateRoom(quarto);
                    break;
                }
            }
            EstadioDAO.deleteEstadia(id);
            estadias = EstadioDAO.readEstadias();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
