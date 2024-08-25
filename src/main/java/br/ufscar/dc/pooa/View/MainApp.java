package br.ufscar.dc.pooa.View;

import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.Service.Client_Service;
import br.ufscar.dc.pooa.Service.Quarto_Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainApp {
    private JFrame frame;
    private JPanel panel1;

    public MainApp() {
        initializeUI();
    }

    private void initializeUI() {
        frame = createFrame("Vagas", 500, 400);
        panel1 = createPanel(new BorderLayout(), new Dimension(400, 300));
        frame.setContentPane(panel1);
        createMenuBarBasic();
    }



    private JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(width, height));
        frame.setVisible(true);
        return frame;
    }

    private JPanel createPanel(LayoutManager layout, Dimension dimension) {
        JPanel panel = new JPanel(layout);
        panel.setPreferredSize(dimension);
        return panel;
    }

    private void createMenuBarBasic() {
        panel1.removeAll();
        JTextArea jobListingsTextArea = createTextArea("Esta Procurando por uma Vaga para descansar?");
        panel1.add(new JScrollPane(jobListingsTextArea), BorderLayout.CENTER);
        frame.setJMenuBar(createMenuBarBasicItems());
        createMenuBarBasicItems();
        refreshPanel();
    }

    private JMenuBar createMenuBarBasicItems() {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem loginItem = createMenuItem("Login", e -> showLoginDialog());
        JMenuItem createAccountItem = createMenuItem("Create Account", e -> {
            try {
                showCreateAccountDialog();
            } catch (SQLException | ClassNotFoundException | ParseException ex) {
                showErrorDialog(ex);
            }
        });
        JMenuItem viewJobsItem = createMenuItem("Quartos Disponíveis Hoje", e -> viewQuartos());

        optionsMenu.add(loginItem);
        optionsMenu.add(createAccountItem);
        optionsMenu.add(viewJobsItem);
        menuBar.add(optionsMenu);

        return menuBar;
    }

    private void viewQuartos() {
        panel1.removeAll();
        JTextArea vagasTextArea = createTextArea("Quartos Disponíveis para Hoje:\n" +
                "Quartos do tipo Familia por 120 reais a diaria, do tipo Single por 70 reais e do tipo Suite por 200 reais a diaria\n");
        panel1.add(new JScrollPane(vagasTextArea), BorderLayout.CENTER);

        try {
            List<DefaultRoom> rooms = Quarto_Service.getInstance().getRooms();
            rooms.stream().filter(room -> !room.isReserved()).forEach(room -> appendRoomInfo(vagasTextArea, room));
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }

        refreshPanel();
    }

    private void appendRoomInfo(JTextArea textArea, DefaultRoom room) {
        textArea.append("ID: " + room.getId() + "\n");
        textArea.append("Tipo: " + room.getBridgeroom().getRoomType() + "\n");
        textArea.append("Descrição: " + room.getDescription() + "\n");
        textArea.append("Comprimento: " + room.getLength() + "\n");
        textArea.append("Largura: " + room.getWidth() + "\n");
        textArea.append("Altura: " + room.getHeight() + "\n\n");
    }


    private void showCreateAccountDialog() throws SQLException, ClassNotFoundException, ParseException {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JTextField emailField = new JTextField();
        JTextField birthdayField = new JTextField();

        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField,
                "Confirm Password", confirmPasswordField,
                "Email:", emailField,
                "Birthday(dd/MM/yyyy):", birthdayField
        };

        if (showConfirmDialog("Create Account", message)) {
            processAccountCreation(usernameField, passwordField, confirmPasswordField, emailField, birthdayField);
        }
    }

    private void processAccountCreation(JTextField usernameField, JPasswordField passwordField, JPasswordField confirmPasswordField, JTextField emailField, JTextField birthdayField) {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String email = emailField.getText();

            if (!Client_Service.getInstance().isValidEmail(email)) {
                showMessageDialog("Email inválido!");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showMessageDialog("Passwords do not match!");
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date birthday = dateFormat.parse(birthdayField.getText());

            if (Client_Service.getInstance().createUser(username, password, email, birthday)) {
                showMessageDialog("Account created successfully!");
            } else {
                showMessageDialog("Failed to create account! Username already exists");
            }
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            showErrorDialog(ex);
        }
    }

    private JMenuItem createMenuItem(String text, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(listener);
        return menuItem;
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
                userLogin(username, password);
            }
        } else {
            showMessageDialog("Login failed!");
        }

    }

    private void refreshPanel() {
        panel1.revalidate();
        panel1.repaint();
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

    private void userLogin(String username, String password) {
        try {
            Client userLogin = Client_Service.getInstance().haveClient(username, password);
            if (userLogin != null) {
                showMessageDialog("Login successful!");
                new ClientView(userLogin);
                closeWindow();
            } else {
                showMessageDialog("Login failed!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            showErrorDialog(e);
        }
    }

    private void showErrorDialog(Exception ex) {
        JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void closeWindow() {
        frame.dispose();
    }
}
