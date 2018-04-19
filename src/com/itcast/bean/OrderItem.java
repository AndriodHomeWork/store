package com.itcast.bean;

public class OrderItem {
	private String itemid;
	private int count;
	private double subTotal;
	
	//表示包含哪个商品
	private Product product;
	//表示属于哪个订单
	private Order order;
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "OrderItem [itemid=" + itemid + ", count=" + count + ", subTotal=" + subTotal + ", product=" + product
				+ "]";
	}
	
	
	
	
	
}
