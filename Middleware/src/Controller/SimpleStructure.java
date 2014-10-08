package Controller;

public class SimpleStructure implements Structure {
    String[][] structure;

    XY aktuell;

    XY length;

    SimpleStructure(int x, int y, String structureString) {
        String[] rows = structureString.split("!");
        String[] colums = rows[0].split(",");
        int numberColums = colums.length;
        String[][] structureArray = new String[rows.length][numberColums];
        for (int i = 0; i < rows.length; i++) {
            String[] cells = rows[i].split(",");
            for (int j = 0; j < cells.length; j++) {
                structureArray[i][j] = cells[j];
            }
        }
        this.aktuell = new XY(x, y);
        this.length = new XY(rows.length, numberColums);
        this.structure = structureArray;
    }

    public String rechts()
    {
        String value = "";
        if (aktuell.getY() + 1 < length.getY()) {
            if (!structure[aktuell.getX()][aktuell.getY() + 1].equals("-")) {
                aktuell.setY(aktuell.getY() + 1);
                value = structure[aktuell.getX()][aktuell.getY()];
                if (value.equals(structure[aktuell.getX()][aktuell.getY() - 1])) {
                    value = rechts();
                }
            }
        } else {
            value = "Arrayindex out of bounds";

        }
        return value;

    }

    public String links()
    {
        String value = "";
        if (aktuell.getY() - 1 >= 0) {
            if (!structure[aktuell.getX()][aktuell.getY() - 1].equals("-")) {
                aktuell.setY(aktuell.getY() - 1);
                value = structure[aktuell.getX()][aktuell.getY()];
                if (value.equals(structure[aktuell.getX()][aktuell.getY() + 1])) {
                    value = links();
                }
            }
        } else {
            value = "Arrayindex out of bounds";

        }
        return value;
    }

    public String oben()
    {
        String value = "";
        if (aktuell.getX() - 1 >= 0) {
            if (!structure[aktuell.getX() - 1][aktuell.getY()].equals("-")) {
                aktuell.setX(aktuell.getX() - 1);
                value = structure[aktuell.getX()][aktuell.getY()];
                if (value.equals(structure[aktuell.getX() + 1][aktuell.getY()])) {
                    value = oben();
                }
            }
        } else {
            value = "Arrayindex out of bounds";

        }
        return value;
    }

    public String unten()
    {
        String value = "";
        if (aktuell.getX() + 1 < length.getX()) {
            if (!structure[aktuell.getX() + 1][aktuell.getY()].equals("-")) {
                aktuell.setX(aktuell.getX() + 1);
                value = structure[aktuell.getX()][aktuell.getY()];
                if (value.equals(structure[aktuell.getX() - 1][aktuell.getY()])) {
                    value = unten();
                }
            }
        } else {
            value = "Arrayindex out of bounds";

        }
        return value;
    }

    public String getActual()
    {
        return structure[aktuell.getX()][aktuell.getY()];
    }

    public String getXY()
    {
        return aktuell.getX() + "," + aktuell.getY();
    }

    class XY {
        private int x;

        private int y;

        XY(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX()
        {
            return x;
        }

        public void setX(int x)
        {
            this.x = x;
        }

        public int getY()
        {
            return y;
        }

        public void setY(int y)
        {
            this.y = y;
        }
    }
}
