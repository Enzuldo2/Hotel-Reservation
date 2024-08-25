package br.ufscar.dc.pooa.Service;


import br.ufscar.dc.pooa.Model.domain.Subject;
import br.ufscar.dc.pooa.dao.ListaEsperaDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public class Waiting_List_Service {
    private static Waiting_List_Service instance = null;
    private  Subject subject = new Subject();
    private HashMap<Integer, List<String>> waitingList;


    private Waiting_List_Service() throws SQLException, ClassNotFoundException {
        waitingList = ListaEsperaDAO.readLista();
        for (Integer key : waitingList.keySet()) {
            for (String email : waitingList.get(key)) {
                subject.attach(key, email);
            }
        }
    }

    public static Waiting_List_Service getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new Waiting_List_Service();
        }
        return instance;
    }

    public void notify(Integer id) throws SQLException, ClassNotFoundException {
        subject.notify(id);
    }

    public void attach(Integer id, String email) throws SQLException, ClassNotFoundException {
        subject.attach(id, email);
        ListaEsperaDAO.createLista(id, email);
    }



    public void delete(int reservaId) throws SQLException, ClassNotFoundException {
        subject.desatach(reservaId);
        ListaEsperaDAO.deleteLista(reservaId);
    }

    public List<String> getListaEspera() throws SQLException, ClassNotFoundException {
        waitingList = ListaEsperaDAO.readLista();
        List<String> emails = null;
        for (Integer key : waitingList.keySet()) {
            emails = waitingList.get(key);
        }
        return emails;
    }
}
