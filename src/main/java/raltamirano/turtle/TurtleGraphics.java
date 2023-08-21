package raltamirano.turtle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static raltamirano.turtle.Instruction.FORWARD;

public class TurtleGraphics extends JComponent {
    public static final String LAST_COMMAND = "lastCommand";
    private final List<Command> commands = new ArrayList<>();

    private Point2D position;
    private int angle;
    private boolean penDown = true;

    public TurtleGraphics() {
        setSize(new Dimension(WIDTH, HEIGHT));
    }

    public TurtleGraphics command(final Instruction instruction) {
        return command(Command.of(instruction));
    }


    public TurtleGraphics command(final Instruction instruction, final List<String> args) {
        return command(Command.of(instruction, args));
    }

    public TurtleGraphics command(final Instruction instruction, final int value) {
        return command(Command.of(instruction, value));
    }

    public TurtleGraphics command(final Command command) {
        commands.add(command);
        repaint();
        return this;
    }

    public void reset() {
        commands.clear();
        repaint();
    }

    @Override
    public void paint(final Graphics g) {
        final Graphics2D graphics2D = (Graphics2D) g;

        final int lastClearScreen = commands.lastIndexOf(Command.CLEARSCREEN);
        for(int i=0; i<=lastClearScreen; i++)
            commands.remove(0);

         position = Point2D.HOME;
         angle = 90;
         penDown = true;

        for(Command command : commands) {
            switch (command.instruction()) {
                case FORWARD:
                case BACK:
                    final int length = Math.abs(Integer.valueOf(command.args().get(0)));
                    final Point2D fwPoint = positionForLength(command.instruction() == FORWARD ? length : -length);
                    if (penDown) drawTo(graphics2D, fwPoint);
                    position = fwPoint;
                    break;
                case LEFT:
                    incrementAngle(Math.abs(Integer.valueOf(command.args().get(0))));
                    break;
                case RIGHT:
                    decrementAngle(Math.abs(Integer.valueOf(command.args().get(0))));
                    break;
                case CLEARSCREEN:
                    // already handled
                    break;
                case HOME:
                    if (penDown) drawTo(graphics2D, Point2D.HOME);
                    position = Point2D.HOME;
                    break;
                case PENDOWN:
                    penDown = true;
                    break;
                case PENUP:
                    penDown = false;
                    break;
            }

            firePropertyChange(LAST_COMMAND, null, command);
        }
    }

    private void drawTo(final Graphics2D target, final Point2D to) {
        final Point awtPoint = position.awtPoint(getSize());
        final Point awtNewPoint = to.awtPoint(getSize());

        target.drawLine((int) awtPoint.getX(), (int) awtPoint.getY(),
                (int) awtNewPoint.getX(), (int) awtNewPoint.getY());
    }

    private void incrementAngle(final int delta) {
        angle = (angle + delta) % 360;
    }

    private void decrementAngle(final int delta) {
        angle = (angle - delta) % 360;
    }

    private Point2D positionForLength(final int length) {
        double newX = position.x() + (length * Math.cos(Math.toRadians(angle)));
        double newY = position.y() + (length * Math.sin(Math.toRadians(angle)));
        final Point2D newPoint = Point2D.of(newX, newY);
        return newPoint;
    }
}
