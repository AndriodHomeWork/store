package com.itcast.bean;

public class CartItem {
	/*商品       Product product;
	购买的数量 int count;
	小计	   double subtotal;(不需要set,算出来的)*/

	private Product product;
	private int count;
	private double subTotal;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	// double subtotal;(不需要set,算出来的)
	public double getSubTotal() {
		return count*product.getShop_price();
	}
	
	
	
	
}
