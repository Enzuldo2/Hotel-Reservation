package br.ufscar.dc.pooa.View;

import br.ufscar.dc.pooa.Model.domain.Reserva.Estadia;
import br.ufscar.dc.pooa.Model.domain.Reserva.Reserva;
import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.Service.*;
import javax.swing.ImageIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class VagasView {
    private JFrame frame;
    private JPanel panel1;

    public VagasView() {
        initializeUI();
    }

    private void initializeUI() {
        frame = createFrame("Vagas", 500, 400);
        panel1 = createPanel(new BorderLayout(), new Dimension(400, 300));
        frame.setContentPane(panel1);
        createMenuBarBasic();
    }

    private JMenuItem createMenuItem(String text, ActionListener listener, String iconPath) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(listener);
        if (iconPath != null && !iconPath.isEmpty()) {
            menuItem.setIcon(new ImageIcon(iconPath));
        }
        return menuItem;
    }

    JMenuItem loginItem = createMenuItem("Login", e -> showLoginDialog(), "C:\\Users\\enzod\\Desktop\\imagem_de_inicio.jpeg");

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

    private void refreshPanel() {
        panel1.revalidate();
        panel1.repaint();
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
                createMenuBarAdmin(password, username);
            } else {
                userLogin(username, password);
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

    private void userLogin(String username, String password) {
        try {
            Client userLogin = Client_Service.getInstance().haveClient(username, password);
            if (userLogin != null) {
                showMessageDialog("Login successful!");
                userInterface(userLogin);
            } else {
                showMessageDialog("Login failed!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            showErrorDialog(e);
        }
    }

    private void userInterface(Client user) {
        panel1.removeAll();
        JTextArea userTextArea = createTextArea("Bem vindo ao Hotel!");
        panel1.add(new JScrollPane(userTextArea), BorderLayout.CENTER);
        createMenuBarUser(user);
        refreshPanel();
    }

    private void createMenuBarUser(Client user) {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem viewJobsItem = createMenuItem("Quartos Disponíveis para Hoje", e -> viewQuartos());
        JMenuItem logoutItem = createMenuItem("Logout", e -> logout());
        JMenuItem reservaItem = createMenuItem("Reservar Quarto", e -> reservaQuartoUser(user));
        JMenuItem viewReservasItem = createMenuItem("Ver Suas Reservas", e -> viewReservas(user));

        optionsMenu.add(viewJobsItem);
        optionsMenu.add(logoutItem);
        optionsMenu.add(reservaItem);
        optionsMenu.add(viewReservasItem);
        menuBar.add(optionsMenu);

        frame.setJMenuBar(menuBar);
    }

    private void logout() {
        showMessageDialog("Logout successful!");
        createMenuBarBasic();
    }

    private void viewReservas(Client user) {
        panel1.removeAll();
        JTextArea reservasTextArea = createTextArea("Suas Reservas:\n");
        panel1.add(new JScrollPane(reservasTextArea), BorderLayout.CENTER);

        try {
            List<Reserva> reservas = Reserva_Service.getInstance().getReservas(user.getId());
            reservas.forEach(reserva -> appendReservaInfo(reservasTextArea, reserva));

            JButton cancelReservaButton = createButton("Cancelar Reserva", e -> cancelReserva());
            panel1.add(cancelReservaButton, BorderLayout.SOUTH);
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }

        refreshPanel();
    }

    private void appendReservaInfo(JTextArea textArea, Reserva reserva) {
        textArea.append("ID: " + reserva.getId() + "\n");
        textArea.append("Data de Entrada: " + reserva.getDataEntrada() + "\n");
        textArea.append("Data de Saida: " + reserva.getDataSaida() + "\n");
        textArea.append("Categoria: " + reserva.getCategoria().getRoomType() + "\n");
        textArea.append("Reservado: " + reserva.getReserved() + "\n\n");
    }

    private void cancelReserva() {
        try {
            int id = Integer.parseInt(showInputDialog("Digite o ID da reserva a ser cancelada:"));
            Reserva_Service.getInstance().CancelReserva(id);
            showMessageDialog("Reserva cancelada com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }
    }

    private String showInputDialog(String message) {
        return JOptionPane.showInputDialog(message);
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

    private void reservaQuartoUser(Client user) {
        panel1.removeAll();
        JTextArea reservaQuartoText = createTextArea("Para reservar um quarto, Informe o periodo da reserva e a Categoria do quarto desejado.\n" +
                "Temos como Categoria disponiveis:\n" +
                "Quartos do tipo Familia por 120 reais a diaria, do tipo Single por 70 reais e do tipo Suite por 200 reais a diaria\n" +
                "Quartos Familia tem capacidade de 4 a 6 pessoas, Single de 1 a 2 pessoas e Suite de 2 a 4 pessoas.");
        panel1.add(new JScrollPane(reservaQuartoText), BorderLayout.CENTER);

        JButton showInputFieldsButton = createButton("Reservar", e -> showReservaDialog(user));
        panel1.add(showInputFieldsButton, BorderLayout.SOUTH);

        refreshPanel();
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }

    private void showReservaDialog(Client user) {
        JTextField dataFieldInicial = new JTextField();
        JTextField dataFieldEnd = new JTextField();
        JTextField categoriaField = new JTextField();

        Object[] message = {
                "Data Inicial(dd/MM/yyyy)", dataFieldInicial,
                "Data de saida(dd/MM/yyyy)", dataFieldEnd,
                "Tipo de Quarto", categoriaField
        };

        if (showConfirmDialog("Reserva", message)) {
            processReserva(user, dataFieldInicial.getText(), dataFieldEnd.getText(), categoriaField.getText());
        } else {
            showMessageDialog("Falha na reserva");
        }
    }

    private void processReserva(Client user, String dataInicial, String dataEnd, String tipoQuarto) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInicialDate = dateFormat.parse(dataInicial);
            Date dataEndDate = dateFormat.parse(dataEnd);

            LocalDate hoje = LocalDate.now();
            String dataAtual = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (Reserva_Service.getInstance().makeReservation(user, dataAtual, dataInicialDate, dataEndDate, tipoQuarto)) {
                showMessageDialog("Reserva feita com sucesso!");
            } else {
                showMessageDialog("Falha na reserva do quarto\n" + "Não temos quartos disponiveis do tipo " + tipoQuarto + " para o periodo informado");
                observerService(user, Reserva_Service.getInstance().get_Ids(dataInicialDate, dataEndDate, tipoQuarto));
            }
        } catch (ParseException | SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }
    }

    private void observerService(Client user, List<Integer> ids) {
        if (JOptionPane.showConfirmDialog(null, "Quarto não disponível. Deseja entrar na lista de espera?", "Lista de Espera", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                for (Integer id : ids) {
                    Waiting_List_Service.getInstance().attach(id, user.getEmail());
                }
                showMessageDialog("Adicionado à lista de espera com sucesso!\n" +
                        "Você será notificado por email quando um quarto estiver disponível. Olhe o Spam!");
            } catch (SQLException | ClassNotFoundException ex) {
                showErrorDialog(ex);
            }
        } else {
            showMessageDialog("Reserva cancelada!");
        }
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

    private void createMenuBarAdmin(String password, String username) {
        if (isAdmin(username, password)) {
            panel1.removeAll();
            frame.setJMenuBar(createMenuBarAdminItems());
            refreshPanel();
        }
    }

    private JMenuBar createMenuBarAdminItems() {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem criarQuarto = createMenuItem("Criar Quarto", e -> showCreateRoomDialog());
        JMenuItem logoutItem = createMenuItem("Logout", e -> logout());
        JMenuItem reservaCliente = createMenuItem("Reservar para Cliente", e -> reservaQuartoAdmin());
        JMenuItem viewReservasItem = createMenuItem("Ver Reservas", e -> viewReservasAdmin());
        JMenuItem createAccountItem = createMenuItem("Create Account", e -> {
            try {
                showCreateAccountDialog();
            } catch (SQLException | ClassNotFoundException | ParseException ex) {
                showErrorDialog(ex);
            }
        });
        JMenuItem mostrarClientes = createMenuItem("Mostrar Clientes", e -> {
            try {
                mostrarClientes();
            } catch (SQLException | ClassNotFoundException ex) {
                showErrorDialog(ex);
            }
        });
        JMenuItem realizarEstadia = createMenuItem("Realizar Estadia", e -> realizarEstadia());
        JMenuItem mostrarQuartos = createMenuItem("Mostrar Quartos", e -> {
            try {
                viewQuartosAdmin();
            } catch (SQLException | ClassNotFoundException ex) {
                showErrorDialog(ex);
            }
        });
        JMenuItem mostrarListaEspera = createMenuItem("Mostrar Lista de Espera", e -> mostrarListaEspera());
        JMenuItem mostrarEstadias = createMenuItem("Mostrar Estadias", e -> mostrarEstadias());

        optionsMenu.add(criarQuarto);
        optionsMenu.add(logoutItem);
        optionsMenu.add(reservaCliente);
        optionsMenu.add(viewReservasItem);
        optionsMenu.add(createAccountItem);
        optionsMenu.add(realizarEstadia);
        optionsMenu.add(mostrarClientes);
        optionsMenu.add(mostrarQuartos);
        optionsMenu.add(mostrarListaEspera);
        optionsMenu.add(mostrarEstadias);
        menuBar.add(optionsMenu);

        return menuBar;
    }

    private void reservaQuartoAdmin() {
        panel1.removeAll();
        JTextArea reservaQuartoText = createTextArea("Para reservar um quarto para um cliente, informe o ID do cliente, o periodo da reserva e a Categoria do quarto desejado.\n" +
                "Temos como Categoria disponiveis:\n" +
                "Quartos do tipo Familia por 120 reais a diaria, do tipo Single por 70 reais e do tipo Suite por 200 reais a diaria\n" +
                "Quartos Familia tem capacidade de 4 a 6 pessoas, Single de 1 a 2 pessoas e Suite de 2 a 4 pessoas.");
        panel1.add(new JScrollPane(reservaQuartoText), BorderLayout.CENTER);

        JButton showInputFieldsButton = createButton("Reservar", e -> showReservaDialogAdmin());
        panel1.add(showInputFieldsButton, BorderLayout.SOUTH);

        refreshPanel();
    }

    private void showReservaDialogAdmin() {
        JTextField clientIdField = new JTextField();
        JTextField dataFieldInicial = new JTextField();
        JTextField dataFieldEnd = new JTextField();
        JTextField categoriaField = new JTextField();

        Object[] message = {
                "ID do Cliente:", clientIdField,
                "Data Inicial(dd/MM/yyyy)", dataFieldInicial,
                "Data de saida(dd/MM/yyyy)", dataFieldEnd,
                "Tipo de Quarto", categoriaField
        };

        if (showConfirmDialog("Reserva", message)) {
            processReservaAdmin(clientIdField, dataFieldInicial, dataFieldEnd, categoriaField);
        } else {
            showMessageDialog("Falha na reserva");
        }
    }

    private void processReservaAdmin(JTextField clientIdField, JTextField dataFieldInicial, JTextField dataFieldEnd, JTextField categoriaField) {
        try {
            int clientId = Integer.parseInt(clientIdField.getText());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInicialDate = dateFormat.parse(dataFieldInicial.getText());
            Date dataEndDate = dateFormat.parse(dataFieldEnd.getText());
            String tipoQuarto = categoriaField.getText();
            LocalDate hoje = LocalDate.now();
            String dataAtual = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));


            if (Reserva_Service.getInstance().makeReservation(Client_Service.getInstance().getClient(clientId), dataAtual, dataInicialDate, dataEndDate, tipoQuarto)) {
                showMessageDialog("Reserva feita com sucesso!");
            } else {
                showMessageDialog("Falha na reserva do quarto\n" + "Não temos quartos disponiveis do tipo " + categoriaField.getText() + " para o periodo informado");
                observerService(Client_Service.getInstance().getClient(clientId), Reserva_Service.getInstance().get_Ids(dataInicialDate, dataEndDate, categoriaField.getText()));
            }
        } catch (ParseException | SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }
    }

    private void realizarEstadia() {
        panel1.removeAll();
        JTextArea estadiaTextArea = createTextArea("Para realizar uma estadia, caso o cliente possua já possua uma reserva, informe o ID do cliente, o ID da reserva e a quantidade de pessoas.\n" +
                "Caso o cliente queira fazer uma estadia sem reserva, informe o ID do cliente, o ID do quarto livre, a quantidade de pessoas, Data de entrada e de Saida.\n" +
                "Caso o cliente possua uma reserva mas queira mudar a Data de Saida, informe o ID da reserva, a quantidade de pessoas, o ID do Cliente e a nova Data de Saida.");
        panel1.add(new JScrollPane(estadiaTextArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(createButton("Já Possui Reserva", e -> showEstadiaDialogComReserva()));
        buttonPanel.add(createButton("Mudar Data de Saida", e -> showEstadiaDialogMudandoData()));
        buttonPanel.add(createButton("Sem Reserva", e -> showEstadiaDialogSemReserva()));

        panel1.add(buttonPanel, BorderLayout.SOUTH);
        refreshPanel();
    }

    private void showEstadiaDialogComReserva() {
        JTextField clientIdField = new JTextField();
        JTextField reservaIdField = new JTextField();
        JTextField pessoasField = new JTextField();

        Object[] message = {
                "ID do Cliente:", clientIdField,
                "ID da Reserva:", reservaIdField,
                "Quantidade de Pessoas:", pessoasField
        };

        if (showConfirmDialog("Estadia", message)) {
            try {
                int clientId = Integer.parseInt(clientIdField.getText());
                int reservaId = Integer.parseInt(reservaIdField.getText());
                int pessoas = Integer.parseInt(pessoasField.getText());

                if (Estadia_Service.getInstance().makeEstadia_ComReserva(clientId, reservaId, pessoas)) {
                    showMessageDialog("Estadia feita com sucesso!");
                } else {
                    showMessageDialog("Falha na estadia");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                showErrorDialog(ex);
            }
        } else {
            showMessageDialog("Falha na estadia");
        }
    }

    private void showEstadiaDialogMudandoData() {
        JTextField clientIdField = new JTextField();
        JTextField reservaIdField = new JTextField();
        JTextField dataFieldEnd = new JTextField();
        JTextField pessoasField = new JTextField();

        Object[] message = {
                "ID do Cliente:", clientIdField,
                "ID da Reserva:", reservaIdField,
                "Data de saida(dd/MM/yyyy):", dataFieldEnd,
                "Quantidade de Pessoas:", pessoasField
        };

        if (showConfirmDialog("Estadia", message)) {
            try {
                int clientId = Integer.parseInt(clientIdField.getText());
                int reservaId = Integer.parseInt(reservaIdField.getText());
                int pessoas = Integer.parseInt(pessoasField.getText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dataEnd = dateFormat.parse(dataFieldEnd.getText());

                if (Estadia_Service.getInstance().makeEstadia_MudandoData(clientId, reservaId,pessoas, dataEnd)) {
                    showMessageDialog("Estadia feita com sucesso!");
                } else {
                    showMessageDialog("Falha na estadia");
                }
            } catch (SQLException | ClassNotFoundException | ParseException ex) {
                showErrorDialog(ex);
            }
        } else {
            showMessageDialog("Falha na estadia");
        }
    }

    private void showEstadiaDialogSemReserva() {
        JTextField clientIdField = new JTextField();
        JTextField roomTypeField = new JTextField();
        JTextField dataFieldInicial = new JTextField();
        JTextField dataFieldEnd = new JTextField();
        JTextField pessoasField = new JTextField();

        Object[] message = {
                "ID do Cliente:", clientIdField,
                "Tipo do Quarto:", roomTypeField,
                "Data Inicial(dd/MM/yyyy):", dataFieldInicial,
                "Data de saida(dd/MM/yyyy):", dataFieldEnd,
                "Quantidade de Pessoas:", pessoasField
        };

        if (showConfirmDialog("Estadia", message)) {
            try {
                int clientId = Integer.parseInt(clientIdField.getText());
                String tipo_quarto = roomTypeField.getText();
                int pessoas = Integer.parseInt(pessoasField.getText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dataInicial = dateFormat.parse(dataFieldInicial.getText());
                Date dataEnd = dateFormat.parse(dataFieldEnd.getText());

                if (Estadia_Service.getInstance().makeEstadia_SemReserva(clientId,  dataInicial, dataEnd, tipo_quarto ,pessoas)) {
                    showMessageDialog("Estadia feita com sucesso!");
                } else {
                    showMessageDialog("Falha na estadia");
                }
            } catch (SQLException | ClassNotFoundException | ParseException ex) {
                showErrorDialog(ex);
            }
        } else {
            showMessageDialog("Falha na estadia");
        }
    }

    private void viewReservasAdmin() {
        panel1.removeAll();
        JTextArea reservasTextArea = createTextArea("Reservas:\n");
        panel1.add(new JScrollPane(reservasTextArea), BorderLayout.CENTER);

        try {
            List<Reserva> reservas = Reserva_Service.getInstance().getReservas();
            reservas.forEach(reserva -> appendReservaInfo(reservasTextArea, reserva));

            JButton cancelReservaButton = createButton("Cancelar Reserva", e -> cancelReserva());
            panel1.add(cancelReservaButton, BorderLayout.SOUTH);
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }

        refreshPanel();
    }

    private void viewQuartosAdmin() throws SQLException, ClassNotFoundException {
        panel1.removeAll();
        JTextArea vagasTextArea = createTextArea("Quartos Disponíveis para Hoje:\n" +
                "Quartos do tipo Familia por 120 reais a diaria, do tipo Single por 70 reais e do tipo Suite por 200 reais a diaria\n");
        panel1.add(new JScrollPane(vagasTextArea), BorderLayout.CENTER);

        List<DefaultRoom> rooms = Quarto_Service.getInstance().getRooms();
        rooms.forEach(room -> appendRoomInfo(vagasTextArea, room));

        refreshPanel();
    }

    private void mostrarClientes() throws SQLException, ClassNotFoundException {
        panel1.removeAll();
        JTextArea clientesTextArea = createTextArea("Clientes:\n");
        panel1.add(new JScrollPane(clientesTextArea), BorderLayout.CENTER);

        List<Client> clients = Client_Service.getInstance().getUsers();
        clients.forEach(client -> appendClientInfo(clientesTextArea, client));

        refreshPanel();
    }

    private void appendClientInfo(JTextArea textArea, Client client) {
        textArea.append("ID: " + client.getId() + "\n");
        textArea.append("Nome: " + client.getName()+ "\n");
        textArea.append("Email: " + client.getEmail() + "\n");
        textArea.append("Data de Aniversário: " + client.getBirthday() + "\n\n");
    }

    private void mostrarListaEspera() {
        panel1.removeAll();
        JTextArea listaEsperaTextArea = createTextArea("Lista de Espera:\n");
        panel1.add(new JScrollPane(listaEsperaTextArea), BorderLayout.CENTER);

        try {
            List<String> waitList = Waiting_List_Service.getInstance().getListaEspera();
            if (waitList.isEmpty()) {
                listaEsperaTextArea.append("Nenhum cliente na lista de espera.\n");
            } else {
                waitList.forEach(email -> listaEsperaTextArea.append("Email: " + email + "\n"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }

        refreshPanel();
    }

    private void showErrorDialog(Exception ex) {
        JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarEstadias() {
        panel1.removeAll();
        JTextArea estadiasTextArea = createTextArea("Estadias:\n");
        panel1.add(new JScrollPane(estadiasTextArea), BorderLayout.CENTER);

        try {
            List<Estadia> estadias = Estadia_Service.getInstance().getEstadias();
            estadias.forEach(estadia -> appendEstadiaInfo(estadiasTextArea, estadia));
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }

        refreshPanel();
    }

    private void appendEstadiaInfo(JTextArea textArea, Estadia estadia) {
        textArea.append("ID: " + estadia.getId() + "\n");
        textArea.append("ID do Cliente: " + estadia.getCliente().getPersonId() + "\n");
        textArea.append("ID do Quarto: " + estadia.getQuarto().getRoomId() + "\n");
        textArea.append("Data de Entrada: " + estadia.getDataEntrada() + "\n");
        textArea.append("Data de Saída: " + estadia.getDataSaida() + "\n");
        textArea.append("Quantidade de Pessoas: " + estadia.getPessoas()+ "\n\n");
    }

    private void showCreateRoomDialog() {
        JTextField roomTypeField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField lengthField = new JTextField();
        JTextField widthField = new JTextField();
        JTextField heightField = new JTextField();
        JTextArea  capacityField = new JTextArea();


        Object[] message = {
                "Tipo de Quarto:", roomTypeField,
                "Capacidade:", capacityField,
                "Descrição:", descriptionField,
                "Comprimento:", lengthField,
                "Largura:", widthField,
                "Altura:", heightField
        };

        if (showConfirmDialog("Criar Quarto", message)) {
            try {
                String roomType = roomTypeField.getText();
                String description = descriptionField.getText();
                int length = Integer.parseInt(lengthField.getText());
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                int capacity = Integer.parseInt(capacityField.getText());

                if (Quarto_Service.getInstance().createRoom(roomType,capacity, description, length, width, height)) {
                    showMessageDialog("Quarto criado com sucesso!");
                } else {
                    showMessageDialog("Falha na criação do quarto");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                showErrorDialog(ex);
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(VagasView::new);
    }
}

