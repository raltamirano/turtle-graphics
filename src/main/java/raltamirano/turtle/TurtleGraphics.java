package raltamirano.turtle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static raltamirano.turtle.Command.FORWARD;

public class TurtleGraphics extends JComponent {
    public static final String LAST_COMMAND = "lastCommand";
    private final List<Instruction> instructions = new ArrayList<>();

    private Point2D position;
    private int angle;
    private boolean penDown = true;
    private boolean showTurtle = true;

    public TurtleGraphics() {
        setSize(new Dimension(WIDTH, HEIGHT));
    }

    public TurtleGraphics command(final Command command) {
        return command(Instruction.of(command));
    }


    public TurtleGraphics command(final Command command, final List<String> args) {
        return command(Instruction.of(command, args));
    }

    public TurtleGraphics command(final Command command, final int value) {
        return command(Instruction.of(command, value));
    }

    public TurtleGraphics command(final Instruction instruction) {
        instructions.add(instruction);
        repaint();
        return this;
    }

    public void reset() {
        instructions.clear();
        repaint();
    }

    @Override
    public void paint(final Graphics g) {
        final Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        graphics2D.setColor(Color.GREEN);

        final int lastClearScreen = instructions.lastIndexOf(Instruction.CLEARSCREEN);
        for(int i=0; i<=lastClearScreen; i++)
            instructions.remove(0);

         position = Point2D.HOME;
         angle = 90;
         penDown = true;

        for(Instruction instruction : instructions) {
            switch (instruction.command()) {
                case SHOW:
                    showTurtle = true;
                    break;
                case HIDE:
                    showTurtle = false;
                    break;
                case FORWARD:
                case BACK:
                    final int length = Math.abs(Integer.valueOf(instruction.args().get(0)));
                    final Point2D fwPoint = positionForLength(instruction.command() == FORWARD ? length : -length);
                    if (penDown) drawTo(graphics2D, fwPoint);
                    position = fwPoint;
                    break;
                case LEFT:
                    incrementAngle(Math.abs(Integer.valueOf(instruction.args().get(0))));
                    break;
                case RIGHT:
                    decrementAngle(Math.abs(Integer.valueOf(instruction.args().get(0))));
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

            firePropertyChange(LAST_COMMAND, null, instruction);
        }

        if (showTurtle) drawTurtle(graphics2D);
    }

    private void drawTo(final Graphics2D target, final Point2D to) {
        final Point awtPoint = position.awtPoint(getSize());
        final Point awtNewPoint = to.awtPoint(getSize());

        target.drawLine((int) awtPoint.getX(), (int) awtPoint.getY(),
                (int) awtNewPoint.getX(), (int) awtNewPoint.getY());
    }

    private void drawTurtle(final Graphics2D target) {
        final int BASE = 5;

        final Point2D pointer = positionForLength(angle, BASE * 3);
        final Point2D side1 = positionForLength(angle + 90, BASE);
        final Point2D side2 = positionForLength(angle - 90, BASE);

        final int[] x = {
                (int) pointer.awtPoint(getSize()).getX(),
                (int) side1.awtPoint(getSize()).getX(),
                (int) side2.awtPoint(getSize()).getX()
        };
        final int[] y = {
                (int) pointer.awtPoint(getSize()).getY(),
                (int) side1.awtPoint(getSize()).getY(),
                (int) side2.awtPoint(getSize()).getY()
        };
        target.fillPolygon(x, y, 3);
    }

    private void incrementAngle(final int delta) {
        angle = (angle + delta) % 360;
    }

    private void decrementAngle(final int delta) {
        angle = (angle - delta) % 360;
    }

    private Point2D positionForLength(final int length) {
       return positionForLength(angle, length);
    }

    private Point2D positionForLength(final int angle, final int length) {
        double newX = position.x() + (length * Math.cos(Math.toRadians(angle)));
        double newY = position.y() + (length * Math.sin(Math.toRadians(angle)));
        return Point2D.of(newX, newY);
    }
}
