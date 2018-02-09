package algorithm;

import java.util.ArrayList;

public class Order implements Cloneable{
	public String status,rainfall_probability,url,time,arrival_time,uv,activity;
	public int distance;
	public double point;
	public ArrayList<String> place;
	public Order(String status,String rainfall_probability,String uv,int distance,String arrival_time,double point,String time,ArrayList<String> place) {	
		this.uv=uv;
		this.place=place;
		this.status=status;
		this.rainfall_probability=rainfall_probability;
		this.distance=distance;
		this.time=time.substring(0, 5);
		this.arrival_time=arrival_time.substring(0,5);		
		this.setPoint(point);
	}
	@Override
	 public Object clone() throws CloneNotSupportedException {
	  return super.clone();
	 }
	public double getPoint() {
		return point;
	}
	public void setPoint(double point) {
		this.point = point;
	}
	
	public String getactivity() {
		return activity;
	}
	public void setactivity(String activity) {
		this.activity = activity;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
