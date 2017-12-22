package gameParts;

import server.Player;

import java.awt.*;

public class Field {
    /*
    private Pawn pawn;
    private int coordinates[];  // array or different variables?
    int x,y,z;
    public Field(int x, int y, int z){

        this.x=x;
        this.y=y;
        this.z=z;

        coordinates = new int[3];
        coordinates[0]=x;
        coordinates[1]=y;
        coordinates[2]=z;

        pawn=null;
        */
    //try with using Axial coordinates
        private Pawn pawn;
        private Point coordinates;
        private Color color;

        public Field(Point coordinates, Color color){
            this.coordinates=coordinates;
            this.pawn=null;
            this.color=color;
        }

        public Field(int x, int y, Color color) {
            coordinates = new Point(x, y);
            this.pawn=null;
            this.color=color;
        }

    public int getX() {
        return coordinates.getX();
    }

    public int getY() {
        return coordinates.getY();
    }

    public Color getColor() {
        return color;
    }
}
