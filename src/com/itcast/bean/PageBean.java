package com.itcast.bean;

import java.util.List;

public class PageBean<T> {
	private List<T> list; //商品的集合
	private int curPage;	//当前页码
	private int totalPage;	//总页数
	private int pageSize;	//一页显示的数量
	private int count;	//总数量
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "PageBean [list=" + list + ", curPage=" + curPage + ", totalPage=" + totalPage + ", pageSize=" + pageSize
				+ ", count=" + count + "]";
	}
	
	
	
}
