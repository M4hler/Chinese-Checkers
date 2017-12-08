package gameParts;

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
        public boolean isTriangle; //is player field

        public Field(Point coordinates,boolean isTriangle){
            this.coordinates=coordinates;
            this.pawn=null;
            this.isTriangle=isTriangle;
        }

        public Field(int x, int y, boolean isTriangle) {
            coordinates = new Point(x, y);
            this.pawn=null;
            this.isTriangle=isTriangle;
        }

    public int getX() {
        return coordinates.getX();
    }

    public int getY() {
        return coordinates.getY();
    }
}
