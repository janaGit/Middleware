/**
 * Copyright Jana Klemp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package de.klemp.middleware.controller;

public class SimpleStructure implements Structure {
    String[][] structure;

    XY current;

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
        this.current = new XY(x, y);
        this.length = new XY(rows.length, numberColums);
        this.structure = structureArray;
    }

    public String right()
    {
        String value = "";
        if (current.getY() + 1 < length.getY()) {
            if (!structure[current.getX()][current.getY() + 1].equals("-")) {
                current.setY(current.getY() + 1);
                value = structure[current.getX()][current.getY()];
                if (value.equals(structure[current.getX()][current.getY() - 1])) {
                    value = right();
                }
            }
        } else {
            value = "Arrayindex out of bounds";

        }
        return value;

    }

    public String left()
    {
        String value = "";
        if (current.getY() - 1 >= 0) {
            if (!structure[current.getX()][current.getY() - 1].equals("-")) {
                current.setY(current.getY() - 1);
                value = structure[current.getX()][current.getY()];
                if (value.equals(structure[current.getX()][current.getY() + 1])) {
                    value = left();
                }
            }
        } else {
            value = "Arrayindex out of bounds";

        }
        return value;
    }

    public String up()
    {
        String value = "";
        if (current.getX() - 1 >= 0) {
            if (!structure[current.getX() - 1][current.getY()].equals("-")) {
                current.setX(current.getX() - 1);
                value = structure[current.getX()][current.getY()];
                if (value.equals(structure[current.getX() + 1][current.getY()])) {
                    value = up();
                }
            }
        } else {
            value = "Arrayindex out of bounds";

        }
        return value;
    }

    public String down()
    {
        String value = "";
        if (current.getX() + 1 < length.getX()) {
            if (!structure[current.getX() + 1][current.getY()].equals("-")) {
                current.setX(current.getX() + 1);
                value = structure[current.getX()][current.getY()];
                if (value.equals(structure[current.getX() - 1][current.getY()])) {
                    value = down();
                }
            }
        } else {
            value = "Arrayindex out of bounds";

        }
        return value;
    }

    public String getCurrent()
    {
        return structure[current.getX()][current.getY()];
    }

    public String getXY()
    {
        return current.getX() + "," + current.getY();
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
