package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.LinkedList;

import static main.Main.leftPain;

class SelectBox extends Rectangle {
    private Ellipse[] ellipse = new Ellipse[4];
    private Ellipse[] centerEllipse = new Ellipse[4];
    private boolean[] isInsideEllipse = new boolean[4];
    private boolean[] isInsideCenterEllipse = new boolean[4];
    private double maxWidth = 0;
    private double maxHeight = 0;
    private double clickX = 0;
    private double clickY = 0;
    private boolean isInside = false;
    private static final double radius = 6;
    private static final double initPos = -1000;
    private LinkedList<History> histories = new LinkedList<>();
    private int historyIndex = 0;

    SelectBox() {
        super(initPos, initPos, 0, 0);
        histories.add(historyIndex++, new History(getX(), getY(), getWidth(), getHeight()));
        setFill(null);
        setStroke(Color.RED);
        setStrokeWidth(1);

        for (int i = 0; i < ellipse.length; i++) {
            ellipse[i] = new Ellipse(initPos, initPos, radius, radius);
            ellipse[i].setFill(Color.WHITE);
            ellipse[i].setStroke(Color.BLACK);
            ellipse[i].setStrokeWidth(1);
            centerEllipse[i] = new Ellipse(initPos, initPos, radius, radius);
            centerEllipse[i].setFill(Color.WHITE);
            centerEllipse[i].setStroke(Color.BLACK);
            centerEllipse[i].setStrokeWidth(1);
            isInsideEllipse[i] = false;
            isInsideCenterEllipse[i] = false;
        }

        for (int i = 0; i < ellipse.length; i++) {
            int finalI = i;
            ellipse[i].setOnMouseEntered(event -> {
                setIsInsideEllipse(finalI, true, 0);
                setEllipseFill(finalI, Color.AQUA, 0);
            });
            ellipse[i].setOnMouseExited(event -> {
                setIsInsideEllipse(finalI, false, 0);
                setEllipseFill(finalI, Color.WHITE, 0);
            });
            ellipse[i].setOnMousePressed(event -> setPosition(ellipse[3 - finalI].getCenterX(), ellipse[3 - finalI].getCenterY()));
            ellipse[i].setOnMouseDragged(event -> setSize(event.getX(), event.getY()));
            ellipse[i].setOnMouseReleased(event -> print());

            centerEllipse[i].setOnMouseEntered(event -> {
                setIsInsideEllipse(finalI, true, 1);
                setEllipseFill(finalI, Color.AQUA, 1);
            });
            centerEllipse[i].setOnMouseExited(event -> {
                setIsInsideEllipse(finalI, false, 1);
                setEllipseFill(finalI, Color.WHITE, 1);
            });
            centerEllipse[i].setOnMousePressed(event -> setPosition(centerEllipse[(finalI + 2) % 4].getCenterX(), centerEllipse[(finalI + 2) % 4].getCenterY()));
            centerEllipse[i].setOnMouseReleased(event -> print());
            if (finalI % 2 == 0) {
                centerEllipse[i].setOnMouseDragged(event -> setSize(-1, event.getY()));
            } else {
                centerEllipse[i].setOnMouseDragged(event -> setSize(event.getX(), -1));
            }
        }
    }

    void init(double width, double height) {
        maxWidth = width;
        maxHeight = height;
    }

    private void update() {
        for (int i = 0; i < ellipse.length; i++) {
            double x = 0, y = 0;
            double centerX = 0, centerY = 0;
            switch (i) {
                case 0:
                    x = getX();
                    y = getY();
                    centerX = getX() + getWidth() / 2;
                    centerY = getY();
                    break;
                case 1:
                    x = getX() + getWidth();
                    y = getY();
                    centerX = getX() + getWidth();
                    centerY = getY() + getHeight() / 2;
                    break;
                case 2:
                    x = getX();
                    y = getY() + getHeight();
                    centerX = getX() + getWidth() / 2;
                    centerY = getY() + getHeight();
                    break;
                case 3:
                    x = getX() + getWidth();
                    y = getY() + getHeight();
                    centerX = getX();
                    centerY = getY() + getHeight() / 2;
            }
            ellipse[i].setCenterX(x);
            ellipse[i].setCenterY(y);
            centerEllipse[i].setCenterX(centerX);
            centerEllipse[i].setCenterY(centerY);
        }
    }

    private void setPosition(double x, double y) {
        clickX = x;
        clickY = y;
    }

