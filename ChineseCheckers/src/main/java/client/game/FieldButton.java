package client.game;

import gameParts.Point;

import javax.swing.*;

class FieldButton extends JButton{
    private Point coordinates;

    FieldButton(Point point){
        super();
        coordinates=point;
    }

    public Point getCoordinates() {
        return coordinates;
    }
}
