/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.presenter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javagame.mediador.Mediador;
import javagame.model.Personagem_Enum;
import javagame.view.ChooseView;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author mfernandes
 */
public class ChoosePresenter {

    private String personagem_A, personagem_B;
    private ChooseView view;
    private int pos = 0;
    private final Mediador mediador;
    private final String palavrasInvalidas[] = {"botao", "ajuda", "help", "score", "game", "panel"};
    private Image backGround = null;

    public ChoosePresenter(String nomeA, String nomeB, Mediador mediador) throws IOException {

        this.mediador = mediador;
        view = new ChooseView();
        view.getPainelA().setBorder(BorderFactory
                .createTitledBorder(BorderFactory.createEmptyBorder(), nomeA.toUpperCase(),
                        TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, view.getCenarioLbl().getFont(), Color.YELLOW));
        view.getPainelB().setBorder(BorderFactory
                .createTitledBorder(BorderFactory.createEmptyBorder(), nomeB.toUpperCase(),
                        TitledBorder.RIGHT, TitledBorder.DEFAULT_POSITION, view.getCenarioLbl().getFont(), Color.BLUE));

        popularPainel(view.getPainelA());
        popularPainel(view.getPainelB());

        view.getCenarioLbl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                chooseCenario();
            }

        });

        view.getGoBtn().addActionListener((ActionEvent e) -> {
            try {
                jogar(view.getPainelA(), view.getPainelB());
            } catch (IOException ex) {
                System.err.println("erro: " + ex);
            } catch (InterruptedException ex) {
                System.err.println("erro: " + ex);
            } catch (JavaLayerException ex) {
                System.err.println("erro no player de audio: " + ex);
            }
        });

        view.setTitle("Escolha os personagens e o cenário do jogo");
        view.setState(JFrame.MAXIMIZED_BOTH);
        view.setExtendedState(JFrame.MAXIMIZED_BOTH);
        view.getPainelDeFundo().setImage(ImageIO.read(new File(Personagem_Enum.cenarios_path + "suspense.gif")));
        view.setVisible(true);
    }

    private void chooseCenario() {

        String cenarios[] = new File(Personagem_Enum.cenarios_path).list();

        if (cenarios != null) {
            String cenario = cenarios[(pos++ % cenarios.length)];

            if ((cenario.contains(".jpg") || cenario.contains(".png") || cenario.contains(".gif"))
                    && taLimpo(cenario)) {

                backGround = Toolkit.getDefaultToolkit()
                        .getImage(Personagem_Enum.cenarios_path + cenario);

                if (backGround != null) {
                    view.getPainelDeFundo().setImage(backGround);
                }

            }
        }
    }

    boolean taLimpo(String palavra) {
        for (String invalid : palavrasInvalidas) {
            if (palavra.contains(invalid)) {
                return false;
            }
        }
        return true;
    }

    void popularPainel(JPanel panel) {

        String personagens[] = new File(Personagem_Enum.personagens_path).list();
        Rectangle rectangle = null;
        if (personagens != null) {
            for (String personagen : personagens) {
                if (personagen.contains(".properties")) {

                    int w = panel.getWidth() / 2;
                    int h = panel.getHeight() / 4;

                    String nome = personagen.substring(0, personagen.indexOf(".properties"));
                    ImageIcon iconReal = new ImageIcon(Personagem_Enum.personagens_path + nome + ".gif");
                    Image img = iconReal.getImage();
                    Image newimg = img.getScaledInstance(w / 3, h, java.awt.Image.SCALE_FAST);

                    Image iconM = img.getScaledInstance(w / 2, h, java.awt.Image.SCALE_FAST);
                    ImageIcon icoSe = new ImageIcon(iconM);

                    JToggleButton button = new JToggleButton(nome, new ImageIcon(newimg)) {

                        @Override
                        public Icon getSelectedIcon() {
                            return icoSe;
                        }
                    };
                    if (rectangle == null) {
                        rectangle = new Rectangle(panel.getWidth() / 4, panel.getHeight() / 6, w, h);
                    } else {
                        rectangle = new Rectangle(panel.getWidth() / 4, rectangle.y + rectangle.height + panel.getHeight() / 16, w, h);
                    }
                    button.setBounds(rectangle);
                    addActionButon(button, panel);
                    panel.add(button);
                    button.setBackground(Color.WHITE);
                    /*para setOpaque funcionar*/
                    button.setBorderPainted(false);
                    button.setFont(view.getCenarioLbl().getFont());
                    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    button.setOpaque(false);
                    button.setVisible(true);
                }
            }
            view.pack();
        }
    }

    void addActionButon(JToggleButton button, JPanel pane) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Component component : pane.getComponents()) {

                    if (component instanceof JToggleButton) {
                        JToggleButton btn = (JToggleButton) component;

                        if (btn != button) {
                            btn.setSelected(false);
                        }
                        btn.setVisible(true);
                    }
                }
            }
        });
    }

    void jogar(JPanel paneA, JPanel paneB) throws IOException, InterruptedException, JavaLayerException {

        for (Component component : paneA.getComponents()) {

            if (component instanceof JToggleButton) {
                JToggleButton btn = (JToggleButton) component;
                if (btn.isSelected()) {

                    personagem_A = btn.getText();

                }

            }
        }

        for (Component component : paneB.getComponents()) {

            if (component instanceof JToggleButton) {
                JToggleButton btn = (JToggleButton) component;
                if (btn.isSelected()) {

                    personagem_B = btn.getText();

                }

            }
        }

        if (personagem_A != null && personagem_B != null) {

            mediador.inicializar(personagem_A, personagem_B, backGround);

            view.dispose();
        }

    }

}
