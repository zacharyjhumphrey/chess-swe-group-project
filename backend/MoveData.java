package backend;

import java.io.Serializable;

public class MoveData implements Serializable{
 private int x;
 private int y;
 
 public int getX() {
	 return x;
 }
 
 public int getY() {
	 return y;
 }
 
 public int[] getLocation() {
	 int[] location = {x, y};
	 return location;
 }
 
 public void setX(int xcord) {
	 x = xcord;
 }
 
 public void setY(int ycord) {
	 y = ycord;
 }
 
 public void MoveData(int xcord, int ycord) {
	 x = xcord;
	 y = ycord;
 }
}
