/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.presenter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
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
import javax.swing.BorderFactory;
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
    private Image backGround = null, seta;

    public ChoosePresenter(String nomeA, String nomeB, Mediador mediador) throws IOException {

        this.mediador = mediador;
        Image image = Toolkit.getDefaultToolkit()
                .getImage(Personagem_Enum.cenarios_path + "fundo.jpg"),
                imagePanel = Toolkit.getDefaultToolkit()
                .getImage(Personagem_Enum.cenarios_path + "fundo-panel-a.png"),
                imagePanelB = Toolkit.getDefaultToolkit()
                .getImage(Personagem_Enum.cenarios_path + "fundo-panel-b.png");

        view = new ChooseView(image, imagePanel, imagePanelB);
        view.getPainelA().setBorder(BorderFactory
                .createTitledBorder(BorderFactory.createEmptyBorder(), nomeA.toUpperCase(),
                        TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, view.getCenarioLbl().getFont(), Color.YELLOW));
        view.getPainelB().setBorder(BorderFactory
                .createTitledBorder(BorderFactory.createEmptyBorder(), nomeB.toUpperCase(),
                        TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, view.getCenarioLbl().getFont(), Color.BLUE));

        seta = Toolkit.getDefaultToolkit()
                .getImage(Personagem_Enum.cenarios_path + "seta.gif");

        ImageIcon ico
                = new ImageIcon(Toolkit.getDefaultToolkit()
                        .getImage(Personagem_Enum.cenarios_path + "fundo-jogar.gif")
                        .getScaledInstance(view.getGoBtn().getWidth(),
                                view.getGoBtn().getHeight(), java.awt.Image.SCALE_FAST));

        view.getGoBtn().setIcon(ico);
        view.getGoBtn().setBackground(Color.red);
        view.getGoBtn().setOpaque(false);
        view.getGoBtn().setBorderPainted(false);
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
        view.getPainelDeFundo().setImage(backGround = Toolkit.getDefaultToolkit()
                .getImage((Personagem_Enum.cenarios_path + "cenario-suspense.gif")));

        view.setVisible(true);
    }

    private void chooseCenario() {

        String cenarios[] = new File(Personagem_Enum.cenarios_path).list();
        int cont = 0;
        if (cenarios != null) {
            String cenario;

            while (!taLimpo(cenario = cenarios[(pos++ % cenarios.length)])) {
                if (cont++ > 500) {
                    return;
                }
            }

            if ((cenario.contains(".jpg") || cenario.contains(".png") || cenario.contains(".gif"))) {

                backGround = Toolkit.getDefaultToolkit()
                        .getImage(Personagem_Enum.cenarios_path + cenario);

                if (backGround != null) {
                    view.getPainelDeFundo().setImage(backGround);
                }

            }
        }
    }

    boolean taLimpo(String palavra) {
        return palavra.contains("cenario-");
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
                    Image iconM = img.getScaledInstance(w / 2, h + (h / 5), java.awt.Image.SCALE_FAST);

                    Font font = view.getCenarioLbl().getFont();
                    Font f = new Font(font.getName(), font.getStyle(), 18);

                    JToggleButton button = new JToggleButton(nome) {
                        @Override
                        public void paint(Graphics g) {
                            if (isSelected()) {
                                g.drawImage(seta, 0, 0, null);
                                g.translate(getWidth() / 7, -10);
                                g.drawImage(iconM, 0, 0, null);
                                g.translate(20, 10);
                            } else {
                                g.drawImage(newimg, 0, 0, null);
                            }
                            g.setFont(f);
                            g.drawString(nome.toUpperCase(), 50, getHeight() / 2);
                        }
                    };

                    button.setIcon(new ImageIcon(iconM));

                    if (rectangle == null) {
                        rectangle = new Rectangle(panel.getWidth() / 4, panel.getHeight() / 6, w, h);
                    } else {
                        rectangle = new Rectangle(panel.getWidth() / 4, rectangle.y + rectangle.height + panel.getHeight() / 16, w, h);
                    }
                    button.setBounds(rectangle);
                    addActionButon(button, panel);
                    panel.add(button);

                    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
