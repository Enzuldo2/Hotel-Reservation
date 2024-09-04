package br.ufscar.dc.pooa.Service;



import br.ufscar.dc.pooa.Model.users.Factory_Person;
import br.ufscar.dc.pooa.Model.users.Person;
import br.ufscar.dc.pooa.dao.ClientDAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Client_Service {
    private List<Person> clients;

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

        return !matcher.matches();
    }

    public boolean createUser(String username, String password, String email, Date birthday,String phone) throws SQLException, ClassNotFoundException, ParseException {
        if (ClientDAO.userExists(username)) {
            logger.info("User already exists");
            return false;
        }
        if (isValidEmail(email)) {
            logger.info("Invalid email");
            return false;
        }
        Person user = Factory_Person.createClient(username, password, email, birthday,phone);
        clients.add(user);
        ClientDAO.createClient(username, password, email, birthday,phone);
        logger.info("User created successfully");
        clients = ClientDAO.readClientslist();
        return true;
    }



    public boolean updateUser(Person user) throws SQLException, ClassNotFoundException {
        for (Person client : clients) {
            if (client.getPersonId() == user.getPersonId()) {
                ClientDAO.update(user.getPersonId(), user.getName(), user.getPassword(), user.getEmail(), user.getBirthday(), user.getPhone());
                clients = ClientDAO.readClientslist();
                return true;
            }
        }
        return false;
    }


    public List<Person> getUsers() throws SQLException, ClassNotFoundException {
        return clients = ClientDAO.readClientslist();
    }

    public Person getClient(int idCliente) throws SQLException, ClassNotFoundException {
        clients = getUsers();
        for (Person client : clients) {
            if (client.getPersonId() == idCliente) {
                return client;
            }
        }
        return null;
    }

    public Person haveClient(String username,String password) throws SQLException, ClassNotFoundException {
        clients = getUsers();
        for (Person client : clients) {
            if (client.getName().equals(username) && client.getPassword().equals(password)) {
                return client;
            }
        }
        return null;
    }

    public void deleteUser(int id) throws SQLException, ClassNotFoundException {
        clients = getUsers();
        for (Person client : clients) {
            if (client.getPersonId() == id) {
                clients.remove(client);
                try {
                    ClientDAO.delete(id);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Error deleting user");
                }
                break;
            }
        }
    }
}
