package br.ufscar.dc.pooa.View;

import br.ufscar.dc.pooa.Model.domain.Reserva.Reserva;
import br.ufscar.dc.pooa.Model.domain.rooms.DefaultRoom;
import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.Service.Client_Service;
import br.ufscar.dc.pooa.Service.Quarto_Service;
import br.ufscar.dc.pooa.Service.Reserva_Service;
import br.ufscar.dc.pooa.Service.Waiting_List_Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public abstract class UserView {
    protected JFrame frame;
    protected JPanel panel1;

    public UserView(String title) {
        initializeUI(title);
    }



    private void initializeUI(String title) {
        frame = createFrame(title, 800, 600);
        panel1 = new JPanel(new BorderLayout());

        frame.setIconImage(new ImageIcon("C:\\Users\\enzod\\Desktop\\icon_hotel2.png").getImage());

        // Adiciona uma mensagem de boas-vindas
        JLabel welcomeLabel = createWelcomeLabel("C:\\Users\\enzod\\Desktop\\imagem_de_inicio.jpeg", "Bem-vindo ao Nosso Hotel!");
        panel1.add(welcomeLabel, BorderLayout.CENTER);

        frame.setContentPane(panel1);
        frame.setVisible(true);
    }

    protected JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(width, height));
        return frame;
    }

    protected JLabel createWelcomeLabel(String imagePath, String welcomeMessage) {
        return getjLabel(imagePath, welcomeMessage);
    }

    static JLabel getjLabel(String imagePath, String welcomeMessage) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel label = new JLabel(welcomeMessage, imageIcon, JLabel.CENTER);

        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);

        label.setFont(new Font("Serif", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(new Color(0, 0, 0, 100));

        return label;
    }

    JMenuItem createMenuItem(String text, ActionListener listener, String iconPath) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(listener);
        if (iconPath != null && !iconPath.isEmpty()) {
            menuItem.setIcon(new ImageIcon(iconPath));
        }
        return menuItem;
    }

    public static void viewQuartoSemLogin() throws SQLException, ClassNotFoundException {
        Date dataAtual = java.sql.Date.valueOf(LocalDate.now());
        Date dataFim = java.sql.Date.valueOf(LocalDate.now().plusDays(1));
        JTextArea textArea = new JTextArea();
        List<DefaultRoom> rooms = Quarto_Service.getInstance().getRooms();
        int tipos = 0;
        if(Reserva_Service.getInstance().verifica_Reserva(dataAtual, dataAtual, dataFim, "Single")) {
            for (DefaultRoom room : rooms) {
                if (room.getBridgeroom().getRoomType().equals("Single") && !room.isReserved()) {
                    textArea.append("Temos Quarto Single disponivel para hoje!\n");
                    tipos++;
                    break;
                }
            }

        }
        if(Reserva_Service.getInstance().verifica_Reserva(dataAtual, dataAtual, dataFim, "Familia")) {
            for (DefaultRoom room : rooms) {
                if (room.getBridgeroom().getRoomType().equals("Familia") && !room.isReserved()) {
                    textArea.append("Temos Quarto Familia disponivel para hoje!\n");
                    tipos++;
                    break;
                }
            }
        }
        if(Reserva_Service.getInstance().verifica_Reserva(dataAtual, dataAtual, dataFim, "Suite")) {
            for (DefaultRoom room : rooms) {
                if (room.getBridgeroom().getRoomType().equals("Suite") && !room.isReserved()) {
                    textArea.append("Temos Quarto Suite disponivel para hoje!\n");
                    tipos++;
                    break;
                }
            }
        }
        if(tipos == 0){
            textArea.append("Não temos quartos disponiveis para hoje!\n");
        }

        textArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Quartos Disponíveis", JOptionPane.INFORMATION_MESSAGE);
    }

    void appendRoomInfo(JTextArea textArea, DefaultRoom room) {
        textArea.append("ID: " + room.getId() + "\n");
        textArea.append("Tipo: " + room.getBridgeroom().getRoomType() + "\n");
        textArea.append("Descrição: " + room.getDescription() + "\n");
        textArea.append("Comprimento: " + room.getLength() + "\n");
        textArea.append("Largura: " + room.getWidth() + "\n");
        textArea.append("Altura: " + room.getHeight() + "\n\n");
    }

    void appendReservaInfo(JTextArea textArea, Reserva reserva) {
        textArea.append("ID: " + reserva.getId() + "\n");
        textArea.append("ID Cliente: " + reserva.getCliente().getPersonId() + "\n");
        textArea.append("Data de Entrada: " + reserva.getDataEntrada() + "\n");
        textArea.append("Data de Saida: " + reserva.getDataSaida() + "\n");
        textArea.append("Categoria: " + reserva.getCategoria().getRoomType() + "\n");
        textArea.append("Reservado: " + reserva.getReserved() + "\n\n");
    }

    public void showCreateAccountDialog() throws SQLException, ClassNotFoundException, ParseException {
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

    private static boolean showConfirmDialog(String title, Object[] message) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
    }

    public void processAccountCreation(JTextField usernameField, JPasswordField passwordField, JPasswordField confirmPasswordField, JTextField emailField, JTextField birthdayField) {
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

    protected void refreshPanel() {
        panel1.revalidate();
        panel1.repaint();
    }

    void observerService(Client user, List<Integer> ids) {
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




    public void showErrorDialog(Exception ex) {
        JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }

    void logout() {
        showMessageDialog("Logout successful!");
        frame.dispose();
        new MainApp();
    }

    private static void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // Método abstrato que deve ser implementado nas subclasses
    protected abstract void createMenuBar(Client user);
}
