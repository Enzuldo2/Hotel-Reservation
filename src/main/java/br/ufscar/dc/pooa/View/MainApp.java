package br.ufscar.dc.pooa.View;




import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.Service.Client_Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;


public class MainApp extends UserView {

    public MainApp() {
        super("Bem vindo ao APP do nosso Hotel");
        createMenuBar(null);
    }


    @Override
    protected void createMenuBar(Client user) {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem loginItem = createMenuItem("Login", e -> showLoginDialog() , "C:\\Users\\enzod\\Desktop\\Hotel-Reservation\\dados_icon.png");
        JMenuItem createAccountItem = createMenuItem("Create Account", e -> {
            try {
                super.showCreateAccountDialog();
            } catch (SQLException | ClassNotFoundException | ParseException ex) {
                showErrorDialog(ex);
            }
        },"C:\\Users\\enzod\\Desktop\\Hotel-Reservation\\criar_conta_icon.png");
        JMenuItem viewJobsItem = createMenuItem("Informações Sobre Disponibilidade de Quartos", e -> {
            try {
                viewQuartos();
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        },"C:\\Users\\enzod\\Desktop\\Hotel-Reservation\\quarto_icon.png");

        optionsMenu.add(loginItem);
        optionsMenu.add(createAccountItem);
        optionsMenu.add(viewJobsItem);
        menuBar.add(optionsMenu);

        frame.setJMenuBar(menuBar);
    }


    private void viewQuartos() throws SQLException, ClassNotFoundException {
        panel1.removeAll();
        JTextArea vagasTextArea = createTextArea("Caso Tenha Quartos disponiveis Para Reserva Hoje:\n" +
                "Quartos do tipo Familia por 120 reais a diaria, do tipo Single por 70 reais e do tipo Suite por 200 reais a diaria\n"+
                "Caso queira reservar um quarto, por favor, faça o login ou vá a recepção do Hotel.\n\n"+
                "Check-in: 14:00\n"+
                "Check-out: 12:00\n\n"+
                "Obrigado por escolher o nosso Hotel!");
        panel1.add(new JScrollPane(vagasTextArea), BorderLayout.CENTER);

        UserView.viewQuartoSemLogin();

        refreshPanel();
    }



    private JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        return textArea;
    }

    private void showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Username:", usernameField, "Password:", passwordField};

        if (showConfirmDialog("Login", message)) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (isAdmin(username, password)) {
                showMessageDialog("Login successful!");
                new AdminView();
                closeWindow();
            } else {
                userlogin(username, password);
            }
        } else {
            showMessageDialog("Login failed!");
        }

    }


    private boolean showConfirmDialog(String title, Object[] message) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }


    private boolean isAdmin(String username, String password) {
        return "admin".equals(username) && "admin".equals(password);
    }

    private void userlogin(String username, String password) {
        try {
            Client user = Client_Service.getInstance().haveClient(username, password);
            if(user != null) {
                showMessageDialog("Login successful!");
                new ClientView(user);
                closeWindow();
            } else {
                showMessageDialog("Login failed! Invalid username or password");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }
    }



    private void closeWindow() {
        frame.dispose();
    }
}
