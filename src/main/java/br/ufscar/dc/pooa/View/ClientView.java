package br.ufscar.dc.pooa.View;
import br.ufscar.dc.pooa.Model.Reserva;
import br.ufscar.dc.pooa.Model.users.Person;
import br.ufscar.dc.pooa.Service.Client_Service;
import br.ufscar.dc.pooa.Service.Reserva_Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ClientView extends UserView {

    public ClientView(Person user) {
        super("Cliente - Bem-vindo");
        createMenuBar(user);
    }

    @Override
    protected void createMenuBar(Person user) {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem viewRoomsItem = createMenuItem("Informações Sobre Disponibilidade de Quartos", e -> viewQuartos(), "C:\\Users\\enzod\\Desktop\\Hotel-Reservation\\Icons\\quarto_icon.png");
        JMenuItem makeReservationItem = createMenuItem("Fazer Reserva", e -> reservaQuartoUser(user), "C:\\Users\\enzod\\Desktop\\Hotel-Reservation\\Icons\\reserva_icon.png");
        JMenuItem viewReservationsItem = createMenuItem("Minhas Reservas", e -> viewReservas(user), "C:\\Users\\enzod\\Desktop\\Hotel-Reservation\\Icons\\icon_hotel2.png");
        JMenuItem exitItem = createMenuItem("Sair", e -> super.logout(), "C:\\Users\\enzod\\Desktop\\Hotel-Reservation\\Icons\\sair_icon.png");
        JMenuItem olhardados = createMenuItem("Olhar Meus Dados", e -> olharDados(user), "C:\\Users\\enzod\\Desktop\\Hotel-Reservation\\Icons\\dados_icon.png");

        optionsMenu.add(viewRoomsItem);
        optionsMenu.add(makeReservationItem);
        optionsMenu.add(viewReservationsItem);
        optionsMenu.add(exitItem);
        optionsMenu.add(olhardados);
        menuBar.add(optionsMenu);

        frame.setJMenuBar(menuBar);
    }

    private void olharDados(Person user) {
        panel1.removeAll();
        JTextArea dadosTextArea = createTextArea("Seus Dados:\n");
        panel1.add(new JScrollPane(dadosTextArea), BorderLayout.CENTER);

        dadosTextArea.append("ID: " + user.getPersonId() + "\n");
        dadosTextArea.append("Nome: " + user.getName() + "\n");
        dadosTextArea.append("Senha: " + user.getPassword() + "\n");
        dadosTextArea.append("Email: " + user.getEmail() + "\n");
        if(user.getPhone() != null) {
            dadosTextArea.append("Telefone: " + user.getPhone() + "\n");
        }
        else{
            dadosTextArea.append("Telefone: Não informado\n");
        }
        if(user.getBirthday() != null) {
            dadosTextArea.append("Data de Nascimento: " + user.getBirthday() + "\n\n\n");
        }
        else{
            dadosTextArea.append("Data de Nascimento: Não informada\n\n\n");
        }
        dadosTextArea.append("Caso queria atualizar seus dados, clique no botão abaixo\n" +
                "Se não quiser alterar alguma informação, deixe o campo vazio");



        JButton updateButton = createButton("Atualizar Dados", e -> updateDados(user));
        panel1.add(updateButton, BorderLayout.SOUTH);

        refreshPanel();
    }

    private void updateDados(Person user) {
        panel1.removeAll();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField birthdayField = new JTextField();
        JTextArea  phoneField = new JTextArea();

        Object[] message = {
                "Nome:", nameField,
                "Email:", emailField,
                "Senha:", passwordField,
                "Data de Nascimento dd/MM/yyyy", birthdayField,
                "Telefone:", phoneField
        };

        if (showConfirmDialog("Atualizar Dados(Se não quiser mudar deixar vazio)", message)) {
            processUpdateDados(user, nameField.getText(), emailField.getText(), passwordField.getText(), birthdayField.getText(), phoneField.getText());
        } else {
            showMessageDialog("Falha na atualização dos dados");
        }
    }

    private void processUpdateDados(Person user, String text, String text1, String text2, String text3, String text4) {
        try {
            if (!text.isEmpty()) {
                user.setName(text);
            }
            if (!text1.isEmpty()) {
                user.setEmail(text1);
            }
            if (!text2.isEmpty()) {
                user.setPassword(text2);
            }
            if (!text3.isEmpty()) {
                user.setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse(text3));
            }
            if (!text4.isEmpty()) {
                user.setPhone(text4);
            }
            if(Client_Service.getInstance().updateUser(user)){
                showMessageDialog("Dados atualizados com sucesso!");
            }
            else{
                showMessageDialog("Falha na atualização dos dados");
            }
        } catch (Exception ex) {
            showErrorDialog(ex);
        }
    }



    private void viewQuartos() {
        panel1.removeAll();
        JTextArea vagasTextArea = createTextArea("Caso tenha Quartos disponiveis, segue informações:\n" +
                "Quartos do tipo Familia por 120 reais a diaria, do tipo Single por 70 reais e do tipo Suite por 200 reais a diaria\n"+
                "Check-in: 14:00\n"+
                "Check-out: 12:00\n\n"+
                "A reserva pode ser feita via aba de Opções -> Fazer Reserva\n\n" +
                "Ou na recepção do Hotel.\n\n"+
                "Obrigado por escolher o nosso Hotel!");
        panel1.add(new JScrollPane(vagasTextArea), BorderLayout.CENTER);

        try {
            viewQuartoSemLogin();
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }

        refreshPanel();
    }




    private void reservaQuartoUser(Person user) {
        panel1.removeAll();
        JTextArea reservaQuartoText = createTextArea("Para reservar um quarto, Informe o periodo da reserva e a Categoria do quarto desejado.\n" +
                "Temos como Categoria disponiveis:\n" +
                "Quartos do tipo Familia por 120 reais a diaria, do tipo Single por 70 reais e do tipo Suite por 200 reais a diaria\n" +
                "Quartos Familia tem capacidade de 4 a 6 pessoas, Single de 1 a 2 pessoas e Suite de 2 a 4 pessoas.\n"+
                "Check-in: 14:00\n"+
                "Check-out: 12:00\n\n"+
                "Obrigado por escolher o nosso Hotel!\n"+
                "Caso queira Saber os Tipos de Quartos disponiveis para Hoje vá para -> Opções -> Informações Sobre Disponibilidade de Quartos");
        panel1.add(new JScrollPane(reservaQuartoText), BorderLayout.CENTER);

        JButton showInputFieldsButton = createButton("Reservar", e -> {
            try {
                showReservaDialog(user);
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        panel1.add(showInputFieldsButton, BorderLayout.SOUTH);

        refreshPanel();
    }

    private void showReservaDialog(Person user) throws SQLException, ClassNotFoundException {
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

    private void processReserva(Person user, String dataInicial, String dataEnd, String tipoQuarto) throws SQLException, ClassNotFoundException {
        Reserva_Service reserva_Service = Reserva_Service.getInstance();
        try {
            Date dataInicialDate = reserva_Service.parseDate(dataInicial);
            Date dataEndDate = reserva_Service.parseDate(dataEnd);
            Date dataAtual = java.sql.Date.valueOf(LocalDate.now());


            if (reserva_Service.makeReservation(user, dataAtual, dataInicialDate, dataEndDate, tipoQuarto)) {
                showMessageDialog("Reserva feita com sucesso!");
            } else {
                if(dataAtual.equals(dataInicialDate)){
                    throw new IllegalArgumentException("Infelizmente não temos quartos disponiveis para essa Data :( \n"+
                            "Talvez tentar outro dia ou outro tipo de Quarto.\n"+
                            "Caso queira mais informações, vá a recepção do Hotel.");
                }
                showMessageDialog("Falha na reserva do quarto\n" + "Não temos quartos disponiveis do tipo " + tipoQuarto + " para o periodo informado");
                super.observerService(user, reserva_Service.get_Ids(dataInicialDate, dataEndDate, tipoQuarto));
            }
        } catch (ParseException | SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }
        catch (IllegalArgumentException ex) {
            showMessageDialog("Erro: " + ex.getMessage());
        }
    }



    private void viewReservas(Person user) {
        panel1.removeAll();
        JTextArea reservasTextArea = createTextArea("Suas Reservas:\n");
        panel1.add(new JScrollPane(reservasTextArea), BorderLayout.CENTER);

        try {
            List<Reserva> reservas = Reserva_Service.getInstance().getReservas(user.getPersonId());
            reservas.forEach(reserva -> super.appendReservaInfo(reservasTextArea, reserva));

            JButton cancelReservaButton = createButton("Cancelar Reserva", e -> cancelReserva());
            panel1.add(cancelReservaButton, BorderLayout.SOUTH);
        } catch (SQLException | ClassNotFoundException ex) {
            showErrorDialog(ex);
        }

        refreshPanel();
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

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private boolean showConfirmDialog(String title, Object[] message) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
    }


}