package br.ufscar.dc.pooa.View;

import br.ufscar.dc.pooa.Model.domain.Reserva.Estadia;
import br.ufscar.dc.pooa.Model.domain.Reserva.Reserva;
import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.Service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VagasView {
    private JFrame frame;
    private JPanel panel1;

    public VagasView() {
        createUIComponents();
    }

    private void createUIComponents() {
        frame = new JFrame("Vagas");
        panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(400, 300));
        panel1.setLayout(new BorderLayout());


        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 400));
        frame.setVisible(true);
        frame.pack();

        createMenuBar_basic();
    }

    private void createMenuBar_basic() {
        panel1.removeAll();
        JTextArea jobListingsTextArea = new JTextArea("Esta Procurando por uma Vaga para descansar?");
        panel1.add(new JScrollPane(jobListingsTextArea), BorderLayout.CENTER);
        jobListingsTextArea.setEditable(false);
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem loginItem = new JMenuItem("Login");
        JMenuItem createAccountItem = new JMenuItem("Create Account");
        JMenuItem viewJobsItem = new JMenuItem("Quartos Disponíveis Hoje");

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
        viewJobsItem.addActionListener(e -> viewQuartos());

        panel1.revalidate();
        panel1.repaint();
    }

    private void createMenuBar_user(Client user) {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem viewJobsItem = new JMenuItem("Quartos Disponíveis para Hoje");
        JMenuItem logoutItem = new JMenuItem("Logout");
        JMenuItem reservaItem = new JMenuItem("Reservar Quarto");
        JMenuItem viewReservasItem = new JMenuItem("Ver Suas Reservas");

        optionsMenu.add(viewJobsItem);
        optionsMenu.add(logoutItem);
        optionsMenu.add(reservaItem);
        optionsMenu.add(viewReservasItem);
        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);

        logoutItem.addActionListener(e -> lougout());
        reservaItem.addActionListener(e -> reserva_quarto_user(user));
        viewJobsItem.addActionListener(e -> viewQuartos());
        viewReservasItem.addActionListener(e -> viewReservas(user));

    }

    public void lougout(){
        JOptionPane.showMessageDialog(null, "Logout successful!");
        createMenuBar_basic();
    }

    private void viewReservas(Client user) {
        panel1.removeAll();
        JTextArea reservasTextArea = new JTextArea("Suas Reservas:\n");
        reservasTextArea.setEditable(false);
        panel1.add(new JScrollPane(reservasTextArea), BorderLayout.CENTER);

        try {
            List<Reserva> reservas = Reserva_Service.getInstance().getReservas(user.getId());
            for (Reserva reserva : reservas) {
                reservasTextArea.append("ID: " + reserva.getId() + "\n");
                reservasTextArea.append("Data de Entrada: " + reserva.getDataEntrada() + "\n");
                reservasTextArea.append("Data de Saida: " + reserva.getDataSaida() + "\n");
                reservasTextArea.append("Categoria: " + reserva.getCategoria().getRoomType() + "\n");
                reservasTextArea.append("Reservado: " + reserva.getReserved() + "\n\n");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        JButton cancelReservaButton = new JButton("Cancelar Reserva");
        panel1.add(cancelReservaButton, BorderLayout.SOUTH);

        cancelReservaButton.addActionListener(e -> {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da reserva a ser cancelada:"));
            try {
                Reserva_Service.getInstance().CancelReserva(id);
                JOptionPane.showMessageDialog(null, "Reserva cancelada com sucesso!");
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        panel1.revalidate();
        panel1.repaint();
    }

    private void viewQuartos() {
        panel1.removeAll();
        JTextArea vagasTextArea = new JTextArea("Quartos Disponíveis para Hoje:\n"
                + "Quartos do tipo Familia por 120 reais a diaria , do tipo Single por 70 reais e do tipo Suite por 200 reias a diaria\n");
        vagasTextArea.setEditable(false);
        panel1.add(new JScrollPane(vagasTextArea), BorderLayout.CENTER);

        try {
            List<DefaultRoom> rooms = Quarto_Service.getInstance().getRooms();
            for (DefaultRoom room : rooms) {
                if(!(room.isReserved())){
                    vagasTextArea.append("ID: " + room.getId() + "\n");
                    vagasTextArea.append("Tipo: " + room.getBridgeroom().getRoomType() + "\n");
                    vagasTextArea.append("Descrição: " + room.getDescription() + "\n");
                    vagasTextArea.append("Comprimento: " + room.getLength() + "\n");
                    vagasTextArea.append("Largura: " + room.getWidth() + "\n");
                    vagasTextArea.append("Altura: " + room.getHeight() + "\n\n");
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        panel1.revalidate();
        panel1.repaint();
    }

    private void reserva_quarto_user(Client user) {
        panel1.removeAll();
        JTextArea reserva_quarto_text = new JTextArea("Para reservar um quarto, Informe o periodo da reserva e a Categoria do quarto desejado.\n" +
                "Temos como Categoria disponiveis: \n Quartos do tipo Familia por 120 reais a diaria , do tipo Single por 70 reais e do tipo Suite por 200 reias a diaria\n"+
                "Quartos Familia tem capacidade de 4 a 6 pessoas , Single de 1 a 2 pessoas e Suite de 2 a 4 pessoas");
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
                    if(Reserva_Service.getInstance().makeReservation(user, dataAtual , data_inicial, data_end, tipo_quarto)){
                        JOptionPane.showMessageDialog(null, "Reserva feita com sucesso!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Falha na reserva do quarto\n" + "Não temos quartos disponiveis do tipo"+tipo_quarto+" para o periodo informado");
                        List<Integer> ids = Reserva_Service.getInstance().get_Ids(user, dataAtual, data_inicial, data_end, tipo_quarto);
                        observer_Service(user, ids);
                    }
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Falha na reserva");
            }
        });

        panel1.revalidate();
        panel1.repaint();
    }

    private void observer_Service(Client user, List<Integer> ids) {
        int option = JOptionPane.showConfirmDialog(null, "Quarto não disponível. Deseja entrar na lista de espera?", "Lista de Espera", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                for (Integer id : ids) {
                    Waiting_List_Service.getInstance().attach(id, user.getEmail());
                }
                JOptionPane.showMessageDialog(null, "Adicionado à lista de espera com sucesso!\n" +
                        "Você será notificado por email quando um quarto estiver disponível. Olhe o Spam!");
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Reserva cancelada!");
        }
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
            if(username.equals("admin") && password.equals("admin")){
                JOptionPane.showMessageDialog(null, "Login successful!");
                crate_menu_bar_admin(password, username);
            }
            else{
                userLogin(username,password);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Login failed!");
        }
    }

    private void userLogin(String username, String password) {
        try {
           var user_login =  Client_Service.getInstance().haveClient(username, password);
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
            var validEmail  = Client_Service.getInstance().isValidEmail(email);
            if(!validEmail){
                JOptionPane.showMessageDialog(null, "Email inválido!");
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date birthday = dateFormat.parse(birthdayField.getText());
            if (password.equals(confirmPassword) && validEmail) {
                if (Client_Service.getInstance().createUser(username, password, email, birthday)) {
                    JOptionPane.showMessageDialog(null, "Account created successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to create account!" + "Username already exists");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Passwords do not match!");
            }
        }
    }


    private void crate_menu_bar_admin(String password, String username) {
        if(username.equals("admin") && password.equals("admin")) {
            panel1.removeAll();
            JMenuBar menuBar = new JMenuBar();
            JMenu optionsMenu = new JMenu("Options");
            JMenuItem criar_quarto = new JMenuItem("Criar Quarto");
            JMenuItem logoutItem = new JMenuItem("Logout");
            JMenuItem reserva_cliente = new JMenuItem("Reservar  para Cliente");
            JMenuItem viewReservasItem = new JMenuItem("Ver Reservas");
            JMenuItem createAccountItem = new JMenuItem("Create Account");
            JMenuItem mostrar_clientes = new JMenuItem("Mostrar Clientes");
            JMenuItem realizarEstadia = new JMenuItem("Realizar Estadia");
            JMenuItem mostrar_quartos = new JMenuItem("Mostrar Quartos");
            JMenuItem mostrar_lista_espera = new JMenuItem("Mostrar Lista de Espera");
            JMenuItem mostrar_estadias = new JMenuItem("Mostrar Estadias");


            optionsMenu.add(criar_quarto);
            optionsMenu.add(logoutItem);
            optionsMenu.add(reserva_cliente);
            optionsMenu.add(viewReservasItem);
            optionsMenu.add(createAccountItem);
            optionsMenu.add(realizarEstadia);
            optionsMenu.add(mostrar_clientes);
            optionsMenu.add(mostrar_quartos);
            optionsMenu.add(mostrar_lista_espera);
            optionsMenu.add(mostrar_estadias);
            menuBar.add(optionsMenu);
            frame.setJMenuBar(menuBar);

            logoutItem.addActionListener(e -> lougout());
            criar_quarto.addActionListener(e -> showCreateRoomDialog());
            reserva_cliente.addActionListener(e -> reserva_quarto_admim());
            viewReservasItem.addActionListener(e -> {
                viewReservas_admin();
            });
            createAccountItem.addActionListener(e -> {
                try {
                    showCreateAccountDialog();
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            });
            realizarEstadia.addActionListener(e -> {
                realizarEstadia();
            });
            mostrar_clientes.addActionListener(e -> {
                try {
                    mostrar_clientes();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });
            mostrar_quartos.addActionListener(e -> {
                try {
                    viewQuartos_admin();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            mostrar_lista_espera.addActionListener(e -> {
                mostrar_lista_espera();
            });
            mostrar_estadias.addActionListener(e -> {
                mostrar_estadias();
            });


            panel1.revalidate();
            panel1.repaint();
        }
    }

    private void mostrar_estadias() {
        panel1.removeAll();
        JTextArea estadiasTextArea = new JTextArea("Estadias:\n");
        estadiasTextArea.setEditable(false);
        panel1.add(new JScrollPane(estadiasTextArea), BorderLayout.CENTER);

        try {
            List<Estadia> estadias = Estadia_Service.getInstance().getEstadias();
            for (Estadia estadia : estadias) {
                estadiasTextArea.append("ID: " + estadia.getId() + "\n");
                estadiasTextArea.append("Data de Entrada: " + estadia.getDataEntrada() + "\n");
                estadiasTextArea.append("Data de Saida: " + estadia.getDataSaida() + "\n");
                estadiasTextArea.append("Categoria: " + estadia.getQuarto().getBridgeroom().getRoomType() + "\n");
                estadiasTextArea.append("Cliente: " + estadia.getCliente().getName() + "\n\n");
                estadiasTextArea.append("Pessoas: " + estadia.getPessoas() + "\n\n");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        JButton cancelEstadiaButton = new JButton("Check-Out");
        panel1.add(cancelEstadiaButton, BorderLayout.SOUTH);

        cancelEstadiaButton.addActionListener(e -> {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da estadia a ser deletada:"));
            Estadia_Service.getInstance().DeleteEstadia(id);
            JOptionPane.showMessageDialog(null, "Estadia deletada com sucesso!");
        });
    }

    private void mostrar_lista_espera() {
        panel1.removeAll();
        JTextArea listaEsperaTextArea = new JTextArea("Lista de Espera:\n");
        listaEsperaTextArea.setEditable(false);
        panel1.add(new JScrollPane(listaEsperaTextArea), BorderLayout.CENTER);

        try {
            List<String> listaEspera = Waiting_List_Service.getInstance().getListaEspera();
            for (String email : listaEspera) {
                listaEsperaTextArea.append("Email: " + email + "\n");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void viewQuartos_admin() {
        panel1.removeAll();
        JTextArea vagasTextArea = new JTextArea("Situação dos Quartos:\n");
        vagasTextArea.setEditable(false);
        panel1.add(new JScrollPane(vagasTextArea), BorderLayout.CENTER);

        try {
            List<DefaultRoom> rooms = Quarto_Service.getInstance().getRooms();
            for (DefaultRoom room : rooms) {
                vagasTextArea.append("ID: " + room.getId() + "\n");
                vagasTextArea.append("Tipo: " + room.getBridgeroom().getRoomType() + "\n");
                vagasTextArea.append("Descrição: " + room.getDescription() + "\n");
                vagasTextArea.append("Comprimento: " + room.getLength() + "\n");
                vagasTextArea.append("Largura: " + room.getWidth() + "\n");
                vagasTextArea.append("Altura: " + room.getHeight() + "\n");
                vagasTextArea.append("Reservado: " + room.isReserved() + "\n\n");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        JButton showInputFieldsButton = new JButton("Remover Quarto");
        panel1.add(showInputFieldsButton, BorderLayout.SOUTH);

        showInputFieldsButton.addActionListener(e -> {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do quarto a ser deletado:"));
            try {
                Quarto_Service.getInstance().removeRoom(id);
                JOptionPane.showMessageDialog(null, "Quarto deletado com sucesso!");
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private void realizarEstadia() {
        panel1.removeAll();
        JTextArea estadiaTextArea = new JTextArea("Para realizar uma estadia caso o cliente possua ja possua uma reserva, informe o ID do cliente , o ID da reserva  e a quantidade de pessoas.\n" +
                "Caso o cliente queira fazer uma estadia sem reserva informe o ID do cliente , o ID do quarto livre, a quantidade de pessoas , Data de entrada e de Saida.\n" +
                "Caso o cliente Possua uma reserva mas queira Mudar a Data de Saida , informe o ID da reserva ,a quantidade de pessoas, o ID do Cliente e a nova Data de Saida.\n");
        estadiaTextArea.setEditable(false);
        panel1.add(new JScrollPane(estadiaTextArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout()); // FlowLayout centers the button

        JButton showInputFieldsButton = new JButton("Ja Possui reserva");
        JButton showInputFieldsButton2 = new JButton("Mudar Data de Saida, mas ja possui reserva");
        JButton showInputFieldsButton3 = new JButton("Sem Reserva");

        buttonPanel.add(showInputFieldsButton, BorderLayout.AFTER_LAST_LINE);
        buttonPanel.add(showInputFieldsButton2, BorderLayout.AFTER_LAST_LINE);
        buttonPanel.add(showInputFieldsButton3 , BorderLayout.AFTER_LAST_LINE);

        panel1.add(buttonPanel, BorderLayout.SOUTH);

        showInputFieldsButton.addActionListener(e -> {
            JTextField clientIdField = new JTextField();
            JTextField reservaIdField = new JTextField();
            JTextField pessoasField = new JTextField();

            Object[] message = {
                    "ID do Cliente:", clientIdField,
                    "ID da Reserva:", reservaIdField,
                    "Quantidade de Pessoas:", pessoasField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Estadia", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    int clientId = Integer.parseInt(clientIdField.getText());
                    int reservaId = Integer.parseInt(reservaIdField.getText());
                    int pessoas = Integer.parseInt(pessoasField.getText());
                    if(Estadia_Service.getInstance().makeEstadia_ComReserva(clientId, reservaId, pessoas)){
                        JOptionPane.showMessageDialog(null, "Estadia feita com sucesso!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Falha na estadia");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Falha na estadia");
            }


        });

        showInputFieldsButton2.addActionListener(e -> {
            JTextField clientIdField = new JTextField();
            JTextField reservaIdField = new JTextField();
            JTextField dataField_end = new JTextField();
            JTextField pessoasField = new JTextField();

            Object[] message = {
                    "ID do Cliente:", clientIdField,
                    "ID da Reserva:", reservaIdField,
                    "Data de saida(dd/mm/yyyy)", dataField_end,
                    "Quantidade de Pessoas:", pessoasField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Estadia", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    int clientId = Integer.parseInt(clientIdField.getText());
                    int reservaid = Integer.parseInt(reservaIdField.getText());
                    int pessoas = Integer.parseInt(pessoasField.getText());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date data_end = dateFormat.parse(dataField_end.getText());
                    if(Estadia_Service.getInstance().makeEstadia_MudandoData(clientId,reservaid,  pessoas , data_end)){
                        JOptionPane.showMessageDialog(null, "Estadia feita com sucesso!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Falha na estadia");
                    }
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Falha na estadia");
            }

        });

        showInputFieldsButton3.addActionListener(e -> {
            JTextField clientIdField = new JTextField();
            JTextField tipo_quartoField = new JTextField();
            JTextField dataField_inicial = new JTextField();
            JTextField dataField_end = new JTextField();
            JTextField pessoasField = new JTextField();

            Object[] message = {
                    "ID do Cliente:", clientIdField,
                    "Tipo do quarto:", tipo_quartoField,
                    "Data Inicial(dd/mm/yyyy)", dataField_inicial,
                    "Data de saida(dd/mm/yyyy)", dataField_end,
                    "Quantidade de Pessoas:", pessoasField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Estadia", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    int clientId = Integer.parseInt(clientIdField.getText());
                    String tipo_quarto = tipo_quartoField.getText();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date data_inicial = dateFormat.parse(dataField_inicial.getText());
                    Date data_end = dateFormat.parse(dataField_end.getText());
                    int pessoas = Integer.parseInt(pessoasField.getText());
                    if(Estadia_Service.getInstance().makeEstadia_SemReserva(clientId, data_inicial, data_end, tipo_quarto, pessoas)){
                        JOptionPane.showMessageDialog(null, "Estadia feita com sucesso!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Falha na estadia");
                    }
                } catch (SQLException | ParseException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Falha na estadia");
            }

        });

        panel1.revalidate();
        panel1.repaint();
    }

    private void viewReservas_admin() {
        panel1.removeAll();
        JTextArea reservasTextArea = new JTextArea("Reservas:\n");
        reservasTextArea.setEditable(false);
        panel1.add(new JScrollPane(reservasTextArea), BorderLayout.CENTER);

        try {
            List<Reserva> reservas = Reserva_Service.getInstance().getReservas();
            for (Reserva reserva : reservas) {
                reservasTextArea.append("ID: " + reserva.getId() + "\n");
                reservasTextArea.append("Data de Entrada: " + reserva.getDataEntrada() + "\n");
                reservasTextArea.append("Data de Saida: " + reserva.getDataSaida() + "\n");
                reservasTextArea.append("Categoria: " + reserva.getCategoria().getRoomType() + "\n");
                reservasTextArea.append("Reservado: " + reserva.getReserved() + "\n\n");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void mostrar_clientes() throws SQLException, ClassNotFoundException {
        panel1.removeAll();
        List<Client> clients = Client_Service.getInstance().getUsers();
        JTextArea clientsTextArea = new JTextArea("Clientes:\n");
        clientsTextArea.setEditable(false);
        panel1.add(new JScrollPane(clientsTextArea), BorderLayout.CENTER);

        for (Client client : clients) {
            clientsTextArea.append("ID: " + client.getId() + "\n");
            clientsTextArea.append("Username: " + client.getName() + "\n");
            clientsTextArea.append("Email: " + client.getEmail() + "\n");
            clientsTextArea.append("Birthday: " + client.getBirthday() + "\n\n");
        }
    }

    private void reserva_quarto_admim() {
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
            JTextField userIdField = new JTextField();

            Object[] message = {
                    "Data Inicial(dd/mm/yyyy)", dataField_inicial,
                    "Data de saida(dd/mm/yyyy)", dataField_end,
                    "Tipo de Quarto", categoria_Field,
                    "ID do Cliente", userIdField
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
                    int userId = Integer.parseInt(userIdField.getText());
                    var user = Client_Service.getInstance().getClient(userId);
                    if(Reserva_Service.getInstance().makeReservation( user, dataAtual , data_inicial, data_end, tipo_quarto)){
                        JOptionPane.showMessageDialog(null, "Reserva feita com sucesso!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Falha na reserva do quarto\n" + "Não temos quartos disponiveis do tipo"+tipo_quarto+" para o periodo informado");
                        List<Integer> ids = Reserva_Service.getInstance().get_Ids( user, dataAtual, data_inicial, data_end, tipo_quarto);
                        observer_Service(user, ids);
                    }
                } catch (SQLException | ParseException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Falha na reserva");
            }
        });

        panel1.revalidate();
        panel1.repaint();
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
            try {
                if (Quarto_Service.getInstance().createRoom(roomType, roomCapacity, roomDescription, roomLength, roomWidth, roomHeight)) {
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

