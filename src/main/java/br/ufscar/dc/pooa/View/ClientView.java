package br.ufscar.dc.pooa.View;
import br.ufscar.dc.pooa.Model.domain.Reserva.Reserva;
import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.Service.Quarto_Service;
import br.ufscar.dc.pooa.Service.Reserva_Service;

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

public class ClientView extends UserView {

    private Client user;

    public ClientView(Client user) {
        super("Cliente - Bem-vindo");
        this.user = user;
        createMenuBar(user);
    }

    @Override
    protected void createMenuBar(Client user) {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem viewRoomsItem = createMenuItem("Quartos Disponíveis", e -> viewQuartos(), "path/to/viewRoomsIcon.png");
        JMenuItem makeReservationItem = createMenuItem("Fazer Reserva", e -> reservaQuartoUser(user), "path/to/makeReservationIcon.png");
        JMenuItem viewReservationsItem = createMenuItem("Minhas Reservas", e -> viewReservas(user), "path/to/viewReservationsIcon.png");
        JMenuItem exitItem = createMenuItem("Sair", e -> super.logout(), "path/to/exitIcon.png");

        optionsMenu.add(viewRoomsItem);
        optionsMenu.add(makeReservationItem);
        optionsMenu.add(viewReservationsItem);
        optionsMenu.add(exitItem);
        menuBar.add(optionsMenu);

        frame.setJMenuBar(menuBar);
    }


    private JMenuItem createMenuItem(String text, ActionListener listener, String iconPath) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(listener);
        if (iconPath != null && !iconPath.isEmpty()) {
            menuItem.setIcon(new ImageIcon(iconPath));
        }
        return menuItem;
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
                super.observerService(user, Reserva_Service.getInstance().get_Ids(dataInicialDate, dataEndDate, tipoQuarto));
            }
        } catch (ParseException | SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }
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
            super.showErrorDialog(ex);
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
            super.showErrorDialog(ex);
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

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private boolean showConfirmDialog(String title, Object[] message) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
    }


}