    private void moveBox(double x, double y) {
        double currentX = getX() + (x - clickX);
        double currentY = getY() + (y - clickY);
        if (currentX + getWidth() < maxWidth && currentX > 0) {
            setX(currentX);
            clickX = x;
        } else if (currentX + getWidth() > maxWidth) {
            setX(maxWidth - getWidth());
        } else if (currentX < 0) {
            setX(0);
        }
        if (currentY + getHeight() < maxHeight && currentY > 0) {
            setY(currentY);
            clickY = y;
        } else if (currentY + getHeight() > maxHeight) {
            setY(maxHeight - getHeight());
        } else if (currentY < 0) {
            setY(0);
        }

        update();
    }

    void print() {
        System.out.println("x = " + (int) getX() + ", y = " + (int) getY()
                + ", width = " + (int) getWidth() + ", height = " + (int) getHeight());
        leftPain.setParam(getX(), getY(), getWidth(), getHeight());
    }

    private void resetRect() {
        setX(-20);
        setY(-20);
        setWidth(0);
        setHeight(0);
        update();
    }

    private void setSize(double x, double y) {
        if (x >= 0) {
            setSizeX(x);
        }
        if (y >= 0) {
            setSizeY(y);
        }

        update();
    }

    private void setIsInsideEllipse(int i, boolean bool, int type) {
        if (type == 0) {
            isInsideEllipse[i] = bool;
        } else {
            isInsideCenterEllipse[i] = bool;
        }
    }

    private boolean getIsInsideEllipse() {
        for (boolean flag : isInsideEllipse) {
            if (flag) {
                return true;
            }
        }
        for (boolean flag : isInsideCenterEllipse) {
            if (flag) {
                return true;
            }
        }
        return false;
    }

    private void setEllipseFill(int i, Color color, int type) {
        if (type == 0) {
            ellipse[i].setFill(color);
        } else {
            centerEllipse[i].setFill(color);
        }
    }

    private void setSizeX(double x) {
        if (x > clickX) {
            setX(clickX);
            setWidth(x - getX());
        } else {
            setX(x);
            setWidth(clickX - x);
        }

        if (getX() + getWidth() > maxWidth) {
            setWidth(maxWidth - getX());
        }
        if (getX() < 0) {
            setX(0);
            setWidth(clickX);
        }
    }

    private void setSizeY(double y) {
        if (y > clickY) {
            setY(clickY);
            setHeight(y - getY());
        } else {
            setY(y);
            setHeight(clickY - getY());
        }

        if (getY() + getHeight() > maxHeight) {
            setHeight(maxHeight - getY());
        }
        if (getY() < 0) {
            setY(0);
            setHeight(clickY);
        }
    }

    Shape[] getAll() {
        return new Shape[]{this, ellipse[0], ellipse[1], ellipse[2], ellipse[3],
                centerEllipse[0], centerEllipse[1], centerEllipse[2], centerEllipse[3]};
    }

    void mousePress(double x, double y) {
        setPosition(x, y);
        isInside = x > getX() && x < getX() + getWidth()
                && y > getY() && y < getY() + getHeight();
        if (!isInside) {
            resetRect();
        }
    }

    void mouseDrag(double x, double y) {
        if (!getIsInsideEllipse()) {
            if (isInside) {
                moveBox(x, y);
            } else {
                setSize(x, y);
            }
        }
    }

    void save() {
        if (histories.size() > historyIndex) {
            histories.subList(historyIndex, histories.size()).clear();
        }
        histories.add(historyIndex++, new History(getX(), getY(), getWidth(), getHeight()));
        int HISTORY_SIZE = 16;
        if (historyIndex == HISTORY_SIZE + 1) {
            histories.remove(0);
            historyIndex = HISTORY_SIZE;
        }
    }

    void redo() {
        if (historyIndex == 1) {
            return;
        }
        historyIndex--;
        setX(histories.get(historyIndex - 1).getX());
        setY(histories.get(historyIndex - 1).getY());
        setWidth(histories.get(historyIndex - 1).getWidth());
        setHeight(histories.get(historyIndex - 1).getHeight());
        update();
    }

    void undo() {
        if (historyIndex == histories.size()) {
            return;
        }
        setX(histories.get(historyIndex).getX());
        setY(histories.get(historyIndex).getY());
        setWidth(histories.get(historyIndex).getWidth());
        setHeight(histories.get(historyIndex).getHeight());
        historyIndex++;
        update();
    }
}

class History {
    private double x;
    private double y;
    private double width;
    private double height;

    History(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    double getWidth() {
        return width;
    }

    double getHeight() {
        return height;
    }
}