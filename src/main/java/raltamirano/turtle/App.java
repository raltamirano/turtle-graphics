package raltamirano.turtle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static raltamirano.turtle.Command.BYE;
import static raltamirano.turtle.TurtleGraphics.LAST_COMMAND;


public class App {
    private static TurtleGraphics turtleGraphics;
    private static JTextField textField;

    public static void main(String[] args)  {
        final JFrame app = new JFrame("Turtle Graphics");
        app.setExtendedState(JFrame.MAXIMIZED_BOTH);
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);
        app.getContentPane().setLayout(new BorderLayout());

        turtleGraphics = new TurtleGraphics();
        turtleGraphics.addPropertyChangeListener(LAST_COMMAND,
                e -> { if (e.getNewValue().equals(BYE)) System.exit(0); });
        textField = new JTextField();
        textField.addActionListener(App::textFieldKeyListener);

        app.getContentPane().add(turtleGraphics, BorderLayout.CENTER);
        app.getContentPane().add(textField, BorderLayout.SOUTH);
        app.setVisible(true);
    }

    public static void textFieldKeyListener(final ActionEvent e) {
        try {
            for(Command command : Parser.parse(e.getActionCommand()))
                turtleGraphics.command(command);
        } catch (final Exception ex) {
            System.err.println(ex);
        } finally {
            textField.setText("");
        }
    }
}
