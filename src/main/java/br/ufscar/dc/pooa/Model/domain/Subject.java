package br.ufscar.dc.pooa.Model.domain;



import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Subject{
    private final Observer observer = new GmailService();
    private final HashMap<Integer,List<String>> waitingList = new HashMap<>();

    public void notify(Integer id) throws SQLException, ClassNotFoundException {
        for (Integer key : waitingList.keySet()) {
            if(waitingList.containsKey(id)){
                observer.update(waitingList.get(id),key);
            }
        }
    }

    public void attach(Integer id, String email) {
        if(waitingList.containsKey(id)){
            waitingList.get(id).add(email);
        }else{
            waitingList.put(id, List.of(email));
        }
    }

    public void desatach(int reservaId) {
        waitingList.remove(reservaId);
    }
}
