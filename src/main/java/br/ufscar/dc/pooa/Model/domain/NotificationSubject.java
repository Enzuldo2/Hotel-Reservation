package br.ufscar.dc.pooa.Model.domain;



import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class NotificationSubject{
    private final HashMap<Integer,List<Observer>> waitingList = new HashMap<>();

    public void notify(Integer id) throws SQLException, ClassNotFoundException {
        for (Integer key : waitingList.keySet()) {
            if(key.equals(id)){
                for (Observer observer : waitingList.get(key)) {
                    observer.update("Reserva_Disponivel");
                }
            }
        }
    }

    public void attach(Integer id, Observer observer) {
        if(waitingList.containsKey(id)){
            waitingList.get(id).add(observer);
        }else{
            waitingList.put(id, List.of(observer));
        }

    }

    public void notifyReserva_feita(Observer observer) throws SQLException, ClassNotFoundException {
        observer.update("Reserva_Feita");
    }

    public void desatach(int reservaId) {
        waitingList.remove(reservaId);
    }
}
