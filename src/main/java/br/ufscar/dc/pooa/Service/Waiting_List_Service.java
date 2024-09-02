package br.ufscar.dc.pooa.Service;


import br.ufscar.dc.pooa.Model.GmailService;
import br.ufscar.dc.pooa.Model.NotificationSubject;
import br.ufscar.dc.pooa.dao.ListaEsperaDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Waiting_List_Service {
    private static Waiting_List_Service instance = null;
    private NotificationSubject subject = new NotificationSubject();
    private HashMap<Integer, List<String>> waitingList;


    private Waiting_List_Service() throws SQLException, ClassNotFoundException {
        waitingList = ListaEsperaDAO.readLista();
        for (Integer key : waitingList.keySet()) {
            for (String tipo : waitingList.get(key)) {
                if(isValidEmail(tipo)){
                    subject.attach(key, new GmailService(tipo));
                }
            }
        }
    }

    public boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
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

    public void attach(Integer id, String tipo_aviso) throws SQLException, ClassNotFoundException {
        if(isValidEmail(tipo_aviso)){
            subject.attach(id, new GmailService(tipo_aviso));
        }
        ListaEsperaDAO.createLista(id, tipo_aviso);
    }

    public void notifyReserva_feita(String tipo_aviso) throws SQLException, ClassNotFoundException {
        subject.notifyReserva_feita(new GmailService(tipo_aviso));
    }



    public void delete(int reservaId) throws SQLException, ClassNotFoundException {
        subject.desatach(reservaId);
        ListaEsperaDAO.deleteLista(reservaId);
    }

    public HashMap<Integer, List<String>> getListaEspera() throws SQLException, ClassNotFoundException {
        waitingList = ListaEsperaDAO.readLista();
        return waitingList;
    }
}
