import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

class SelectBox {
    private Rectangle rectangle;
    private Ellipse ellipse[];
    private Ellipse centerEllipse[];
    private boolean isInsideEllipse[];
    private boolean isInsideCenterEllipse[];
    private double maxWidth;
    private double maxHeight;
    private double clickX;
    private double clickY;
    private boolean isInside;

    SelectBox() {
        maxWidth = 0;
        maxHeight = 0;
        clickX = 0;
        clickY = 0;
        isInside = false;

        double radius = 3;
        double init = -radius * 2;
        rectangle = new Rectangle(init, init, 0, 0);
        rectangle.setFill(null);
        rectangle.setStroke(Color.RED);
        rectangle.setStrokeWidth(1);

        ellipse = new Ellipse[4];
        isInsideEllipse = new boolean[4];
        centerEllipse = new Ellipse[4];
        isInsideCenterEllipse = new boolean[4];
        for (int i = 0; i < ellipse.length; i++) {
            ellipse[i] = new Ellipse(init, init, radius, radius);
            ellipse[i].setFill(Color.WHITE);
            ellipse[i].setStroke(Color.BLACK);
            ellipse[i].setStrokeWidth(1);
            centerEllipse[i] = new Ellipse(init, init, radius, radius);
            centerEllipse[i].setFill(Color.WHITE);
            centerEllipse[i].setStroke(Color.BLACK);
            centerEllipse[i].setStrokeWidth(1);
            isInsideEllipse[i] = false;
            isInsideCenterEllipse[i] = false;
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
                    x = rectangle.getX();
                    y = rectangle.getY();
                    centerX = rectangle.getX() + rectangle.getWidth() / 2;
                    centerY = rectangle.getY();
                    break;
                case 1:
                    x = rectangle.getX() + rectangle.getWidth();
                    y = rectangle.getY();
                    centerX = rectangle.getX() + rectangle.getWidth();
                    centerY = rectangle.getY() + rectangle.getHeight() / 2;
                    break;
                case 2:
                    x = rectangle.getX();
                    y = rectangle.getY() + rectangle.getHeight();
                    centerX = rectangle.getX() + rectangle.getWidth() / 2;
                    centerY = rectangle.getY() + rectangle.getHeight();
                    break;
                case 3:
                    x = rectangle.getX() + rectangle.getWidth();
                    y = rectangle.getY() + rectangle.getHeight();
                    centerX = rectangle.getX();
                    centerY = rectangle.getY() + rectangle.getHeight() / 2;
            }
            ellipse[i].setCenterX(x);
            ellipse[i].setCenterY(y);
            centerEllipse[i].setCenterX(centerX);
            centerEllipse[i].setCenterY(centerY);
        }
    }

    Rectangle getRectangle() {
        return rectangle;
    }

    Ellipse[] getEllipse() {
        return ellipse;
    }

    void setInside(double x, double y) {
        isInside = x > rectangle.getX() && x < rectangle.getX() + rectangle.getWidth() && y > rectangle.getY() && y < rectangle.getY() + rectangle.getHeight();
    }

    boolean isInside() {
        return isInside;
    }

    void setPosition(double x, double y) {
        clickX = x;
        clickY = y;
    }

    void moveBox(double x, double y) {
        double currentX = rectangle.getX() + (x - clickX);
        double currentY = rectangle.getY() + (y - clickY);
        if (currentX + rectangle.getWidth() < maxWidth && currentX > 0) {
            rectangle.setX(currentX);
            clickX = x;
        } else if (currentX + rectangle.getWidth() > maxWidth) {
            rectangle.setX(maxWidth - rectangle.getWidth());
        } else if (currentX < 0) {
            rectangle.setX(0);
        }
        if (currentY + rectangle.getHeight() < maxHeight && currentY > 0) {
            rectangle.setY(currentY);
            clickY = y;
        } else if (currentY + rectangle.getHeight() > maxHeight) {
            rectangle.setY(maxHeight - rectangle.getHeight());
        } else if (currentY < 0) {
            rectangle.setY(0);
        }

        update();
    }

    void print() {
        System.out.println("x = " + (int) rectangle.getX() +
                ", y = " + (int) rectangle.getY() + ", width = " + (int) rectangle.getWidth() +
                ", height = " + (int) rectangle.getHeight());
    }

    void resetRect() {
        rectangle.setX(-20);
        rectangle.setY(-20);
        rectangle.setWidth(0);
        rectangle.setHeight(0);
        update();
    }

    void setSize(double x, double y) {
        if (x >= 0) {
            setSizeX(x);
        }
        if (y >= 0) {
            setSizeY(y);
        }

        update();
    }

    void setIsInsideEllipse(int i, boolean bool, int type) {
        if (type == 0) {
            isInsideEllipse[i] = bool;
        } else {
            isInsideCenterEllipse[i] = bool;
        }
    }

    boolean getIsInsideEllipse() {
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

    void setEllipseFill(int i, Color color, int type) {
        if (type == 0) {
            ellipse[i].setFill(color);
        } else {
            centerEllipse[i].setFill(color);
        }
    }

    Ellipse[] getCenterEllipse() {
        return centerEllipse;
    }

    private void setSizeX(double x) {
        if (x > clickX) {
            rectangle.setX(clickX);
            rectangle.setWidth(x - rectangle.getX());
        } else {
            rectangle.setX(x);
            rectangle.setWidth(clickX - x);
        }

        if (rectangle.getX() + rectangle.getWidth() > maxWidth) {
            rectangle.setWidth(maxWidth - rectangle.getX());
        }
        if (rectangle.getX() < 0) {
            rectangle.setX(0);
            rectangle.setWidth(clickX);
        }
    }

    private void setSizeY(double y) {
        if (y > clickY) {
            rectangle.setY(clickY);
            rectangle.setHeight(y - rectangle.getY());
        } else {
            rectangle.setY(y);
            rectangle.setHeight(clickY - rectangle.getY());
        }

        if (rectangle.getY() + rectangle.getHeight() > maxHeight) {
            rectangle.setHeight(maxHeight - rectangle.getY());
        }
        if (rectangle.getY() < 0) {
            rectangle.setY(0);
            rectangle.setHeight(clickY);
        }
    }
}
