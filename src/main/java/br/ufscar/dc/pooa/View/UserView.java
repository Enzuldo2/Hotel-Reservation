package br.ufscar.dc.pooa.View;

import br.ufscar.dc.pooa.Model.domain.users.Client;
import br.ufscar.dc.pooa.Service.Client_Service;
import br.ufscar.dc.pooa.Service.Waiting_List_Service;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
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



    void showErrorDialog(Exception ex) {
        JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }

    void logout() {
        showMessageDialog("Logout successful!");
        frame.dispose();
        new MainApp();
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // Método abstrato que deve ser implementado nas subclasses
    protected abstract void createMenuBar(Client user);
}
