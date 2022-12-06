package backend;

import java.io.Serializable;

public class MoveData implements Serializable{
 private int x;
 private int y;
 //getting y position
 public int getX() {
	 return x;
 }
 //getting x position
 public int getY() {
	 return y;
 }
 //getting x and y
 public int[] getLocation() {
	 int[] location = {x, y};
	 return location;
 }
 //setting x value
 public void setX(int xcord) {
	 x = xcord;
 }
 //setting y value
 public void setY(int ycord) {
	 y = ycord;
 }
 //setting move data to x and y cords
 public void MoveData(int xcord, int ycord) {
	 x = xcord;
	 y = ycord;
 }
}
