/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.presenter;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javagame.mediador.Mediador;
import javagame.view.ChooseView;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

/**
 *
 * @author mfernandes
 */
public class ChoosePresenter {

    private String personagem_A, personagem_B;
    ChooseView view;
    int pos = 0;
    Mediador mediador;

    public ChoosePresenter(String nomeA, String nomeB, Mediador mediador) {

        this.mediador = mediador;
        view = new ChooseView();
        view.getPainelA().setBorder(BorderFactory.createTitledBorder(nomeA));
        view.getPainelB().setBorder(BorderFactory.createTitledBorder(nomeB));

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
            }
        });

        view.setVisible(true);
        view.invalidate();
        view.repaint();
    }

    private void chooseCenario() {

        String cenarios[] = new File("src/javagame/cenarios/").list();

        if (cenarios != null) {
            String cenario = cenarios[(pos++ % cenarios.length)];

            if (cenario.contains(".jpg") || cenario.contains(".png") || cenario.contains(".gif")) {
                view.getCenarioLbl().setText(cenario);

                if (!cenario.contains(".gif")) {

                    ImageIcon ic = new ImageIcon("src/javagame/cenarios/" + cenario);
                    view.getCenarioLbl().setIcon(ic);
                } else {
                    view.getCenarioLbl().setIcon(null);
                    view.setIconImage(null);
                }
            }
        }
    }

    void popularPainel(JScrollPane panel) {

        String personagens[] = new File("src/javagame/personagens/").list();
        Rectangle rectangle = null;
        if (personagens != null) {
            for (String personagen : personagens) {
                if (personagen.contains(".properties")) {

                    JToggleButton button = new JToggleButton(personagen.substring(0, personagen.indexOf(".properties")));
                    if (rectangle == null) {
                        rectangle = new Rectangle(5, panel.getHeight() / 16, panel.getWidth() - 10, panel.getHeight() / 4);
                    } else {
                        rectangle = new Rectangle(5, rectangle.y + rectangle.height + panel.getHeight() / 16, panel.getWidth() - 10, panel.getHeight() / 4);
                    }
                    button.setBounds(rectangle);
                    addActionButon(button, panel);
                    panel.add(button);
                    button.setVisible(true);
                }
            }
           
        }
    }

    void addActionButon(JToggleButton button, JScrollPane pane
    ) {
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
                        btn.repaint();
                    }
                }

            }
        });
    }

    void jogar(JScrollPane paneA, JScrollPane paneB) throws IOException {

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

            mediador.inicializar(personagem_A, personagem_B, view.getCenarioLbl().getText());

            view.dispose();
        }

    }

}
