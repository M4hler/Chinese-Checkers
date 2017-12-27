package client.game;

import gameParts.Point;

import javax.swing.*;

class FieldButton extends JButton{
    Point coordinates;

    FieldButton(Point point){
        super();
        coordinates=point;
    }


}
