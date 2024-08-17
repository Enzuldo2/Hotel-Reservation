package br.ufscar.dc.pooa.View;

import br.ufscar.dc.pooa.Model.domain.hotel.Hotel;
import br.ufscar.dc.pooa.Model.domain.users.Admin;
import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.dao.ClientDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VagasView {
    private JFrame frame;
    private JPanel panel1;
    private JTextArea jobListingsTextArea;

    public VagasView() {
        createUIComponents();
    }

    private void createUIComponents() {
        frame = new JFrame("Vagas");
        panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(400, 300));
        panel1.setLayout(new BorderLayout());

        jobListingsTextArea = new JTextArea("Esta Procurando por uma Vaga para descansar?");
        jobListingsTextArea.setEditable(false);
        panel1.add(new JScrollPane(jobListingsTextArea), BorderLayout.CENTER);

        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 400));
        frame.setVisible(true);
        frame.pack();

        createMenuBar_basic();
    }

    private void createMenuBar_basic() {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem loginItem = new JMenuItem("Login");
        JMenuItem createAccountItem = new JMenuItem("Create Account");
        JMenuItem viewJobsItem = new JMenuItem("Quartos Disponíveis");

        optionsMenu.add(loginItem);
        optionsMenu.add(createAccountItem);
        optionsMenu.add(viewJobsItem);
        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);

        loginItem.addActionListener(e -> showLoginDialog());
        createAccountItem.addActionListener(e -> {
            try {
                showCreateAccountDialog();
            } catch (SQLException | ClassNotFoundException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        viewJobsItem.addActionListener(e -> showVagasScreen());
    }

    private void createMenuBar_user(Client user) {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem viewJobsItem = new JMenuItem("Quartos Disponíveis");
        JMenuItem logoutItem = new JMenuItem("Logout");
        JMenuItem reservaItem = new JMenuItem("Reservar Quarto");

        optionsMenu.add(viewJobsItem);
        optionsMenu.add(logoutItem);
        optionsMenu.add(reservaItem);
        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);

        logoutItem.addActionListener(e -> createMenuBar_basic());
        reservaItem.addActionListener(e -> reserva_quarto_user(user));
        viewJobsItem.addActionListener(e -> viewQuartos(user));


        viewJobsItem.addActionListener(e -> showVagasScreen());
    }

    private void viewQuartos(Client user) {
    }

    private void reserva_quarto_user(Client user) {
        panel1.removeAll();
        JTextArea reserva_quarto_text = new JTextArea("Para reservar um quarto, Informe o periodo da reserva e a Categoria do quarto desejado.\n" +
                "Temos como Categoria disponiveis: \n Quartos do tipo Familia por 120 reais a diaria , do tipo Single por 70 reais e do tipo Suite por 200 reias a diaria ");
        reserva_quarto_text.setEditable(false);
        panel1.add(new JScrollPane(reserva_quarto_text), BorderLayout.CENTER);

        JButton showInputFieldsButton = new JButton("Reservar");
        panel1.add(showInputFieldsButton, BorderLayout.SOUTH);

        showInputFieldsButton.addActionListener(e -> {
            JTextField dataField_inicial = new JTextField();
            JTextField dataField_end = new JTextField();
            JTextField categoria_Field = new JTextField();

            Object[] message = {
                    "Data Inicial(dd/mm/yyyy)", dataField_inicial,
                    "Data de saida(dd/mm/yyyy)", dataField_end,
                    "Tipo de Quarto", categoria_Field
            };
            int option = JOptionPane.showConfirmDialog(null, message, "Reserva", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String tipo_quarto = categoria_Field.getText();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date data_inicial = null;
                Date data_end = null;
                try {
                    data_inicial = dateFormat.parse(dataField_inicial.getText());
                    data_end = dateFormat.parse(dataField_end.getText());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                LocalDate hoje = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dataAtual = hoje.format(formatter);
                try {
                    if(Admin.getInstance().makeReservation(user, dataAtual , data_inicial, data_end, tipo_quarto)){
                        JOptionPane.showMessageDialog(null, "Reserva feita com sucesso!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Falha na reserva 2");
                        System.out.println("" + tipo_quarto + " " + data_inicial + " " + data_end + " " + dataAtual + " " + user.getName());
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Falha na reserva");
            }
        });

        panel1.revalidate();
        panel1.repaint();
    }

    private void showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            createRoomAdmin(password, username);
            userLogin(username,password);
        } else {
            JOptionPane.showMessageDialog(null, "Login failed!");
        }
    }

    private void userLogin(String username, String password) {
        try {
           var user_login =  Hotel.getInstance().haveClient(username, password);
           if(user_login != null){
                JOptionPane.showMessageDialog(null, "Login successful!");
                userInterface(user_login);
           }else{
                JOptionPane.showMessageDialog(null, "Login failed!");
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void userInterface(Client user) {
        panel1.removeAll();
        JTextArea userTextArea = new JTextArea("Bem vindo ao Hotel!\n");
        userTextArea.setEditable(false);
        panel1.add(new JScrollPane(userTextArea), BorderLayout.CENTER);

        createMenuBar_user(user);

        panel1.revalidate();
        panel1.repaint();
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
                "Birthday(dd/mm/yyyy):", birthdayField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Create Account", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String email = emailField.getText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date birthday = dateFormat.parse(birthdayField.getText());
            Admin admin = Admin.getInstance();
            if (password.equals(confirmPassword)) {
                if (admin.createUser(username, password, email, birthday)) {
                    JOptionPane.showMessageDialog(null, "Account created successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to create account!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Passwords do not match!");
            }
        }
    }

    private void showVagasScreen() {
        panel1.removeAll();
        JTextArea vagasTextArea = new JTextArea("Quartos Disponíveis:\n");
        vagasTextArea.setEditable(false);
        panel1.add(new JScrollPane(vagasTextArea), BorderLayout.CENTER);

        JButton queroVagaButton = new JButton("Quero Vaga");
        panel1.add(queroVagaButton, BorderLayout.SOUTH);

        panel1.revalidate();
        panel1.repaint();
    }

    private void createRoomAdmin(String password, String username) {
        if (username.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            panel1.removeAll();

            JTextArea adminTextArea = new JTextArea("Criar quartos:\n");
            adminTextArea.setEditable(false);
            panel1.add(new JScrollPane(adminTextArea), BorderLayout.CENTER);

            JButton criarQuartoButton = new JButton("Criar quarto");
            criarQuartoButton.addActionListener(e -> showCreateRoomDialog());
            panel1.add(criarQuartoButton, BorderLayout.SOUTH);

            panel1.revalidate();
            panel1.repaint();
        }
    }

    private void showCreateRoomDialog() {
        JFrame createRoomFrame = new JFrame("Criar Quarto");
        JPanel createRoomPanel = new JPanel(new GridLayout(7, 2));

        JTextField roomTypeField = new JTextField();
        JTextField roomCapacityField = new JTextField();
        JTextField roomDescriptionField = new JTextField();
        JTextField roomLengthField = new JTextField();
        JTextField roomWidthField = new JTextField();
        JTextField roomHeightField = new JTextField();

        createRoomPanel.add(new JLabel("Tipo de Quarto:"));
        createRoomPanel.add(roomTypeField);
        createRoomPanel.add(new JLabel("Capacidade:"));
        createRoomPanel.add(roomCapacityField);
        createRoomPanel.add(new JLabel("Descrição:"));
        createRoomPanel.add(roomDescriptionField);
        createRoomPanel.add(new JLabel("Comprimento:"));
        createRoomPanel.add(roomLengthField);
        createRoomPanel.add(new JLabel("Largura:"));
        createRoomPanel.add(roomWidthField);
        createRoomPanel.add(new JLabel("Altura:"));
        createRoomPanel.add(roomHeightField);

        JButton submitButton = new JButton("Criar");
        submitButton.addActionListener(e -> {
            String roomType = roomTypeField.getText();
            int roomCapacity = Integer.parseInt(roomCapacityField.getText());
            String roomDescription = roomDescriptionField.getText();
            float roomLength = Float.parseFloat(roomLengthField.getText());
            float roomWidth = Float.parseFloat(roomWidthField.getText());
            float roomHeight = Float.parseFloat(roomHeightField.getText());
            Admin admin = Admin.getInstance();
            try {
                if (admin.createRoom(roomType, roomCapacity, roomDescription, roomLength, roomWidth, roomHeight)) {
                    JOptionPane.showMessageDialog(createRoomFrame, "Quarto criado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(createRoomFrame, "Falha ao criar quarto!");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        createRoomPanel.add(submitButton);
        createRoomFrame.add(createRoomPanel);
        createRoomFrame.pack();
        createRoomFrame.setVisible(true);
    }
}

