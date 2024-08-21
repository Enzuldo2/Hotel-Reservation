package br.ufscar.dc.pooa.Service;


import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.Model.domain.users.Factory_Person;
import br.ufscar.dc.pooa.Model.domain.users.Person;
import br.ufscar.dc.pooa.dao.ClientDAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Client_Service {
    private List<Client> clients;

    private static final Logger logger = Logger.getLogger(Client_Service.class.getName());
    private static Client_Service instance = null;


    private Client_Service() throws SQLException, ClassNotFoundException {
        this.clients = ClientDAO.readClientslist();
    }

    // Singleton
    public static Client_Service getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new Client_Service();
        }
        return instance;
    }

    public boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public boolean createUser(String username, String password, String email, Date birthday) throws SQLException, ClassNotFoundException, ParseException {
        if (ClientDAO.userExists(username, password, email, birthday)) {
            logger.info("User already exists");
            return false;
        }
        if (!isValidEmail(email)) {
            logger.info("Invalid email");
            return false;
        }
        Client user = (Client) Factory_Person.createPerson(username, password, email, birthday);
        clients.add(user);
        ClientDAO.createClient(username, password, email, birthday);
        logger.info("User created successfully");
        clients = ClientDAO.readClientslist();
        return true;
    }

    public Person getUser(int userId) throws SQLException, ClassNotFoundException {
        for (Client client : clients) {
            if (client.getId() == userId) {
                return client;
            }
        }
        return null;
    }

    public boolean updateUser(Person user) throws SQLException, ClassNotFoundException {
        for (Client client : clients) {
            if (client.getId() == user.getPersonId()) {
                clients.remove(client);
                clients.add((Client) user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(int userId) throws SQLException, ClassNotFoundException {
        for (Client client : clients) {
            if (client.getId() == userId) {
                clients.remove(client);
                return true;
            }
        }
        return false;
    }

    public List<Client> getUsers()  {
        return clients;
    }

    public Client getClient(int idCliente) {
        for (Client client : clients) {
            if (client.getId() == idCliente) {
                return client;
            }
        }
        return null;
    }

    public Client haveClient(String username,String password) {
        for (Client client : clients) {
            if (client.getName().equals(username) && client.getPassword().equals(password)) {
                return client;
            }
        }
        return null;
    }
}
