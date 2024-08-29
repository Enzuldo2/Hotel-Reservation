package br.ufscar.dc.pooa.View;

import br.ufscar.dc.pooa.Model.domain.Reserva.Estadia;
import br.ufscar.dc.pooa.Model.domain.Reserva.Reserva;
import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public class AdminView extends UserView {

    public AdminView() {
        super("Admin - Bem-vindo");
        createMenuBar(null);
    }

    @Override
    protected void createMenuBar(Client user) {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem criarQuarto = createMenuItem("Criar Quarto", e -> showCreateRoomDialog());
        JMenuItem logoutItem = createMenuItem("Logout", e -> super.logout());
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

        frame.setJMenuBar(menuBar);

    }







    private void reservaQuartoAdmin() {
        panel1.removeAll();
        JTextArea reservaQuartoText = createTextArea("Para reservar um quarto para um cliente, informe o ID do cliente, o periodo da reserva e a Categoria do quarto desejado.\n" +
                "Temos como Categoria disponiveis:\n" +
                "Quartos do tipo Familia por 120 reais a diaria, do tipo Single por 70 reais e do tipo Suite por 200 reais a diaria\n" +
                "Quartos Familia tem capacidade de 4 a 6 pessoas, Single de 1 a 2 pessoas e Suite de 2 a 4 pessoas."+
                "Check-in: 14:00\n"+
                "Check-out: 12:00\n\n"+
                "Caso a reserva não esteja disponivel, o cliente será colocado na lista de espera, Informe o cliente disso.\n");
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
            Reserva_Service reserva_Service = Reserva_Service.getInstance();
            int clientId = Integer.parseInt(clientIdField.getText());
            Date dataInicialDate = reserva_Service.parseDate(dataFieldInicial.getText());
            Date dataEndDate = reserva_Service.parseDate(dataFieldEnd.getText());
            Date dataAtual = java.sql.Date.valueOf(LocalDate.now());
            String tipoQuarto = categoriaField.getText();


            if (reserva_Service.makeReservation(Client_Service.getInstance().getClient(clientId), dataAtual, dataInicialDate, dataEndDate, tipoQuarto)) {
                showMessageDialog("Reserva feita com sucesso!");
            } else {
                if(dataAtual.equals(dataInicialDate)){
                    throw new IllegalArgumentException("Hoje não temos quartos desse tipo disponiveis");
                }
                showMessageDialog("Falha na reserva do quarto\n" + "Não temos quartos disponiveis do tipo " + categoriaField.getText() + " para o periodo informado");
                observerService(Client_Service.getInstance().getClient(clientId), Reserva_Service.getInstance().get_Ids(dataInicialDate, dataEndDate, categoriaField.getText()));
            }
        } catch (ParseException | SQLException | ClassNotFoundException ex ) {
            showErrorDialog(ex);
        }
        catch (IllegalArgumentException ex) {
            showMessageDialog("Erro na reserva, dados invalidos. " + ex.getMessage());
        }
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
                "Quartos do tipo Familia por 120 reais a diaria, do tipo Single por 70 reais e do tipo Suite por 200 reais a diaria\n" +
                "Quartos Familia tem capacidade de 4 a 6 pessoas, Single de 1 a 2 pessoas e Suite de 2 a 4 pessoas.\n");
        panel1.add(new JScrollPane(vagasTextArea), BorderLayout.CENTER);

        List<DefaultRoom> rooms = Quarto_Service.getInstance().getRooms();
        for (DefaultRoom room : rooms) {
            super.appendRoomInfo(vagasTextArea, room);
        }

        JButton deleteRoomButton = createButton("Deletar Quarto", e -> deleteRoom());
        panel1.add(deleteRoomButton, BorderLayout.SOUTH);


        refreshPanel();
    }

    private void deleteRoom() {
        int id = Integer.parseInt(showInputDialog("Digite o ID do quarto a ser deletado:"));
        try {
            Quarto_Service.getInstance().removeRoom(id);
            showMessageDialog("Quarto deletado com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }
    }


    private void mostrarClientes() throws SQLException, ClassNotFoundException {
        panel1.removeAll();
        JTextArea clientesTextArea = createTextArea("Clientes:\n");
        panel1.add(new JScrollPane(clientesTextArea), BorderLayout.CENTER);

        List<Client> clients = Client_Service.getInstance().getUsers();
        clients.forEach(client -> appendClientInfo(clientesTextArea, client));

        JButton deleteClientButton = createButton("Deletar Cliente", e -> deleteClient());
        panel1.add(deleteClientButton, BorderLayout.SOUTH);

        refreshPanel();
    }

    private void deleteClient() {
        int id = Integer.parseInt(showInputDialog("Digite o ID do cliente a ser deletado:"));
        try {
            Client_Service.getInstance().deleteUser(id);
            showMessageDialog("Cliente deletado com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }
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
            var waitList = Waiting_List_Service.getInstance().getListaEspera();
            if (waitList == null) {
                listaEsperaTextArea.append("Nenhum cliente na lista de espera.\n");
            } else {
                for(var entry : waitList.entrySet()) {
                    listaEsperaTextArea.append("ID da Reserva: " + entry.getKey() + "\n");
                    listaEsperaTextArea.append("Email do Cliente: " + entry.getValue() + "\n\n");
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }

        JButton deleteWaitListButton = createButton("Deletar Lista de Espera", e -> deleteWaitList());
        panel1.add(deleteWaitListButton, BorderLayout.SOUTH);

        refreshPanel();
    }

    private void deleteWaitList() {
        try {
            Waiting_List_Service.getInstance().delete(Integer.parseInt(showInputDialog("Digite o ID da reserva a ser cancelada:")));
            showMessageDialog("Lista de espera deletada com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }
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

        JButton cancelEstadiaButton = createButton("Check-OUT", e -> {
            try {
                cancelEstadia();
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        panel1.add(cancelEstadiaButton, BorderLayout.SOUTH);


        refreshPanel();
    }

    private void cancelEstadia() throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(showInputDialog("Digite o ID da estadia a ser cancelada/Check-OUT:"));
        if(Estadia_Service.getInstance().DeleteEstadia(id)){
            showMessageDialog("Estadia removida com sucesso!");
        }else{
            showMessageDialog("Falha ao cancelar estadia");
        }
    }

    private void appendEstadiaInfo(JTextArea textArea, Estadia estadia) {
        textArea.append("ID: " + estadia.getId() + "\n");
        textArea.append("ID do Cliente: " + estadia.getCliente().getPersonId() + "\n");
        textArea.append("ID do Quarto: " + estadia.getQuarto().getRoomId() + "\n");
        textArea.append("Data de Entrada: " + estadia.getDataEntrada() + "\n");
        textArea.append("Data de Saída: " + estadia.getDataSaida() + "\n");
        textArea.append("Quantidade de Pessoas: " + estadia.getPessoas()+ "\n\n");
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

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }

    private JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        return textArea;
    }

    private static void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private static boolean showConfirmDialog(String title, Object[] message) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
    }


    private JMenuItem createMenuItem(String text, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(listener);
        return menuItem;
    }


}
